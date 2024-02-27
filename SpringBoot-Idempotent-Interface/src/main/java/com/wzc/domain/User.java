package com.wzc.domain;

import com.wzc.annotation.RequestKeyParam;
import lombok.Data;

import java.util.List;

@Data
public class User {

    @RequestKeyParam
    private String userName;
    @RequestKeyParam
    private String userPhone;

    private List<Long> roleIdList;
}
