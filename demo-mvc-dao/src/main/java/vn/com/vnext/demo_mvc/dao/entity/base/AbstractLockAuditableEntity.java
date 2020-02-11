/**
 * AbstractLockAuditableEntity.java
 * @copyright  Copyright © 2016 VNext software
 * @author     quannl
 * @package    vn.com.vnext.demo_mvc.dao.entity.base
 * @version    1.0.0
 */

package vn.com.vnext.demo_mvc.dao.entity.base;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * AbstractLockAuditableEntity. LockAuditableEntity with type of ID is {@link Long}
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.dao.entity.base
 */
@MappedSuperclass
@Getter
@Setter
public class AbstractLockAuditableEntity extends LockAuditableEntity<Integer> implements Serializable {


    private static final long serialVersionUID = -1193119711498805214L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Override
    public String toString() {
        return "AbstractLockAuditableEntity [id=" + id + "]";
    }

}
