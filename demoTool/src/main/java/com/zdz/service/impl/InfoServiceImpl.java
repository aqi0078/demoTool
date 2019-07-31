package com.zdz.service.impl;

import com.zdz.bean.Info;
import com.zdz.mapper.InfoDao;
import com.zdz.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("info2Service")
public class InfoServiceImpl implements InfoService {

    @Autowired
    InfoDao infodao;
    public void printData(){
        System.out.println("67890===");
        Optional<Info> infoOptional=infodao.findById(1L);
        if (infoOptional.isPresent()){
            Info info=infoOptional.get();
            System.out.println(info.getId()+"==="+info.getName());
        }

        List<Info> list=infodao.findAllByIdGreaterThan(1L);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId()+"==list==="+list.get(i).getName());
        }
        List<Long> listId=new ArrayList();
        listId.add(1L);
        listId.add(2L);
        listId.add(3L);

        List<Info> listData=infodao.findByIdIn(listId);
        for (int i = 0; i < listData.size(); i++) {
            System.out.println(listData.get(i).getId()+"==listData==="+listData.get(i).getName());
        }



    }
}
