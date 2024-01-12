package com.ispan.mingle.projmingle.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.mingle.projmingle.domain.MessageBean;
import com.ispan.mingle.projmingle.repository.ChatRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ChatAjaxService {
    @Autowired
    private ChatRepository chatRepository = null;

    public List<MessageBean> getChatHistory(String recieverID, String serderID) {
        return null;
    }
}
