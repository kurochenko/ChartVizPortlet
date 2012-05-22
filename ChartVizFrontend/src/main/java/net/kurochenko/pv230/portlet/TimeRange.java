package net.kurochenko.pv230.portlet;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
public enum TimeRange {

    WEEK("menu.week"),
    MONTH("menu.month"),
    YEAR("menu.year");

    private String msgBundleCode;


    private TimeRange(String msgBundleCode) {
        this.msgBundleCode = msgBundleCode;
    }

    public String getMsgBundleCode() {
        return msgBundleCode;
    }

    public String getName() {
        return this.toString();
    }
}
