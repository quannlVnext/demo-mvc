/**
 * CurrentUserGetterUtils.java
 *
 * @copyright  Copyright © 2019 Systems design
 * @author quannl
 * @package vn.com.vnext.demo_mvc.util.util
 * @version 1.0.0
 */
package vn.com.vnext.demo_mvc.util.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import vn.com.vnext.demo_mvc.model.dto.SessionInfoDto;
import vn.com.vnext.demo_mvc.util.constants.DemoMvcConstants;

/**
 * CurrentUserGetterUtils
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.util.util
 */
public class CurrentUserGetterUtils {
    private static CurrentUserGetterUtils instance = new CurrentUserGetterUtils();

    /**
     * Singleton method.
     *
     * @return the instance
     */
    public static CurrentUserGetterUtils getInstance() {
        if (instance == null) {
            instance = new CurrentUserGetterUtils();
        }
        return instance;
    }

    public int getCurrentUser() {
        SessionInfoDto sessionInfo = null;
        try {
            sessionInfo = (SessionInfoDto) RequestContextHolder.currentRequestAttributes().getAttribute(
                DemoMvcRequestUtils.DEMO_MVC_SESSION_INFO_KEY, RequestAttributes.SCOPE_SESSION
            );
            if (null != sessionInfo) {
                return sessionInfo.getUserId();
            }
        } catch (Exception ex) {
            // do nothing
        }
        return DemoMvcConstants.DEFAULT_USER;
    }

}
