package com.paulblog.httprequestdto;

import static java.lang.Math.max;
import static java.lang.Math.min;

import lombok.Builder;
import lombok.Getter;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.httprequestdto
 * @fileName : PostSearch
 * @date : 2025-03-02
 */
@Getter
@Builder
public class PostSearch {

    private static final int MAX_SIZE = 2000;

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10;

    public long getOffset(){
        return max(0,page - 1) * min(size,MAX_SIZE);
    }
}
