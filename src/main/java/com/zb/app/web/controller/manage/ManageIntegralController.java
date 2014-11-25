/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.manage;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zb.app.biz.cons.IntegralSourceEnum;
import com.zb.app.biz.cons.PhotoTypeEnum;
import com.zb.app.biz.domain.TravelGiftClassDO;
import com.zb.app.biz.domain.TravelGiftDO;
import com.zb.app.biz.domain.TravelGiftOrderFullDO;
import com.zb.app.biz.domain.TravelIntegralDO;
import com.zb.app.biz.query.TravelGiftClassQuery;
import com.zb.app.biz.query.TravelGiftOrderQuery;
import com.zb.app.biz.query.TravelGiftQuery;
import com.zb.app.biz.query.TravelIntegralQuery;
import com.zb.app.biz.service.impl.FileServiceImpl.IFileHandle;
import com.zb.app.common.authority.AuthorityPolicy;
import com.zb.app.common.authority.Right;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.DefaultIpageUrl;
import com.zb.app.common.result.JsonResultUtils;
import com.zb.app.common.result.JsonResultUtils.JsonResult;
import com.zb.app.common.result.Result;
import com.zb.app.common.velocity.CustomVelocityLayoutView;
import com.zb.app.web.controller.BaseController;
import com.zb.app.web.tools.WebUserTools;

/**
 * Manage礼品管理,积分管理 控制层
 * 
 * @author zxc Jun 16, 2014 3:53:26 PM
 */
@Controller
@RequestMapping("/zbmanlogin")
public class ManageIntegralController extends BaseController {
	
	@AuthorityPolicy(authorityTypes = { Right.GIFT_LIST })
    @RequestMapping(value = "/giftlist.htm")
    public ModelAndView gift(ModelAndView mav, TravelGiftQuery query, Integer page) {
        query.setPageSize(20);
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);

