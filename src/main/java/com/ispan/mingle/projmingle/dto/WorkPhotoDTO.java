package com.ispan.mingle.projmingle.dto;

import lombok.Data;

import java.util.Date;

@Data
public class WorkPhotoDTO {

    private Integer photoid;
    private Integer workid;
    private byte[] photo;
    private Integer photoSize;
    private String contentType;
    private Date createdAt;
    private Date updatedAt;
    private Boolean isDeleted;

    // Constructors, getters, and setters


}



