package net.kurochenko.pv230.backend.model;

import javax.persistence.*;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
@Entity
public class Config {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String lastTimeRange;

    @OneToOne
    private Currency lastCurrency;

    private int imgWidth;
    private int imgHeight;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastTimeRange() {
        return lastTimeRange;
    }

    public void setLastTimeRange(String lastTimeRange) {
        this.lastTimeRange = lastTimeRange;
    }

    public Currency getLastCurrency() {
        return lastCurrency;
    }

    public void setLastCurrency(Currency lastCurrency) {
        this.lastCurrency = lastCurrency;
    }

    public int getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(int imgWidth) {
        this.imgWidth = imgWidth;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Config config = (Config) o;

        if (imgHeight != config.imgHeight) return false;
        if (imgWidth != config.imgWidth) return false;
        if (id != null ? !id.equals(config.id) : config.id != null) return false;
        if (lastCurrency != null ? !lastCurrency.equals(config.lastCurrency) : config.lastCurrency != null)
            return false;
        if (lastTimeRange != null ? !lastTimeRange.equals(config.lastTimeRange) : config.lastTimeRange != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (lastTimeRange != null ? lastTimeRange.hashCode() : 0);
        result = 31 * result + (lastCurrency != null ? lastCurrency.hashCode() : 0);
        result = 31 * result + imgWidth;
        result = 31 * result + imgHeight;
        return result;
    }
}
