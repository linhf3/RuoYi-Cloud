package com.ruoyi.securities.service.impl;

import java.util.*;
import java.util.concurrent.Future;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.core.utils.http.HttpUtils;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.securities.algorithm.CoreAlgorithmContet;
import com.ruoyi.securities.enums.Constant;
import com.ruoyi.securities.task.TbSecuritiesDataThread;
import com.ruoyi.securities.vo.SecuritiesFutureVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import com.ruoyi.securities.mapper.TbSecuritiesDataMapper;
import com.ruoyi.securities.domain.TbSecuritiesData;
import com.ruoyi.securities.service.ITbSecuritiesDataService;
import org.springframework.util.CollectionUtils;

/**
 * 证劵交易Service业务层处理
 * 
 * @author linhf
 * @date 2025-03-01
 */
@Service
@Slf4j
public class TbSecuritiesDataServiceImpl implements ITbSecuritiesDataService 
{
    @Autowired
    private TbSecuritiesDataMapper tbSecuritiesDataMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private CoreAlgorithmContet coreAlgorithmContet;

    /**
     * 查询证劵交易
     * 
     * @param id 证劵交易主键
     * @return 证劵交易
     */
    @Override
    public TbSecuritiesData selectTbSecuritiesDataById(Long id)
    {
        return tbSecuritiesDataMapper.selectTbSecuritiesDataById(id);
    }

    /**
     * 查询证劵交易列表
     * 
     * @param tbSecuritiesData 证劵交易
     * @return 证劵交易
     */
    @Override
    public List<TbSecuritiesData> selectTbSecuritiesDataList(TbSecuritiesData tbSecuritiesData)
    {
        return tbSecuritiesDataMapper.selectTbSecuritiesDataList(tbSecuritiesData);
    }

    /**
     * 新增证劵交易
     * 
     * @param tbSecuritiesData 证劵交易
     * @return 结果
     */
    @Override
    public int insertTbSecuritiesData(TbSecuritiesData tbSecuritiesData)
    {
        redisService.deleteObject("tbSecuritiesDataList");
        return tbSecuritiesDataMapper.insertTbSecuritiesData(tbSecuritiesData);
    }

    /**
     * 修改证劵交易
     * 
     * @param tbSecuritiesData 证劵交易
     * @return 结果
     */
    @Override
    public int updateTbSecuritiesData(TbSecuritiesData tbSecuritiesData)
    {
        redisService.deleteObject("tbSecuritiesDataList");
        return tbSecuritiesDataMapper.updateTbSecuritiesData(tbSecuritiesData);
    }

    /**
     * 批量删除证劵交易
     * 
     * @param ids 需要删除的证劵交易主键
     * @return 结果
     */
    @Override
    public int deleteTbSecuritiesDataByIds(Long[] ids)
    {
        redisService.deleteObject("tbSecuritiesDataList");
        return tbSecuritiesDataMapper.deleteTbSecuritiesDataByIds(ids);
    }

    /**
     * 删除证劵交易信息
     * 
     * @param id 证劵交易主键
     * @return 结果
     */
    @Override
    public int deleteTbSecuritiesDataById(Long id)
    {
        return tbSecuritiesDataMapper.deleteTbSecuritiesDataById(id);
    }

    @Override
    public boolean crawl() {
        List<String> listF = Arrays.asList("103", "112", "113", "114", "115");
        //List<String> listF = Arrays.asList("115");
        LinkedList<TbSecuritiesData> tbSecuritiesDataLinkedList = new LinkedList<>();
        listF.forEach(s -> {
            Map urlMap = new HashMap<>();
            urlMap.put("place", s);
            //发送http请求
            String rx = null;
            try {
                rx = HttpUtils.sendGet(new StrSubstitutor(urlMap).replace(Constant.FUTURESMAINFORCEURL).toString());
            } catch (Exception e) {
                log.error("爬取数据异常：",e);
            }
            Map node = (Map) JSON.parse(rx);
            List<Map<String, Object>> list = (List<Map<String, Object>>) node.get("list");
            //数据集
            System.out.println(list.toArray());
            list.forEach(map -> {
                String name = (String) map.get("name");
                TbSecuritiesData tbSecuritiesData = new TbSecuritiesData();
                tbSecuritiesData.setName(name);
                String dm = (String) map.get("dm");
                tbSecuritiesData.setCode(dm);
                tbSecuritiesData.setExchangeCode(new StringBuilder(s).append(".").append(dm).toString());
                tbSecuritiesData.setDeviation(90.0);
                tbSecuritiesData.setType(1);
                tbSecuritiesData.setStatus(1);
                tbSecuritiesData.setAddUser("admin");
                tbSecuritiesData.setAddTime(new Date());
                tbSecuritiesDataLinkedList.add(tbSecuritiesData);
            });
        });
        if (!CollectionUtils.isEmpty(tbSecuritiesDataLinkedList)){
            tbSecuritiesDataMapper.deleteAll();
            tbSecuritiesDataMapper.insertList(tbSecuritiesDataLinkedList);
        }
        return true;
    }

    @Override
    public List<SecuritiesFutureVo> findList() {
        //1.查询有效配置
        List<TbSecuritiesData> tbSecuritiesDataList = redisService.getCacheList("tbSecuritiesDataList");
        if (CollectionUtils.isEmpty(tbSecuritiesDataList)){
            TbSecuritiesData tbSecuritiesData = new TbSecuritiesData();
            tbSecuritiesData.setType(1);
            tbSecuritiesData.setStatus(0);
            tbSecuritiesDataList = tbSecuritiesDataMapper.selectTbSecuritiesDataList(tbSecuritiesData);
            if (!CollectionUtils.isEmpty(tbSecuritiesDataList)){
                redisService.setCacheList("tbSecuritiesDataList",tbSecuritiesDataList);
            }
        }
        if (CollectionUtils.isEmpty(tbSecuritiesDataList)){
            return new ArrayList<>();
        }
        List<Future<SecuritiesFutureVo>> futures = new ArrayList<>();
        //2、构建线程
        long startTime = System.currentTimeMillis();
        for (TbSecuritiesData tbSecuritiesData : tbSecuritiesDataList) {
            TbSecuritiesDataThread tbSecuritiesDataThread = new TbSecuritiesDataThread(tbSecuritiesData, coreAlgorithmContet);
            Future<SecuritiesFutureVo> future = taskExecutor.submit(tbSecuritiesDataThread);
            futures.add(future);
        }
        //3、等待所有任务完成
        List<SecuritiesFutureVo> list = new ArrayList<>();
        for (Future<SecuritiesFutureVo> future : futures) {
            try {
                list.add(future.get());// 阻塞直到任务完成
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        log.info("执行时长：{}", endTime - startTime);
        return list;
    }


}
