/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.upload;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.zb.app.common.cons.ResultCode;
import com.zb.app.common.result.Result;
import com.zb.app.web.controller.BaseController;

/**
 * file upload
 * 
 * @author zxc Jun 30, 2014 3:09:36 PM
 */
@Controller
public class FileUploadController extends BaseController {

    @RequestMapping(value = "/ajaxUpload.htm")
    public ModelAndView ajaxUpload(@RequestParam("files")
    MultipartFile... files) {
        if (files == null || files.length <= 0) {
            return createFileJsonMav(ResultCode.ERROR, "上传失败", null);
        }
        List<String> urlList = new ArrayList<String>();
        for (int i = 0; i < files.length; i++) {
            Result result = fileService.createFilePath(files[i]);
            if (result == null || result.getData() == null) {
                return createFileJsonMav(ResultCode.ERROR, "上传失败", null);
            }
            urlList.add((String) result.getData());
        }
        return createFileJsonMav(ResultCode.SUCCESS, "上传成功", urlList.get(0));
    }

    @RequestMapping("/doUploads.htm")
    public ModelAndView filesUpload(@RequestParam("files")
    MultipartFile... files) {
        Result result = null;
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                result = fileService.createFilePath(file, file.getOriginalFilename());
            }
        }
        if (result == null || result.getData() == null) {
            return createFileJsonMav(ResultCode.ERROR, "上传失败", null);
        }
        return createFileJsonMav(ResultCode.SUCCESS, "上传成功", result.getData().toString());
    }

    @RequestMapping("/doUpload.htm")
    public ModelAndView fileUpload(@RequestParam("file")
    MultipartFile file) {
        Result result = null;
        if (!file.isEmpty()) {
            result = fileService.createFilePath(file, file.getOriginalFilename());
        }
        if (result == null || result.getData() == null) {
            return createFileJsonMav(ResultCode.ERROR, "上传失败", null);
        }
        return createFileJsonMav(ResultCode.SUCCESS, "上传成功", result.getData().toString());
    }

    @RequestMapping(value = "/fileUpload.htm")
    public String index() {
        return "upload";
    }
}
