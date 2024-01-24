package com.ispan.mingle.projmingle.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.ispan.mingle.projmingle.domain.WorkTypeBean;
import com.ispan.mingle.projmingle.repository.WorkTypeRepository;

@Service
@Transactional
public class WorkTypeService {

    @Autowired
    private WorkTypeRepository workTypeRepository;

    public List<WorkTypeBean> getAllWorkTypes() {
        return workTypeRepository.findAll();
    }
}