        PaginationList<TravelGiftDO> list = integralService.listPagination(query, new DefaultIpageUrl());

        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.addObject("giftList", list);
        mav.addObject("pagination", list.getQuery());
        mav.setViewName("/manage/gift/giftlist");
        return mav;
    }

    @RequestMapping(value = "/gift.htm")
    public ModelAndView gift(ModelAndView mav){
        List<TravelGiftClassDO> classDOs = integralService.list(new TravelGiftClassQuery());
        mav.addObject("gClassList", classDOs);
        mav.setViewName("/manage/gift/index");
    	return mav;
    }
    
    
    @RequestMapping(value = "/giftadd.htm")
    public ModelAndView giftadd(ModelAndView mav) {
        List<TravelGiftClassDO> classDOs = integralService.list(new TravelGiftClassQuery());
        mav.addObject("gClassList", classDOs);
        mav.setViewName("/manage/gift/add");
        return mav;
    }
    
    @RequestMapping(value = "/addGift.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addGift(ModelAndView mav, final TravelGiftDO travelGiftDO) {
        Result rusult = fileService.saveFileByPath(travelGiftDO.getgPic(), new IFileHandle() {

            @Override
            public String parse(String prefix, String suffix) {
                return prefix + WebUserTools.getCid() + "/" + PhotoTypeEnum.GIFT_PRODUCT.getName() + "/" + suffix;
            }
        });
        travelGiftDO.setgPic((String) rusult.getData());
        Integer i = integralService.addTravelGift(travelGiftDO);
        return i == 0 ? JsonResultUtils.error(travelGiftDO, "创建失败!") : JsonResultUtils.success(travelGiftDO, "创建成功!");
    }

    @RequestMapping(value = "/ljgiftedit.htm")
    public ModelAndView ljGiftEdit(ModelAndView mav, Long id) {
        TravelGiftDO giftDO = integralService.getTravelGiftById(id.intValue());
        List<TravelGiftClassDO> classDOs = integralService.list(new TravelGiftClassQuery());

        mav.addObject("gift", giftDO);
        mav.addObject("type", "update");
        mav.addObject("gClassList", classDOs);
        mav.setViewName("/manage/gift/add");
        return mav;
    }

    @RequestMapping(value = "/giftedit.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult giftedit(final TravelGiftDO giftDO) {
        Result rusult = fileService.saveFileByPath(giftDO.getgPic(), new IFileHandle() {

            @Override
            public String parse(String prefix, String suffix) {
                return prefix + WebUserTools.getCid() + "/" + PhotoTypeEnum.GIFT_PRODUCT.getName() + "/" + suffix;
            }
        });
        giftDO.setgPic((String) rusult.getData());
        boolean b = integralService.updateTravelGift(giftDO);
        if (b) {
            return JsonResultUtils.success(giftDO, "修改成功!");
        } else {
            return JsonResultUtils.error(giftDO, "修改失败!");
        }
    }

    /**
     * 删除产品
     * 
     * @return
     */
    @RequestMapping(value = "/deleteGift.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult deleteGift(Long id) {
        boolean b = integralService.deleteTravelGift(id.intValue());
        if (b) {
            return JsonResultUtils.success(id, "删除成功!");
        } else {
            return JsonResultUtils.error(id, "删除失败!");
        }
    }

    @RequestMapping(value = "/cartlist.htm")
    public ModelAndView gift_cart(ModelAndView mav, TravelGiftOrderQuery query, Integer page) {
        query.setPageSize(10);
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);

        PaginationList<TravelGiftOrderFullDO> list = integralService.fullListPagination(query, new DefaultIpageUrl());

        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.addObject("giftOrderList", list);
        mav.addObject("pagination", list.getQuery());
        mav.setViewName("/manage/gift/cartlist");
        return mav;

    }
    
    @RequestMapping(value = "/gift_cart.htm")
    public String gift_cart(){
    	return "/manage/gift/cart";
    }

    @RequestMapping(value = "/cart_view.htm")
    public ModelAndView cart_view(Long id, ModelAndView mav) {
        TravelGiftOrderFullDO fullDO = integralService.getTravelGiftOrderFullById(id.intValue());

        mav.addObject("fullDO", fullDO);
        mav.setViewName("/manage/gift/cart_view");
        return mav;
    }

    @RequestMapping(value = "/gift_sort.htm")
    public ModelAndView cart_sort(ModelAndView mav, TravelGiftClassQuery query, Integer page) {
        query.setPageSize(100);
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);

        PaginationList<TravelGiftClassDO> list = integralService.listPagination(query, new DefaultIpageUrl());

        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.addObject("giftClassList", list);
        mav.addObject("pagination", list.getQuery());
        mav.setViewName("/manage/gift/sort");
        return mav;
    }

    @RequestMapping(value = "/gift_sortadd.htm")
    public String gift_sortadd() {
        return "/manage/gift/sort_add";
    }

    @RequestMapping(value = "/gift_sortedit.htm")
    public ModelAndView gift_sortedit(ModelAndView mav, Long id) {
        TravelGiftClassDO giftDO = integralService.getTravelGiftClassById(id.intValue());
        mav.addObject("sort", giftDO);
        mav.addObject("type", "update");
        mav.setViewName("/manage/gift/sort_add");
        return mav;
    }

    @RequestMapping(value = "/addGiftClass.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addGiftClass(TravelGiftClassDO classDO) {
        Integer i = integralService.addTravelGiftClass(classDO);
        return i == 0 ? JsonResultUtils.error(classDO, "创建失败!") : JsonResultUtils.success(classDO, "创建成功!");
    }

    @RequestMapping(value = "/editgiftclass.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult gift_sortedit(TravelGiftClassDO giftClassDO) {
        boolean i = integralService.updateTravelGiftClass(giftClassDO);

        return i == false ? JsonResultUtils.error(giftClassDO, "修改失败!") : JsonResultUtils.success(giftClassDO, "修改成功!");
    }

    /**
     * 删除产品类别
     * 
     * @return
     */
    @RequestMapping(value = "/deleteGiftClass.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult deleteGiftClass(Long id) {
        boolean b = integralService.deleteTravelGiftClass(id.intValue());
        if (b) {
            return JsonResultUtils.success(id, "删除成功!");
        } else {
            return JsonResultUtils.error(id, "删除失败!");
        }
    }

    /**
     * 积分列表
     * 
     * @return
     */
    @RequestMapping(value = "/integral.htm")
    public String integral() {
        return "/manage/integral/index";
    }

    /**
     * 积分使用列表
     * 
     * @return
     */
    @RequestMapping(value = "/integral/list.htm")
    public String integrallist() {
        return "/manage/integral/list";
    }

    /**
     * 积分详细列表
     * 
     * @return
     */
    @RequestMapping(value = "/integral/view.htm")
    public String integralview() {
        return "/manage/integral/view";
    }

    /**
     * 积分添加
     * 
     * @return
     */
    @RequestMapping(value = "/integral/add.htm")
    public String integraladd() {
        return "/manage/integral/add";
    }
    @RequestMapping(value = "/addIntegral.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addIntegral(ModelAndView mav, final TravelIntegralDO integralDO) {
    	TravelIntegralQuery integralQuery = new TravelIntegralQuery();
        integralQuery.setcId(integralDO.getcId());
        TravelIntegralDO integralYE = integralService.queryBala(integralQuery);
        integralDO.setiSource(IntegralSourceEnum.recharge.value);
        if(integralYE == null){
        	integralDO.setiBalance(integralDO.getiAddintegral());
        	integralDO.setiFrozen(0);
        }else{
        	integralDO.setiBalance(integralYE.getiBalance() + integralDO.getiAddintegral());
        	integralDO.setiFrozen(integralYE.getiFrozen());
        }
    	integralDO.setiAltogether(integralDO.getiBalance() + integralDO.getiFrozen());
        Integer i = integralService.addTravelIntegral(integralDO);
        return i == 0 ? JsonResultUtils.error(integralDO, "充值失败!") : JsonResultUtils.success(integralDO, "充值成功!");
    }
    /**
     * 组团社积分添加
     * 
     * @return
     */
    @RequestMapping(value = "/tourAddIntegral.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult tourAddIntegral(ModelAndView mav, final TravelIntegralDO integralDO, Long accId) {
    	if(accId != null){
	    	TravelIntegralQuery accIntegralQuery = new TravelIntegralQuery();
	        accIntegralQuery.setcId(accId);
	        TravelIntegralDO accIntegral = integralService.queryBala(accIntegralQuery);
	        if(accIntegral == null){
	        	return JsonResultUtils.error(integralDO, "批发商没有积分!");
	        }
	        if(accIntegral.getiBalance() < integralDO.getiAddintegral()){
	        	return JsonResultUtils.error(integralDO, "批发商积分不足!");
	        }
	        TravelIntegralDO newACC = new TravelIntegralDO();
	        newACC.setcId(accId);
	        newACC.setiSource(IntegralSourceEnum.transfer.value);
	        newACC.setiAddintegral(0 - integralDO.getiAddintegral());
	        newACC.setiBalance(accIntegral.getiBalance() + newACC.getiAddintegral());
	        newACC.setiFrozen(accIntegral.getiFrozen());
	        newACC.setiAltogether(newACC.getiBalance() + newACC.getiFrozen());
	        newACC.setiRemark(integralDO.getiRemark());
	        integralService.addTravelIntegral(newACC);
    	}
    	TravelIntegralQuery integralQuery = new TravelIntegralQuery();
        integralQuery.setcId(integralDO.getcId());
        integralQuery.setmId(integralDO.getmId());
        TravelIntegralDO integralYE = integralService.queryBala(integralQuery);
        integralDO.setiSource(IntegralSourceEnum.recharge.value);
        if(integralYE == null){
        	integralDO.setiBalance(integralDO.getiAddintegral());
        	integralDO.setiFrozen(0);
        }else{
        	integralDO.setiBalance(integralYE.getiBalance() + integralDO.getiAddintegral());
        	integralDO.setiFrozen(integralYE.getiFrozen());
        }
    	integralDO.setiAltogether(integralDO.getiBalance() + integralDO.getiFrozen());
        Integer i = integralService.addTravelIntegral(integralDO);
        return i == 0 ? JsonResultUtils.error(integralDO, "充值失败!") : JsonResultUtils.success(integralDO, "充值成功!");
    }
}
