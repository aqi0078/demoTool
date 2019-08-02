//package com.zdz.bean;
//
//import cn.qg.clotho.entity.clotho.FundBehalfSettleSummery;
//import cn.qg.clotho.repository.clotho.IFundBehalfConfigureRepository;
//import cn.qg.clotho.repository.clotho.IFundBehalfSettleSummeryRepository;
//import cn.qg.clotho.service.basis.IMailUtilService;
//import com.dangdang.ddframe.job.api.ShardingContext;
//import com.dangdang.ddframe.job.api.simple.SimpleJob;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.mail.MailSender;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//
///**
// * 资金方代偿模板主流程方法
// */
//@Slf4j
//public class FundFailDataSendJob  implements SimpleJob {
//
//
//    private final static String LOG_PREFIX = "[FundFailDataSendJob] ";
//
//
//    @Autowired
//    private IFundBehalfSettleSummeryRepository fundBehalfSettleSummeryRepository;
//    @Autowired
//    private IFundBehalfConfigureRepository IFundBehalfConfigureRepository;
//
//    @Autowired
//    @Qualifier("zhongbangMailSender")
//    private MailSender mailSender;
//
//    @Value("${zhongbang.mail.tomail:''}")
//    private String tomail;
//
//    @Autowired
//    private IMailUtilService mailService;
//
//    @Autowired
//    @Qualifier("stringRedisTemplate")
//    private RedisTemplate<String, String> redisTemplate;
//
//    private final static String FUND_KEY = "fund:configure:";
//
//    private final static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
//
//    public static final String TMPL ="<html><head></head><body><h3>%s</h3><p>TIPS:具体数据以系统查询为准.</p></body></html>";
//
//
//    @Override
//    public void execute(ShardingContext shardingContext) {
//        //获取数据
//        List<FundBehalfConfigure> fcList=getData();
//        if (null==fcList||fcList.size()==0){
//            return ;
//        }
//        //发送数据
//        sendData(fcList);
//    }
//
//    /**
//     * 获取待发送数据
//     * @return
//     */
//    public List<FundBehalfConfigure> getData(){
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
//            redisTemplate.opsForValue().set(FUND_KEY+"failSendTime",oldTime,exTime, TimeUnit.MILLISECONDS);
//        }
//        String str=simpleDateFormat.format(new Date());
//        return IFundBehalfConfigureRepository.findAllByFialData(str,oldTime);
//    }
//
//    /**
//     * 发送失败数据
//     * @param fcList
//     */
//    public void sendData(List<FundBehalfConfigure> fcList){
//        Map<Long,String> fundNameMap=new HashMap<>();
//        List<Long> fundIdList=new ArrayList<>();
//        for (int i = 0; i < fcList.size(); i++) {
//            fundNameMap.put(fcList.get(i).getFundId(),fcList.get(i).getFundName());
//            fundIdList.add(fcList.get(i).getFundId());
//        }
//        LocalDate nowLocalDate = LocalDate.now();
//        Date dateNow = Date.from(nowLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
//        //  查询失败数据，关联代付订单失败数据
//        List<FundBehalfSettleSummery> failData=fundBehalfSettleSummeryRepository.findByFailData(fundIdList,dateNow);
//        //  查询在途数据，关联代付订单非失败数据
//        List<FundBehalfSettleSummery> runData=fundBehalfSettleSummeryRepository.findAllByRunData(fundIdList,dateNow);
//        //发送邮件数据
//        StringBuffer sb=new StringBuffer();
//        if (null==failData||failData.size()==0){
//            sb.append("今日无代偿失败的数据：");
//            sb.append("\n");
//        }else{
//            sb.append("代偿失败的数据如下，总条数为：");
//            sb.append(failData.size());
//            sb.append("\n");
//            for (int i = 0; i < failData.size(); i++) {
//                sb.append("资金方:");
//                sb.append(fundNameMap.get(failData.get(i).getFundId()));
//                sb.append(",ID:");
//                sb.append(failData.get(i).getFundId());
//                sb.append(",代偿总额为：");
//                sb.append(failData.get(i).getBehalfSumAmount());
//                sb.append(",代偿流水号为：");
//                sb.append(failData.get(i).getMerOrderNo());
//                sb.append(",代偿支付流水号为：");
//                sb.append(failData.get(i).getPayOrderNo());
//                sb.append(",代偿失败");
//                sb.append("\n");
//            }
//        }
//        if (null==runData||runData.size()==0){
//            sb.append("今日无代偿在途数据");
//            sb.append("\n");
//        }else{
//            sb.append("代偿在途数据如下，总条数为：");
//            sb.append(runData.size());
//            sb.append("\n");
//            for (int i = 0; i < runData.size(); i++) {
//                sb.append("资金方:");
//                sb.append(fundNameMap.get(runData.get(i).getFundId()));
//                sb.append(",ID:");
//                sb.append(runData.get(i).getFundId());
//                sb.append(",代偿总额为：");
//                sb.append(runData.get(i).getBehalfSumAmount());
//                sb.append(",代偿流水号为：");
//                sb.append(runData.get(i).getMerOrderNo());
//                sb.append(",代偿支付流水号为：");
//                sb.append(runData.get(i).getPayOrderNo());
//                sb.append(",正在代偿中，具体结果请查询，获知。");
//                sb.append("\n");
//            }
//        }
//        String hStr=new String();
//        if (sb.length()>0){
//            hStr="【系统监控】今日代偿失败，在途数据明细";
//        }else{
//            hStr="【系统监控】今日无代偿失败，在途数据明细";
//        }
//
//        String body = String.format(TMPL,sb);
//        mailService.sendMailV3(mailSender,tomail, hStr, body);
//    }
//}
