package com.ruoyi.securities.task;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.core.utils.http.HttpUtils;
import com.ruoyi.securities.algorithm.CoreAlgorithmContet;
import com.ruoyi.securities.domain.TbSecuritiesData;
import com.ruoyi.securities.enums.Constant;
import com.ruoyi.securities.vo.SecuritiesFutureVo;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class TbSecuritiesDataThread implements Callable<SecuritiesFutureVo> {

    private TbSecuritiesData tbSecuritiesData;

    private CoreAlgorithmContet coreAlgorithmContet;

    public TbSecuritiesDataThread(TbSecuritiesData tbSecuritiesData, CoreAlgorithmContet coreAlgorithmContet) {
        this.tbSecuritiesData = tbSecuritiesData;
        this.coreAlgorithmContet = coreAlgorithmContet;
    }

    @Override
    public SecuritiesFutureVo call() throws Exception {
        //拼接地址
        Map urlMap = new HashMap<>();
        urlMap.put("futuresUrl", tbSecuritiesData.getExchangeCode());
        //发送http请求
        String rx = HttpUtils.sendGet(new StrSubstitutor(urlMap).replace(Constant.futuresUrl));
        Map node = (Map) JSON.parse(rx);
        //数据集
        Map map = (Map) node.get("data");
        List trendsM = (List) map.get("trends");
        //白天开盘，无数据的情况（晚上）
        if (CollectionUtils.isEmpty(trendsM)) {
            return null;
        }
        SecuritiesFutureVo securitiesFutureVo = new SecuritiesFutureVo();
        securitiesFutureVo.setCode(tbSecuritiesData.getCode());
        securitiesFutureVo.setName(tbSecuritiesData.getName());
        securitiesFutureVo.setExchangeCode(tbSecuritiesData.getExchangeCode());
        Map<String, Object> reMap = coreAlgorithmContet.deviationTheDayRate("futuresCoreAlgorithm", map);
        String proportion = (String) reMap.get("proportion");
        Double proportionDouble = Double.valueOf(proportion.replace("+", "").replace("%", ""));
        securitiesFutureVo.setProportion(proportion);
        Double dailySpread = (Double) reMap.get("dailySpread");
        Double price = (Double) reMap.get("price");
        Double min = (Double) reMap.get("min");
        Double max = (Double) reMap.get("max");
        securitiesFutureVo.setDailySpread(dailySpread);
        securitiesFutureVo.setPrice(price);
        securitiesFutureVo.setUndulate(tbSecuritiesData.getUndulate());
        securitiesFutureVo.setDianshu((Integer) reMap.get("dianshu"));
        securitiesFutureVo.setUp((Integer) reMap.get("up"));
        securitiesFutureVo.setDown((Integer) reMap.get("down"));
        //获取策略，上下偏离
        Double deviation = null == tbSecuritiesData.getDeviation() || 0 == tbSecuritiesData.getDeviation() ? 100 : tbSecuritiesData.getDeviation();
        //点数振幅
        //Double undulate = null == tbSecuritiesData.getUndulate() || 0 == tbSecuritiesData.getUndulate()?0:tbSecuritiesData.getUndulate();
        Double undulate = tbSecuritiesData.getUndulate();
        if (proportionDouble <= -deviation) {
            if (null != undulate) {
                if (max - price >= undulate) {
                    securitiesFutureVo.setPositiveNegativeFlag(-1);
                }
            } else {
                securitiesFutureVo.setPositiveNegativeFlag(-1);
            }
        } else if (proportionDouble >= deviation) {
            if (null != undulate) {
                if (price - min >= undulate) {
                    securitiesFutureVo.setPositiveNegativeFlag(1);
                }
            } else {
                securitiesFutureVo.setPositiveNegativeFlag(1);
            }
        } else {
            securitiesFutureVo.setPositiveNegativeFlag(0);
        }
        return securitiesFutureVo;
    }
}
