/**
 * 
 */
package com.zb.app.biz.domain;

/**
 * @author ZhouZhong
 *
 */
public class TravelBlackListFullDO extends TravelBlackListDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4478235837673322966L;
	
	private String beCName;
	private String cName;
	private String mName;
	public String getBeCName() {
		return beCName;
	}
	public void setBeCName(String beCName) {
		this.beCName = beCName;
	}
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
}
