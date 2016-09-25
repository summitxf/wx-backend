package com.xfeng.wx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xfeng.wx.dao.WeixinMessageMapper;
import com.xfeng.wx.model.Message;

@Component
public class WeixinMessageService {

	@Autowired
	private WeixinMessageMapper weixinMessageMapper;

	public Message getMessageById(String string) {
		return weixinMessageMapper.getMessageById(string);
	}

}
