package com.ispan.mingle.projmingle.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ReviewReplyDTO {

    private Integer reviewid;
    private String reply;
    private Date replyCreatedAt;
    private Date replyUpdatedAt;

}
