/*
、 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.domain;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;
import com.zb.app.common.core.CustomToStringStyle;

/**
 * @author ZhouZhong 2014-6-27 下午1:56:08
 */
public class TravelColumnDO implements Serializable {

    private static final long serialVersionUID = -949099409591475819L;

    private Long              zId;                                    // 主键
    private Date              gmtCreate;                              // 创建时间
    private Date              gmtModified;                            // 最后更新时间

    @NotNull(message = "站点ID不能为空")
    private Long              sId;                                    // 站点ID
    @NotNull(message = "出港点ID不能为空")
    private Long              sToId;                                  // 出港点ID
    @NotEmpty(message = "专线名称不能为空")
    private String            zName;                                  // 专线名称
    private Integer           zCat;                                   // 专线分类(0=短线，1=长线，2=国际线)
    private Integer           zDesc;                                  // 专线排序(大号排前)
    private Integer           zHot;                                   // 状态(1=热门，2=推荐)
    @NotEmpty(message = "专线图片不能为空")
    private String            zPic;                                   // 专线图片
    private Integer           zState;                                 // 状态(0=正常，1=停止)

    public Long getzId() {
        return zId;
    }

    public void setzId(Long zId) {
        this.zId = zId;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getsId() {
        return sId;
    }

    public void setsId(Long sId) {
        this.sId = sId;
    }

    public Long getsToId() {
        return sToId;
    }

    public void setsToId(Long sToId) {
        this.sToId = sToId;
    }

    public String getzName() {
        return zName;
    }

    public void setzName(String zName) {
        this.zName = zName;
    }

    public Integer getzCat() {
        return zCat;
    }

    public void setzCat(Integer zCat) {
        this.zCat = zCat;
    }

    public Integer getzDesc() {
        return zDesc;
    }

    public void setzDesc(Integer zDesc) {
        this.zDesc = zDesc;
    }

    public Integer getzHot() {
        return zHot;
    }

    public void setzHot(Integer zHot) {
        this.zHot = zHot;
    }

    public String getzPic() {
        return zPic;
    }

    public void setzPic(String zPic) {
        this.zPic = zPic;
    }

    public Integer getzState() {
        return zState;
    }

    public void setzState(Integer zState) {
        this.zState = zState;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }
}
