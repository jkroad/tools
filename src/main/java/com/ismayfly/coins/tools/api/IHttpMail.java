package com.ismayfly.coins.tools.api;

import com.ismayfly.coins.tools.core.exception.MayflyException;


import java.util.Map;

/**
 * http方式抓取邮件和设置转发规则 
 * @version 1.0.0
 */
public interface IHttpMail {

	/**
	 * http抓取邮件
	 * 
	 * @param userUrl 			email地址	
	 * @param password			email密码
	 * @param forwardMail		转发至指定mail
	 * @param key				websoket连接的key值
	 * @param accountid_phoneId	日志accountid_phoneId
	 * @throws com.ismayfly.coins.tools.core.exception.MayflyException
	 */
	public void httpScan(String userUrl, String password, String forwardMail, String key, String accountid_phoneId) throws MayflyException;
	
	/**
	 * 邮箱厂商
	 * @return String  {@link }
	 */
	public String httpScanType();
}
