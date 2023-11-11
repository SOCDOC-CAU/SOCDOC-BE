package com.cau.socdoc.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Meta {

    private SameName same_name;
    private Integer pageable_count;
    private Integer total_count;
    private Boolean is_end;
}
