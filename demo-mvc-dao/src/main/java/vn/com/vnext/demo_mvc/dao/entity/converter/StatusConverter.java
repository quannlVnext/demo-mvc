/**
 * RoleConverter.java
 * @copyright  Copyright © 2016 Systems design
 * @author     User
 * @package    jp.co.sdc.discovery_faq.model.util
 * @version    1.0.0
 */

package vn.com.vnext.demo_mvc.dao.entity.converter;

import javax.persistence.AttributeConverter;
import vn.com.vnext.demo_mvc.model.enumeration.Status;


/**
 * RoleConverter.
 * This class convert user status value to class UserStatus.
 * @author hieuvh
 * @access public
 * @package jp.co.sdc.discovery_faq.model.util
 */
public class StatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status status) {
        return status.getValue();
    }

    @Override
    public Status convertToEntityAttribute(String value) {
        return Status.fromValue(value);
    }

}
