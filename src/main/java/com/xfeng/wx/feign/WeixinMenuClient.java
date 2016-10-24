package com.xfeng.wx.feign;

import com.xfeng.wx.model.wxpage.WxResponse;

import feign.Param;
import feign.RequestLine;

//https://api.weixin.qq.com/cgi-bin
public interface WeixinMenuClient {

	//Feign.builder().encoder(new GsonEncoder())
	@RequestLine("POST /menu/create?access_token={access_token}")
	WxResponse create(@Param("access_token") String access_token, Object obj);

	@RequestLine("GET /menu/get?access_token={access_token}")
	Object get(@Param("access_token") String access_token);

	@RequestLine("GET /menu/delete?access_token={access_token}")
	WxResponse delete(@Param("access_token") String access_token);

}
