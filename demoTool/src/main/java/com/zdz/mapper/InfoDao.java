package com.zdz.mapper;

import com.zdz.bean.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//@Component
public interface InfoDao extends JpaRepository<Info, Long> {
    List<Info> findAllByIdGreaterThan(Long id);
    List<Info> findByIdIn(List<Long> list);
}
