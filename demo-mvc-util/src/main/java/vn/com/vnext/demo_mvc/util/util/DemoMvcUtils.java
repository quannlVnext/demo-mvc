/**
 * DemoMvcUtils.java
 *
 * @copyright  Copyright © 2019 VNext Software
 * @author quannl
 * @package vn.com.vnext.demo_mvc.util
 * @version 1.0.0
 */
package vn.com.vnext.demo_mvc.util.util;

import com.amdelamar.jhash.Hash;
import com.amdelamar.jhash.algorithms.Type;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * DemoMvcUtils
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.util
 */
public class DemoMvcUtils {
    private static final String[] CHAR_POOL = new String[] { "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "abcdefghijklmnopqrstuvwxyz",
        "0123456789" };

    protected static final char[] hexArray = "0123456789abcdef".toCharArray();
    private Logger logger = LogManager.getLogger(DemoMvcUtils.class);
    private static DemoMvcUtils instance = new DemoMvcUtils();

    public static DemoMvcUtils getInstance() {
        if (null == instance) {
            instance = new DemoMvcUtils();
        }
        return instance;
    }

    /**
     * Copies properties from one object (source) to another (destination)
     * @param source
     * @destination
     * @return
     */
    public void copyNonNullProperties(Object source, Object destination){
        BeanUtils.copyProperties(source, destination, getNullPropertyNames(source));
    }

    /**
     * Returns an array of null properties of an object
     * @param source
     * @return
     */
    private String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        List<String> emptyNames = Arrays.asList(pds).stream()
                .filter(pd -> null == src.getPropertyValue(pd.getName()))
                .map(pd -> pd.getName())
                .collect(Collectors.toList());

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * Calculate MD5 string of given input string.
     *
     * @param input
     *            string need to calculate md5
     * @return MD5 value.
     * @throws Exception
     *             delegate from MD5 algorithm.
     * @deprecated
     */
    public String md5(String input) throws Exception {
        byte[] bytesOfMessage = input.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] thedigest = md.digest(bytesOfMessage);
        return this.bytesToHex(thedigest);
    }

    /**
     * Create token string by given email. It will merge will current timestamp in miliseconds.
     *
     * @param email
     *            given email.
     * @return unique token string.
     */
    public String createToken(String email) {
        long timestamp = System.currentTimeMillis();
        String result = email + "_" + timestamp;
        try {
            result = sha256(result);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return result;
    }

    /**
     * Calculate SHA-256 string of given input string.
     *
     * @param input
     *            string need to calculate SHA-256
     * @return SHA-256 value.
     * @throws Exception
     *             delegate from SHA-256 algorithm.
     */
    public String sha256(String input) {
        char[] charArray = input.toCharArray();
        // pbkdf2 hmac sha256 + salt
        return Hash.password(charArray).algorithm(Type.PBKDF2_SHA256).create();
    }

    /**
     * Byte Array to HEX string.
     *
     * @param bytes
     *            input bytes.
     * @return HEX value of input bytes.
     */
    public String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int value = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[value >>> 4];
            hexChars[j * 2 + 1] = hexArray[value & 0x0F];
        }
        return new String(hexChars);
    }

    public LocalDateTime getCurrentJapanDate() {
        Date date = new Date(System.currentTimeMillis());

        SimpleDateFormat japanDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPAN);
        japanDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+9"));
        String japanDateString = japanDateFormat.format(date);

        SimpleDateFormat systemDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            date = systemDateFormat.parse(japanDateString);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return LocalDateTime.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
            cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
    }
}
