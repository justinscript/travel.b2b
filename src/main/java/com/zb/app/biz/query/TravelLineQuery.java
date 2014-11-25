/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.query;

import java.io.Serializable;
import java.util.Date;

import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.pagination.Pagination;
import com.zb.app.common.util.DateViewTools;

/**
 * @author zxc Jun 24, 2014 5:14:10 PM
 */
public class TravelLineQuery extends Pagination implements Serializable {

    private static final long serialVersionUID = -1213213546321321634L;
    private Long              lId;
    private Long[]            zIds;                                    // 专线ID （配置文件最终使用）
    private Long              zId;
    private Long              mId;                                     // 用户ID（发布人）
    private Long              cId;                                     // 公司ID（发布人公司）
    private String            lTile;                                   // 标题
    private Date              lGoGroupTime;                            // 出团日期
    private Date              lGoGroupEndTime;                         // 截止日期
    private Integer           lType;                                   // 类型
    private String            lProvince;                               // 省
    private String            lCity;                                   // 市
    private String            lArea;                                   // 区/县
    private String            lArrivalCity;                            // 抵达市
    private Integer           lEditUserId;                             // 最后修改人ID
    private Integer           lTemplateState;                          // 0=线路,1=模板
    private Integer           lState;                                  // 状态（0=正常,1=停止,2=客满,3=过期）
    private Integer           lDelState        = 0;                    // 0=正常,1=删除 默认查询正常
    private Integer           lDisplay         = 0;                    // 显示状态（0=显,1=隐） 默认查询显示
    private Integer           lDay;                                    // 旅游天数
    private Integer           lTrafficyType;                           // 出发交通类型（0=飞机,1=汽车,2=火车,3=游船,4=待定）
    private Integer           lTrafficBackType;                        // 返回交通类型（0=飞机,1=汽车,2=火车,3=游船,4=待定）
    private Integer           lIsJs;                                   // 是否带接送（0=不带，1=带）
    private String            lProduct;                                // 所属分组
    private Integer           lIsIntegral;                             // 是否积分
    private Integer           lIsVouchers;                             // 是否可用抵用券
    private String            lGroupNumber;                            // 团号
    private Long[]            lids;                                    // 用于批量修改线路使用
    private Date              gmtCreate;                               // 用于统计昨日发布线路使用
    private Integer           groupType;                               // 分组查询类型(null=分组线路显示分组查询 ，1=出发城市分组查询)
    private Integer           columnType;                              // 统计参数（null：分组后统计个数，1：分组后统计统计浏览量）

    public Integer getColumnType() {
        return columnType;
    }

    public void setColumnType(Integer columnType) {
        this.columnType = columnType;
    }

    public String getlArrivalCity() {
        return lArrivalCity;
    }

    public void setlArrivalCity(String lArrivalCity) {
        this.lArrivalCity = lArrivalCity;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long[] getLids() {
        return lids;
    }

    public void setLids(Long[] lids) {
        this.lids = lids.length == 0 ? null : lids;
    }

    public String getlGroupNumber() {
        return lGroupNumber;
    }

    public void setlGroupNumber(String lGroupNumber) {
        this.lGroupNumber = lGroupNumber;
    }

    public TravelLineQuery() {

    }

    public TravelLineQuery(String lGoGroupEndTime, String lGoGroupTime, Integer lTemplateState, int pageSize,
                           int nowPageIndex) {
        setlGoGroupEndTime(lGoGroupEndTime);
        setlGoGroupTime(lGoGroupTime);
        setlTemplateState(lTemplateState);
        setPageSize(pageSize);
        setNowPageIndex(nowPageIndex);
    }

    public static void parse(TravelLineQuery query, String lGoGroupTime, String lGoGroupEndTime, Integer page,
                             Integer pagesize, Integer lTemplateState) {
        query.setlGoGroupEndTime(lGoGroupEndTime);
        query.setlGoGroupTime(lGoGroupTime);
        query.setlTemplateState(lTemplateState);
        query.setPageSize(Argument.isNotPositive(pagesize) ? 15 : pagesize);
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);
    }

    public Long[] getzIds() {
        return zIds;
    }

