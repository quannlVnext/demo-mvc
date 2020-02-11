/**
 * EntityUpdateTracker.java
 *
 * @copyright © 2019 VNext software
 * @author quannl
 * @package vn.com.vnext.demo_mvc.util.tracker
 * @version 1.0.0
 */
package vn.com.vnext.demo_mvc.dao.entity.tracker;

import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.com.vnext.demo_mvc.dao.entity.base.LockAuditableEntity;
import vn.com.vnext.demo_mvc.model.holder.EntityHolder;

/**
 * EntityUpdateTracker
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.util.tracker
 */
@Component
public class EntityUpdateTracker implements Serializable {

    private static final long serialVersionUID = 6899074699507010642L;

    @Autowired
    private EntityHolder entityHolder;

    public void track(Class<? extends LockAuditableEntity> clazz, Serializable id, int updateCount) {
        this.entityHolder.getTrackerMap().put(clazz.getName() + id.toString(), updateCount);
    }

    public int retrieve(Class<? extends LockAuditableEntity> clazz, Serializable id) {
        Integer value = this.entityHolder.getTrackerMap().get(clazz.getName() + id.toString());
        return value != null ? value : 0;
    }

    public void unTrack(Class<? extends LockAuditableEntity> clazz, Serializable id) {
        this.entityHolder.getTrackerMap().remove(clazz.getName() + id.toString());
    }
}
