package ua.com.gavluk.turing.utils;

import java.util.Collections;
import java.util.List;

/**
 * Ready to Turing Commerce API format pagination & items container
 * @param <T> type of pageable items
 */
public class PageableList<T> {

    private final PaginationMeta paginationMeta;
    private final List<? extends T> items;


    public PageableList(List<T> items, PagingSettings pagingSettings, long totalItems) {
        this.items = items;
        this.paginationMeta = new PaginationMeta(
                pagingSettings.getPage(),
                pagingSettings.getLimit(),
                totalItems / pagingSettings.getLimit()
                        + (totalItems % pagingSettings.getLimit() > 0 ? 1 : 0),
                totalItems
        );
    }

    public PaginationMeta getPaginationMeta() {
        return this.paginationMeta;
    }

    public List<T> getRows() {
        return Collections.unmodifiableList(this.items) ;
    }

    static class PaginationMeta {
        long currentPage;
        int currentPageSize;
        long totalPages;
        long totalRecords;

        PaginationMeta(long currentPage, int currentPageSize, long totalPages, long totalRecords) {
            this.currentPage = currentPage;
            this.currentPageSize = currentPageSize;
            this.totalPages = totalPages;
            this.totalRecords = totalRecords;
        }

        public long getCurrentPage() {
            return currentPage;
        }

        public int getCurrentPageSize() {
            return currentPageSize;
        }

        public long getTotalPages() {
            return totalPages;
        }

        public long getTotalRecords() {
            return totalRecords;
        }
    }

}
