/**
 * LoginPayLoad.java
 *
 * @copyright © 2019 VNext Software
 * @author quannl
 * @package vn.com.vnext.demo_mvc.model.payload
 * @version 1.0.0
 */
package vn.com.vnext.demo_mvc.model.payload;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 * LoginPayLoad
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.model.payload
 */
@Getter
@Setter
public class LoginPayLoad implements Serializable {

    private static final long serialVersionUID = -2939614332247115695L;
    private String email;
    private String password;
    public static Map<String, String> fieldMap = new LinkedHashMap<>();

    static {
        fieldMap.put("email", "login.input.text.email");
        fieldMap.put("password", "login.input.text.password");
    }

    @Override
    public String toString() {
        return "LoginPayLoad{" +
            "email='" + email + '\'' +
            ", password='" + password + '\'' +
            '}';
    }
}
