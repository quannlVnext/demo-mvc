/**
 * DemoMVCAuthenticationFilter.java
 *
 * @copyright  Copyright © 2019 VNext Software
 * @author quannl
 * @package vn.com.vnext.demo_mvc.web.filter
 * @version 1.0.0
 */
package vn.com.vnext.demo_mvc.web.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * DemoMVCAuthenticationFilter
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.web.filter
 */
public class DemoMVCAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
        FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null == authentication) {
            httpServletResponse.sendRedirect("/login");
        } else {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}
