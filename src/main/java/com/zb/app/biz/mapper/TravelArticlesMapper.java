/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.mapper;

import java.util.List;

import com.zb.app.biz.base.BaseMapper;
import com.zb.app.biz.domain.TravelArticlesDO;
import com.zb.app.biz.query.TravelArticlesQuery;
import com.zb.app.web.vo.TravelArticlesVO;

/**
 * @author zxc Aug 19, 2014 4:04:05 PM
 */
public interface TravelArticlesMapper extends BaseMapper<TravelArticlesDO> {

	List<TravelArticlesVO> listQueryVO(TravelArticlesQuery travelArticlesQuery);

}
