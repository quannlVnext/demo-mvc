/**
 * HomeController.java
 *
 * @copyright  Copyright © 2019 VNext Software
 * @author quannl
 * @package vn.com.vnext.demo_mvc.web.controller
 * @version 1.0.0
 */
package vn.com.vnext.demo_mvc.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * HomeController
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.web.controller
 */
@Controller
public class HomeController {
    public static final String DEMO_MVC_HOME_ACTION = "/home";
    public static final String DEMO_MVC_HOME_VIEW = "home";

    @GetMapping(value = DEMO_MVC_HOME_ACTION)
    public String showForm() {
        return DEMO_MVC_HOME_VIEW;
    }

}
