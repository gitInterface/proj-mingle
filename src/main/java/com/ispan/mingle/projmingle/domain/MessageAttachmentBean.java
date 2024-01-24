package com.ispan.mingle.projmingle.domain;

import java.util.Date;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "MessageAttachment")
@Component
public class MessageAttachmentBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "messageAttachmentID", nullable = false, columnDefinition = "INT")
    private int messageAttachmentID;

    @Column(name = "fk_messageID", nullable = false, columnDefinition = "INT")
    private int fk_messageID;

    @Column(name = "contentType", nullable = false, columnDefinition = "VARCHAR(50)")
    private String contentType;

    @Column(name = "fileSize", nullable = false, columnDefinition = "INT")
    private int fileSize;

    @Column(name = "relativePath", columnDefinition = "VARCHAR(3000)")
    private String relativePath;

    @Column(name = "createdAt", nullable = false, columnDefinition = "DATETIME")
    private Date createdAt;

    @Column(name = "isDeleted", nullable = false, columnDefinition = "BIT")
    private boolean isDeleted;

}