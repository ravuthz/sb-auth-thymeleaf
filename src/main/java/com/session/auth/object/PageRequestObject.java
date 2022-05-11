package com.session.auth.object;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Min;

@Getter
@Setter
public class PageRequestObject {
    @Min(1)
    private int page = 1;

    @Min(1)
    private int size = 3;

    public PageRequest toPageRequest() {
        return PageRequest.of(page - 1, size);
    }

    public PageRequest toPageRequest(Sort sort) {
        // PageRequest.of(request.getPage() - 1, rowPerPage, Sort.by("id").ascending())
        return PageRequest.of(page - 1, size, sort);
    }
}
