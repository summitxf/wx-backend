package com.xfeng.wx.feign;

import com.xfeng.wx.model.wxpage.AccessToken;

import feign.Param;
import feign.RequestLine;

//https://api.weixin.qq.com/cgi-bin/
public interface WeixinAccessTokenClient {

	@RequestLine("GET /token?grant_type=client_credential&appid={appid}&secret={secret}")
	AccessToken get(@Param("appid") String appid, @Param("secret") String secret);
}
