/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.mapper;

import java.util.List;

import com.zb.app.biz.base.BaseMapper;
import com.zb.app.biz.domain.TravelOrderGuestDO;
import com.zb.app.biz.domain.TravelOrderGuestFullDO;

/**
 * @author Administrator 2014-6-24 下午2:15:25
 */
public interface TravelOrderGuestMapper extends BaseMapper<TravelOrderGuestDO> {

    List<TravelOrderGuestDO> getByOrId(Long orId);

    List<TravelOrderGuestDO> getByLId(Long id);

    List<TravelOrderGuestFullDO> getByLIdAndPrice(Long id);

	Integer countByOrderGuest();
}
