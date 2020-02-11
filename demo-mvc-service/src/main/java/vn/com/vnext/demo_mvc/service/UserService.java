/**
 * UserService.java
 *
 * @copyright  Copyright © 2019 VNext Software
 * @author quannl
 * @package vn.com.vnext.demo_mvc.service
 * @version 1.0.0
 */
package vn.com.vnext.demo_mvc.service;

/**
 * UserService
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.service
 */
public interface UserService {
    boolean login(String userName, String password);

}
