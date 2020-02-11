/**
 * LocalDateAttributeConverter.java
 *
 * @copyright © 2019 VNext software
 * @author quannl
 * @package vn.com.vnext.demo_mvc.model.entity.converter
 * @version 1.0.0
 */
package vn.com.vnext.demo_mvc.dao.entity.converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.AttributeConverter;

/**
 * LocalDateAttributeConverter
 *
 * @author quannl
 * @access public
 * @package vn.com.vnext.demo_mvc.model.entity.converter
 */
public class LocalDateAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
        return localDateTime == null ? null : Timestamp.valueOf(localDateTime);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp sqlTimeStamp) {
        return (sqlTimeStamp == null ? null : sqlTimeStamp.toLocalDateTime());
    }
}
