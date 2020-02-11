/**
 * User.java
 *
 * @copyright  Copyright © 2019 VNext Software
 * @author quannl
 * @package vn.com.vnext.demo_mvc.model.entity
 * @version 1.0.0
 */
package vn.com.vnext.demo_mvc.dao.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.vnext.demo_mvc.dao.entity.base.StatusLockAuditableEntity;

/**
 * User
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.model.entity
 */
@Getter
@Setter
@Entity
@Table(name = "users")

public class User extends StatusLockAuditableEntity implements Serializable {

    private static final long serialVersionUID = -2499012319174215711L;
    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

}
