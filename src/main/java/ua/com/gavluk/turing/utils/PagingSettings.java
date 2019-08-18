package ua.com.gavluk.turing.utils;


public class PagingSettings {

    private final long page;
    private final int limit;
    private final String sortBy;
    private final SortOrder sortOrder;
    private final boolean withSorting;

    public PagingSettings(long page, int limit) {
        this.page = page;
        this.limit = limit;
        this.withSorting = false;
        this.sortBy = null;
        this.sortOrder = null;
    }

    public PagingSettings(long page, int limit, String sortBy, SortOrder sortOrder) {
        this.page = page;
        this.limit = limit;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
        this.withSorting = true;
    }

    public boolean isWithSorting() {
        return withSorting;
    }

    public long getPage() {
        return this.page;
    }

    public int getLimit() {
        return this.limit;
    }

    public SortOrder getSortOrder() {
        return this.sortOrder;
    }

    public String getSortBy() {
        return this.sortBy;
    }
}
