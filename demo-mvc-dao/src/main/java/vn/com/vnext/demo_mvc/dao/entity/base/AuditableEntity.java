/**
 * AuditableEntity.java
 * @copyright © 2019 VNext software
 * @author     quannl
 * @package    vn.com.vnext.demo_mvc.dao.entity.base
 * @version    1.0.0
 */

package vn.com.vnext.demo_mvc.dao.entity.base;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import vn.com.vnext.demo_mvc.util.util.CurrentUserGetterUtils;

/**
 * AuditableEntity.
 * ------------- Manage CREATED_DATE, CREATED_BY, UPDATED_DATE, UDPATED_BY. -------------
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.dao.entity.base
 */
@MappedSuperclass
@Getter
@Setter
public abstract class AuditableEntity extends TimestampEntity implements Serializable {

    private static final long serialVersionUID = -2560225247955786554L;

    @Column(name = "create_user_id")
    private Integer createUserId;

    @Column(name = "update_user_id")
    private Integer updateUserId;

    @PreUpdate
    protected void onUpdate() {
        super.onUpdate();
        if (this.updateUserId == null) {
            this.updateUserId = CurrentUserGetterUtils.getInstance().getCurrentUser();
        }
    }

    @PrePersist
    protected void onSave() {
        super.onSave();
        if (this.createUserId == null) {
            this.createUserId = CurrentUserGetterUtils.getInstance().getCurrentUser();
        }
        if (this.updateUserId == null) {
            this.updateUserId = CurrentUserGetterUtils.getInstance().getCurrentUser();
        }
    }
}
