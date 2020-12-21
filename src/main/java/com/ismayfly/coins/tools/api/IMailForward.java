package com.ismayfly.coins.tools.api;

import com.ismayfly.coins.tools.core.exception.MayflyException;

/**
 * 邮箱转发规则设置
 * @version 1.0.0
 */
public interface IMailForward {

	/**
	 * 添加转发规则
	 * 
	 * @param mailUrl 		email地址
	 * @param password		email密码
	 * @param forwardMail 	转发至指定mail
	 * @return boolean 		转发规则添加结果 ：true 成功; false 失败;
	 * @throws java.lang.reflect.MalformedParameterizedTypeException
	 */
	public boolean addForwardRules(String mailUrl, String password, String forwardMail) throws MayflyException;
	/**
	 * 邮箱厂商
	 * @return String  {@link }
	 */
	public String httpScanType();
}
