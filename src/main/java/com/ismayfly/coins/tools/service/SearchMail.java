package com.ismayfly.coins.tools.service;

import com.ismayfly.coins.tools.core.constant.Constants;
import com.ismayfly.coins.tools.core.exception.MayflyException;
import com.ismayfly.coins.tools.core.socket.MWebSocket;
import com.ismayfly.coins.tools.core.utils.httpmail.HttpMailFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.search.OrTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 邮件  - 过滤、下载、解析及数据格式化存储
 * @version 1.0.0
 */
@Slf4j
@Service
public class SearchMail extends MWebSocket{

	@Autowired
	private MailMatch mailMatch;

	@Autowired
	private HttpMailFactory httpMailFactory;

	
	/**
	 * 选取邮件过滤方式（http或javamail）
	 * 
	 * @param mailUrl				邮箱地址
	 * @param password				邮箱密码
	 * @param key					长连接标示
	 * @param accountid_phoneId		日志accountid_phoneId
	 */
	public void httpOrJavamail(String mailUrl, String password, String key, String accountid_phoneId)
			{
		String[] split = accountid_phoneId.split("_");
		String accountId = null;
		String phoneId = null;
		if (split.length != 0){
			accountId = split[0];
			phoneId = split[1];
		}
		if (this.getFetchType(mailUrl).equals(Constants.HTTP_FETCH)) {
			log.info("e_mail:{} \taccount_id:{} \tphone_id:{} start http login...", new Object[]{mailUrl, accountId, phoneId});
			this.send("A1`开始登陆...", key);
			httpSearchMail(mailUrl, password, key, accountid_phoneId);
		} else {
			log.info("e_mail:{} \taccount_id:{} \tphone_id:{} start pop login...", new Object[]{mailUrl, accountId, phoneId});
			this.send("A1`开始登陆...", key);
			loginMail(mailUrl, password, key, accountid_phoneId);
		}
	}

	/**
	 * http方式过滤邮件、下载、解析
	 * 
	 * @param mailUrl			邮箱地址
	 * @param password			邮箱密码
	 * @param key				websoket长连接的标示
	 * @param accountid_phoneId	日志accountid_phoneId
	 */
	public void httpSearchMail(String mailUrl, String password, String key, String accountid_phoneId) {
		httpMailFactory.getHttpMailFactory(mailUrl, password, key, accountid_phoneId);
	}
	
	/**
	 * javamail方式过滤邮件、下载、解析
	 * 
	 * @param mailUrl				邮箱地址
	 * @param password				邮箱密码
	 * @param key					websoket长连接标示
	 * @param accountid_phoneId		日志accountid_phoneId
	 */
	
	public void loginMail(String mailUrl, String password, String key, String accountid_phoneId)
			{
		Store store = mailMatch.match(mailUrl, password);
		if (store != null){
			log.info("e_mail:{} pop login successful", mailUrl);
			this.send("A1`登陆成功...", key);

			// 登陆成功添加转法规则 TODO 目前的二期版本暂时不需要转发
			/*boolean mailForwardFactory2 = mailForwardFactory.getMailForwardFactory(mailUrl, password);
			if (billLoggerEntity != null){
        		billLoggerEntity.setForwardResults(mailForwardFactory2 ? MailBillTypeConstants.SUCCESS : MailBillTypeConstants.FAILED);
        	}*/
			
			long beginTime = System.currentTimeMillis();
			Folder defaultFolder;
			try {
				defaultFolder = store.getDefaultFolder();
				Folder[] folder_arr = defaultFolder.list();
				
				int messageCount = 0;
				int folder_length = 0;
				if(folder_arr != null && folder_arr.length>0){
					folder_length = folder_arr.length;
				}
				for(int i = 0; i < folder_length; i ++){
					log.info("e_mail:{} filter before \tfolder_name{} \tfullname:{} \turlname:{}", new Object[]{mailUrl, folder_arr[i].getName(), folder_arr[i].getFullName(), folder_arr[i].getURLName().toString()});
				    Folder folder = store.getFolder(folder_arr[i].getName()); // 得到各个文件夹
				    // 以读写模式打开收件箱 
				    folder.open(Folder.READ_ONLY); // qq 邮箱只能使用此模式打开“其他邮箱”文件夹
				    // 过滤邮件
				    log.info("e_mail:{} bill email pop filter begin", mailUrl);
				    this.send("A1`过滤账单邮件开始...", key);
				    Message[] messages = search(folder);
				    int mail_length = 0;
				    if(messages != null){
				    	mail_length = messages.length;
				    }
				    messageCount += mail_length;
				    // 过滤个性邮件账单
//				    Message[] specialMail = iSpecialMail.searchSpecialMailBill(folder);
					log.info("e_mail:{} filter after \tfolder_name:{} \tcontain_mails_count:{} \tfetch_mails_count:{}", new Object[]{mailUrl, folder_arr[i].getName(), folder.getMessageCount(), mail_length}); 
				    // 解析搜索到的邮件 
				    if (messages != null && messages.length > 0) {
				    	//this.send("A1`搜索到账单邮件："+ messageCount +"封", key);
				    	//解析邮件入库
						//todo-jinlu:
				    }
				    // 解析搜索到的的特殊邮件 

				    folder.close(true);
				}
				this.send("NUMBER`&" + mailUrl + "&" + messageCount, key); // TODO 配合测试用例, 测完要删掉
				
				long endTime = System.currentTimeMillis();
				long millisecond = endTime - beginTime;
				long second = millisecond/1000;
				long l = millisecond / (messageCount == 0 ? 1 :messageCount);
				this.send("EACH`&" + mailUrl + "&" + l, key); // TODO 配合测试用例, 测完要删掉
				log.info("e_mail:{} mail_bill_count:{} \tfetch_mail_bill_time:{}s \teach_bill_time:{}ms/one", new Object[]{mailUrl, messageCount, second, l});
				store.close();
			} catch (MessagingException e) {
				log.info("loginMail Method Filed",e);
				throw new  MayflyException("oginMail Method File");
			}
		}else{
			this.send("A1`登陆失败...", key);
			log.info("loginMail Method Filed");
		}
	}
		
