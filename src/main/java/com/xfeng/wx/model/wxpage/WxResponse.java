package com.xfeng.wx.model.wxpage;

import java.io.Serializable;

public class WxResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 167845105678805830L;

	private long errcode;

	private String errmsg;

	public long getErrcode() {
		return errcode;
	}

	public void setErrcode(long errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
}
