/**
 * ErrorsKeyConverter.java
 * @copyright © 2019 VNext Software
 * @author     quannl
 * @package    vn.com.vnext.demo_mvc.component
 * @version    1.0.0
 */

package vn.com.vnext.demo_mvc.util.component;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import vn.com.vnext.demo_mvc.util.constants.ErrorCodePrefix;

/**
 * ErrorsKeyConverter.
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.component
 */
@Component
public class ErrorsKeyConverter {
    private static final String DEMO_MVC_FIELD_LABEL_PARAM_NAME = "[FIELD_LABEL]";

    private boolean errorCodeEnable = false;

    @Autowired
    private MessageSource messageSource;

    /**
     * Process errors.<br/>
     * Global Error, Field Error.<br/>
     * After that, order errors follow request attribute map
     * (DiscoveryFaqWebConstants.DEMO_MVC_FIELD_LIST_PARAM_NAME)
     *
     * @param fieldMap Map of [Field Name, Message Key of Field Name]
     * @param errors
     *            {@link Errors}
     * @throws NoSuchFieldException
     *             from Java Reflect API
     * @throws IllegalAccessException
     *             from Java Reflect API
     */
    public void processErrors(Map<String, String> fieldMap, Errors errors)
            throws NoSuchFieldException, IllegalAccessException {
        this.processFieldErrors(fieldMap, errors);
        this.processGlobalErrors(fieldMap, errors);
        this.processOrderErrors(fieldMap, errors);
    }

