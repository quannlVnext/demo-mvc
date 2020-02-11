/**
 * SessionHolder.java
 *
 * @copyright  Copyright © 2019 VNext Software
 * @author quannl
 * @package vn.com.vnext.demo_mvc.holder
 * @version 1.0.0
 */
package vn.com.vnext.demo_mvc.util.tracker;

import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.com.vnext.demo_mvc.model.dto.SessionInfoDto;
import vn.com.vnext.demo_mvc.model.holder.SessionHolder;


/**
 * LoginUserTracker
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.holder
 */
@Component
public class LoginUserTracker implements Serializable {

    private static final long serialVersionUID = -4040411606489968833L;

    @Autowired
    private SessionHolder sessionHolder;

    /**
     * Check a session is valid or not by given session object.
     *
     * @param email key of pool map.
     * @return {@link SessionInfoDto} instance if email logged in.
     */
    public SessionInfoDto getSession(String email) {
        return this.sessionHolder.getPool().get(email);
    }

    public void dropSession(String email) {
        this.sessionHolder.getPool().remove(email);
    }

    /**
     * Drop session by of email by given email and token.
     * If match session of email has token equals input token.
     * Remove this once from map.
     * @param email given email.
     * @param token given token.
     */
    public void dropSession(String email, String token) {
        SessionInfoDto info = this.sessionHolder.getPool().get(email);
        if (info != null && info.getToken() != null && info.getToken().equals(token)) {
            this.sessionHolder.getPool().remove(email);
        }
    }

    public void putSession(String email, SessionInfoDto session) {
        this.sessionHolder.getPool().put(email, session);
    }
}
