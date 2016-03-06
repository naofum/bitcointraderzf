package de.dev.eth0.bitcointrader.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing a Zaif Wallet History
 */
public final class ZaifWalletHistory {

    private final int records;
    private final ZaifWalletHistoryEntry[] zaifWalletHistoryEntries;
    private final int currentPage;
    private final int maxPage;
    private final int maxResults;

    /**
     * Constructor
     *
     * @param records
     * @param zaifWalletHistoryEntries
     */
    public ZaifWalletHistory(@JsonProperty("records") int records, @JsonProperty("result") ZaifWalletHistoryEntry[] zaifWalletHistoryEntries, @JsonProperty("current_page") int currentPage,
                              @JsonProperty("max_page") int maxPage, @JsonProperty("max_results") int maxResults) {

        this.records = records;
        this.zaifWalletHistoryEntries = zaifWalletHistoryEntries;
        this.currentPage = currentPage;
        this.maxPage = maxPage;
        this.maxResults = maxResults;
    }

    public int getRecords() {

        return records;
    }

    public ZaifWalletHistoryEntry[] getZaifWalletHistoryEntries() {

        return zaifWalletHistoryEntries;
    }

    public int getCurrentPage() {

        return currentPage;
    }

    public int getMaxPage() {

        return maxPage;
    }

    public int getMaxResults() {

        return maxResults;
    }
}
