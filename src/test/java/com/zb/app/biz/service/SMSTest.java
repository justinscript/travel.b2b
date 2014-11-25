/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.service;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

/**
 * <pre>
 * 
 * // 注册
 * String result_register = client.register(&quot;省份&quot;, &quot;城市&quot;, &quot;服务&quot;, &quot;公司名称&quot;, &quot;联系人&quot;, &quot;88888888&quot;, &quot;13910423404&quot;, &quot;111@126.com&quot;,
 *                                          &quot;88888888&quot;, &quot;北京&quot;, &quot;123456&quot;);
 * if (result_register.startsWith(&quot;-&quot;)) {
 *     System.out.print(result_register);
 * } else {
 *     System.out.print(&quot;恭喜您，注册成功！&quot;);
 * }
 * 
 * // 充值，参数依次为充值卡号和密码
 * String result_charge = client.chargeFee(&quot;&quot;, &quot;&quot;);
 * if (result_charge.startsWith(&quot;-&quot;)) {
 *     System.out.print(result_charge);
 * } else {
 *     System.out.print(&quot;充值成功,最新余额为：&quot; + client.getBalance() + &quot;条&quot;);
 * }
 * 
 * // 查询余额
 * String result_balance = client.getBalance();
 * System.out.print(result_balance);
 * 
 * // 短信发送
 * String result_mt = client.mt(&quot;&quot;, &quot;程序接口测试&quot;, &quot;&quot;, &quot;&quot;, &quot;&quot;);
 * System.out.print(result_mt);
 * 
 * // 查询msgid //此方法用来查询最后成功发送短信的100次msgid
 * String result_msgid = client.msgid();
 * System.out.print(result_msgid);
 * 
 * // 接收短信
 * String result_mo = client.mo();
 * if (result_mo.startsWith(&quot;-&quot;)) { // 接收失败的情况，输出失败信息
 *     System.out.print(result_mo);
 * } else if (&quot;1&quot; == result_mo) {
 *     System.out.print(&quot;无可接收信息&quot;);
 * } else { // 多条信息的情况，以回车换行分割
 *     String[] result = result_mo.split(&quot;\r\n&quot;);
 *     for (int i = 0; i &lt; result.length; i++) { // 内容做了url编码，在此解码，编码方式gb2312
 *         System.out.print(URLDecoder.decode(result_mo, &quot;gb2312&quot;) + &quot;\r\n&quot;);
 *     }
 * }
 * String content1 = URLEncoder.encode(&quot;您好1&quot;, &quot;gb2312&quot;);
 * String content2 = URLEncoder.encode(&quot;您好2&quot;, &quot;gb2312&quot;);
 * String content3 = URLEncoder.encode(&quot;您好3&quot;, &quot;gb2312&quot;);
 * String mobile1 = &quot;15201692834&quot;;
 * String mobile2 = &quot;15201692834&quot;;// 手机号不同
 * String mobile3 = &quot;15201692834&quot;;
 * String content4 = content1 + content2 + content3;
 * String mobile4 = mobile1 + mobile2 + mobile3;
 * String result = client.gxmt(mobile4, content4, &quot;&quot;, &quot;&quot;, &quot;&quot;);
 * System.out.print(result);
 * 
 * <pre>
 * 
 * @author zxc Nov 13, 2014 12:39:06 PM
 */
public class SMSTest {

    public static void main(String[] args) throws IOException {

        for (int i = 1; i < 11; i++) {
            Client registerClient = new Client("SDK-JJJ-010-01820", "df9a441@");

            System.out.println("左边网,短信测试!!!收到请不要回复,谢谢! 第" + i + "次测试,Test Start!");
            registerClient.mt("13918731742,15001968175,18616799662,18912386146",
                              "左边网,短信测试!!!收到请不要回复,谢谢! 第" + i + "次测试", "", "", "");
            System.out.println("左边网,短信测试!!!收到请不要回复,谢谢! 第" + i + "次测试,Test End!");

            System.out.println("Now,start sleep!");
            try {
                Thread.sleep(1000 * 180);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Now,sleep end!");
        }
    }

    public static void main1(String[] args) throws IOException {

        Client registerClient = new Client();
        String result = registerClient.register("上海", "上海", "旅游", "上海左边信息科技有限公司", "张文缙", "021-57610236", "18616799662",
                                                "893714217@qq.com", "021-57610236", "上海市松江区文诚路356弄6号嘉和商务中心1307室",
                                                "201600");
        System.out.println(result);

        String sn = "SDK-JJJ-010-01820";
        String pwd = "df9a44";
        Client client = new Client(sn, pwd);

        // 如为第一次使用，请先注册，注册方法如下，为方便在后来的合作成功客服人员给您提供服务请如实填写下列信息
        // 只需注册一次即可
        // 返回值说明：注册成功返回0 注册成功
        List list = client.RECSMSEx("1");
        if (list != null && list.size() > 0) {
            for (int i = 0; i <= list.size() - 1; i++) {
                String str = "";
                str = list.get(i).toString();
                String aa[] = str.split(",");
                System.out.print("特服号为: " + aa[1]);
                System.out.print("手机号为: " + aa[2]);
                System.out.print("短信内容为: " + URLDecoder.decode(aa[3], "GB2312"));// 对内容进行解码
                System.out.print("回复时间为: " + aa[4]);
                System.out.println("");
            }
        } else {
            System.out.println("没有回复数据");
        }
    }
}
