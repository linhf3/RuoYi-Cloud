package com.ruoyi.securities.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.securities.mapper.TbSecuritiesDataMapper;
import com.ruoyi.securities.domain.TbSecuritiesData;
import com.ruoyi.securities.service.ITbSecuritiesDataService;

/**
 * 证劵交易Service业务层处理
 * 
 * @author linhf
 * @date 2025-03-01
 */
@Service
public class TbSecuritiesDataServiceImpl implements ITbSecuritiesDataService 
{
    @Autowired
    private TbSecuritiesDataMapper tbSecuritiesDataMapper;

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
}
