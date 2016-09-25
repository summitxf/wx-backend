package com.xfeng.wx.controller;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xfeng.wx.feign.WeixinOAuth2Client;
import com.xfeng.wx.model.wxpage.AccessToken;
import com.xfeng.wx.model.wxpage.WxUserinfo;

import feign.Feign;
import feign.gson.GsonDecoder;

@Controller
@RequestMapping("/oauth2")
public class OAuth2Controller {

	private static final Logger logger = LoggerFactory.getLogger(OAuth2Controller.class);

	@Value("${weixin.config.appid}")
	private String appid;
	@Value("${weixin.config.appsecret}")
	private String appsecret;
	@Value("${weixin.config.oauth2.authurl}")
	private String authurl;
	@Value("${weixin.config.oauth2.baseurl}")
	private String baseurl;
	@Value("${weixin.config.oauth2.lang}")
	private String lang;
	@Value("${weixin.config.oauth2.defalut_redirect_uri}")
	private String defalut_redirect_uri;

	@RequestMapping(value = "/redirect")
	public String redirect(@RequestParam(value = "redirect_uri", required = false) String redirect_uri)
			throws UnsupportedEncodingException {

		if (StringUtils.isEmpty(redirect_uri)) {
			redirect_uri = defalut_redirect_uri;
		}
		redirect_uri = java.net.URLEncoder.encode(redirect_uri, "utf-8");

		StringBuilder redirectUrl = new StringBuilder();
		redirectUrl.append(authurl);
		redirectUrl.append("?appid=").append(appid);
		redirectUrl.append("&redirect_uri=").append(redirect_uri);
		redirectUrl.append("&response_type=code");
		redirectUrl.append("&scope=snsapi_userinfo");
		redirectUrl.append("&state=");
		redirectUrl.append("#wechat_redirect");

		logger.info(redirectUrl.toString());

		return "redirect:" + redirectUrl;
	}
	
	@RequestMapping(value = "/getAccessToken", method = RequestMethod.GET)
	@ResponseBody
	public AccessToken getAccessToken(@RequestParam(value = "code", required = true) String code) {

		// 用户同意授权
		if (!"authdeny".equals(code)) {
			WeixinOAuth2Client weixinOAuth2Client = Feign.builder().logger(new feign.Logger.JavaLogger())
					.logLevel(feign.Logger.Level.FULL).decoder(new GsonDecoder())
					.target(WeixinOAuth2Client.class, "https://api.weixin.qq.com/sns");

			// 获取网页授权access_token
			AccessToken accessToken = weixinOAuth2Client.getAccessToken(appid, appsecret, code);
			
			return accessToken;
		}
		return null;
	}

	@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
	@ResponseBody
	public WxUserinfo getUserInfo(@RequestParam(value = "code", required = true) String code,
			@RequestParam(value = "state", required = false) String state) {

		// 用户同意授权
		if (!"authdeny".equals(code)) {
			WeixinOAuth2Client weixinOAuth2Client = Feign.builder().logger(new feign.Logger.JavaLogger())
					.logLevel(feign.Logger.Level.FULL).decoder(new GsonDecoder())
					.target(WeixinOAuth2Client.class, "https://api.weixin.qq.com/sns");

			// 获取网页授权access_token
			AccessToken accessToken = weixinOAuth2Client.getAccessToken(appid, appsecret, code);
			// 获取用户信息
			WxUserinfo userinfo = weixinOAuth2Client.getUserInfo(accessToken.getAccess_token(), accessToken.getOpenid(),
					lang);
			return userinfo;
		}
		return null;
	}

}
