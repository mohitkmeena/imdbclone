package com.mohit.backend.dto;

import java.util.List;

public record MoviePageResponse(List<MovieDto> movieDtos,
                                Integer pageNum,
                                Integer pageSize,
                                long totalElem,
                                int totalPage,
                                boolean isLast) {




    
}
