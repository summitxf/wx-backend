package com.xfeng.wx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.xfeng.wx.Application;
import com.xfeng.wx.dao.WeixinMessageMapper;
import com.xfeng.wx.model.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ApplicationTests {

	@Autowired
	private WeixinMessageMapper weixinMessageMapper;

	@Test
	public void findByName() throws Exception {
		
//		Message msg = weixinMessageMapper.getMessageById("");
//		Assert.assertEquals();
	}
}
