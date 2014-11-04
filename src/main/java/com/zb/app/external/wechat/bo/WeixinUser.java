/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.wechat.bo;

/**
 * 微信用户信息
 * 
 * <pre>
 *  subscribe       用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
 *  openid          用户的标识，对当前公众号唯一
 *  nickname        用户的昵称
 *  sex             用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
 *  city            用户所在城市
 *  country         用户所在国家
 *  province        用户所在省份
 *  language        用户的语言，简体中文为zh_CN
 *  headimgurl      用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
 *  subscribe_time  用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
 * </pre>
 * 
 * @author zxc Oct 22, 2014 4:43:49 PM
 */
public class WeixinUser {

    private String subscribe;
    private String openid;
    private String nickname;
    private String sex;
    private String city;
    private String country;
    private String province;
    private String language;
    private String headimgurl;
    private long   subscribeTime;

    /**
     * @return the subscribe
     */
    public String getSubscribe() {
        return subscribe;
    }

    /**
     * @param subscribe the subscribe to set
     */
    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    /**
     * @return the openid
     */
    public String getOpenid() {
        return openid;
    }

    /**
     * @param openid the openid to set
     */
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    /**
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname the nickname to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return the sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the province
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param province the province to set
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return the headimgurl
     */
    public String getHeadimgurl() {
        return headimgurl;
    }

    /**
     * @param headimgurl the headimgurl to set
     */
    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    /**
     * @return the subscribeTime
     */
    public long getSubscribeTime() {
        return subscribeTime;
    }

    /**
     * @param subscribeTime the subscribeTime to set
     */
    public void setSubscribe_time(long subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public String toString() {
        return "WeixinUser [subscribe=" + subscribe + ", openid=" + openid + ", nickname=" + nickname + ", sex=" + sex
               + ", city=" + city + ", country=" + country + ", province=" + province + ", language=" + language
               + ", headimgurl=" + headimgurl + ", subscribeTime=" + subscribeTime + "]";
    }
}
