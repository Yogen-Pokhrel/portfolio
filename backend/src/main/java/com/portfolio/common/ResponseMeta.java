package com.portfolio.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMeta {
    private int page;
    private int perPage;
    private long total;
    private int pageCount;
}
