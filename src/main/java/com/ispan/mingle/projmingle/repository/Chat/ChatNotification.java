package com.ispan.mingle.projmingle.repository.Chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatNotification {
    private String chatid;
    private String senderID;
    private String recieverID;
    private String contents;

}
