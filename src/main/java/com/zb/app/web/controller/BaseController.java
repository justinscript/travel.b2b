/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.zb.app.biz.cons.CompanyStateEnum;
import com.zb.app.biz.cons.CompanyTypeEnum;
import com.zb.app.biz.cons.MemberStateEnum;
import com.zb.app.biz.domain.TravelCompanyDO;
import com.zb.app.biz.domain.TravelMemberDO;
import com.zb.app.biz.query.TravelMemberQuery;
import com.zb.app.biz.service.combiz.BizCommonService;
import com.zb.app.biz.service.interfaces.CmsService;
import com.zb.app.biz.service.interfaces.CompanyService;
import com.zb.app.biz.service.interfaces.FileService;
import com.zb.app.biz.service.interfaces.FinanceService;
import com.zb.app.biz.service.interfaces.IntegralService;
import com.zb.app.biz.service.interfaces.LineService;
import com.zb.app.biz.service.interfaces.MemberService;
import com.zb.app.biz.service.interfaces.MessageService;
import com.zb.app.biz.service.interfaces.OperationLogService;
import com.zb.app.biz.service.interfaces.OrderService;
import com.zb.app.biz.service.interfaces.PhotoService;
import com.zb.app.biz.service.interfaces.SiteService;
import com.zb.app.common.component.ComponentController;
import com.zb.app.common.notify.NotifyService;
import com.zb.app.common.result.JsonResultUtils;
import com.zb.app.common.result.JsonResultUtils.JsonResult;
import com.zb.app.common.result.Result;
import com.zb.app.common.security.EncryptBuilder;
import com.zb.app.external.lucene.solr.client.SolrClient;
import com.zb.app.web.cons.ZuobianInterface;
import com.zb.app.web.webuser.ZuobianWebUser;
import com.zb.app.web.webuser.ZuobianWebUserBuilder;
import com.zb.app.websocket.api.MessageEvent;
import com.zb.app.websocket.api.MessageMapper;

/**
 * @author zxc Jun 15, 2014 11:15:54 PM
 */
@Validated
@ControllerAdvice
public class BaseController extends ComponentController implements ZuobianInterface {

    // 业务层服务对象注入
    @Autowired
    protected MemberService       memberService;
    @Autowired
    protected CompanyService      companyService;
    @Autowired
    protected LineService         lineService;
    @Autowired
    protected OrderService        orderService;
    @Autowired
    protected SiteService         siteService;
    @Autowired
    protected FileService         fileService;
    @Autowired
    protected CmsService          cmsService;
    @Autowired
    protected MessageService      messageService;
    @Autowired
    protected FinanceService      financeService;
    @Autowired
    protected IntegralService     integralService;
    @Autowired
    protected PhotoService        photoService;
    @Autowired
    protected OperationLogService operationLogService;

    @Autowired
    protected BizCommonService    bizCommonService;

    @Autowired
    private NotifyService         notifyService;
    @Autowired
    protected SolrClient          solrClient;

    public void notifyMsgEvent(final ZuobianWebUser webUser, final MessageMapper<?, ?> mm) {
        notifyService.notify(new MessageEvent(webUser, mm));
    }

    /**
     * 返回未通过验证信息
     * 
     * @param result
     * @return
     */
    public Result showErrors(BindingResult result) {
        StringBuffer errorsb = new StringBuffer();
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                errorsb.append(error.getField());
                errorsb.append(error.getDefaultMessage());
                errorsb.append("|");
            }
            String errorsr = errorsb.toString().substring(0, errorsb.toString().length() - 1);
            return Result.failed(errorsr.replaceAll("null", StringUtils.EMPTY));
        }
        return Result.success();
    }

    protected JsonResult doLonginWithCheck(String account, String password, CompanyTypeEnum type) {
        account = StringUtils.trim(account);
        password = StringUtils.trim(password);
        password = EncryptBuilder.getInstance().encrypt(password);
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)) {
            return JsonResultUtils.error("登录失败!提交的数据错误!");
        }
        // Query the database, check the user authentication information
        TravelMemberDO tm = memberService.getByName(new TravelMemberQuery(StringUtils.lowerCase(account),
                                                                          type.getValue()));
        if (tm == null) {
            return JsonResultUtils.error("登录失败!用户不存在!");
        }
        if (!StringUtils.equals(password, tm.getmPassword())) {
            return JsonResultUtils.error("登录失败!用户名或密码错误!");
        }
        if (tm.getmState() == MemberStateEnum.STOP.getValue()) {
            return JsonResultUtils.error("登录失败!该用户已被禁用!");
        }
        // 公司的验证
        TravelCompanyDO tc = companyService.getById(tm.getcId());
        if (tc == null) {
            return JsonResultUtils.error("登录失败!用户所属公司为空!");
        }
        if (tc.getcType() != type.getValue()) {
            return JsonResultUtils.error("登录失败!非" + type.getDesc() + "用户登录!");
        }
        if (tc.getcState() != CompanyStateEnum.NORMAL.getValue()) {
            return JsonResultUtils.error("登录失败!该公司未审核或已停止!");
        }
        // Verified, writing cookie
        tm.setmType(tc.getcType());
        String url = CompanyTypeEnum.getEnum(tc.getcType()).getIndexUrl();
        doLoginSuccess(tm);
        return JsonResultUtils.success(url, "登录成功!");
    }

    /**
     * 写入登录cookie信息到当前线程response中
     * 
     * @param TravelMemberDO
     */
    public void doLoginSuccess(TravelMemberDO tm) {
        ZuobianWebUserBuilder.loginSuccess(cookieManager, tm.getmUserName(), tm.getmId(), tm.getcId(), tm.getmType());
        memberService.update(new TravelMemberDO(tm.getmId()));
    }

    /**
     * 更新退出cookie信息到当前线程response中
     */
    public void doLoginOut() {
        ZuobianWebUserBuilder.loginOut(cookieManager);
    }
}
