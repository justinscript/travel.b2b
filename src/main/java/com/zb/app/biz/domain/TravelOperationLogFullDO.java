/**
 * 
 */
package com.zb.app.biz.domain;

/**
 * @author ZhouZhong
 *
 */
public class TravelOperationLogFullDO extends TravelOperationLogDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1466336532668398781L;
	private String mName;	// 操作人ID
	private String lTitle;	//线路标题
	private String orOrderid;	//订单编号
	public String getlTitle() {
		return lTitle;
	}
	public void setlTitle(String lTitle) {
		this.lTitle = lTitle;
	}
	public String getOrOrderid() {
		return orOrderid;
	}
	public void setOrOrderid(String orOrderid) {
		this.orOrderid = orOrderid;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
}
