package com.zdz.test;

import com.zdz.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service("fundCompensatoryJobService")
public class FundCompensatoryJob implements FundCompensatoryJobService{

    @Autowired
    FundBehalfAccountService fundBehalfAccountService;

    @Autowired
    PayOrderService payOrderService;
    @Autowired
    FundBehalfSettleSummeryService fundBehalfSettleSummeryService;
    @Autowired
    FundBehalfConfigureService fundBehalfConfigureService;

    private final static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
    public void execute() {

        //1.获取需要代偿的账户：资金方list
        Map<Long,List<FundBehalfConfigure>> dataMap=getFundData();
        if(dataMap==null||dataMap.size()==0){
            return ;
        }
        System.out.println("dataMap==="+dataMap);
        //2.循环账户。
        //List<Map<String,Object>> list=new ArrayList<>();//待生产代偿订单数据,可以一个账户创建一个，也可以共用
        try{
            for (Long accountId:dataMap.keySet()){
                FundBehalfAccount accountInfo=fundBehalfAccountService.findByFundAccountId(accountId);
                //   *
                //2.1获取账户余额
                BigDecimal balance =new BigDecimal("10000");// payService.queryMerchantBalance(accountInfo.getCardNo());
                //2.2校验账户余额
                if (balance == null || balance.compareTo(BigDecimal.ZERO) < 0) {
//                    log.error(LOG_PREFIX + "未获取到余额数据或者余额小于0 账户:{}", accountInfo.getCardNo());
//                    mailService.sendMailV3(mailSender,tomail,"代发代付没查询到余额数据，账户:{}",accountInfo.getCardNo());
                    continue;
                }
                BigDecimal behalfSumAmount=new BigDecimal(0);//累加总代偿金额准备与账户余额判断

                //2.3循环资金方
                List<Map<String,Object>> list=new ArrayList<>();//待生产代偿订单数据,可以一个账户创建一个，也可以共用

                for (FundBehalfConfigure fundBehalfConfigure:dataMap.get(accountId)) {
                    Long fundingCorpId=fundBehalfConfigure.getFundId();
                    //2.3.1资金方配置验证
//                    FundingConfig fundingConfig = fundingConfigService.getFundingConfig(fundingCorpId);
//                    if (fundingConfig == null) {
//                        log.error(LOG_PREFIX + "未获取到fundingConfig fundingCorpId:{}", fundingCorpId);
//                        continue;
//                    }
                    list=getData(list,behalfSumAmount,fundBehalfConfigure,fundingCorpId);//获取资金方本次代偿数据
                    //获取历史补扣数据
                    if (fundBehalfConfigure.getHisCompensatoryStatus()==1){
                        list=getHisData(list,fundBehalfConfigure,fundingCorpId,fundBehalfConfigure.getHisCompensatoryDay(),behalfSumAmount);
                    }
//                    log.info(LOG_PREFIX + "资金方:{}，数据已获取", fundingCorpId);
                }

                if(null!=list&&list.size()>0){
                    //2.4判断代偿总金额和余额
                    if (balance.compareTo(behalfSumAmount) < 0) {
                        //2.4.1发送邮件通知余额不足
//                        String body = String.format(TMPL,list.get(0).get("fundingCorpName"),balance,behalfSumAmount,accountInfo.getCardNo());
//                        mailService.sendMailV3(mailSender,tomail, "【系统监控】".concat(accountInfo.getCardNo()).concat(" 余额不足请注意!!"), body);
                        list.clear();//帮助垃圾回收节约空间
                        continue;
                    }
                    for (int i = 0; i < list.size(); i++) {
                        FundBehalfSettleSummery summery =(FundBehalfSettleSummery)list.get(i).get("summery");
//                        log.info(LOG_PREFIX + "准备创建代付订单 fundingCorpId:{},balance:{},needAmount:{},merchantOrderNo:{}",list.get(i).get("fundingCorpId"),balance,list.get(i).get("behalfSumAmount"),list.get(i).get("merchantOrderNo"));
                        String payOrderNo = payOrderService.createSimplePayOrder(String.valueOf(list.get(i).get("merchantOrderNo")), OrderType.TONGYONG_DEDUCTION,summery.getBehalfSumAmount(),"代付代发代偿".concat(String.valueOf(list.get(i).get("merchantOrderNo"))),accountInfo.getCardNo());
                        summery.setMerOrderNo(String.valueOf(list.get(i).get("merchantOrderNo")));
                        fundBehalfSettleSummeryService.save(summery);
//                        log.info(LOG_PREFIX + "成功创建支付订单 fundingCorpId:{},merchantOrderNo:{},payOrderNo:{}",list.get(i).get("fundingCorpId"),list.get(i).get("merchantOrderNo"),payOrderNo);
                    }
                    list.clear();//帮助垃圾回收节约空间
                }
            }
        } catch (Exception e) {
//            log.error(LOG_PREFIX + "出现异常 error:{}",e);
            //list.clear();//帮助垃圾回收节约空间
            System.out.println(e);
            return ;
        }


    }

