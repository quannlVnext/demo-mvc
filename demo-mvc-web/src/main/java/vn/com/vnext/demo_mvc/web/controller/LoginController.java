/**
 * LoginController.java
 *
 * @copyright  Copyright © 2019 VNext Software
 * @author quannl
 * @package vn.com.vnext.demo_mvc.web.controller
 * @version 1.0.0
 */
package vn.com.vnext.demo_mvc.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.com.vnext.demo_mvc.dao.entity.User;
import vn.com.vnext.demo_mvc.model.dto.SessionInfoDto;
import vn.com.vnext.demo_mvc.model.payload.LoginPayLoad;
import vn.com.vnext.demo_mvc.model.response.RpcResponse;
import vn.com.vnext.demo_mvc.service.UserService;
import vn.com.vnext.demo_mvc.util.component.ErrorsKeyConverter;
import vn.com.vnext.demo_mvc.util.tracker.LoginUserTracker;
import vn.com.vnext.demo_mvc.util.util.DemoMvcRequestUtils;
import vn.com.vnext.demo_mvc.util.util.DemoMvcUtils;

/**
 * LoginController
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.web.controller
 */
@Controller
public class LoginController {
    private final String DEMO_MVC_LOGIN_ACTION = "/login";
    private final String DEMO_MVC_LOGIN_VIEW = "login";

    private final Logger logger = LogManager.getLogger(LoginController.class);

    private UserService userService;
    private ErrorsKeyConverter errorsProcessor;
    private LoginUserTracker loginUserTracker;

    @Autowired
    public LoginController(UserService userService, ErrorsKeyConverter errorsProcessor, LoginUserTracker loginUserTracker) {
        this.userService = userService;
        this.errorsProcessor = errorsProcessor;
        this.loginUserTracker = loginUserTracker;
    }

    @GetMapping(value = DEMO_MVC_LOGIN_ACTION)
    public String showLoginForm() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return DEMO_MVC_LOGIN_VIEW;
        }
        return "redirect:" + HomeController.DEMO_MVC_HOME_ACTION;
    }

    @PostMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:" + DEMO_MVC_LOGIN_ACTION;
    }

    @PostMapping(value = DEMO_MVC_LOGIN_ACTION)
    @ResponseBody
    public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response,
        @Valid @RequestBody LoginPayLoad loginPayLoad, Errors errors) {

        RpcResponse result = new RpcResponse(DEMO_MVC_LOGIN_ACTION);
        SessionInfoDto sessionInfo = DemoMvcRequestUtils.getInstance().getSessionInfo(request);
        if (null != sessionInfo) {
            sessionInfo.setSessionTimeout(false);
            DemoMvcRequestUtils.getInstance().setSessionInfo(request, sessionInfo);
        }

        HttpStatus status = HttpStatus.OK;
        if (!errors.hasErrors()) {
            boolean loginResult = this.userService.login(loginPayLoad.getEmail(), loginPayLoad.getPassword());
            if (!loginResult) {
                errors.reject("1101");
            } else {
                User user = new User();
                user.setEmail("quannl@outlook.com");
                user.setName("quannl");
                user.setPassword("123456");
                this.saveSession(request, user);
            }
        }
        if (errors.hasErrors()) {
            try {
                errorsProcessor.processErrors(LoginPayLoad.fieldMap, errors);
            } catch (Exception ex) {
                // do nothing
            }

            int index = 1;
            for (ObjectError error : errors.getAllErrors()) {
                result.addAttribute("error-" + index++, error.getDefaultMessage());
            }
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(result, status);
    }

    private void saveSession(HttpServletRequest request, User user) {
        String token = DemoMvcUtils.getInstance().createToken(user.getEmail());

        SessionInfoDto sessionInfo = DemoMvcRequestUtils.getInstance().getSessionInfo(request);
        if (null == sessionInfo) {
            sessionInfo = new SessionInfoDto();
        }
        sessionInfo.setToken(token);
        sessionInfo.setEmail(user.getEmail());
        sessionInfo.setUserId(user.getId());
        sessionInfo.setSessionTimeout(false);

        this.loginUserTracker.putSession(user.getEmail(), sessionInfo);
        DemoMvcRequestUtils.getInstance().setSessionInfo(request, sessionInfo);
    }
}
