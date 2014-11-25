/**
 * 
 */
package com.zb.app.web.vo;

/**
 * @author ZhouZhong
 *
 */
public class TravelArticlesVO {
	private Long              aId;                                    // 文章编号主键
	private Integer           source;                                 // 文章类别来源
	private String            title;                                  // 文章标题
	private Integer           sort;                                   // 文章排序
	private Integer           state;                                  // 文章状态
	public Long getaId() {
		return aId;
	}
	public void setaId(Long aId) {
		this.aId = aId;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
}
