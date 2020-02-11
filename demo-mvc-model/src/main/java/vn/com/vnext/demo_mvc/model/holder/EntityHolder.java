/**
 * EntityHolder.java
 *
 * @copyright  Copyright © 2019 VNext software
 * @author quannl
 * @package vn.com.vnext.demo_mvc.web.holder
 * @version 1.0.0
 */
package vn.com.vnext.demo_mvc.model.holder;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

/**
 * EntityHolder
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.web.holder
 */
@Component
@ApplicationScope
@Getter
@Setter
public class EntityHolder implements Serializable {

    private static final long serialVersionUID = 5143719594322369566L;
    private Map<String, Integer> trackerMap = new ConcurrentHashMap<>();
}
