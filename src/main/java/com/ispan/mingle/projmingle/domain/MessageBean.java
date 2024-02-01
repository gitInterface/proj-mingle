package com.ispan.mingle.projmingle.domain;

import java.util.Date;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Message")
@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageBean {

    @Id
    @Column(name = "messageID", columnDefinition = "int")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer messageID;

    @Column(name = "senderID", nullable = false, columnDefinition = "varchar(100)")
    private String senderID;

    @Column(name = "recieverID", nullable = false, columnDefinition = "varchar(100)")
    private String recieverID;

    @Column(name = "contents", nullable = false, columnDefinition = "nvarchar(3000)")
    private String contents;

    @Column(name = "isRead", nullable = false, columnDefinition = "bit")
    private boolean isRead;

    @Column(name = "replyMessageID", columnDefinition = "int")
    private Integer replyMessageID;

    @Column(name = "createdTime", nullable = false, columnDefinition = "datetime")
    private Date createdTime;

    @Column(name = "isDeleted", nullable = false, columnDefinition = "bit")
    private boolean isDeleted;

    @Column(name = "chatID", columnDefinition = "varchar(100)")
    private String chatid;

    @Column(name = "type", columnDefinition = "int")
    private Integer type;
}