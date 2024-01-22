package com.ispan.mingle.projmingle.Service.chat;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ispan.mingle.projmingle.domain.VolunteerDetailBean;
import com.ispan.mingle.projmingle.repository.Chat.ChatUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatUserService {
    private final ChatUserRepository repository;

    public void saveUser(VolunteerDetailBean user) {
        user.setStatus('1');
        repository.save(user);

    }

    public void disconnect(VolunteerDetailBean user) {
        var storedUser = repository.findById(user.getUserid())
                .orElse(null);
        if (storedUser != null) {
            storedUser.setStatus('0');
            repository.save(storedUser);
        }
    }

    public List<VolunteerDetailBean> findConnectedUsers() {
        return repository.findAllByStatus('1');
    }
}
