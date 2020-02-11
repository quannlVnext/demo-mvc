/**
 * TimestampEntity.java
 * @copyright  Copyright © 2016 VNext software
 * @author     hieuvh
 * @package    vn.com.vnext.demo_mvc.dao.entity.base
 * @version    1.0.0
 */

package vn.com.vnext.demo_mvc.dao.entity.base;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import vn.com.vnext.demo_mvc.dao.entity.converter.LocalDateAttributeConverter;

/**
 * vn.com.vnext.demo_mvc.dao.entity.base - TimestampEntity.
 *
 * @author hieuvh
 * @access public
 * @package vn.com.vnext.demo_mvc.dao.entity.base
 */
@MappedSuperclass
@Getter
@Setter
public abstract class TimestampEntity implements Serializable {

    private static final long serialVersionUID = -4650040856742049667L;

    //@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP")
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDateTime createdDate;

    //@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date", columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP")
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDateTime updatedDate;

    @Transient
    private boolean manualUpdatedDate = false;

    @PreUpdate
    protected void onUpdate() {
        if (!isManualUpdatedDate() || this.updatedDate == null) {
            this.updatedDate = this.getCurrentJapanDate();
        }
    }

    @PrePersist
    protected void onSave() {
        LocalDateTime ldt = this.getCurrentJapanDate();

        this.createdDate = ldt;
        this.updatedDate = ldt;
    }


    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "TimestampEntity [createdDate=" + createdDate + ", updatedDate=" + updatedDate + "]";
    }

    private LocalDateTime getCurrentJapanDate() {
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
