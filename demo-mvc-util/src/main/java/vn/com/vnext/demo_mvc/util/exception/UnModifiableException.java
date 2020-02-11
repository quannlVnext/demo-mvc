/**
 * UnModifiableException.java
 *
 * @copyright  Copyright © 2019 VNext software
 * @author quannl
 * @package vn.com.vnext.demo_mvc.util.exception
 * @version 1.0.0
 */
package vn.com.vnext.demo_mvc.util.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * UnModifiableException
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.util.exception
 */
@Getter
@Setter
public class UnModifiableException extends RuntimeException {

    private static final long serialVersionUID = -7163187913427048079L;

    private Object source;
    private int updateCount;
    private int currentUpdateCount;
    private int updateUserId;

    public UnModifiableException() {
        super();
    }

    /**
     * @param source {@link LockAuditableEntity} instance.
     * @param updateCount updateCount of updated entity
     * @param currentUpdateCount updateCount of persistence entity
     * @param updateUserId latest change by user id.
     */
    public UnModifiableException(Object source, int updateCount,
        int currentUpdateCount, int updateUserId) {
        this.source = source;
        this.updateCount = updateCount;
        this.currentUpdateCount = currentUpdateCount;
        this.updateUserId = updateUserId;
    }


    /**
     * @param source {@link LockAuditableEntity} instance.
     * @param updateCount updateCount of updated entity
     * @param currentUpdateCount updateCount of persistence entity
     * @param updateUserId latest change by user id.
     * @param message message
     */
    public UnModifiableException(String message, Object source, int updateCount,
        int currentUpdateCount, int updateUserId) {
        this(message);
        this.source = source;
        this.updateCount = updateCount;
        this.currentUpdateCount = currentUpdateCount;
        this.updateUserId = updateUserId;
    }


    public UnModifiableException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnModifiableException(String message) {
        super(message);
    }

    public UnModifiableException(Throwable cause) {
        super(cause);
    }

}
