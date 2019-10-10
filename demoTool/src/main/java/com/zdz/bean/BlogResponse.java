package com.zdz.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@Setter
@Getter
public class BlogResponse {
    private String merchantno;
    private String errorCode;

    private String errorMsg;


}