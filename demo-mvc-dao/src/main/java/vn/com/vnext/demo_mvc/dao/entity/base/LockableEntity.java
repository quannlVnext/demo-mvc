/**
 * ClockableEntity.java
 * @copyright © 2019 VNext software
 * @author     quannl
 * @package    vn.com.vnext.demo_mvc.dao.entity.base
 * @version    1.0.0
 */

package vn.com.vnext.demo_mvc.dao.entity.base;

/**
 * LockableEntity. ------------- Interface present for a lock-able entity by count number of update. -------------
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.dao.entity.base
 */
public interface LockableEntity {

    /**
     * Get current count update time of the entity.
     *
     * @return integer number present count of update of the entity.
     */
    int getUpdateCount();

}
