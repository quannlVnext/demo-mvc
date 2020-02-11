/**
 * RpcResponse.java
 *
 * @copyright © 2019 VNext Software
 * @author quannl
 * @package vn.com.vnext.demo_mvc.model.response
 * @version 1.0.0
 */
package vn.com.vnext.demo_mvc.model.response;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 * RpcResponse
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.model.response
 */
@Getter
@Setter
public class RpcResponse implements Serializable {

    private static final long serialVersionUID = -6559867034842611976L;

    private String serviceName = "N/A";

    private Map<String, Object> data = new LinkedHashMap<>();

    /**
     * Constructor with Service Name.
     * @param serviceName input Service Name
     */
    public RpcResponse(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * Add an attribute.
     * @param key String
     * @param value Object
     */
    public void addAttribute(String key, Object value) {
        data.put(key, value);
    }
}