    /**
     * Order errors by (global errors first, field errors order by given fieldMap.
     * @param fieldMap Map of [field name / field message key]
     * @param errors {@link Errors}
     */
    public void processOrderErrors(Map<String, String> fieldMap, Errors errors) {
        List<ObjectError> result = new LinkedList<ObjectError>();
        if (errors != null && errors.hasGlobalErrors()) {
            result.addAll(errors.getGlobalErrors());
        }

        if (errors != null && errors.hasFieldErrors()) {
            if (fieldMap != null && !fieldMap.isEmpty()) {
                for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
                    if (errors.hasFieldErrors(entry.getKey())) {
                        result.add(errors.getFieldError(entry.getKey()));
                    }
                }
            }

            for (ObjectError error : errors.getFieldErrors()) {
                if (result.contains(error)) {
                    continue;
                }
                result.add(error);
            }

            try {
                Field field = errors.getClass().getSuperclass().getSuperclass().getDeclaredField("errors");
                field.setAccessible(true);
                field.set(errors, result);
            } catch (Exception ex) {
                // do nothing
            }
        }
    }

    /**
     * process Global validation errors.
     *
     * @param fieldMap Map of [Field Name, Message Key of Field Name]
     * @param errors
     *            {@link BindingResult} instance
     * @throws NoSuchFieldException
     *             from Java Reflect API
     * @throws IllegalAccessException
     *             from Java Reflect API
     */
    public void processGlobalErrors(Map<String, String> fieldMap, Errors errors)
            throws NoSuchFieldException, IllegalAccessException {
        if (errors != null && errors.hasErrors()) {
            List<ObjectError> globalErrors = errors.getGlobalErrors();
            if (globalErrors != null) {
                for (ObjectError error : globalErrors) {
                    this.processGlobalError(fieldMap, error);
                }

            }
        }
    }

    /**
     * process a global validation error.
     * @param fieldMap Map of [Field Name, Message Key of Field Name]
     * @param error
     *            {@link ObjectError}
     * @throws NoSuchFieldException
     *             from Java Reflect API
     * @throws IllegalAccessException
     *             from Java Reflect API
     */
    public void processGlobalError(Map<String, String> fieldMap, ObjectError error)
            throws NoSuchFieldException, IllegalAccessException {
        String value = null;

        try {
            value = this.messageSource.getMessage(ErrorCodePrefix.DEMO_MVC_INFO_CODE_PREFIX + error.getCode(),
                    error.getArguments(), LocaleContextHolder.getLocale());
        } catch (Exception ex) {
            // do nothing
        }

        if (StringUtils.isEmpty(value)
                || value.equals(ErrorCodePrefix.DEMO_MVC_INFO_CODE_PREFIX + error.getCode())) {
            value = error.getCode() + (this.isErrorCodeEnable() ? " [ERROR_CODE(UNKNOWN)]" : "");
        } else {
            value = value + (this.isErrorCodeEnable() ? " [ERROR_CODE(" + error.getCode() + ")]" : "");
        }

        Field field = error.getClass().getSuperclass().getDeclaredField("defaultMessage");
        field.setAccessible(true);
        field.set(error, value);
    }

    /**
     * process FIELD validation errors.
     * @param fieldMap Map of [Field Name, Message Key of Field Name]
     * @param errors
     *            {@link BindingResult} instance
     * @throws NoSuchFieldException
     *             from Java Reflect API
     * @throws IllegalAccessException
     *             from Java Reflect API
     */
    public void processFieldErrors(Map<String, String> fieldMap, Errors errors)
            throws NoSuchFieldException, IllegalAccessException {
        if (errors != null && errors.hasFieldErrors()) {
            List<FieldError> fieldErrors = errors.getFieldErrors();
            if (fieldErrors != null) {
                for (FieldError error : fieldErrors) {
                    this.processFieldError(fieldMap, error);
                }
            }
        }
    }

    /**
     * process a FIELD validation error.
     *
     * @param error
     *            {@link FieldError} instance
     * @throws NoSuchFieldException
     *             from Java Reflect API
     * @throws IllegalAccessException
     *             from Java Reflect API
     */
    public void processFieldError(Map<String, String> fieldMap, FieldError error)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = error.getClass().getSuperclass().getSuperclass().getDeclaredField("defaultMessage");
        field.setAccessible(true);

        String value = null;
        try {
            value = this.messageSource.getMessage(
                    ErrorCodePrefix.DEMO_MVC_INFO_CODE_PREFIX + error.getDefaultMessage(), error.getArguments(),
                    LocaleContextHolder.getLocale());
        } catch (Exception ex) {
            // do nothing
        }

        if (StringUtils.isEmpty(value)
                || value.equals(ErrorCodePrefix.DEMO_MVC_INFO_CODE_PREFIX + error.getDefaultMessage())) {
            value = error.getDefaultMessage() + (this.isErrorCodeEnable() ? " [ERROR_CODE(UNKNOWN)]" : "");
        } else {
            if (value.contains(DEMO_MVC_FIELD_LABEL_PARAM_NAME)) {
                value = this.replaceFieldLabel(fieldMap, error.getField(), value);
            }
            value = value + (this.isErrorCodeEnable() ? " [ERROR_CODE(" + error.getDefaultMessage() + ")]" : "");
        }

        if (!StringUtils.isEmpty(value)) {
            field.set(error, value);
        }
    }

    /**
     * @param fieldMap Map of [Field Name, Message Key of Field Name]
     * @param key
     *            field name.
     * @param value
     *            message value.
     * @return value after replace DiscoveryFaqWebConstants.DEMO_MVC_FIELD_LIST_PARAM_NAME
     */
    private String replaceFieldLabel(Map<String, String> fieldMap, String key, String value) {
        String fieldLabel = key;
        try {
            if (fieldMap != null && fieldMap.get(key) != null) {
                fieldLabel = this.messageSource.getMessage(fieldMap.get(key), new Object[] {}, key,
                        LocaleContextHolder.getLocale());
            }
        } catch (Exception ex) {
            // do nothing
        }
        value = value.replace(DEMO_MVC_FIELD_LABEL_PARAM_NAME, fieldLabel);

        return value;
    }

    /**
     * get value of <b>errorCodeEnable</b>.
     * @return the errorCodeEnable
     */
    public boolean isErrorCodeEnable() {
        return errorCodeEnable;
    }

    /**
     * Set value to <b>errorCodeEnable</b>.
     * @param errorCodeEnable the errorCodeEnable to set
     */
    public void setErrorCodeEnable(boolean errorCodeEnable) {
        this.errorCodeEnable = errorCodeEnable;
    }

}
