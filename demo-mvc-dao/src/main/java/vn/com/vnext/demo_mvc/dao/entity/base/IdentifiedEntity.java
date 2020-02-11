/**
 * IdentifiedEntity.java
 * @copyright  Copyright © 2016 VNext software
 * @author     quannl
 * @package    vn.com.vnext.demo_mvc.dao.entity.base
 * @version    1.0.0
 */

package vn.com.vnext.demo_mvc.dao.entity.base;

import java.io.Serializable;

/**
 * IdentifiedEntity.
 * Sub-class must be specify type of ID.
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.dao.entity.base
 */
public interface IdentifiedEntity<I extends Serializable> {

    /**
     * Retrieve ID of entity.
     * @return {@link Serializable} instance.
     */
    I getId();
}
