package com.aluminium.acoe.common.resource;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.PageRequest;

@Data
public class PageResource {
    @Schema(description = "page")
    private Integer page;
    @Schema(description = "sizePerPage")
    private Integer sizePerPage;

    /**
     * Get the page request based on the values in this resource.
     *
     * @return the page request
     */
    @Hidden // Hide this operation from Swagger UI
    public PageRequest getPageInfo() {
        if (this.getPage() == null && this.getSizePerPage() == null) {
            this.setPage(0);
            this.setSizePerPage(10);
        }

        return PageRequest.of(this.getPage(), this.getSizePerPage());
    }
}
