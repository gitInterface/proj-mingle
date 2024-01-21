package com.ispan.mingle.projmingle.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class ChatPreviewDTO {
    private String userid;
    private String username;
    private String photo;
    private String contents;
    private Date createdTime;
}
