/**
 * GenericServiceImpl.java
 *
 * @copyright  Copyright © 2019 VNext software
 * @author quannl
 * @package vn.com.vnext.demo_mvc.util.service.impl
 * @version 1.0.0
 */
package vn.com.vnext.demo_mvc.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.OptimisticLockException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import vn.com.vnext.demo_mvc.dao.entity.base.LockAuditableEntity;
import vn.com.vnext.demo_mvc.dao.entity.base.StatusEntity;
import vn.com.vnext.demo_mvc.dao.entity.tracker.EntityUpdateTracker;
import vn.com.vnext.demo_mvc.service.GenericService;
import vn.com.vnext.demo_mvc.util.exception.DemoMvcServiceException;
import vn.com.vnext.demo_mvc.util.exception.UnModifiableException;
import vn.com.vnext.demo_mvc.util.util.DemoMvcUtils;

/**
 * GenericServiceImpl
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.util.service.impl
 */
@Component
public abstract class GenericServiceImpl<T extends LockAuditableEntity<I>, I extends Serializable> implements
    GenericService<T, I> {

    private Logger logger = LogManager.getLogger(GenericServiceImpl.class);

    @Autowired
    private EntityUpdateTracker entityUpdateTracker;

    @Override
    public T get(I id) throws DemoMvcServiceException {
        T result = null;
        try {
            CrudRepository<T, I> respository = getDao();
            if (respository != null) {
                result = respository.findById(id).orElse(null);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            result = null;
        }

        return result;
    }

    @Override
    public List<T> get(List<I> ids) throws DemoMvcServiceException {
        List<T> result = new ArrayList<>();
        try {
            CrudRepository<T, I> respository = getDao();
            if (respository != null) {
                Iterable<T> tmp = respository.findAllById(ids);
                tmp.forEach(result::add);
            }
        } catch (Exception ex) {
            throw new DemoMvcServiceException(ex.getMessage(), ex);
        }

        return result;
    }

    @Override
    public T getActivated(I id) throws DemoMvcServiceException {
        T result = this.get(id);
        if (((StatusEntity) result).isActivated()) {
            // Save the tracker information into the tracker holder
            entityUpdateTracker.track(result.getClass(), result.getId(), result.getUpdateCount());
            return result;
        }
        return null;
    }

    @Override
    public List<T> getAll() throws DemoMvcServiceException {
        List<T> result = new LinkedList<T>();
        Iterable<T> iter = getDao().findAll();
        iter.forEach(result::add);
        return result;
    }

    @Override
    public List<T> getAllActivated() throws DemoMvcServiceException {
        List<T> result = new LinkedList<T>();
        CrudRepository<T, I> dao = this.getDao();
        Iterable<T> iter = dao.findAll();
        iter.forEach(t -> {
            if (((StatusEntity) t).isActivated()) {
                result.add(t);
            }
        });

        return result;
    }

    @Override
    public T delete(I id) throws DemoMvcServiceException {
        return null;
    }

    @Override
    public T delete(T entity) throws UnModifiableException, DemoMvcServiceException {
        return null;
    }

    @Override
    public boolean checkModifiable(T entity, int currentUpdateCount) throws DemoMvcServiceException {
        if (entity == null) {
            return false;
        }

        return entity.getUpdateCount() == currentUpdateCount;
    }

    @Override
    public boolean checkModifiable(I id, int currentUpdateCount) throws DemoMvcServiceException {
        T entity = null;
        try {
            entity = this.get(id);
        } catch (Exception ex) {
            throw new DemoMvcServiceException(ex.getMessage(), ex);
        }

        return this.checkModifiable(entity, currentUpdateCount);
    }

    @Override
    public T create(T entity) throws DemoMvcServiceException {
        T result = null;

        try {
            I id = entity.getId();
            if (id != null && this.getDao().existsById(id)) {
                throw new DemoMvcServiceException("Create failure !!! Entity existed: " + id);
            }
            result = this.getDao().save(entity);
        } catch (Exception ex) {
            throw new DemoMvcServiceException(ex.getMessage(), ex);
        }

        return result;
    }

    @Override
    public List<T> bulkSave(List<T> entities) throws DemoMvcServiceException {
        List<T> results = new ArrayList<>();

        try {
            Iterable<T> temp = this.getDao().saveAll(entities);
            temp.forEach(results::add);
        } catch (Exception ex) {
            throw new DemoMvcServiceException(ex.getMessage(), ex);
        }
        return results;
    }

    @Override
    public T update(T entity) throws DemoMvcServiceException {
        T result = null;
        try {
            T current = this.isUpdateAble(entity);

            entity.setUpdateCount(current.getUpdateCount() + 1);
            entity.setCreatedDate(current.getCreatedDate());
            entity.setCreateUserId(current.getCreateUserId());
            result = this.getDao().save(entity);
            entityUpdateTracker.unTrack(entity.getClass(), entity.getId());
        } catch (DemoMvcServiceException | UnModifiableException | OptimisticLockException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new DemoMvcServiceException(ex.getMessage(), ex);
        }

        return result;
    }

    @Override
    public T updatePartial(T entity) throws DemoMvcServiceException {
        T result = null;
        try {
            T current = this.isUpdateAble(entity);

            int updateCount = current.getUpdateCount() + 1;
            DemoMvcUtils.getInstance().copyNonNullProperties(entity, current);
            current.setUpdateCount(updateCount);

            result = this.getDao().save(current);
        } catch (OptimisticLockException ex) {
            throw new DemoMvcServiceException(ex.getMessage(), ex);
        }
        return result;
    }

    private T isUpdateAble(T entity) {
        int currentUpdateCount = entityUpdateTracker.retrieve(entity.getClass(), entity.getId());
        I id = entity.getId();
        T current = this.get(id);
        if (current == null) {
            throw new DemoMvcServiceException("Update failure !!! Entity is not existed: " + id);
        }

        if (!this.checkModifiable(current, currentUpdateCount)) {
            throw new UnModifiableException("テーブル更新時に競合が発生しました、再検索してください。", entity, entity.getUpdateCount(),
                current.getUpdateCount(), current.getUpdateUserId());
        }
        return current;
    }

    @Override
    public T saveOrUpdate(T entity) throws DemoMvcServiceException {
        return null;
    }
}
