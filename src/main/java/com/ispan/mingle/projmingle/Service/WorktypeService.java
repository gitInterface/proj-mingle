package com.ispan.mingle.projmingle.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.ispan.mingle.projmingle.domain.WorktypeBean;
import com.ispan.mingle.projmingle.repository.WorktypeRepository;

@Service
@Transactional
public class WorktypeService {

    @Autowired
    private WorktypeRepository worktypeRepository;

    public List<WorktypeBean> getAllWorktypes() {
        return worktypeRepository.findAll();
    }
}