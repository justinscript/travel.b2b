/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zb.app.biz.cons.PhotoTypeEnum;
import com.zb.app.biz.domain.TravelPhotoDO;
import com.zb.app.biz.query.TravelPhotoQuery;
import com.zb.app.biz.service.impl.FileServiceImpl.IFileHandle;
import com.zb.app.common.authority.AuthorityPolicy;
import com.zb.app.common.authority.Right;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.DefaultIpageUrl;
import com.zb.app.common.result.JsonResultUtils;
import com.zb.app.common.result.JsonResultUtils.JsonResult;
import com.zb.app.common.result.Result;
import com.zb.app.web.controller.BaseController;
import com.zb.app.web.tools.WebUserTools;
import com.zb.app.web.vo.TravelPhotoVO;

/**
 * Account 图片管理,线路相关图片
 * 
 * @author zxc Jun 17, 2014 6:04:20 PM
 */
@Controller
@RequestMapping("/account")
public class AccountPhotoController extends BaseController {

    /**
     * 添加图片 TravelPhotoDO
     * 
     * @return
     */
    @AuthorityPolicy(authorityTypes = { Right.UPLOAD_PHOTOS })
    @RequestMapping(value = "/addPhoto.htm")
    @ResponseBody
    public JsonResult addPhoto(final TravelPhotoVO photos) {
        Integer success = 0, lose = 0;
        for (int i = 0; i < photos.getpPaths().length; i++) {
            final TravelPhotoDO photo = new TravelPhotoDO();
            photo.setcId(WebUserTools.getCid());
            photo.setpType(photos.getpType());
            photo.setpPath(photos.getpPaths()[i]);
            photo.setpTitle(photos.getpTitles()[i]);
            Result rusult = fileService.saveFileByPath(photo.getpPath(), new IFileHandle() {

                @Override
                public String parse(String prefix, String suffix) {
                    return prefix + WebUserTools.getCid() + "/" + PhotoTypeEnum.getAction(photo.getpType()).getName()
                           + "/" + suffix;
                }
            });
            photo.setpPath((String) rusult.getData());
            // data
            int index = photoService.addTravelPhoto(photo);
            index = index == 0 ? lose++ : success++;
        }
        return JsonResultUtils.success(null, "成功" + success + "张,失败" + lose + "张");
    }

    /**
     * 图片列表
     * 
     * @param query
     * @param model
     * @param pagesize
     * @param page
     * @return
     */
    @RequestMapping(value = "/photolist.htm")
    public String photo(TravelPhotoQuery query, Model model, Integer pagesize, Integer page) {
        // 设置条件
        query.setPageSize(Argument.isNotPositive(pagesize) ? 15 : pagesize);
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);
        query.setcId(WebUserTools.getCid());
        // 查询
        PaginationList<TravelPhotoDO> list = photoService.listPagination(query, new DefaultIpageUrl());
        model.addAttribute("list", list);
        model.addAttribute("pagination", list.getQuery());
        return "account/photo/list";
    }

    /**
     * 进入添加图片页面
     * 
     * @return
     */
    @AuthorityPolicy(authorityTypes = { Right.UPLOAD_PHOTOS })
    @RequestMapping(value = "/photoadd.htm")
    public String photoadd() {
        return "account/photo/add";
    }

    /***
     * 进入图片
     * 
     * @return
     */
    @RequestMapping(value = "/photo.htm")
    public String photo() {
        return "account/photo/index";
    }

    /**
     * 删除图片
     * 
     * @param id
     * @return
     */
    @AuthorityPolicy(authorityTypes = { Right.DELETE_PHOTOS })
    @RequestMapping(value = "/photodel.htm")
    @ResponseBody
    public JsonResult photodel(Integer id) {
        if (Argument.isNotPositive(id)) {
            return JsonResultUtils.error("格式错误!");
        }
        fileService.delFileByPath(photoService.getTravelPhotoById(id).getpPath());
        boolean bool = photoService.deleteTravelPhoto(id);
        return bool ? JsonResultUtils.success(null, "删除成功!") : JsonResultUtils.error("删除失败!");
    }

    @RequestMapping(value = "/num.htm")
    public String num(Model model, String act) {
        model.addAttribute("act", act);
        return "account/line/num";
    }
}
