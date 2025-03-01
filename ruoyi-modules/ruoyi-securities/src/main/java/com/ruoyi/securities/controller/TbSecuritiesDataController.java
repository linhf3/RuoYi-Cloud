package com.ruoyi.securities.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.securities.domain.TbSecuritiesData;
import com.ruoyi.securities.service.ITbSecuritiesDataService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 证劵交易Controller
 * 
 * @author linhf
 * @date 2025-03-01
 */
@RestController
@RequestMapping("/securities")
public class TbSecuritiesDataController extends BaseController
{
    @Autowired
    private ITbSecuritiesDataService tbSecuritiesDataService;

    /**
     * 查询证劵交易列表
     */
    @RequiresPermissions("securities:securities:list")
    @GetMapping("/list")
    public TableDataInfo list(TbSecuritiesData tbSecuritiesData)
    {
        startPage();
        List<TbSecuritiesData> list = tbSecuritiesDataService.selectTbSecuritiesDataList(tbSecuritiesData);
        return getDataTable(list);
    }

    /**
     * 导出证劵交易列表
     */
    @RequiresPermissions("securities:securities:export")
    @Log(title = "证劵交易", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TbSecuritiesData tbSecuritiesData)
    {
        List<TbSecuritiesData> list = tbSecuritiesDataService.selectTbSecuritiesDataList(tbSecuritiesData);
        ExcelUtil<TbSecuritiesData> util = new ExcelUtil<TbSecuritiesData>(TbSecuritiesData.class);
        util.exportExcel(response, list, "证劵交易数据");
    }

    /**
     * 获取证劵交易详细信息
     */
    @RequiresPermissions("securities:securities:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tbSecuritiesDataService.selectTbSecuritiesDataById(id));
    }

    /**
     * 新增证劵交易
     */
    @RequiresPermissions("securities:securities:add")
    @Log(title = "证劵交易", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbSecuritiesData tbSecuritiesData)
    {
        return toAjax(tbSecuritiesDataService.insertTbSecuritiesData(tbSecuritiesData));
    }

    /**
     * 修改证劵交易
     */
    @RequiresPermissions("securities:securities:edit")
    @Log(title = "证劵交易", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbSecuritiesData tbSecuritiesData)
    {
        return toAjax(tbSecuritiesDataService.updateTbSecuritiesData(tbSecuritiesData));
    }

    /**
     * 删除证劵交易
     */
    @RequiresPermissions("securities:securities:remove")
    @Log(title = "证劵交易", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbSecuritiesDataService.deleteTbSecuritiesDataByIds(ids));
    }
}
