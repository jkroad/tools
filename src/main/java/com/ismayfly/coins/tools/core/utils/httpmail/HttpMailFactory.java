package com.ismayfly.coins.tools.core.utils.httpmail;

import com.ismayfly.coins.tools.api.IHttpMail;
import com.ismayfly.coins.tools.core.exception.MayflyException;
import com.ismayfly.coins.tools.core.utils.mail.MailAnalyze;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * http方式工厂类
 * @version 1.0.0
 */
@Slf4j
@Service
public class HttpMailFactory {

	
	@Autowired
	private MailAnalyze mailAnalyze;
	// spring 注入
	private List<IHttpMail> httpScanTypeList;
	
	public List<IHttpMail> getHttpScanTypeList() {
		return httpScanTypeList;
	}

	public void setHttpScanTypeList(List<IHttpMail> httpScanTypeList) {
		this.httpScanTypeList = httpScanTypeList;
	}

	/**
	 * 根据后缀选择登录模板
	 * @param mailUrl				邮箱地址
	 * @param password				密码信息
	 * @param key					websocket长连接标示
	 * @param accountid_phoneId		日志map信息的key值
	 */
	
	public void getHttpMailFactory(String mailUrl,String password, String key, String accountid_phoneId) throws MayflyException {
		//分解accountId与phoneId
		String[] split = accountid_phoneId.split("_");
		String accountId = null;
		String phoneId = null;
		if (split.length != 0){
			accountId = split[0];
			phoneId = split[1];
		}
		
		// TODO 每个邮箱生成对应的自有邮箱，作为转发邮箱
		String forwardMail = "zzj99@126.com";
		String scanType = mailAnalyze.getHttpScanType(mailUrl);

		if (CollectionUtils.isNotEmpty(httpScanTypeList)) {
			for (IHttpMail iHttpMail : httpScanTypeList) {
				if (iHttpMail.httpScanType().equals(scanType)) {
					log.info("e_mail:{} \taccount_id:{} \tphone_id:{} http scan\tforward_mail:{}", new Object[]{mailUrl, accountId, phoneId, forwardMail});
					iHttpMail.httpScan(mailUrl, password, forwardMail, key, accountid_phoneId);
					break;
				}
			}
		} else {
			log.info("getHttpMailFactory method eroror");
			throw new MayflyException();
		}
	}
}
