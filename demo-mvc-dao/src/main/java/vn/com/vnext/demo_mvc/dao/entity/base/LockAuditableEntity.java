/**
 * ClockAuditableEntity.java
 * @copyright  Copyright © 2016 VNext software
 * @author     hieuvh
 * @package    vn.com.vnext.demo_mvc.dao.entity.base
 * @version    1.0.0
 */

package vn.com.vnext.demo_mvc.dao.entity.base;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import lombok.Getter;
import lombok.Setter;

/**
 * ClockAuditableEntity. {@link AuditableEntity} and support {@link LockableEntity} when update.
 *
 * @author hieuvh
 * @access public
 * @package vn.com.vnext.demo_mvc.dao.entity.base
 */
@MappedSuperclass
@Getter
@Setter
public abstract class LockAuditableEntity<I extends Serializable> extends AuditableEntity
            implements Serializable, LockableEntity, IdentifiedEntity<I> {

    private static final long serialVersionUID = 7755047651432932764L;

    @Column(name = "update_count")
    @Version
    private int updateCount;

}
