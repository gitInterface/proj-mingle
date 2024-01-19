package com.ispan.mingle.projmingle.domain;

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
@Table(name = "ChatRoom")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomBean {
    @Id
    @Column(name = "roomID", columnDefinition = "int")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roomid;
    @Column(name = "chatID", columnDefinition = "varchar", length = 100)
    private String chatid;
    @Column(name = "senderID", columnDefinition = "varchar", length = 100)
    private String senderid;

    @Column(name = "recieverID", columnDefinition = "varchar", length = 100)
    private String recieverid;
}
