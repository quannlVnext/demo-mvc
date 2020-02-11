/**
 * DemoMvcServiceException.java
 *
 * @copyright  Copyright © 2019 VNext software
 * @author quannl
 * @package vn.com.vnext.demo_mvc.util.exception
 * @version 1.0.0
 */
package vn.com.vnext.demo_mvc.util.exception;

/**
 * DemoMvcServiceException
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.util.exception
 */
public class DemoMvcServiceException extends RuntimeException {

    private static final long serialVersionUID = 750710512849218865L;

    private int errorCode;

    public DemoMvcServiceException() {
    }

    public DemoMvcServiceException(int errorCode) {
        this.errorCode = errorCode;
    }

    public DemoMvcServiceException(String message) {
        super(message);
    }

    public DemoMvcServiceException(Throwable cause) {
        super(cause);
    }

    public DemoMvcServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DemoMvcServiceException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
