package ua.com.gavluk.turing.ecommerce.api;

import javax.validation.constraints.Positive;

public class PagingDTO {

    @Positive
    private Long page = 1L;

    @Positive
    private Integer limit = 20;

    public PagingDTO() {
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
