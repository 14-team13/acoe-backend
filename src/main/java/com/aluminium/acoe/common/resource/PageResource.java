package com.aluminium.acoe.common.resource;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.PageRequest;

@Data
public class PageResource {
    @Schema(description = "page")
    private Integer page;
    @Schema(description = "sizePerPage")
    private Integer sizePerPage;

    public PageRequest getPageInfo() {
        if (this.getPage() == null && this.getSizePerPage() == null) {
            this.setPage(1);
            this.setSizePerPage(1);
        }

        return PageRequest.of(this.getPage(), this.getSizePerPage());
    }


}
