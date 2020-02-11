/**
 * AbstractLockAuditableEntity.java
 * @copyright  Copyright © 2016 VNext software
 * @author     hieuvh
 * @package    jp.co.sdc.discovery_faq.model.base
 * @version    1.0.0
 */

package vn.com.vnext.demo_mvc.dao.entity.base;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import vn.com.vnext.demo_mvc.dao.entity.converter.StatusConverter;
import vn.com.vnext.demo_mvc.model.enumeration.Status;

/**
 * AbstractLockAuditableEntity. LockAuditableEntity with type of ID is {@link Integer}
 *
 * @author hieuvh
 * @access public
 * @package jp.co.sdc.discovery_faq.model.base
 */
@MappedSuperclass
@Getter
@Setter
public class StatusLockAuditableEntity extends AbstractLockAuditableEntity
        implements StatusEntity, Serializable {

    private static final long serialVersionUID = 1777275215864593430L;

    @Column(name = "status", columnDefinition = "TINYINT(10) default 2 NOT NULL")
    @Convert(converter = StatusConverter.class)
    private Status status;

    @Override
    public boolean isActivated() {
        return Status.VALID.equals(status);
    }

    @Override
    public boolean isDeleted() {
        return Status.INVALID.equals(status);
    }

    @Override
    public boolean isDraft() {
        return Status.DRAFT.equals(status);
    }

    @Override
    public boolean isError() {
        return Status.ERROR.equals(status);
    }


}
