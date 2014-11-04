/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.zb.app.common.pagination.Pagination;

/**
 * <pre>
 *      可以在BaseMapper中加入Q查询对象 BaseMapper<T,Q>,分页可以用BaseMapper实现然后在BaseDao中封装掉
 * </pre>
 * 
 * @author zxc Jun 15, 2014 11:11:48 PM
 */
public interface BaseMapper<T extends Serializable> {

    /**
     * 根据ID查找
     * 
     * @param id
     * @return
     */
    public T getById(Integer id);

    /**
     * 分页查询返回第一页
     * 
     * @param map
     * @return
     */
    @Deprecated
    public List<T> listPagination(Pagination pagination);

    /**
     * 查询所有或limit 的几条数据
     * 
     * @param map
     * @param limitSize == null 则查所有
     */
    public List<T> list(Map<String, Object> map);

    /**
     * 删除
     * 
     * @param id
     * @return
     */
    public Integer deleteById(Integer id);

    /**
     * 插入新数据
     */
    public Integer insert(T t);

    /**
     * 根据Id更新对象
     * 
     * @param t
     * @return
     */
    public Integer updateById(T t);
}
