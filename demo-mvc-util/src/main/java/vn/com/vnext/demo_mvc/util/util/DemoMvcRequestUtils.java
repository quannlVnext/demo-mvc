/**
 * DemoMvcRequestUtil.java
 *
 * @copyright © 2019 VNext Software
 * @author quannl
 * @package vn.com.vnext.demo_mvc.util
 * @version 1.0.0
 */
package vn.com.vnext.demo_mvc.util.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import vn.com.vnext.demo_mvc.model.dto.SessionInfoDto;

/**
 * DemoMvcRequestUtil
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.util
 */
public class DemoMvcRequestUtils {
    public static final String DEMO_MVC_SESSION_INFO_KEY = "SESSION_INFO";

    private static DemoMvcRequestUtils instance = new DemoMvcRequestUtils();


    /**
     * Singleton method.
     *
     * @return the instance
     */
    public static DemoMvcRequestUtils getInstance() {
        if (instance == null) {
            instance = new DemoMvcRequestUtils();
        }
        return instance;
    }

    /**
     * Get {@link SessionInfoDto} from HttpServletRequest.
     *
     * @param request
     *            {@link HttpServletRequest}
     * @return {@link SessionInfoDto} object or null
     */
    public SessionInfoDto getSessionInfo(HttpServletRequest request) {
        return (SessionInfoDto) getSession(request).getAttribute(DEMO_MVC_SESSION_INFO_KEY);
    }

    /**
     * Set {@link SessionInfoDto} instance into Session get from {@link HttpServletRequest}.
     *
     * @param request
     *            {@link HttpServletRequest}
     * @param sessionInfo
     *            {@link SessionInfoDto}
     */
    public void setSessionInfo(HttpServletRequest request, SessionInfoDto sessionInfo) {
        request.getSession(true).setAttribute(DEMO_MVC_SESSION_INFO_KEY, sessionInfo);
    }

    /**
     * Retrieve {@link HttpSession} object.
     *
     * @param request {@link HttpServletRequest} object
     * @return {@link HttpSession}
     */
    public HttpSession getSession(HttpServletRequest request) {
        return request.getSession(true);
    }

}
