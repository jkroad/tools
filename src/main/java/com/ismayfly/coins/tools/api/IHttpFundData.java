package com.ismayfly.coins.tools.api;

import com.ismayfly.coins.tools.core.exception.MayflyException;

/**
 * 抓取基金平台数据 
 * 
 * @author zhaozj
 * @date 2015-9-21 上午11:51:16
 * @version 0.0.1
 */
public interface IHttpFundData {

	/**
	 * 开放式基金净值数据 {@link http
	 * ://fund.eastmoney.com/fund.html#os_0;isall_0;ft_;pt_1}
	 * 
	 * @param initUrl
	 * @param queryUrl
	 * @throws MayflyException
	 */
	public void httpScan(String initUrl, String queryUrl, int fundType)
			throws MayflyException;
}
