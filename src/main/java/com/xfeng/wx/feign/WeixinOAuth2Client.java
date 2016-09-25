package com.xfeng.wx.feign;

import com.xfeng.wx.model.wxpage.AccessToken;
import com.xfeng.wx.model.wxpage.WxResponse;
import com.xfeng.wx.model.wxpage.WxUserinfo;

import feign.Param;
import feign.RequestLine;

//https://api.weixin.qq.com/sns
public interface WeixinOAuth2Client {

	// /oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
	@RequestLine("GET /oauth2/access_token?appid={appid}&secret={secret}&code={code}&grant_type=authorization_code")
	AccessToken getAccessToken(@Param("appid") String appid, @Param("secret") String secret,
			@Param("code") String code);

	// /oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN
	@RequestLine("GET /oauth2/refresh_token?appid={appid}&grant_type=refresh_token&refresh_token={refresh_token}")
	AccessToken refreshAccessToken(@Param("appid") String appid, @Param("refresh_token") String refresh_token);

	// /userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
	@RequestLine("GET /userinfo?access_token={access_token}&openid={openid}&lang={lang}")
	WxUserinfo getUserInfo(@Param("access_token") String access_token, @Param("openid") String openid,
			@Param("lang") String lang);

	// /auth?access_token=ACCESS_TOKEN&openid=OPENID
	@RequestLine("GET /auth??access_token={access_token}&openid={openid}")
	WxResponse checkAccessToken(@Param("access_token") String access_token, @Param("openid") String openid);

}
