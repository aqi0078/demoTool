//package com.zdz.bean;
//
//import cn.qg.clotho.entity.clotho.FundBehalfSettleSummery;
//import cn.qg.clotho.entity.clotho.ThirdPartyPayOrder;
//import cn.qg.clotho.model.behalf.BehalftSettleStatus;
//import cn.qg.clotho.model.funds.common.OrderType;
//import cn.qg.clotho.repository.clotho.IFundBehalfConfigureRepository;
//import cn.qg.clotho.repository.clotho.IFundBehalfSettleSummeryRepository;
//import cn.qg.clotho.repository.clotho.IThirdPartyPayOrderRepository;
//import cn.qg.clotho.service.basis.IMailUtilService;
//import cn.qg.clotho.service.funds.IFundRepayService;
//import cn.qg.clotho.service.interact.IFundingConfigService;
//import cn.qg.clotho.service.interact.model.FundingConfig;
//import cn.qg.clotho.service.payapi.IPayOrderService;
//import cn.qg.clotho.service.payapi.IPayService;
//import com.dangdang.ddframe.job.api.ShardingContext;
//import com.dangdang.ddframe.job.api.simple.SimpleJob;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.mail.MailSender;
//
//import java.math.BigDecimal;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//
///**
// * 资金方代偿模板主流程方法
// */
//@Slf4j
//public class FundRepayJob implements SimpleJob {
//
//
//    private final static String LOG_PREFIX = "[FundRepayJob] ";
//
//    @Autowired
//    private IPayService payService;
//
//    @Autowired
//    private IMailUtilService mailService;
//    @Autowired
//    @Qualifier("zhongbangMailSender")
//    private MailSender mailSender;
//
//    @Value("${zhongbang.mail.tomail:''}")
//    private String tomail;
//
//    @Autowired
//    private IFundingConfigService fundingConfigService;
//
//    @Autowired
//    private IThirdPartyPayOrderRepository thPayOrderRepository;
//
//    @Autowired
//    private IFundBehalfSettleSummeryRepository fundBehalfSettleSummeryRepository;
//
//    @Autowired
//    private IPayOrderService payOrderService;
//
//    @Autowired
//    private IFundBehalfConfigureRepository IFundBehalfConfigureRepository;
//
//    @Autowired
//    Map<String,IFundRepayService> map;
//
//
//    @Autowired
//    @Qualifier("stringRedisTemplate")
//    private RedisTemplate<String, String> redisTemplate;
//
//    public static final String TMPL ="<html><head></head><body><h2>由系统监测到%s的账户余额不足代偿,请留意.</h2><h3>当前余额：<font color=red>%s</font> 元</h3><h3>放款金额：%s 元</h3><p>TIPS:如果账户%s用尽,会影响代偿功能.</p></body></html>";
//
//    private final static String FUND_KEY = "fund:configure:";
//
//
//    private final static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
//
//    @Override
//    //@Transactional
//    public void execute(ShardingContext shardingContext) {
//        log.info(LOG_PREFIX + "shardingContext:{}", shardingContext);
//
//        //1.获取需要代偿的账户：资金方list
//        Map<String,List<String>> dataMap=getFundData();
//        if(dataMap==null){
//            return ;
//        }
//
//        //2.循环账户。
//        //List<Map<String,Object>> list=new ArrayList<>();//待生产代偿订单数据,可以一个账户创建一个，也可以共用
//        try{
//            for (String account:dataMap.keySet()){
//                //2.1获取账户余额
//                BigDecimal balance = payService.queryMerchantBalance(account);
//                //2.2校验账户余额
//                if (balance == null || balance.compareTo(BigDecimal.ZERO) < 0) {
//                    log.error(LOG_PREFIX + "未获取到余额数据或者余额小于0 fundingCorpId:{}", dataMap.get(account));
//                    mailService.sendMailV3(mailSender,tomail,"代发代付没查询到余额数据",String.valueOf(dataMap.get(account)));
//                    continue;
//                }
//                BigDecimal behalfSumAmount=new BigDecimal(0);//累加总代偿金额准备与账户余额判断
//
//                //2.3循环资金方
//                List<Map<String,Object>> list=new ArrayList<>();//待生产代偿订单数据,可以一个账户创建一个，也可以共用
//                for (int i = 0; i < dataMap.get(account).size(); i++) {
//                    //Long fundingCorpId=dataMap.get(account).get(i);//账户下的其中一个资金方
//                    IFundRepayService fundRepayService=map.get(dataMap.get(account).get(i)+"FundRepayService");//从map里获取资金方实现类
//                    Long fundingCorpId=fundRepayService.getFundingCorpId();//账户下的其中一个资金方
//                    //2.3.1资金方配置验证
//                    FundingConfig fundingConfig = fundingConfigService.getFundingConfig(fundingCorpId);
//                    if (fundingConfig == null) {
//                        log.error(LOG_PREFIX + "未获取到fundingConfig fundingCorpId:{}", fundingCorpId);
//                        continue;
//                    }
//                    list=getData(list,behalfSumAmount,fundRepayService,fundingCorpId);//获取资金方本次代偿数据
//
//                    //获取历史补扣数据
//                    if (fundRepayService.getHisRepay()){
//                        list=getHisData(list,fundRepayService,fundingCorpId,fundRepayService.getHisday(),behalfSumAmount);
//                    }
//
//                }
//                if(null!=list&&list.size()>0){
//                    //2.4判断代偿总金额和余额
//                    if (balance.compareTo(behalfSumAmount) < 0) {
//                        //2.4.1发送邮件通知余额不足
//                        String body = String.format(TMPL,list.get(0).get("fundingCorpName"),balance,behalfSumAmount,account);
//                        mailService.sendMailV3(mailSender,tomail, "【系统监控】".concat(account.toString()).concat(" 余额不足请注意!!"), body);
//                        list.clear();//帮助垃圾回收节约空间
//                        continue;
//                    }
//                    for (int i = 0; i < list.size(); i++) {
//                        FundBehalfSettleSummery summery =(FundBehalfSettleSummery)list.get(i).get("summery");
//                        log.info(LOG_PREFIX + "准备创建代付订单 fundingCorpId:{},balance:{},needAmount:{},merchantOrderNo:{}",list.get(i).get("fundingCorpId"),balance,list.get(i).get("behalfSumAmount"),list.get(i).get("merchantOrderNo"));
//                        String payOrderNo = payOrderService.createSimplePayOrder(String.valueOf(list.get(i).get("merchantOrderNo")), (OrderType)list.get(i).get("type"),summery.getBehalfSumAmount(),"代付代发代偿".concat(String.valueOf(list.get(i).get("merchantOrderNo"))),String.valueOf(account));
//                        //fundBehalfSettleSummeryRepository.updatePayOrder(payOrderNo,summery.getFundId(),summery.getBehalfAt());
//                        summery.setPayOrderNo(payOrderNo);
//                        summery.setMerOrderNo(String.valueOf(list.get(i).get("merchantOrderNo")));
//                        fundBehalfSettleSummeryRepository.save(summery);
//                        log.info(LOG_PREFIX + "成功创建支付订单 fundingCorpId:{},merchantOrderNo:{},payOrderNo:{}",list.get(i).get("fundingCorpId"),list.get(i).get("merchantOrderNo"),payOrderNo);
//                    }
//                    list.clear();//帮助垃圾回收节约空间
//                }
//            }
//        } catch (Exception e) {
//            log.error(LOG_PREFIX + "出现异常 error:{}",e);
//            //list.clear();//帮助垃圾回收节约空间
//            return ;
//        }
//
//
//    }
//
//    /**
//     * 判断获取需要执行的账户：资金方(1：n)
//     * @return
//     */
//    public Map<String,List<String>> getFundData(){
//        //1.获取需要代偿的账户：资金方list
//        Map<String,List<String>> dataMap=new HashMap<>();
//        //去Redis获取最后一次执行时间
//        String oldTime=redisTemplate.opsForValue().get(FUND_KEY+"time");
//        long exTime=0;
//        if (oldTime==null){//时间为获取到或过期，从新设置过期时间和初始值
//            oldTime="00:00";
//            try {
//                exTime=simpleDateFormat.parse("23:59").getTime()-
//                        simpleDateFormat.parse(simpleDateFormat.format(new Date())).getTime();
//            } catch (ParseException e) {
//                log.error(LOG_PREFIX + "redis 过期时间计算错误");
//            }
//            redisTemplate.opsForValue().set(FUND_KEY+"time",oldTime,exTime,TimeUnit.MILLISECONDS);
//        }
//        //获取当前时间和上次执行的时间内需要执行的数据
//        String str=simpleDateFormat.format(new Date());
//        List<FundBehalfConfigure> list =IFundBehalfConfigureRepository.findAllByFundStatusEqualsAndBehalfAtLessThanEqualAndBehalfAtGreaterThan(1,str,oldTime);
//        if (list!=null&&list.size()>0){//存在需要执行的数据，进行组装，并设置Redis新的最新执行时间
//            //已账户为维度，组装资金方
//            for (int i = 0; i < list.size(); i++) {
//                if (dataMap.get(list.get(i).getFundAccount())==null){
//                    List<String> fundList=new ArrayList<>();
//                    fundList.add(list.get(i).getFundServiceId());
//                    dataMap.put(list.get(i).getFundAccount(),fundList);
//                }else{
//                    dataMap.get(list.get(i).getFundAccount()).add(list.get(i).getFundServiceId());
//                }
//            }
//            redisTemplate.opsForValue().getAndSet(FUND_KEY+"time",str);//设置Redis新的最新执行时间
//            return dataMap;
//        }
//        return null;
//    }
//
//    /**
//     * 查找历史支付失败数据，进行重新计算
//     * @param list
//     * @param fundRepayService
//     * @param fundingCorpId
//     * @param hisday
//     * @param behalfSumAmount
//     * @return
//     */
//    public List<Map<String,Object>> getHisData(List<Map<String,Object>> list,IFundRepayService fundRepayService,Long fundingCorpId,int hisday,BigDecimal behalfSumAmount){
//        //获取历史数据区间
//        LocalDate nowLocalDate = LocalDate.now();
//        Date dateNow = Date.from(nowLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
//
//        Calendar now = Calendar.getInstance();
//        now.set(Calendar.DATE, now.get(Calendar.DATE) - hisday);
//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY-MM-DD");
//        Date f=null;
//        try {
//            f=simpleDateFormat.parse(simpleDateFormat.format(now.getTime()));
//        } catch (ParseException e) {
//            log.error(LOG_PREFIX + "日期计算出现异常 error:{}",e);
//        }
//        //获取历史支付失败需要重新发送的数据
//        List<FundBehalfSettleSummery> summeryList =null;
//        summeryList = fundBehalfSettleSummeryRepository.findAllByHisData(fundingCorpId,dateNow,f);
//        if (summeryList!=null){
//            for (int i = 0; i < summeryList.size(); i++) {
//                FundBehalfSettleSummery summery=summeryList.get(i);
//                //2.3.4累加代偿金额
//                behalfSumAmount=behalfSumAmount.add(summery.getBehalfSumAmount());
//                //2.3.5组装待支付订单数据
//                Map<String,Object> map=new HashMap<>();
//                map.put("merchantOrderNo",summery.getMerOrderNo());
//                map.put("behalfSumAmount",summery.getBehalfSumAmount());
//                map.put("type",fundRepayService.getType());
//                map.put("fundingCorpId",fundingCorpId);
//                map.put("fundingCorpName",fundRepayService.getFundingCorpName());
//                map.put("summery",summery);
//                list.add(map);
//            }
//        }
//        return list;
//    }
//    /**
//     * 获取资金方本次代偿数据
//     * @param list
//     * @param behalfSumAmount
//     * @param fundRepayService
//     * @param fundingCorpId
//     * @return
//     */
//    public List<Map<String,Object>> getData(List<Map<String,Object>> list,BigDecimal behalfSumAmount,IFundRepayService fundRepayService,Long fundingCorpId){
//        //2.3.2获取校验当天代偿数据
//        LocalDate nowLocalDate = LocalDate.now();
//        Date dateNow = Date.from(nowLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
//        String merchantOrderNo = nowLocalDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")).concat("funddeduction").concat(String.valueOf(fundingCorpId));
//        ThirdPartyPayOrder payOrder = thPayOrderRepository.findByMerOrderNo(merchantOrderNo, fundRepayService.getType().getCode());
//
//        FundBehalfSettleSummery summery =null;
//
//        if (payOrder != null) {//第2次以上补代偿
//            log.info(LOG_PREFIX + "已经处理过了 pass; fundingCorpId:{}",fundingCorpId);
//            //查询需要再次代偿的数据,由于payOrder为多条，但是SettleSummery为一条，并且为第二次SettleSummery，已有关联的payorderId，so直接判断是否是支付失败状态
//            summery = fundBehalfSettleSummeryRepository.findByFundIdAndBehalfAtAndSettleStatusAndPayOrderNoNotNull(fundingCorpId,dateNow, BehalftSettleStatus.not_settled.getValue());
//            if (summery==null){
//                return list;
//            }else{
//                ThirdPartyPayOrder thirdPartyPayOrder=thPayOrderRepository.findByPayOrderNo(summery.getPayOrderNo());
//                if(thirdPartyPayOrder.getPayStatus()!=4){
//                    return list;
//                }//是失败状态出if进行2.3.4累加代偿金额，组装待支付数据。
//            }
//        }else{//第一次代偿
//            //2.3.3校验代偿明细结算信息,where 资金方 and 日期 and 未结算
//            // String fundingCorpName = fundingConfig.getFundingCorpName();
//            summery = fundBehalfSettleSummeryRepository.findByFundIdAndBehalfAtAndSettleStatus(fundingCorpId,dateNow, BehalftSettleStatus.not_settled.getValue());
//            if (summery == null) {
//                log.error(LOG_PREFIX + "未获取到代偿数据 fundingCorpId:{}", fundingCorpId);
//                return list;
//            }
//        }
//
//        //2.3.4累加代偿金额
//        behalfSumAmount=behalfSumAmount.add(summery.getBehalfSumAmount());
//        //2.3.5组装待支付订单数据
//        Map<String,Object> map=new HashMap<>();
//        map.put("merchantOrderNo",merchantOrderNo);
//        map.put("behalfSumAmount",summery.getBehalfSumAmount());
//        map.put("type",fundRepayService.getType());
//        map.put("fundingCorpId",fundingCorpId);
//        map.put("fundingCorpName",fundRepayService.getFundingCorpName());
//        map.put("summery",summery);
//        list.add(map);
//
//        return list;
//    }
//}
