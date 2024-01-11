package com.ispan.mingle.projmingle.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.mingle.projmingle.repository.ChatRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ChatAjaxService {
    @Autowired
    private ChatRepository chatRepository = null;
}
