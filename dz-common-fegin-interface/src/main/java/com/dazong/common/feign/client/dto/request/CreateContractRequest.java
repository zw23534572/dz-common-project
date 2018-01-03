package com.dazong.common.feign.client.dto.request;

import java.math.BigDecimal;

import lombok.Data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 *  @author yanghui
 */
@Data
public class CreateContractRequest {
	
	/**
     * �������
     */
    @JSONField(name = "order_code")
    Long orderNo;
    /**
     * ����֧����ʽ��1��������֧����ʽ��0
     */
    @JSONField(name = "offline_pay")
    Integer payType;
    /**
     * �ҵ���
     */
    @JSONField(name = "hang_order_id")
    Long boardNo;
    /**
     * �ֿ�ID
     */
    @JSONField(name = "warehouse_id")
    Integer whId;
    /**
     * �ֿ�����
     */
    @JSONField(name = "warehouse_name")
    String whName;
    /**
     * ��Ҳֵ����
     */
    @JSONField(name = "buy_warehouse_code")
    String realWhReceipt;
    /**
     * ���Ҳֵ����
     */
    @JSONField(name = "sell_warehouse_code")
    String boardWhReceipt;
    /**
     * ���׷�ʽ��1��ͨ 2������
     */
    @JSONField(name = "sell_type")
    Integer sellType = 2;
    /**
     * �ҵ���ʽ��1һ�ڼ� 2������ 3�����ף�
     */
    @JSONField(name = "sell_mode")
    Integer boardMode;
    /**
     * ��Ʊ��ʽ��1����Ʊ 2����Ʊ 3����Ʊ��
     */
    @JSONField(name = "invoice_mode")
    Integer invoiceMode;
    /**
     * ����û�ID
     */
    @JSONField(name = "buy_uid")
    Long buyDealerId;
    /**
     * ����û���
     */
    @JSONField(name = "buy_name")
    String buyDealerName;
    /**
     * �����û�ID
     */
    @JSONField(name = "sell_uid")
    Long sellDealerId;
    /**
     * �����û���
     */
    @JSONField(name = "sell_name")
    String sellDealerName;
    /**
     * Ʒ��ID
     */
    @JSONField(name = "brand_id")
    Integer brandId;
    /**
     * ��� Ʒ������
     */
    @JSONField(name = "brand_name")
    String brandName;
    /**
     * ��� Ʒ��id
     */
    @JSONField(name = "class_id")
    Integer classId;
    /**
     * Ʒ������
     */
    @JSONField(name = "class_name")
    String className;
    /**
     * �ҵ��ֵ���
     */
    @JSONField(name = "category_id")
    Integer categoryId;
    /**
     * ��������
     */
    @JSONField(name = "category_name")
    String categoryName;
    /**
     * ʵ������
     */
    @JSONField(name = "number")
    BigDecimal realQuantity;
    /**
     * ���׵��ۣ�������ʱ���ڻ���+����ˮ
     */
    @JSONField(name = "deal_price")
    BigDecimal dealPrice;
    /**
     * ��˰�۸�
     */
    @JSONField(name = "tax_price")
    BigDecimal taxPrice;
    /**
     * �ܽ�Ԫ��
     */
    @JSONField(name = "total_price")
    BigDecimal totalAmount;
    /**
     * ����ˮ
     */
    BigDecimal premium;
    /**
     * ��ҹ�˾ID
     */
    @JSONField(name = "buy_company_id")
    Long buyCoId;
    /**
     * ��ҹ�˾����
     */
    @JSONField(name = "buy_company_name")
    String buyCoName;
    /**
     * ��ҵ绰
     */
    @JSONField(name = "buy_tel")
    String buyCoTel;
    /**
     * ���ҹ�˾ID
     */
    @JSONField(name = "sell_company_id")
    Long sellCoId;
    /**
     * ���ҹ�˾����
     */
    @JSONField(name = "sell_company_name")
    String sellCoName;
    /**
     * ���ҵ绰
     */
    @JSONField(name = "sell_tel")
    String sellCoTel;
    /**
     * �г�����
     */
    @JSONField(name = "market_type")
    String marketType="lh";
    /**
     * ��֤����
     */
    @JSONField(name = "deposit_radio")
    String depositRadio;
    /**
     * ΥԼ����
     */
    @JSONField(name = "penalty_radio")
    String penaltyRadio;
    /**
     * ����ģʽ��10����ͨģʽ 20.����ģʽ
     */
    @JSONField(name = "trade_mode")
    Integer tradeMode;

}
