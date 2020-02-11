/**
 * GenericService.java
 *
 * @copyright  Copyright © 2019 VNext software
 * @author quannl
 * @package vn.com.vnext.demo_mvc.service
 * @version 1.0.0
 */
package vn.com.vnext.demo_mvc.service;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import vn.com.vnext.demo_mvc.dao.entity.base.LockAuditableEntity;
import vn.com.vnext.demo_mvc.util.exception.DemoMvcServiceException;
import vn.com.vnext.demo_mvc.util.exception.UnModifiableException;

/**
 * GenericService
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.service
 */
public interface GenericService<T extends LockAuditableEntity<I>, I extends Serializable> {

    /**
     * sub-class must implement this method to get exactly {@link CrudRepository} instance for specific DAO.<br/>
     * Sample:<br/>
     * <code>
     * &commat;Service
     * public class UserServiceImpl extends GenericService&lt;User, Long&gt; implements UserService {
     *     &commat;Autowired
     *     private UserDao userDao;
     *
     *     public CrudRepository&lt;User, Long&gt; getDao() {
     *         return this.userDao;
     *     }
     * }
     * </code>
     * @return {@link CrudRepository} instance.
     */
    CrudRepository<T, I> getDao();

    /**
     * Get entity by given entity id.
     *
     * @param id
     *            {@link Serializable} is @@Id field from Entity
     * @return instance of {@link LockAuditableEntity}
     * @throws DemoMvcServiceException if any issue cause by persistence.
     */
    T get(I id) throws DemoMvcServiceException;

    /**
     * Get entity by given entity list of ids.
     *
     * @param ids
     *            List of {@link Serializable} is @@Id field from Entity
     * @return instance of {@link LockAuditableEntity}
     * @throws DemoMvcServiceException if any issue cause by persistence.
     */
    List<T> get(List<I> ids) throws DemoMvcServiceException;

    /**
     * Get activated entity by given entity id.
     * if only if entity is instance of {@link LockAuditableEntity}
     *
     * @param id
     *            {@link Serializable} is @@Id field from Entity
     * @return instance of {@link LockAuditableEntity}
     * @throws DemoMvcServiceException if any issue cause by persistence.
     */
    T getActivated(I id) throws DemoMvcServiceException;

    /**
     * Get all entity.
     * @return list of all entity.
     * @throws DemoMvcServiceException if any issue cause by persistence.
     */
    List<T> getAll() throws DemoMvcServiceException;

    /**
     * Get all activated entity. If only if entity is instance of {@link LockAuditableEntity}
     * @return list of all entity {@link LockAuditableEntity}.
     * @throws DemoMvcServiceException if any issue cause by persistence.
     */
    List<T> getAllActivated() throws DemoMvcServiceException;

    /**
     * Delete entity by given entity id.
     *
     * @param id
     *            {@link Serializable} is @@Id field from Entity
     * @return instance of deleted {@link LockAuditableEntity}, can use for check from higher-layer
     * @throws DemoMvcServiceException if any issue cause by persistence.
     */
    T delete(I id) throws DemoMvcServiceException;

    /**
     * Delete entity by given entity.
     * Checking entity is modifiable or not.
     * @param entity {@link LockAuditableEntity} instance
     * @return instance of deleted {@link LockAuditableEntity}, can use for check from higher-layer
     * @throws UnModifiableException if entity has updated by other user.
     * @throws DemoMvcServiceException if any issue cause by persistence.
     */
    T delete(T entity) throws UnModifiableException, DemoMvcServiceException;

    /**
     * Check given {@link LockAuditableEntity}.
     *
     * @param entity
     *            {@link LockAuditableEntity}
     * @param currentUpdateCount
     *            value to comparison.
     * @return true if entity can be modified. otherwise, false will be return.
     * @throws DemoMvcServiceException if any issue cause by persistence.
     */
    boolean checkModifiable(T entity, int currentUpdateCount) throws DemoMvcServiceException;

    /**
     * Check given {@link Serializable} present id of {@link LockAuditableEntity}.
     *
     * @param id
     *            {@link Serializable} instance present id of {@link LockAuditableEntity}
     * @param currentUpdateCount
     *            value to comparison.
     * @return true if entity can be modified. otherwise, false will be return.
     * @throws DemoMvcServiceException if any issue cause by persistence.
     */
    boolean checkModifiable(I id, int currentUpdateCount) throws DemoMvcServiceException;

    /**
     * Create new entity and save it to persistence.
     * @param entity {@link LockAuditableEntity}.
     * @return {@link LockAuditableEntity} instance (include ID) if success saved.
     * @throws DemoMvcServiceException if any issue cause by persistence.
     */
    T create(T entity) throws DemoMvcServiceException;

    /**
     * Bulk insert entities
     * @param entities
     * @return
     * @throws DemoMvcServiceException
     */
    List<T> bulkSave(List<T> entities) throws DemoMvcServiceException;

    /**
     * Update a {@link LockAuditableEntity} entity.
     * @param entity {@link LockAuditableEntity}
     * @return {@link LockAuditableEntity} after update.
     * @throws DemoMvcServiceException DemoMvcServiceException if any issue cause by persistence.
     */
    T update(T entity) throws DemoMvcServiceException;

    /**
     * Update a partial of {@link LockAuditableEntity} entity.
     * Only update not null field.
     * @param entity {@link LockAuditableEntity}
     * @return {@link LockAuditableEntity} after update.
     * @throws DemoMvcServiceException DemoMvcServiceException if any issue cause by persistence.
     */
    T updatePartial(T entity) throws DemoMvcServiceException;

    /**
     * Delegate save from DAO.
     *
     * @param entity
     *            entity need to save
     * @return entity after save
     * @throws DemoMvcServiceException
     *             if saving cause error.
     */
    T saveOrUpdate(T entity) throws DemoMvcServiceException;
}
