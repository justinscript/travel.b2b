/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 序列号SDK-JJJ-010-01820 密码df9a441@ 特服号228489
 * 
 * @author zxc Nov 13, 2014 12:39:06 PM
 */
public class Client {

    /*
     * webservice服务器定义
     */
    // public String service_old="http://sdk2.entinfo.cn/webservice.asmx";
    private String serviceURL = "http://sdk2.entinfo.cn/webservice.asmx";

    private String sn         = "SDK-JJJ-010-01820";                     // 序列号

    private String password   = "df9a44";

    private String pwd        = "df9a44";                                // 密码

    Document       document   = null;

    NodeList       allNode    = null;

    List           list       = new ArrayList();

    public Client() throws UnsupportedEncodingException {

    }

    /*
     * 构造函数
     */
    public Client(String sn, String password) throws UnsupportedEncodingException {
        this.sn = sn;
        this.password = password;
        this.pwd = this.getMD5(sn + password);
    }

    /*
     * 方法名称：getMD5 功 能：字符串MD5加密 参 数：待转换字符串 返 回 值：加密之后字符串
     */
    public String getMD5(String sourceStr) throws UnsupportedEncodingException {
        String resultStr = "";
        try {
            byte[] temp = sourceStr.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(temp);
            // resultStr = new String(md5.digest());
            byte[] b = md5.digest();
            for (int i = 0; i < b.length; i++) {
                char[] digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
                char[] ob = new char[2];
                ob[0] = digit[(b[i] >>> 4) & 0X0F];
                ob[1] = digit[b[i] & 0X0F];
                resultStr += new String(ob);
            }
            return resultStr;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 按条件输出
    public List showByCondition(NodeList allNode) throws UnsupportedEncodingException {
        Element element;
        // 对符合条件的所有节点进行遍历
        for (int i = 0; i < allNode.getLength(); i++) {
            // 获得一个元素
            element = (Element) allNode.item(i);
            // 输出这个元素的personid属性
            // System.out.println(element.getAttribute("personid"));
            // 此元素有子节点，获取所有子节点，返回一个personList
            NodeList personList = element.getChildNodes();
            // 遍历所有子节点
            String counts = personList.item(0).getTextContent();// 总条数
            String reString = personList.item(2).getTextContent();// 接收者
            String sendString = personList.item(3).getTextContent();// 发送者
            String content = personList.item(4).getTextContent();// 发送内容
            String dateString = personList.item(5).getTextContent();// 回复日期
            if (Integer.parseInt(counts) > 0) {
                // 对内容进行编码
                content = URLEncoder.encode(content, "gb2312");
                list.add(counts + "," + reString + "," + sendString + "," + content + "," + dateString);
            } else {
                list = null;
            }
        }
        return list;
    }

    /*
     * 方法名称：register 功 能：注册 参 数：对应参数 省份，城市，行业，企业名称，联系人，电话，手机，电子邮箱，传真，地址，邮编 返 回 值：注册结果（String）
     */
    public String register(String province, String city, String trade, String entname, String linkman, String phone,
                           String mobile, String email, String fax, String address, String postcode) {
        String result = "";
        String soapAction = "http://tempuri.org/Register";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xml += "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">";
        xml += "<soap12:Body>";
        xml += "<Register xmlns=\"http://tempuri.org/\">";
        xml += "<sn>" + sn + "</sn>";
        xml += "<pwd>" + password + "</pwd>";
        xml += "<province>" + province + "</province>";
        xml += "<city>" + city + "</city>";
        xml += "<trade>" + trade + "</trade>";
        xml += "<entname>" + entname + "</entname>";
        xml += "<linkman>" + linkman + "</linkman>";
        xml += "<phone>" + phone + "</phone>";
        xml += "<mobile>" + mobile + "</mobile>";
        xml += "<email>" + email + "</email>";
        xml += "<fax>" + fax + "</fax>";
        xml += "<address>" + address + "</address>";
        xml += "<postcode>" + postcode + "</postcode>";
        xml += "</Register>";
        xml += "</soap12:Body>";
        xml += "</soap12:Envelope>";

        URL url;
        try {
            url = new URL(serviceURL);

            URLConnection connection = url.openConnection();
            HttpURLConnection httpconn = (HttpURLConnection) connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bout.write(xml.getBytes());
            byte[] b = bout.toByteArray();
            httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpconn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            httpconn.setRequestProperty("SOAPAction", soapAction);
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);

            OutputStream out = httpconn.getOutputStream();
            out.write(b);
            out.close();

            InputStreamReader isr = new InputStreamReader(httpconn.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            String inputLine;
            while (null != (inputLine = in.readLine())) {
                Pattern pattern = Pattern.compile("<RegisterResult>(.*)</RegisterResult>");
                Matcher matcher = pattern.matcher(inputLine);
                while (matcher.find()) {
                    result = matcher.group(1);
                }
            }
            in.close();
            return new String(result.getBytes(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /*
     * 方法名称：chargeFee 功 能：充值 参 数：充值卡号，充值密码 返 回 值：操作结果（String）
     */
    public String chargeFee(String cardno, String cardpwd) {
        String result = "";
        String soapAction = "http://tempuri.org/ChargUp";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xml += "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">";
        xml += "<soap12:Body>";
        xml += "<ChargUp xmlns=\"http://tempuri.org/\">";
        xml += "<sn>" + sn + "</sn>";
        xml += "<pwd>" + password + "</pwd>";
        xml += "<cardno>" + cardno + "</cardno>";
        xml += "<cardpwd>" + cardpwd + "</cardpwd>";
        xml += "</ChargUp>";
        xml += "</soap12:Body>";
        xml += "</soap12:Envelope>";

        URL url;
        try {
            url = new URL(serviceURL);

            URLConnection connection = url.openConnection();
            HttpURLConnection httpconn = (HttpURLConnection) connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bout.write(xml.getBytes());
            byte[] b = bout.toByteArray();
            httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpconn.setRequestProperty("Content-Type", "text/xml; charset=gb2312");
            httpconn.setRequestProperty("SOAPAction", soapAction);
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);

            OutputStream out = httpconn.getOutputStream();
            out.write(b);
            out.close();

            InputStreamReader isr = new InputStreamReader(httpconn.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            String inputLine;
            while (null != (inputLine = in.readLine())) {
                Pattern pattern = Pattern.compile("<ChargUpResult>(.*)</ChargUpResult>");
                Matcher matcher = pattern.matcher(inputLine);
                while (matcher.find()) {
                    result = matcher.group(1);
                }
            }
            in.close();
            // return result;
            return new String(result.getBytes(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /*
     * 方法名称：getBalance 功 能：获取余额 参 数：无 返 回 值：余额（String）
     */
    public String getBalance() {
        String result = "";
        String soapAction = "http://tempuri.org/balance";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
        xml += "<soap:Body>";
        xml += "<balance xmlns=\"http://tempuri.org/\">";
        xml += "<sn>" + sn + "</sn>";
        xml += "<pwd>" + pwd + "</pwd>";
        xml += "</balance>";
        xml += "</soap:Body>";
        xml += "</soap:Envelope>";

        URL url;
        try {
            url = new URL(serviceURL);

            URLConnection connection = url.openConnection();
            HttpURLConnection httpconn = (HttpURLConnection) connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bout.write(xml.getBytes());
            byte[] b = bout.toByteArray();
            httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpconn.setRequestProperty("Content-Type", "text/xml; charset=gb2312");
            httpconn.setRequestProperty("SOAPAction", soapAction);
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);

            OutputStream out = httpconn.getOutputStream();
            out.write(b);
            out.close();

            InputStreamReader isr = new InputStreamReader(httpconn.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            String inputLine;
            while (null != (inputLine = in.readLine())) {
                Pattern pattern = Pattern.compile("<balanceResult>(.*)</balanceResult>");
                Matcher matcher = pattern.matcher(inputLine);
                while (matcher.find()) {
                    result = matcher.group(1);
                }
            }
            in.close();
            return new String(result.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /*
     * 方法名称：mt 功 能：发送短信 参 数：mobile,content,ext,stime,rrid(手机号，内容，扩展码，定时时间，唯一标识) 返 回 值：唯一标识，如果不填写rrid将返回系统生成的
     */
    public String mt(String mobile, String content, String ext, String stime, String rrid) {
        String result = "";
        String soapAction = "http://tempuri.org/mt";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
        xml += "<soap:Body>";
        xml += "<mt xmlns=\"http://tempuri.org/\">";
        xml += "<sn>" + sn + "</sn>";
        xml += "<pwd>" + pwd + "</pwd>";
        xml += "<mobile>" + mobile + "</mobile>";
        xml += "<content>" + content + "</content>";
        xml += "<ext>" + ext + "</ext>";
        xml += "<stime>" + stime + "</stime>";
        xml += "<rrid>" + rrid + "</rrid>";
        xml += "</mt>";
        xml += "</soap:Body>";
        xml += "</soap:Envelope>";

        URL url;
        try {
            url = new URL(serviceURL);

            URLConnection connection = url.openConnection();
            HttpURLConnection httpconn = (HttpURLConnection) connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bout.write(xml.getBytes());
            byte[] b = bout.toByteArray();
            httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpconn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            httpconn.setRequestProperty("SOAPAction", soapAction);
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);

            OutputStream out = httpconn.getOutputStream();
            out.write(b);
            out.close();

            InputStreamReader isr = new InputStreamReader(httpconn.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            String inputLine;
            while (null != (inputLine = in.readLine())) {
                Pattern pattern = Pattern.compile("<mtResult>(.*)</mtResult>");
                Matcher matcher = pattern.matcher(inputLine);
                while (matcher.find()) {
                    result = matcher.group(1);
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /*
     * 方法名称：gxmt 功 能：发送短信 参 数：mobile,content,ext,stime,rrid(手机号，内容，扩展码，定时时间，唯一标识) 返 回 值：唯一标识，如果不填写rrid将返回系统生成的
     */
    public String gxmt(String mobile, String content, String ext, String stime, String rrid) {
        String result = "";
        String soapAction = "http://tempuri.org/gxmt";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
        xml += "<soap:Body>";
        xml += "<gxmt xmlns=\"http://tempuri.org/\">";
        xml += "<sn>" + sn + "</sn>";
        xml += "<pwd>" + pwd + "</pwd>";
        xml += "<mobile>" + mobile + "</mobile>";
        xml += "<content>" + content + "</content>";
        xml += "<ext>" + ext + "</ext>";
        xml += "<stime>" + stime + "</stime>";
        xml += "<rrid>" + rrid + "</rrid>";
        xml += "</gxmt>";
        xml += "</soap:Body>";
        xml += "</soap:Envelope>";

        URL url;
        try {
            url = new URL(serviceURL);

            URLConnection connection = url.openConnection();
            HttpURLConnection httpconn = (HttpURLConnection) connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bout.write(xml.getBytes());
            byte[] b = bout.toByteArray();
            httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpconn.setRequestProperty("Content-Type", "text/xml; charset=gb2312");
            httpconn.setRequestProperty("SOAPAction", soapAction);
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);

            OutputStream out = httpconn.getOutputStream();
            out.write(b);
            out.close();

            InputStreamReader isr = new InputStreamReader(httpconn.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            String inputLine;
            while (null != (inputLine = in.readLine())) {
                Pattern pattern = Pattern.compile("<gxmtResult>(.*)</gxmtResult>");
                Matcher matcher = pattern.matcher(inputLine);
                while (matcher.find()) {
                    result = matcher.group(1);
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /*
     * 方法名称：mo 功 能：接收短信 参 数：无 返 回 值：接收到的信息
     */
    public String mo() {
        String result = "";
        String soapAction = "http://tempuri.org/mo";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
        xml += "<soap:Body>";
        xml += "<mo xmlns=\"http://tempuri.org/\">";
        xml += "<sn>" + sn + "</sn>";
        xml += "<pwd>" + pwd + "</pwd>";
        xml += "</mo>";
        xml += "</soap:Body>";
        xml += "</soap:Envelope>";

        URL url;
        try {
            url = new URL(serviceURL);

            URLConnection connection = url.openConnection();
            HttpURLConnection httpconn = (HttpURLConnection) connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bout.write(xml.getBytes());
            byte[] b = bout.toByteArray();
            httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpconn.setRequestProperty("Content-Type", "text/xml; charset=gb2312");
            httpconn.setRequestProperty("SOAPAction", soapAction);
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);

            OutputStream out = httpconn.getOutputStream();
            out.write(b);
            out.close();

            InputStream isr = httpconn.getInputStream();
            StringBuffer buff = new StringBuffer();
            byte[] byte_receive = new byte[10240];
            for (int i = 0; (i = isr.read(byte_receive)) != -1;) {
                buff.append(new String(byte_receive, 0, i));
            }
            isr.close();
            String result_before = buff.toString();
            int start = result_before.indexOf("<moResult>");

            int end = result_before.indexOf("</moResult>");
            result = result_before.substring(start + 10, end);

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /*
     * 方法名称：RECSMSEx 功 能：接收短信 参 数：无 返 回 值：接收到的信息
     */

    public List RECSMSEx(String subcode) throws IOException {
        String result = "";
        String soapAction = "http://tempuri.org/RECSMSEx";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
        xml += "<soap:Body>";
        xml += "<RECSMSEx xmlns=\"http://tempuri.org/\">";
        xml += "<sn>" + sn + "</sn>";
        xml += "<pwd>" + password + "</pwd>";
        xml += "<subcode>" + subcode + "</subcode>";
        xml += "</RECSMSEx>";
        xml += "</soap:Body>";
        xml += "</soap:Envelope>";

        URL url;
        try {
            url = new URL(serviceURL);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpconn = (HttpURLConnection) connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bout.write(xml.getBytes("gbk"));
            byte[] b = bout.toByteArray();
            httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpconn.setRequestProperty("Content-Type", "text/xml; charset=gb2312");
            httpconn.setRequestProperty("SOAPAction", soapAction);
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);
            OutputStream outStream = httpconn.getOutputStream();
            outStream.write(b);
            outStream.close();
            InputStream isr = httpconn.getInputStream();
            // 建立DocumentBuilderFactory对象
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            try {
                // 建立DocumentBuilder对象
                builder = builderFactory.newDocumentBuilder();
                // 用DocumentBuilder对象的parse方法引入文件建立Document对象
                document = builder.parse(isr);
                allNode = document.getChildNodes();
                // 找出所有person标签，返回NodeList
                NodeList person = document.getElementsByTagName("MOBody");
                // 按条件输出peron标签中的属性和值
                List list = showByCondition(person);
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }
        } finally {

        }
        return list;
    }

    /*
     * 方法名称：msgid 功 能：获取msgid（发送成功的最后100次） 参 数：无 返 回 值：msgid串
     */
    public String msgid() {
        String result = "";
        String soapAction = "http://tempuri.org/msgid";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
        xml += "<soap:Body>";
        xml += "<msgid xmlns=\"http://tempuri.org/\">";
        xml += "<sn>" + sn + "</sn>";
        xml += "<pwd>" + pwd + "</pwd>";
        xml += "</msgid>";
        xml += "</soap:Body>";
        xml += "</soap:Envelope>";

        URL url;
        try {
            url = new URL(serviceURL);

            URLConnection connection = url.openConnection();
            HttpURLConnection httpconn = (HttpURLConnection) connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bout.write(xml.getBytes());
            byte[] b = bout.toByteArray();
            httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpconn.setRequestProperty("Content-Type", "text/xml; charset=gb2312");
            httpconn.setRequestProperty("SOAPAction", soapAction);
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);

            OutputStream out = httpconn.getOutputStream();
            out.write(b);
            out.close();

            InputStream isr = httpconn.getInputStream();
            StringBuffer buff = new StringBuffer();
            byte[] byte_receive = new byte[10240];
            for (int i = 0; (i = isr.read(byte_receive)) != -1;) {
                buff.append(new String(byte_receive, 0, i));
            }
            isr.close();
            String result_before = buff.toString();
            int start = result_before.indexOf("<msgidResult>");
            int end = result_before.indexOf("</msgidResult>");
            result = result_before.substring(start + 13, end);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
