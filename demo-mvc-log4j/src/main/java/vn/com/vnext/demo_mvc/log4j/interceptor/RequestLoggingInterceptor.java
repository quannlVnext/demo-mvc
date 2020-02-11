/**
 * RequestLoggingInterceptor.java
 *
 * @copyright  Copyright © 2019 VNext Software
 * @author quannl
 * @package vn.com.vnext.demo_mvc.log4j.interceptor
 * @version 1.0.0
 */
package vn.com.vnext.demo_mvc.log4j.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * RequestLoggingInterceptor
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.log4j.interceptor
 */
public class RequestLoggingInterceptor extends HandlerInterceptorAdapter {
    private final String DEMO_MVC_TIME_LOG_KEY = "TIME_LOG";
    private Logger logger = LogManager.getLogger(RequestLoggingInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod method = (HandlerMethod) handler;

        request.setAttribute(DEMO_MVC_TIME_LOG_KEY, System.currentTimeMillis());
        logger.info(method.getMethod().getDeclaringClass().getName() + " START " + request.getRequestURI() + " " + request.getMethod());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return;
        }

        HandlerMethod method = (HandlerMethod) handler;
        Long startTime = (Long) request.getAttribute(DEMO_MVC_TIME_LOG_KEY);
        if (startTime != null) {
            long processTime = System.currentTimeMillis() - startTime;
            logger.info(method.getMethod().getDeclaringClass().getName() + " FINISH " + processTime + " msec");
        }
        super.afterCompletion(request, response, handler, ex);
    }
}
