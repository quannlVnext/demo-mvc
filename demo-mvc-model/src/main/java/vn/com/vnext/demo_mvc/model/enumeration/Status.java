/**
 * Status.java
 *
 * @copyright  Copyright © 2019 Systems design
 * @author quannl
 * @package vn.com.vnext.demo_mvc.model.enumeration
 * @version 1.0.0
 */
package vn.com.vnext.demo_mvc.model.enumeration;

/**
 * Status
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.model.enumeration
 */
public enum Status {
    VALID("0"),
    DRAFT("1"),
    INVALID("9"),
    ERROR("e");

    private String value;

    private Status(String value) {
        this.value = value;
    }

    /**
     * get value of <b>value</b>.
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Retrieve a UserStatus instance by given the string value.
     * @param value String value "0", "1", "2", "9", "e"
     * @return the UserStatus associated with given value. Otherwise, [INVALID] will be returned.
     */
    public static Status fromValue(String value) {
        Status status = ERROR;
        switch (value) {
            case "0":
                status = VALID;
                break;
            case "1":
                status = DRAFT;
                break;
            case "9":
                status = INVALID;
                break;
            case "e":
                status = ERROR;
                break;
            default:
                break;
        }
        return status;
    }
}
