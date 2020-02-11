/**
 * EntityTrackerAspect.java
 *
 * @copyright  Copyright © 2019 Systems design
 * @author quannl
 * @package vn.com.vnext.demo_mvc.web.aspectJ
 * @version 1.0.0
 */
package vn.com.vnext.demo_mvc.web.aspectJ;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

/**
 * EntityTrackerAspect
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.web.aspectJ
 */
@Aspect
@Configuration
public class EntityTrackerAspect {
    private Logger logger = LogManager.getLogger(EntityTrackerAspect.class);

    @Before(value = "execution(* vn.com.vnext.demo_mvc.web.controller.*)")
    public void beforeGetAction(JoinPoint joinPoint) {
        logger.info("");
    }

}
