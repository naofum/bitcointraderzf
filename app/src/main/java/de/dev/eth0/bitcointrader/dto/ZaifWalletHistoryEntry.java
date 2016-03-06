package de.dev.eth0.bitcointrader.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.zaif.v1.dto.ZaifValue;

/**
 * Data object representing a Wallet History Entry
 */
public final class ZaifWalletHistoryEntry {

    private final int index;
    private final String date;
    private final String type;
    private final ZaifValue value;
    private final ZaifValue balance;
    private final String info;
    private final String[] link;
    private final ZaifWalletHistoryEntryTrade trade;

    /**
     * Constructor
     *
     * @param index
     * @param date
     * @param type
     * @param value
     * @param balance
     * @param link
     * @param info
     * @param trade
     */
    public ZaifWalletHistoryEntry(@JsonProperty("Index") int index, @JsonProperty("Date") String date, @JsonProperty("Type") String type, @JsonProperty("Info") String info,
                                  @JsonProperty("Link") String[] link, @JsonProperty("Value") ZaifValue value, @JsonProperty("Balance") ZaifValue balance, @JsonProperty("Trade") ZaifWalletHistoryEntryTrade trade) {

        this.index = index;
        this.date = date;
        this.type = type;
        this.info = info;
        this.link = link;
        this.value = value;
        this.balance = balance;
        this.trade = trade;
    }

    public int getIndex() {

        return index;
    }

    public String getDate() {

        return date;
    }

    public String getType() {

        return type;
    }

    public ZaifValue getValue() {

        return value;
    }

    public ZaifValue getBalance() {

        return balance;
    }

    public String getInfo() {

        return info;
    }

    public String[] getLink() {

        return link;
    }

    public ZaifWalletHistoryEntryTrade getTrade() {

        return trade;
    }

    @Override
    public String toString() {

        return "ZaifWalletHistoryEntry{" + "index=" + index + ", date=" + date + ", type=" + type + ", value=" + value + ", balance=" + balance + ", info=" + info + ", link=" + link + '}';
    }

    public static class ZaifWalletHistoryEntryTrade {

        private final String oid;
        private final String tid;
        private final String app;
        private final String properties;
        private final ZaifValue amount;

        public ZaifWalletHistoryEntryTrade(@JsonProperty("oid") String oid, @JsonProperty("tid") String tid, @JsonProperty("app") String app, @JsonProperty("Properties") String properties,
                                            @JsonProperty("Amount") ZaifValue amount) {

            this.oid = oid;
            this.tid = tid;
            this.app = app;
            this.properties = properties;
            this.amount = amount;
        }

        public ZaifValue getAmount() {

            return amount;
        }

        public String getProperties() {

            return properties;
        }

        public String getApp() {

            return app;
        }

        public String getOid() {

            return oid;
        }

        public String getTid() {

            return tid;
        }

        @Override
        public String toString() {

            return "ZaifWalletHistoryEntryTrade{" + "oid=" + oid + ", tid=" + tid + ", app=" + app + ", properties=" + properties + ", amount=" + amount + '}';
        }

    }
}
