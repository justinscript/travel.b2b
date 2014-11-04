/**
 * 
 */
package com.zb.app.biz.domain;

import java.io.Serializable;

/**
 * @author ZhouZhong
 *
 */
public class TravelOrderCountByStateDO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1769965595900354102L;
	private Integer           orState;                                 // 状态
    private Integer           orStateCount;                            // 总数
	public Integer getOrState() {
		return orState;
	}
	public void setOrState(Integer orState) {
		this.orState = orState;
	}
	public Integer getOrStateCount() {
		return orStateCount;
	}
	public void setOrStateCount(Integer orStateCount) {
		this.orStateCount = orStateCount;
	}
	public TravelOrderCountByStateDO(Integer orState, Integer orStateCount) {
		super();
		this.orState = orState;
		this.orStateCount = orStateCount;
	}
	public TravelOrderCountByStateDO() {
		super();
	}
    
}
