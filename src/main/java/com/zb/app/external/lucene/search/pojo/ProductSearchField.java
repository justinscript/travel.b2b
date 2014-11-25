/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.lucene.search.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.solr.client.solrj.beans.Field;

import com.zb.app.common.core.CustomToStringStyle;
import com.zb.app.common.util.PinyinParser;
import com.zb.app.common.util.StringFormatter;
import com.zb.app.external.lucene.solr.pojo.SearchField;

/**
 * @author zxc Sep 3, 2014 12:21:00 PM
 */
public class ProductSearchField implements SearchField, Serializable {

    private static final long serialVersionUID = -4307714692648797518L;

    private Long              lId;                                     // 线路ID
    @Field
    private String            lGroupNumber;                            // 团号
    @Field
    private String            lProduct;                                // 所属分组
    @Field
    private String            lTile;                                   // 标题
    @Field
    private Date              lGoGroupTime;                            // 出团日期
    @Field
    private String            lArrivalCity;                            // 抵达市
    @Field
    private String            lPhotoCover;                             // varchar2(500) 封面地址
    @Field
    private String            lMode;                                   // 线路备注
    @Field
    private String            lYesItem;                                // 包含项目
    @Field
    private String            lNoItem;                                 // 不包含项目
    @Field
    private String            lChildren;                               // 儿童安排
    @Field
    private String            lShop;                                   // 购物安排
    @Field
    private String            lExpenseItem;                            // 自费项目
    @Field
    private String            lPreseItem;                              // 赠送项目
    @Field
    private String            rContent;                                // 行程内容
    @Field
    private String            rCar;                                    // 交通介绍
    @Field
    private Integer           lType;                                   // 线路类型
    @Field
    private Integer           lDay;                                    // 行程天数
    @Field
    private Integer           zId;                                     // 专线ID
    @Field
    private float             lJCrPrice;                               // 成人结算价
    @Field
    private String            lTitle;                                  // 标题不分词
    @Field
    private List<String>      pinyin;                                  // 标题全拼
    @Field
    private List<String>      abbre;                                   // 标题首字母

    public String getlProduct() {
        return lProduct;
    }

    public void setlProduct(String lProduct) {
        this.lProduct = lProduct;
    }

    public float getlJCrPrice() {
        return lJCrPrice;
    }

    public void setlJCrPrice(float lJCrPrice) {
        this.lJCrPrice = lJCrPrice;
    }

    public String getrContent() {
        return rContent;
    }

    public Long getlId() {
        return lId;
    }

    public Integer getlType() {
        return lType;
    }

    public void setlType(Integer lType) {
        this.lType = lType;
    }

    public Integer getlDay() {
        return lDay;
    }

    public void setlDay(Integer lDay) {
        this.lDay = lDay;
    }

    public Integer getzId() {
        return zId;
    }

    public void setzId(Integer zId) {
        this.zId = zId;
    }

    public void setlId(Long lId) {
        this.lId = lId;
    }

    public void setrContent(String rContent) {
        this.rContent = rContent;
    }

    public String getrCar() {
        return rCar;
    }

    public void setrCar(String rCar) {
        this.rCar = rCar;
    }

    public String getlTile() {
        return lTile;
    }

    public String getlGroupNumber() {
        return lGroupNumber;
    }

    public void setlGroupNumber(String lGroupNumber) {
        this.lGroupNumber = lGroupNumber;
    }

    public void setlTile(String lTile) {
        this.lTile = lTile;
        // 除去中英文标点符号
        this.lTitle = StringFormatter.replaceCnEnSign(lTile);
        this.pinyin = new ArrayList<String>(PinyinParser.converter2AllSpell(lTitle));
        this.abbre = new ArrayList<String>(PinyinParser.converter2FirstSpell(lTitle));
    }

    public Date getlGoGroupTime() {
        return lGoGroupTime;
    }

    public String getlTitle() {
        return lTitle;
    }

    public void setlTitle(String lTitle) {
        this.lTitle = lTitle;
    }

    public void setlGoGroupTime(Date lGoGroupTime) {
        this.lGoGroupTime = lGoGroupTime;
    }

    public String getlArrivalCity() {
        return lArrivalCity;
    }

    public void setlArrivalCity(String lArrivalCity) {
        this.lArrivalCity = lArrivalCity;
    }

    public String getlPhotoCover() {
        return lPhotoCover;
    }

    public void setlPhotoCover(String lPhotoCover) {
        this.lPhotoCover = lPhotoCover;
    }

    public String getlMode() {
        return lMode;
    }

    public void setlMode(String lMode) {
        this.lMode = lMode;
    }

    public String getlYesItem() {
        return lYesItem;
    }

    public void setlYesItem(String lYesItem) {
        this.lYesItem = lYesItem;
    }

    public String getlNoItem() {
        return lNoItem;
    }

    public void setlNoItem(String lNoItem) {
        this.lNoItem = lNoItem;
    }

    public String getlChildren() {
        return lChildren;
    }

    public void setlChildren(String lChildren) {
        this.lChildren = lChildren;
    }

    public String getlShop() {
        return lShop;
    }

    public void setlShop(String lShop) {
        this.lShop = lShop;
    }

    public String getlExpenseItem() {
        return lExpenseItem;
    }

    public void setlExpenseItem(String lExpenseItem) {
        this.lExpenseItem = lExpenseItem;
    }

    public String getlPreseItem() {
        return lPreseItem;
    }

    public void setlPreseItem(String lPreseItem) {
        this.lPreseItem = lPreseItem;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }
}
