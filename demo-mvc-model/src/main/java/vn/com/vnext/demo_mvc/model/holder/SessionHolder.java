/**
 * SessionHolder.java
 *
 * @copyright  Copyright © 2019 VNext Software
 * @author quannl
 * @package vn.com.vnext.demo_mvc.holder
 * @version 1.0.0
 */
package vn.com.vnext.demo_mvc.model.holder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;
import vn.com.vnext.demo_mvc.model.dto.SessionInfoDto;


/**
 * SessionHolder
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.holder
 */
@ApplicationScope
@Component
@Getter
@Setter
public class SessionHolder implements Serializable {

    private static final long serialVersionUID = -7122409760864829825L;
    private Map<String, SessionInfoDto> pool = new HashMap<>();

}
