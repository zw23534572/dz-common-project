package com.dazong.common.feign.client.api;

import com.dazong.common.feign.client.config.FastJsonConfiguration;
import com.dazong.common.feign.client.dto.request.*;
import com.dazong.common.feign.client.dto.response.BankInfoResponse;
import com.dazong.common.feign.client.dto.response.Company;
import com.dazong.common.feign.client.dto.response.UserInfo;
import com.dazong.common.feign.client.dto.response.WhiteListResponse;
import com.dazong.common.resp.DataResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author yanghui
 */
@FeignClient(name = "${feignclient.userapi.serviceId}",
        url = "${feignclient.userapi.url}",
        configuration = FastJsonConfiguration.class)
public interface IUserInfoService {

    /**
     * <B>方法名称：获取用户</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @param userRequest
     * @return UserInfo
     */
    @RequestMapping(value = "/user/get_user_info", method = RequestMethod.POST)
    DataResponse<UserInfo> queryUserByUserId(@RequestBody UserRequest userRequest);

    /**
     * <B>方法名称：检查敏感词汇</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @param sensitiveRequest
     * @return String
     */
    @RequestMapping(value = "/flex/getFlexWord", method = RequestMethod.POST)
    DataResponse<String> checkSensitiveWords(@RequestBody SensitiveRequest sensitiveRequest);

    /**
     * <B>方法名称：获取公司</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @param companyRequest
     * @return Company
     */
    @RequestMapping(value = "/userapi/getCompanyDetail", method = RequestMethod.POST)
    DataResponse<Company> queryCompanyByComId(@RequestBody CompanyRequest companyRequest);

    /**
     * <B>方法名称：检查白名单</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @param whiteRequest
     * @return String
     */
    @RequestMapping(value = "/flex/checkIsWhite", method = RequestMethod.POST)
    DataResponse<String> checkIsWhite(@RequestBody WhiteRequest whiteRequest);

    /**
     * <B>方法名称：获取银行卡信息</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @param bankInfoRequest
     * @return List<BankInfoResponse>
     */
    @RequestMapping(value = "/pc_api/getcbank", method = RequestMethod.POST)
    DataResponse<List<BankInfoResponse>> getBankInfo(BankInfoRequest bankInfoRequest);

    /**
     * 获取白名单列表
     *
     * @param whiteRequest
     * @return List<WhiteListResponse>
     */
    @RequestMapping(value = "/flex/getWhiteList", method = RequestMethod.POST)
    DataResponse<List<WhiteListResponse>> getWhiteList(@RequestBody WhiteRequest whiteRequest);

    /**
     * 根据仓库名称查询仓库
     *
     * @param warehouseRequest
     * @return String
     */
    @RequestMapping(value = "/flexibleWarehouse/getWarehouseByName", method = RequestMethod.POST)
    DataResponse<String> getWarehouseByName(@RequestBody WarehouseRequest warehouseRequest);

    /**
     * 分页查询货主列表
     *
     * @param request
     * @return List<Company>
     */
    @RequestMapping(value = "/goodsOwner/list", method = RequestMethod.POST)
    DataResponse<List<Company>> getGoodsOwnerList(@RequestBody PageRequest request);

    /**
     * 查询货主详情
     *
     * @param request
     * @return Company
     */
    @RequestMapping(value = "/goodsOwner/detail", method = RequestMethod.POST)
    DataResponse<Company> getGoodsOwnerDetail(@RequestBody BankInfoRequest request);

    /**
     * 查询货主名称列表
     *
     * @return List<Company>
     */
    @RequestMapping(value = "/goodsOwner/nameList", method = RequestMethod.POST)
    DataResponse<List<Company>> getGoodsOwnerNameList();
}