	  /**
		* 根据关键字过滤邮件
		* 
		* @param folder			邮箱的文件
		* @return Message[]		文件夹中邮件内容
		*/
		
		public Message[] search(Folder folder)  {
			String querykey = Constants.QUERYKEY;
			SearchTerm subjectTerm = null ;
			SearchTerm orTerm1 = null;
			List<SearchTerm> searchTermList = new ArrayList<SearchTerm>();
			
			try {
				if(-1 != querykey.indexOf("，")){
					String[] split = querykey.split("，");
					int length = split.length;
					for (int i = 0; i < length; i++) {
						subjectTerm = new SubjectTerm(split[i]);
						searchTermList.add(subjectTerm);
					}
					int size = searchTermList.size();
					for (int j = 0 ; j < size; j++){
						if (size == 1){
								return folder.search(searchTermList.get(j));
						}
						if (size <= 2){
							orTerm1 = new OrTerm( searchTermList.get(j), searchTermList.get(j+1));
							return folder.search(orTerm1);
						}
						if (j < 1){
							orTerm1 = new OrTerm(searchTermList.get(j), searchTermList.get(j+1));
							continue;
						}
						orTerm1 = new OrTerm( searchTermList.get(j), orTerm1);
					}
					return folder.search(orTerm1);
				}else if (-1 != querykey.indexOf(",")){
					String[] split = querykey.split(",");
					int length = split.length;
					for (int i = 0; i < length; i++) {
						if (i == 0){
							subjectTerm = new SubjectTerm(split[i]);
							continue;
						}
						orTerm1 = new OrTerm( subjectTerm, new SubjectTerm(split[i]));
					}
					return folder.search(orTerm1);
				}else {
					subjectTerm = new SubjectTerm(querykey);
					return folder.search(subjectTerm);
				}
			} catch (MessagingException e) {
				log.info("search method Error",e);
				throw new RuntimeException("search method Error");
			}
		}
		
	// ----- private function -----
	/**
	 * 抓取邮件的方式
	 * <p>
	 * 	<li>1. http</li>
	 * 	<li>2. javamail</li>
	 * </p>
	 * 
	 * @param mailUrl	邮箱url
	 * @author zhaozj 
	 * Add on 2014/07/31
	 */
	
	private String getFetchType(String mailUrl) {
		String fetch_type = Constants.JAVAMAIL_FETCH;
		String http_fetch_mail_str = Constants.HTTP_FETCH_MAIL; // 切换到http方式抓取邮件列表

		if (StringUtils.isNotBlank(mailUrl) && mailUrl.indexOf("@") != -1) {
			mailUrl = mailUrl.toLowerCase();
			String[] strArr = mailUrl.split("@");
			String mail_type = strArr[1];
			if (http_fetch_mail_str.indexOf(mail_type) != -1) {
				fetch_type = Constants.HTTP_FETCH;
			} else {
				// do nothing
			}
		} else {
			log.error("user input error mail url:{}", mailUrl);
		}

		return fetch_type;
	}

}
