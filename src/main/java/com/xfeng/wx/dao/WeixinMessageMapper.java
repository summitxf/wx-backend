package com.xfeng.wx.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xfeng.wx.model.Message;

@Mapper
public interface WeixinMessageMapper {

	Message getMessageById(String string);

}
