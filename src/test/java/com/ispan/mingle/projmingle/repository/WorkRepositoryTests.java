package com.ispan.mingle.projmingle.repository;

import org.hibernate.boot.registry.classloading.spi.ClassLoaderService.Work;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ispan.mingle.projmingle.domain.WorkBean;

@SpringBootTest
public class WorkRepositoryTests {
    
    @Autowired
    private WorkRepository workRepository;
    @Test
    public void test() {
         WorkBean work = workRepository.findById(2).orElse(null);
         System.err.println(work);
    }

}
