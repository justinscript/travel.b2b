/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zb.app.biz.base.BaseDao;
import com.zb.app.biz.domain.TravelArticlesDO;
import com.zb.app.biz.mapper.TravelArticlesMapper;
import com.zb.app.biz.query.TravelArticlesQuery;
import com.zb.app.web.vo.TravelArticlesVO;

/**
 * @author zxc Aug 19, 2014 4:03:51 PM
 */
@Repository
public class TravelArticlesDao extends BaseDao<TravelArticlesDO, TravelArticlesMapper, TravelArticlesQuery> {

	public List<TravelArticlesVO> listQueryVO(
			TravelArticlesQuery travelArticlesQuery) {
		return m.listQueryVO(travelArticlesQuery);
	}

}