    public void setzIds(Long... zIds) {
        this.zIds = zIds.length == 0 ? new Long[] { -1L } : zIds;
    }

    public void setzId(Long zId) {
        this.zId = zId;
    }

    public Integer getlIsIntegral() {
        return lIsIntegral;
    }

    public void setlIsIntegral(Integer lIsIntegral) {
        this.lIsIntegral = lIsIntegral;
    }

    public Date getlGoGroupTime() {
        return lGoGroupTime;
    }

    public void setlGoGroupTime(String lGoGroupTime) {
        if (lGoGroupTime == null) {
            return;
        }
        Date lGoGroupTimes = DateViewTools.convertDate(lGoGroupTime, "yyyy-MM-dd");
        this.lGoGroupTime = lGoGroupTimes;
    }

    public Date getlGoGroupEndTime() {
        return lGoGroupEndTime;
    }

    public void setlGoGroupEndTime(String lGoGroupEndTime) {
        if (lGoGroupEndTime == null) {
            return;
        }
        Date lGoGroupEndTimes = DateViewTools.convertDate(lGoGroupEndTime, "yyyy-MM-dd");
        this.lGoGroupEndTime = lGoGroupEndTimes;
    }

    public Integer getlIsVouchers() {
        return lIsVouchers;
    }

    public void setlIsVouchers(Integer lIsVouchers) {
        this.lIsVouchers = lIsVouchers;
    }

    public String getlProduct() {
        return lProduct;
    }

    public void setlProduct(String lProduct) {
        this.lProduct = lProduct;
    }

    public TravelLineQuery(Long lId) {
        setlId(lId);
    }
    
    /***
     * 线路分组编号
     * @param lProduct
     */
    public TravelLineQuery(String lProduct) {
        setlProduct(lProduct);
    }

    public Long getlId() {
        return lId;
    }

    public void setlId(Long lId) {
        this.lId = lId;
    }

    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    public Long getzId() {
        return zId;
    }

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public Integer getlTemplateState() {
        return lTemplateState;
    }

    public void setlTemplateState(Integer lTemplateState) {
        this.lTemplateState = lTemplateState;
    }

    public String getlTile() {
        return lTile;
    }

    public void setlTile(String lTile) {
        this.lTile = lTile.replaceAll(" ", "");
    }

    public Integer getlType() {
        return lType;
    }

    public void setlType(Integer lType) {
        this.lType = lType;
    }

    public String getlProvince() {
        return lProvince;
    }

    public void setlProvince(String lProvince) {
        this.lProvince = lProvince;
    }

    public String getlCity() {
        return lCity;
    }

    public void setlCity(String lCity) {
        this.lCity = lCity;
    }

    public String getlArea() {
        return lArea;
    }

    public void setlArea(String lArea) {
        this.lArea = lArea;
    }

    public Integer getlEditUserId() {
        return lEditUserId;
    }

    public void setlEditUserId(Integer lEditUserId) {
        this.lEditUserId = lEditUserId;
    }

    public Integer getlState() {
        return lState;
    }

    public void setlState(Integer lState) {
        this.lState = lState;
    }

    public Integer getlDelState() {
        return lDelState;
    }

    public void setlDelState(Integer lDelState) {
        this.lDelState = lDelState;
    }

    public Integer getlDisplay() {
        return lDisplay;
    }

    public void setlDisplay(Integer lDisplay) {
        this.lDisplay = lDisplay;
    }

    public Integer getlDay() {
        return lDay;
    }

    public void setlDay(Integer lDay) {
        this.lDay = lDay;
    }

    public Integer getlTrafficyType() {
        return lTrafficyType;
    }

    public void setlTrafficyType(Integer lTrafficyType) {
        this.lTrafficyType = lTrafficyType;
    }

    public Integer getlTrafficBackType() {
        return lTrafficBackType;
    }

    public void setlTrafficBackType(Integer lTrafficBackType) {
        this.lTrafficBackType = lTrafficBackType;
    }

    public Integer getlIsJs() {
        return lIsJs;
    }

    public void setlIsJs(Integer lIsJs) {
        this.lIsJs = lIsJs;
    }
}
