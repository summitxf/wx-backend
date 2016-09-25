package com.xfeng.wx.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xfeng.wx.model.Message;
import com.xfeng.wx.model.Reply;
import com.xfeng.wx.utils.WeixinUtil;

@Controller
@RequestMapping("/weixin")
public class WeixinController {

	private static final String TOKEN = "faith";

	@RequestMapping(value = "/test", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String testWeixinRequest(@RequestParam(value = "echostr", required = true) String echostr) {
		return echostr;
	}

	// 微信公众平台验证url是否有效使用的接口
	@RequestMapping(method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String checkWeixinRequest(@RequestParam(value = "signature", required = true) String signature,
			@RequestParam(value = "timestamp", required = true) String timestamp,
			@RequestParam(value = "nonce", required = true) String nonce,
			@RequestParam(value = "echostr", required = true) String echostr) {
		if (signature != null && timestamp != null && nonce != null) {
			String[] values = { TOKEN, timestamp, nonce };
			Arrays.sort(values);
			String value = values[0] + values[1] + values[2];
			String sign = WeixinUtil.sha1(value);
			return sign.equals(signature) ? echostr : "";
		} else {
			return "";
		}
	}

	// 接收微信公众号接收的消息，处理后再做相应的回复
	@RequestMapping(method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String replyMessage(HttpServletRequest request) {
		// 仅处理微信服务端发的请求
		if (checkWeixinReques(request)) {
			Map<String, String> requestMap = WeixinUtil.parseXml(request);
			Message message = WeixinUtil.mapToMessage(requestMap);
			String replyContent = "";
			String type = message.getMsgType();
			if (type.equals(Message.TEXT)) {
				String content = message.getContent();
				replyContent = "你输入了 ： " + content;
			} else {
				replyContent = "暂不支持";
			}
			// 拼装回复消息
			Reply reply = new Reply();
			reply.setToUserName(message.getFromUserName());
			reply.setFromUserName(message.getToUserName());
			reply.setCreateTime(new Date());
			reply.setMsgType(Reply.TEXT);
			reply.setContent(replyContent);
			// 将回复消息序列化为xml形式
			String back = WeixinUtil.replyToXml(reply);
			System.out.println(back);
			return back;
		} else {
			return "error";
		}
	}

	/**
	 * 根据token计算signature验证是否为weixin服务端发送的消息
	 */
	private static boolean checkWeixinReques(HttpServletRequest request) {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		if (signature != null && timestamp != null && nonce != null) {
			String[] strSet = new String[] { TOKEN, timestamp, nonce };
			java.util.Arrays.sort(strSet);
			String key = "";
			for (String string : strSet) {
				key = key + string;
			}
			String pwd = WeixinUtil.sha1(key);
			return pwd.equals(signature);
		} else {
			return false;
		}
	}
}
