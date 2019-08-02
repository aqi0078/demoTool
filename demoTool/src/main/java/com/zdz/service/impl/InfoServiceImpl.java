package com.zdz.service.impl;

import com.zdz.bean.Data;
import com.zdz.bean.FundBehalfConfigure;
import com.zdz.bean.Info;
import com.zdz.mapper.FundBehalfConfigureRepository;
import com.zdz.mapper.InfoDao;
import com.zdz.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.beans.Transient;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service("info2Service")
public class InfoServiceImpl implements InfoService {

    public static final Integer loanMerchantId=70;
    @Autowired
    InfoDao infodao;
    @Autowired
    FundBehalfConfigureRepository fundBehalfConfigureRepository;
    //@Transactional
    public void printData(){
        System.out.println("67890===");
        List<String> list=new ArrayList<>();
        list.add("710");
        list.add("790");
        List<FundBehalfConfigure> fc=fundBehalfConfigureRepository.findByFailData(list,"111");
        System.out.println(fc);

        List<String> fc1=fundBehalfConfigureRepository.findByFailDataId(list,"111");
        System.out.println(fc1);

//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
//
//        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("YYYY-MM-DD");
//        String str=null;
//        str=simpleDateFormat.format(new Date());
//
//        try {
//            long exTime=simpleDateFormat.parse("23:59").getTime()-
//                    simpleDateFormat.parse(simpleDateFormat.format(new Date())).getTime();
//            System.out.println("==exTime====="+exTime);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        List<FundBehalfConfigure> list=fundBehalfConfigureRepository.findAllByFundStatusEqualsAndBehalfAtLessThanEqualAndBehalfAtGreaterThan(1,str,"00:00");
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        }
//        Calendar now = Calendar.getInstance();
//        now.set(Calendar.DATE, now.get(Calendar.DATE) - 5);
//        System.out.println("===Calendar==========="+now.getTime());
//        Date f=null;
//        try {
//            f=simpleDateFormat1.parse(simpleDateFormat1.format(now.getTime()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        LocalDate nowLocalDate = LocalDate.now();
//        Date dateNow = Date.from(nowLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
//
//        System.out.println("===dateNow==========="+dateNow);
//
//        List<Info> list2=infodao.findByIdAndDateTimeLessThanAndDateTimeGreaterThanEqual(1L,dateNow,f);
//        List<Info> list3=infodao.findByIdAndDateTimeLessThanAndDateTimeGreaterThanEqual(1L,dateNow, now.getTime());
//
//        System.out.println("===list2==========="+list2);
//        System.out.println("===list3==========="+list3);
//        Optional<Info> infoOptional=infodao.findById(1L);
//        if (infoOptional.isPresent()){
//            Info info=infoOptional.get();
//            System.out.println(info.getId()+"==="+info.getName());
//            if(info.getId()==1){
//                info.setName("zdzzdz");
//                infodao.save(info);
//            }
//        }

//        List<Info> list=infodao.findAllByIdGreaterThan(1L);
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i).getId()+"==list==="+list.get(i).getName());
//        }
//        List<Long> listId=new ArrayList();
//        listId.add(1L);
//        listId.add(2L);
//        listId.add(3L);
//
//        List<Info> listData=infodao.findByIdIn(listId);
//        for (int i = 0; i < listData.size(); i++) {
//            System.out.println(listData.get(i).getId()+"==listData==="+listData.get(i).getName());
//        }
//
//        //infodao.updateOne("zdz",1);
//        List<Info> list1=infodao.findByIdAndNameNotNull(1L);
//        for (int i = 0; i < list1.size(); i++) {
//            System.out.println(list1.get(i).getId()+"==list1==="+list1.get(i).getName());
//        }
    }

    @Override
    public Integer getloanMerchantId() {
        return loanMerchantId;
    }
}
