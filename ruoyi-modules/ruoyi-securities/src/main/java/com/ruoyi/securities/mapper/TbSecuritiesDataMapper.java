package com.ruoyi.securities.mapper;

import java.util.List;
import com.ruoyi.securities.domain.TbSecuritiesData;

/**
 * 证劵交易Mapper接口
 * 
 * @author linhf
 * @date 2025-03-01
 */
public interface TbSecuritiesDataMapper 
{
    /**
     * 查询证劵交易
     * 
     * @param id 证劵交易主键
     * @return 证劵交易
     */
    TbSecuritiesData selectTbSecuritiesDataById(Long id);

    /**
     * 查询证劵交易列表
     * 
     * @param tbSecuritiesData 证劵交易
     * @return 证劵交易集合
     */
    List<TbSecuritiesData> selectTbSecuritiesDataList(TbSecuritiesData tbSecuritiesData);

    /**
     * 新增证劵交易
     * 
     * @param tbSecuritiesData 证劵交易
     * @return 结果
     */
    int insertTbSecuritiesData(TbSecuritiesData tbSecuritiesData);

    /**
     * 修改证劵交易
     * 
     * @param tbSecuritiesData 证劵交易
     * @return 结果
     */
    int updateTbSecuritiesData(TbSecuritiesData tbSecuritiesData);

    /**
     * 删除证劵交易
     * 
     * @param id 证劵交易主键
     * @return 结果
     */
    int deleteTbSecuritiesDataById(Long id);

    /**
     * 批量删除证劵交易
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteTbSecuritiesDataByIds(Long[] ids);
}
