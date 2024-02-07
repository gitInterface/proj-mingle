package com.ispan.mingle.projmingle.dto;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.Multipart;
import jakarta.servlet.http.Part;
import lombok.Data;

@Data
@Component
public class ReviewReplyDTO {

    private Integer reviewid;
    private String reply;
    private MultipartFile[] photo;
    private Date replyCreatedAt;
    private Date replyUpdatedAt;

}
