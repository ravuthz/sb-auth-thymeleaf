package com.session.auth.object;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Min;

@Getter
@Setter
public class PageRequestObject {
    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_SIZE = 5;

    @Min(1)
    private int page = DEFAULT_PAGE;

    @Min(1)
    private int size = DEFAULT_SIZE;

    public PageRequest toPageRequest() {
        return PageRequest.of(page - 1, size, Sort.by("id").descending());
    }

    public PageRequest toPageRequest(Sort sort) {
        // PageRequest.of(request.getPage() - 1, rowPerPage, Sort.by("id").ascending())
        return PageRequest.of(page - 1, size, sort);
    }
}
