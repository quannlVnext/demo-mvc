/**
 * PersistentEntity.java
 * @copyright  Copyright © 2016 VNext software
 * @author     hieuvh
 * @package    jp.co.sdc.discovery_faq.model.base
 * @version    1.0.0
 */

package vn.com.vnext.demo_mvc.dao.entity.base;

import vn.com.vnext.demo_mvc.model.enumeration.Status;

/**
 * PersistentEntity.
 * Enable deleted Status flag for persistent.
 * @author hieuvh
 * @access public
 * @package jp.co.sdc.discovery_faq.model.base
 */
public interface StatusEntity {

    boolean isDeleted();

    boolean isActivated();

    boolean isDraft();

    boolean isError();

    Status getStatus();

    void setStatus(Status status);
}
