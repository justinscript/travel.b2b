/**
 * 
 */
package com.zb.app.web.tools;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.zb.app.biz.cons.ColumnCatEnum;
import com.zb.app.biz.domain.TravelGiftDO;
import com.zb.app.biz.domain.TravelLabelCategoryDO;
import com.zb.app.biz.query.TravelGiftQuery;
import com.zb.app.biz.query.TravelLabelCategoryQuery;
import com.zb.app.biz.service.interfaces.CmsService;
import com.zb.app.biz.service.interfaces.IntegralService;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.DefaultIpageUrl;

/**
 * @author ZhouZhong
 *
 */
public class IndexHeaderTools {
	@Autowired
    protected CmsService cmsService;
	
	@Autowired
	protected IntegralService integralService;
	
	public Map<Integer, PaginationList<TravelLabelCategoryDO>> getTag() {
		Map<Integer, PaginationList<TravelLabelCategoryDO>> navMap = new LinkedHashMap<Integer, PaginationList<TravelLabelCategoryDO>>();
		// 出港点标签mav
        TravelLabelCategoryQuery categoryQuery = new TravelLabelCategoryQuery();
        categoryQuery.setsId(WebUserTools.getChugangId());
        categoryQuery.setLineType(ColumnCatEnum.SHORT_LINE.getValue());
        categoryQuery.setParentId(0L);
        categoryQuery.setNowPageIndex(0);
        categoryQuery.setPageSize(8);
        PaginationList<TravelLabelCategoryDO> shortCategoryDOs = cmsService.listPagination(categoryQuery,
                                                                                           new DefaultIpageUrl());
        categoryQuery.setLineType(ColumnCatEnum.LONG_LINE.getValue());
        PaginationList<TravelLabelCategoryDO> longCategoryDOs = cmsService.listPagination(categoryQuery,
                                                                                          new DefaultIpageUrl());
        categoryQuery.setLineType(ColumnCatEnum.INTERNATIONAL_LINE.getValue());
        PaginationList<TravelLabelCategoryDO> internationalCategoryDOs = cmsService.listPagination(categoryQuery,
                                                                                                   new DefaultIpageUrl());
        navMap.put(1, shortCategoryDOs);
        navMap.put(2, longCategoryDOs);
        navMap.put(3, internationalCategoryDOs);
        return navMap;
    }
	
    public PaginationList<TravelGiftDO> getGiftList() {
		// 积分商城
        TravelGiftQuery giftQuery = new TravelGiftQuery();
        giftQuery.setPageSize(6);
        PaginationList<TravelGiftDO> giftDOs = integralService.listPagination(giftQuery, new DefaultIpageUrl());
        return giftDOs;
    }
}
