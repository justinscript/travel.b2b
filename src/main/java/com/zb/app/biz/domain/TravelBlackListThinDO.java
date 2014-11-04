/**
 * 
 */
package com.zb.app.biz.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.zb.app.common.core.CustomToStringStyle;

/**
 * 黑名单简单类
 * @author ZhouZhong
 *
 */
public class TravelBlackListThinDO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2846222435206112502L;
	private String cName;
	private Long beCId;
	private Integer blackCount;
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	public Long getBeCId() {
		return beCId;
	}
	public void setBeCId(Long beCId) {
		this.beCId = beCId;
	}
	public Integer getBlackCount() {
		return blackCount;
	}
	public void setBlackCount(Integer blackCount) {
		this.blackCount = blackCount;
	}
	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }
}
