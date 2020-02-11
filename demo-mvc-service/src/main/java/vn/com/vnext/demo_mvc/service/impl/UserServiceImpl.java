/**
 * UserServiceImpl.java
 *
 * @copyright  Copyright © 2019 VNext Software
 * @author quannl
 * @package vn.com.vnext.demo_mvc.service.impl
 * @version 1.0.0
 */
package vn.com.vnext.demo_mvc.service.impl;

import org.springframework.stereotype.Service;
import vn.com.vnext.demo_mvc.service.UserService;

/**
 * UserServiceImpl
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.service.impl
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public boolean login(String userName, String password) {
        return true;
    }
}
