package com.ruoyi.securities.vo;

import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SecuritiesFutureVo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编码 */
    private String code;

    /** 名称 */
    private String name;

    /** 交易所-编码 */
    private String exchangeCode;

    /** 当前价格 */
    private Double price;

    /** 上振 */
    private Integer up;

    /** 下振 */
    private Integer down;

    /** 一日差价 */
    private String proportion;

    /** 振幅 */
    private Double dailySpread;

    /** 当前偏离值（比较上下偏离后得到） */
    private Double deviation;

    /** 波动 */
    private Double undulate;

    /** 点数 */
    private Integer dianshu;

    /** 提示状态 */
    private Integer positiveNegativeFlag;


}
