package com.landak.develab.controller.dto;

import lombok.Data;

// there should be an existing library for this
public record Pageable(int page, int pageSize) {

    public Pageable(final int page, final int pageSize) {

        if (page <= 0) {
            this.page = 0;
        } else {
            this.page = page - 1;
        }

        if (pageSize <= 0) {
            this.pageSize = 10;
        } else {
            this.pageSize = pageSize;
        }
    }

    public int getOffset() {
        return this.page * this.pageSize;
    }
}
