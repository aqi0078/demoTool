package com.zdz.mapper;

import com.zdz.bean.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
//@Component
public interface InfoDao extends JpaRepository<Info, Long> {
    List<Info> findAllByIdGreaterThan(Long id);
    List<Info> findByIdIn(List<Long> list);
    List<Info> findByIdAndNameNotNull(Long id);

    List<Info> findByIdAndDateTimeLessThanAndDateTimeGreaterThanEqual(Long id, Date fDate, Date eDate);
    @Modifying
    @Query(value = "update info set name=?1 where id=?2 ", nativeQuery = true)
    public void updateOne(String name, int id);

}