    public Map<String,List<FundBehalfConfigure>> getAllFundData(){
        Map<String,List<FundBehalfConfigure>> dataMap=new HashMap<>();
        List<FundBehalfConfigure> list =fundBehalfConfigureService.findAllByFundStatus(1);
        Map<Long,Long> map=new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            FundBehalfConfigure fc=list.get(i);

            String[] behalfAt=fc.getBehalfAt().split("\\|");
            for (int j = 0; j < behalfAt.length; j++) {
                if (map.get(fc.getFundId())==null){
                    map.put(fc.getFundId(),fc.getFundId());
                }else{
                    continue;
                }
                if (dataMap.get(behalfAt[j])==null){
                    List<FundBehalfConfigure> tmp=new ArrayList<>();
                    tmp.add(fc);
                    dataMap.put(behalfAt[j],tmp);
                }else {
                    dataMap.get(behalfAt[j]).add(fc);
                }
            }
        }

        return dataMap;
    }
    /**
     * 判断获取需要执行的账户：资金方(1：n)
     * @return
     */
    public Map<Long,List<FundBehalfConfigure>> getFundData(){
        //1.获取需要代偿的账户：资金方list
        Map<Long,List<FundBehalfConfigure>> dataMap=new HashMap<>();
        //去Redis获取最后一次执行时间
        String oldTime="00:00";

        Map<String,List<FundBehalfConfigure>> allDataMap=getAllFundData();//获取数据，时间：数据
        for (String str:allDataMap.keySet()){
            try {
                System.out.println(simpleDateFormat.parse(oldTime).compareTo(simpleDateFormat.parse(str)));
                System.out.println(simpleDateFormat.parse(str).compareTo(new Date()));
                if(simpleDateFormat.parse(oldTime).compareTo(simpleDateFormat.parse(str))<0&&simpleDateFormat.parse(str).compareTo(new Date())<=0){
                    for (int i = 0; i < allDataMap.get(str).size(); i++) {
                        //清洗数据到账户：数据
                        FundBehalfConfigure fcf=allDataMap.get(str).get(i);
                        if (dataMap.get(fcf.getFundAccountId())==null){
                            List<FundBehalfConfigure> tmp=new ArrayList<>();
                            tmp.add(fcf);
                            dataMap.put(fcf.getFundAccountId(),tmp);
                        }else{
                            dataMap.get(fcf.getFundAccountId()).add(fcf);
                        }
                    }
                }
            } catch (ParseException e) {
//                log.error(LOG_PREFIX + " 获取时间计算错误");
            }
        }
        String str=simpleDateFormat.format(new Date());
//        redisTemplate.opsForValue().getAndSet(FUND_KEY+"time",str);//设置Redis新的最新执行时间
        return dataMap;
//        //获取当前时间和上次执行的时间内需要执行的数据
//        String str=simpleDateFormat.format(new Date());
//        List<FundBehalfConfigure> list =fundBehalfConfigureService.findAllByFundStatusEqualsAndBehalfAtLessThanEqualAndBehalfAtGreaterThan(1,str,oldTime);
//        if (list!=null&&list.size()>0){//存在需要执行的数据，进行组装，并设置Redis新的最新执行时间
//            //已账户为维度，组装资金方
//            for (int i = 0; i < list.size(); i++) {
//                if (dataMap.get(list.get(i).getFundAccount())==null){
//                    List<FundBehalfConfigure> fundList=new ArrayList<>();
//                    fundList.add(list.get(i));
//                    dataMap.put(list.get(i).getFundAccount(),fundList);
//                }else{
//                    dataMap.get(list.get(i).getFundAccount()).add(list.get(i));
//                }
//            }
//            redisTemplate.opsForValue().getAndSet(FUND_KEY+"time",str);//设置Redis新的最新执行时间
//            return dataMap;
//        }
    }

    /**
     * 查找历史支付失败数据，进行重新计算
     * @param list
     * @param fundBehalfConfigure
     * @param fundingCorpId
     * @param hisday
     * @param behalfSumAmount
     * @return
     */
    public List<Map<String,Object>> getHisData(List<Map<String,Object>> list,FundBehalfConfigure fundBehalfConfigure,Long fundingCorpId,int hisday,BigDecimal behalfSumAmount){
        //获取历史数据区间
        LocalDate nowLocalDate = LocalDate.now();
        Date dateNow = Date.from(nowLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        Calendar now = Calendar.getInstance();
        now.set(Calendar.DATE, now.get(Calendar.DATE) - hisday);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY-MM-DD");
        Date f=null;
        try {
            f=simpleDateFormat.parse(simpleDateFormat.format(now.getTime()));
        } catch (ParseException e) {
//            log.error(LOG_PREFIX + "日期计算出现异常 error:{}",e);
        }
        //获取历史支付失败需要重新发送的数据
        List<FundBehalfSettleSummery> summeryList =null;
        summeryList = fundBehalfSettleSummeryService.findAllByHisData(fundingCorpId,dateNow,f);
        if (summeryList!=null){
            for (int i = 0; i < summeryList.size(); i++) {
                FundBehalfSettleSummery summery=summeryList.get(i);
                //2.3.4累加代偿金额
                behalfSumAmount=behalfSumAmount.add(summery.getBehalfSumAmount());
                //2.3.5组装待支付订单数据
                list.add(createFundData(summery,fundingCorpId,fundBehalfConfigure.getFundName()));
            }
        }
        return list;
    }

    public Map<String,Object> createFundData(FundBehalfSettleSummery summery,Long fundingCorpId,String fundName){
        Map<String,Object> map=new HashMap<>();
        map.put("merchantOrderNo",summery.getMerOrderNo());
        map.put("behalfSumAmount",summery.getBehalfSumAmount());
        map.put("fundingCorpId",fundingCorpId);
        map.put("fundingCorpName",fundName);
        map.put("summery",summery);
        return map;
    }
    /**
     * 获取资金方本次代偿数据
     * @param list
     * @param behalfSumAmount
     * @param fundBehalfConfigure
     * @param fundingCorpId
     * @return
     */
    public List<Map<String,Object>> getData(List<Map<String,Object>> list,BigDecimal behalfSumAmount,FundBehalfConfigure fundBehalfConfigure,Long fundingCorpId){
        //2.3.2获取校验当天代偿数据
        LocalDate nowLocalDate = LocalDate.now();
        Date dateNow = Date.from(nowLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        String merchantOrderNo = nowLocalDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")).concat("funddeduction").concat(String.valueOf(fundingCorpId));
        ThirdPartyPayOrder payOrder = payOrderService.findByMerOrderNo(merchantOrderNo, OrderType.TONGYONG_DEDUCTION.getCode());

        FundBehalfSettleSummery summery =null;

        if (payOrder != null) {//第2次以上补代偿
//            log.info(LOG_PREFIX + "已经处理过了 pass; fundingCorpId:{}",fundingCorpId);
            summery=fundBehalfSettleSummeryService.findTodayFailData(fundingCorpId,dateNow);
            if (summery==null){
                return list;
            }
        }else{//第一次代偿
            //2.3.3校验代偿明细结算信息,where 资金方 and 日期 and 未结算
            // String fundingCorpName = fundingConfig.getFundingCorpName();
            int status=BehalftSettleStatus.not_settled.getValue();
            summery = fundBehalfSettleSummeryService.findByFundIdAndBehalfAtAndSettleStatus(fundingCorpId,dateNow, status);
            if (summery == null) {
//                log.error(LOG_PREFIX + "未获取到代偿数据 fundingCorpId:{}", fundingCorpId);
                return list;
            }
        }

        //2.3.4累加代偿金额
        behalfSumAmount=behalfSumAmount.add(summery.getBehalfSumAmount());
        //2.3.5组装待支付订单数据
        list.add(createFundData(summery,fundingCorpId,fundBehalfConfigure.getFundName()));

        return list;
    }




}
