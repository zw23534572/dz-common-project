package com.dazong.common.feign.client.dto.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;

/**
 *  @author yanghui
 */
@Data
public class CreateContractRequest {
	
	/**
     * 订单编号
     */
    @JSONField(name = "order_code")
    Long orderNo;
    /**
     * 线下支付方式：1，非线下支付方式：0
     */
    @JSONField(name = "offline_pay")
    Integer payType;
    /**
     * 挂单号
     */
    @JSONField(name = "hang_order_id")
    Long boardNo;
    /**
     * 仓库ID
     */
    @JSONField(name = "warehouse_id")
    Integer whId;
    /**
     * 仓库名称
     */
    @JSONField(name = "warehouse_name")
    String whName;
    /**
     * 买家仓单编号
     */
    @JSONField(name = "buy_warehouse_code")
    String realWhReceipt;
    /**
     * 卖家仓单编号
     */
    @JSONField(name = "sell_warehouse_code")
    String boardWhReceipt;
    /**
     * 交易方式（1普通 2连续）
     */
    @JSONField(name = "sell_type")
    Integer sellType = 2;
    /**
     * 挂单方式（1一口价 2浮动价 3定向交易）
     */
    @JSONField(name = "sell_mode")
    Integer boardMode;
    /**
     * 开票方式（1当月票 2下月票 3当日票）
     */
    @JSONField(name = "invoice_mode")
    Integer invoiceMode;
    /**
     * 买家用户ID
     */
    @JSONField(name = "buy_uid")
    Long buyDealerId;
    /**
     * 买家用户名
     */
    @JSONField(name = "buy_name")
    String buyDealerName;
    /**
     * 卖家用户ID
     */
    @JSONField(name = "sell_uid")
    Long sellDealerId;
    /**
     * 卖家用户名
     */
    @JSONField(name = "sell_name")
    String sellDealerName;
    /**
     * 品牌ID
     */
    @JSONField(name = "brand_id")
    Integer brandId;
    /**
     * 获得 品牌名称
     */
    @JSONField(name = "brand_name")
    String brandName;
    /**
     * 获得 品类id
     */
    @JSONField(name = "class_id")
    Integer classId;
    /**
     * 品类名称
     */
    @JSONField(name = "class_name")
    String className;
    /**
     * 挂单仓单号
     */
    @JSONField(name = "category_id")
    Integer categoryId;
    /**
     * 分类名称
     */
    @JSONField(name = "category_name")
    String categoryName;
    /**
     * 实际重量
     */
    @JSONField(name = "number")
    BigDecimal realQuantity;
    /**
     * 交易单价，浮动价时：期货价+升贴水
     */
    @JSONField(name = "deal_price")
    BigDecimal dealPrice;
    /**
     * 含税价格
     */
    @JSONField(name = "tax_price")
    BigDecimal taxPrice;
    /**
     * 总金额（元）
     */
    @JSONField(name = "total_price")
    BigDecimal totalAmount;
    /**
     * 升贴水
     */
    BigDecimal premium;
    /**
     * 买家公司ID
     */
    @JSONField(name = "buy_company_id")
    Long buyCoId;
    /**
     * 买家公司名称
     */
    @JSONField(name = "buy_company_name")
    String buyCoName;
    /**
     * 买家电话
     */
    @JSONField(name = "buy_tel")
    String buyCoTel;
    /**
     * 卖家公司ID
     */
    @JSONField(name = "sell_company_id")
    Long sellCoId;
    /**
     * 卖家公司名称
     */
    @JSONField(name = "sell_company_name")
    String sellCoName;
    /**
     * 卖家电话
     */
    @JSONField(name = "sell_tel")
    String sellCoTel;
    /**
     * 市场类型
     */
    @JSONField(name = "market_type")
    String marketType="lh";
    /**
     * 保证金率
     */
    @JSONField(name = "deposit_radio")
    String depositRadio;
    /**
     * 违约金率
     */
    @JSONField(name = "penalty_radio")
    String penaltyRadio;
    /**
     * 交易模式：10，普通模式 20.交易模式
     */
    @JSONField(name = "trade_mode")
    Integer tradeMode;


}
