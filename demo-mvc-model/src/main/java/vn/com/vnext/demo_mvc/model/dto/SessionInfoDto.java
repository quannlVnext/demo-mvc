/**
 * SessionInfoDto.java
 *
 * @copyright  Copyright © 2019 VNext Software
 * @author quannl
 * @package vn.com.vnext.demo_mvc.model.dto
 * @version 1.0.0
 */
package vn.com.vnext.demo_mvc.model.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * SessionInfoDto
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.model.dto
 */
@Getter
@Setter
public class SessionInfoDto implements Serializable {

    private static final long serialVersionUID = 7995415719205619740L;

    private String email;
    private String token;
    private String role;
    private int userId;
    private boolean isSessionTimeout = false;

}
