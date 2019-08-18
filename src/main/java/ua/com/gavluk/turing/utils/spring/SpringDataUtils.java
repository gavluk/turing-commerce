package ua.com.gavluk.turing.utils.spring;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ua.com.gavluk.turing.utils.PageableList;
import ua.com.gavluk.turing.utils.PagingSettings;

public class SpringDataUtils {

    public static Pageable buildPageable(PagingSettings paging) {

        if (paging.isWithSorting()) {
            return PageRequest.of(
                    (int) paging.getPage() - 1, // Spring uses zero-based page index
                    paging.getLimit(),
                    new Sort(Sort.Direction.fromString(paging.getSortOrder().name()), paging.getSortBy())
            );
        }
        else {
            return PageRequest.of(
                    (int) paging.getPage() - 1, // Spring uses zero-based page index
                    paging.getLimit()
            );
        }
    }

    public static <T> PageableList<T> buildPageableList(Page<T> all, PagingSettings originalSetting) {
        return new PageableList<T>(all.getContent(), originalSetting, all.getTotalElements());
    }
}
