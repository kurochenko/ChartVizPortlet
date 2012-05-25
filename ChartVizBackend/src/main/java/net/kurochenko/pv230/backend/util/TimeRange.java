package net.kurochenko.pv230.backend.util;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
public enum TimeRange {

    WEEK("menu.week", DateTime.now().minusWeeks(1).toDate()),
    MONTH("menu.month", DateTime.now().minusMonths(1).toDate()),
    YEAR("menu.year", DateTime.now().minusYears(1).toDate());

    private String msgBundleCode;
    private Date from;


    private TimeRange(String msgBundleCode, Date from) {
        this.msgBundleCode = msgBundleCode;
        this.from = from;
    }

    public String getMsgBundleCode() {
        return msgBundleCode;
    }

    public Date getFrom() {
        return from;
    }

    public String getName() {
        return this.toString();
    }
}
