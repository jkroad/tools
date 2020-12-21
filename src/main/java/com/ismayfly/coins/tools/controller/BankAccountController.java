package com.ismayfly.coins.tools.controller;

import com.ismayfly.coins.tools.core.exception.MayflyException;
import com.ismayfly.coins.tools.core.socket.MWebSocket;
import com.ismayfly.coins.tools.service.SearchMail;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.websocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Slf4j
@RestController
public class BankAccountController extends MWebSocket {


    @Autowired
    private SearchMail searchMail;

    /**
     * 绑定邮箱 -- [web]
     * <p>
     *  <li>账单流程 {保存-->检索邮件-->下载邮件-->解析邮件}</li>
     * </p>
     *
     * @param emailUrl		邮件地址
     * @param password		密码
     * @param phoneId		手机标示
     * @param accountId		用户账号
     * @param socketKey		websocket长连接的标示
     * @return				String
     */

    @SuppressWarnings("static-access")
    @RequestMapping( method = RequestMethod.POST, value = "/webBindingMail.do")
    public String webBindingMail(@RequestParam(value = "emailUrl", required = true) String emailUrl,
                                 @RequestParam(value = "password", required = true) String password,
                                 @RequestParam(value = "phoneId", required = true) String phoneId,
                                 @RequestParam(value = "accountId", required = true) Long accountId,
                                 @RequestParam(value = "key", required = true) String socketKey) {
        log.info(
                "[c->s] action:{} \temailUrl:{} \tpassword:{} \tphoneId:{} \taccountId:{} \tsocketKey:{}",
                new Object[] { ".../webBindingMail.do", emailUrl, password,
                        phoneId, accountId, socketKey });
        ModelAndView resultVo = new ModelAndView();
        // 保存邮件
        Map<String, WebSocket.Connection> map = super.connectionMap;

        long accountId2 = 123456789; // TODO 测试完事后要注掉-------
        String phoneId2 = "";
        String acc_phone = accountId2 + "_" + phoneId2;
        // 查询账号与邮箱是否已绑定
//        boolean ifBinding = billUserInfoServiceImpl.userIfBindingMail(emailUrl, accountId2);
        boolean ifBinding = false;
        try {
            if (ifBinding){
                // 开始登录搜索
                log.info("e_mail:{} \taccount_id:{} \tphone_id:{} user mail db exist, search begin", new Object[]{emailUrl, accountId2, phoneId2});
                searchMail.httpOrJavamail(emailUrl, password, socketKey, acc_phone);
                log.info("e_mail:{} \taccount_id:{} \tphone_id:{} user mail db exist, search end", new Object[]{emailUrl, accountId2, phoneId2});
            }else{
                // 代表此用户未绑定过，先保存过后才登陆
                log.info("e_mail:{} \taccount_id:{} \tphone_id:{} user mail db no exist, search begin", new Object[]{emailUrl, accountId2, phoneId2});
                searchMail.httpOrJavamail(emailUrl, password, socketKey, acc_phone);
                log.info("e_mail:{} \taccount_id:{} \tphone_id:{} user mail db no exist, search end", new Object[]{emailUrl, accountId2, phoneId2});
            }
        } catch (MayflyException e) {
                this.send("A1`登录失败，您的用户名与密码错误或POP3与IMAP未开启...", socketKey);
            return "failed";
        }

        log.info("e_mail:{} \taccount_id:{} \tphone_id:{} redirect:/findCycleInfo.do", new Object[]{emailUrl, accountId2, phoneId2});

        map.remove(socketKey);
        return "redirect:/findCycleInfo.do?emailUrl="+emailUrl;
    }

    /**
     * 账单周期列表信息-- [web]
     *
     * @param emailUrl		邮箱url
     * @return ModelAndView
     * @throws MayflyException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/findCycleInfo.do")
    public ModelAndView findCycleInfo(@RequestParam(value = "emailUrl", required = true) String emailUrl)
            throws MayflyException {
        log.info("[c->s] action:{} \temailUrl:{} begin", new Object[]{".../findCycleInfo.do", emailUrl });
        ModelAndView resultVo = new ModelAndView();
//        List<JspInfoPartView> view = new ArrayList<JspInfoPartView>(); // 接收账单
//        List<JspInfoPartView> notview = new ArrayList<JspInfoPartView>();
//        log.info("e_mail:{} query cycleinfo by emailUrl begin", emailUrl);
//        List<JspInfoPartView> infoViews = billCycleInfoServiceImpl.findEntityByEmailUrl(emailUrl);
//
//        for (JspInfoPartView infoView : infoViews){
//            if (1 == infoView.getIsBill()){
//                view.add(infoView);
//            }else{
//                notview.add(infoView);
//            }
//        }
//        jsp.setView(view);
//        jsp.setNotview(notview);
//        log.info("e_mail:{} query cycleinfo by emailUrl end", emailUrl);
//        try {
//            resultVo.addObject("jsp", objectMapper.writeValueAsString(jsp));
//        } catch (Exception ex) {
//            throw MailBillExceptionUtil.getWithLog(
//                    ErrorCodeContants.JSON_OBJECT_EXCEPTION_CODE,
//                    ErrorCodeContants.JSON_OBJECT_EXCEPTION.getMsg(), log);
//        }
        resultVo.setViewName("content/manage/billUserInfo-list");
        log.info("e_mail:{} return page viewName:{}", new Object[]{emailUrl, "content/manage/billUserInfo-list"});
        log.info("[c->s] action:{} \temailUrl:{} end", new Object[]{".../findCycleInfo.do", emailUrl });
        return resultVo;
    }

    /**
     * 账单详情-- [web]
     *
     * @param billCyclePkId		周期表主键
     * @param voBillType		账单详细类型
     * @return ModelAndView
     * @throws MayflyException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/findBill.do")
    public ModelAndView findBill(@RequestParam(value = "billCyclePkId", required = true) Long billCyclePkId,
                                 @RequestParam(value = "voBillType", required = true) int voBillType)
            throws MayflyException{
        ModelAndView resultVo = new ModelAndView();
//        log.info("[c->s] action:{} \tbillType:{} \tbillCyclePkId:{} begin", new Object[]{".../findBill.do", voBillType, billCyclePkId});
//        BillTypeView view = findVoService.getView(voBillType, billCyclePkId);
//        int billType = view.getBillType();
//        log.info("[c->s] action:{} \tbillType:{} \tbillCyclePkId:{} end", new Object[]{".../findBill.do", voBillType, billCyclePkId});
//        try {
//            resultVo.addObject("view", objectMapper.writeValueAsString(view));
//        } catch (Exception ex) {
//            throw MailBillExceptionUtil.getWithLog(
//                    ErrorCodeContants.JSON_OBJECT_EXCEPTION_CODE,
//                    ErrorCodeContants.JSON_OBJECT_EXCEPTION.getMsg(), log);
//        }
//        resultVo.setViewName("content/manage/"+(billType == 1 ? "billCommonMonth" : "billCommonDay"));
//        log.info("return page viewName:{}", "content/manage/billUserInfo/"+(billType == 0 ? "billCommonMonth" : "billCommonDay"));

        return resultVo;
    }


    /**
     * 全量刷新或单个刷新邮箱 -- [App]
     *
     * @param mailArr	邮箱数组
     * @param phoneId	手机标示
     * @param accountId	用户账号
     * @param csVersion	客户端数据版本号
     * @param socketKey socket长连接的唯一标示
     * @throws MayflyException
     */
    @SuppressWarnings("static-access")
    @RequestMapping(method = RequestMethod.POST, value = "/refreshMails.do")
    public String refreshMails(
            @RequestParam(value = "mailArr", required = true) String[] mailArr,
            @RequestParam(value = "phoneId", required = true) String phoneId,
            @RequestParam(value = "accountId", required = true) Long accountId,
            @RequestParam(value = "csVersion", required = true) String csVersion,
            @RequestParam(value = "socketKey", required = true) String socketKey)
            throws MayflyException {

        Map<String, WebSocket.Connection> socketConnectionMap = super.connectionMap;

        this.send("BEGIN`SEND BEGIN", socketKey);
//        for (int i = 0; i < mailArr.length; i++) {
//            String emailUrl = mailArr[i];
//            String password = billUserInfoServiceImpl.findPasswordByMailUrl(emailUrl);
//            password = DesUtil.decrypt3DES(Constants.PASSWORDKEY.getBytes(),password);
//            // logger
//            BillLogEntity billLog = billLogService.getBillLog(phoneId, accountId, emailUrl);
//            Map<String, BillLogEntity> loggerMap = new HashMap<String, BillLogEntity>();
//            long logAccountId = billLog.getAccountId();
//            String logPhoneId = billLog.getPhoneId();
//            String acc_phone = logAccountId + "_" + logPhoneId;
//            loggerMap.put(acc_phone, billLog);
//            // 执行抓取操作
//            searchMail.httpOrJavamail(emailUrl, password, socketKey, acc_phone, loggerMap);
//            // 调用推送客户端接口 TODO 该方法得到所有邮箱数据的包数目
//            pushMailBill2App.pushMailBillEntrance(emailUrl, logAccountId, logPhoneId, csVersion, socketKey);
//
//        }
        this.send("END`SEND END", socketKey);
        socketConnectionMap.remove(socketKey); // 回收链接

        return "";
    }

    /**
     * 邮箱解绑  -- [App]
     * @param emailUrl	邮件地址
     * @param phoneId	手机标示
     * @param accountId	用户账号
     * @param socketKey	socket长连接的唯一标示
     * @throws MayflyException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/unBindingMail.do")
    public String unBindingMail(
            @RequestParam(value = "emailUrl", required = true) String emailUrl,
            @RequestParam(value = "phoneId", required = true) String phoneId,
            @RequestParam(value = "accountId", required = true) Long accountId,
            @RequestParam(value = "socketKey", required = true) String socketKey)
            throws MayflyException {
        log.info(
                "[c->s] action:{} \temailUrl:{} \tphoneId:{} \taccountId:{} \tsocketKey:{}",
                new Object[] { ".../unBindingMail.do", emailUrl, phoneId,
                        accountId, socketKey });
        // 调用推送客户端接口
//        pushMailBill2App.pushUnBindingMail(emailUrl, accountId, phoneId, socketKey);

        return "";
    }

    /**
     * 获取用户邮箱列表 -- [App]
     *
     * @param emailUrl	邮件地址
     * @param phoneId	手机标示
     * @param accountId	用户账号
     * @param socketKey	socket长连接的唯一标示
     * @throws MayflyException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/accountBindingMails.do")
    public String accountBindingMails(
            @RequestParam(value = "emailUrl", required = true) String emailUrl,
            @RequestParam(value = "phoneId", required = true) String phoneId,
            @RequestParam(value = "accountId", required = true) Long accountId,
            @RequestParam(value = "socketKey", required = true) String socketKey)
            throws MayflyException {
        log.info(
                "[c->s] action:{} \temailUrl:{} \tphoneId:{} \taccountId:{} \tsocketKey:{}",
                new Object[] { ".../accountBindingMails.do", emailUrl, phoneId,
                        accountId, socketKey });
        // 调用推送客户端接口
        this.send("BEGIN`SEND BEGIN", socketKey);
//        pushMailBill2App.pushAccountOfMails(emailUrl, accountId, phoneId, socketKey);
        this.send("END`SEND END", socketKey);

        return "";
    }

    /**
     * 绑定邮箱 -- [App]
     * <p>
     *  <li>绑定流程： </li>
     *  <li>1.账号绑定邮箱;2.检索邮件;3.下载邮件;4.解析邮件;</li>
     * </p>
     *
     * @param emailUrl		邮件地址
     * @param password		密码
     * @param phoneId		手机标示
     * @param accountId		用户账号
     * @param csVersion		客户端数据版本号
     * @param socketKey		socket长连接的唯一标示
     */
    @SuppressWarnings("static-access")
    @RequestMapping(method = RequestMethod.POST, value = "/bindingMail.do")
    public String appBindingMail(
            @RequestParam(value = "emailUrl", required = true) String emailUrl,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "phoneId", required = true) String phoneId,
            @RequestParam(value = "accountId", required = true) Long accountId,
            @RequestParam(value = "csVersion", required = true) String csVersion,
            @RequestParam(value = "socketKey", required = true) String socketKey) {
        log.info(
                "[c->s] action:{} \temailUrl:{} \tpassword:{} \tphoneId:{} \taccountId:{} \tcsVersion:{} \tsocketKey:{} begin",
                new Object[] { ".../bindingMail.do", emailUrl, password,
                        phoneId, accountId, csVersion, socketKey });
        Map<String, WebSocket.Connection> socketConnectionMap = super.connectionMap;
        // logger
        // 查询账号与邮箱是否已绑定
//        boolean ifBinding = billUserInfoServiceImpl.userIfBindingMail(emailUrl, logAccountId);
//        boolean ifBinding  = false;
//        try {
//            if (ifBinding){
//                // 开始登录搜索
////                log.info("e_mail:{} \taccount_id:{} \tphone_id:{} user mail db exist, search begin", new Object[]{emailUrl, logAccountId, logPhoneId});
//                searchMail.httpOrJavamail(emailUrl, password, socketKey, acc_phone, loggerMap);
////                billUserInfoServiceImpl.updateToBinding(logAccountId, emailUrl, password);
////                log.info("e_mail:{} \taccount_id:{} \tphone_id:{} user mail db exist, search end", new Object[]{emailUrl, logAccountId, logPhoneId});
//            }else{
//                // 代表此用户未绑定过，先保存过后才登陆
//                log.info("e_mail:{} \taccount_id:{} \tphone_id:{} user mail db no exist, search begin", new Object[]{emailUrl, logAccountId, logPhoneId});
//                searchMail.httpOrJavamail(emailUrl, password, socketKey, acc_phone, loggerMap);
//                // 绑定邮箱
////                billUserInfoServiceImpl.save(emailUrl, password, logAccountId);
//                log.info("e_mail:{} \taccount_id:{} \tphone_id:{} user mail db no exist, search end", new Object[]{emailUrl, logAccountId, logPhoneId});
//            }
//        } catch (Exception e) {
////            if (e.getErrorCode() == ErrorCodeContants.POP_NOT_OPEN_EXCEPTION_CODE
////                    || e.getErrorCode() == ErrorCodeContants.IMAP_NOT_OPEN_EXCEPTION_CODE){
////                this.send("ERR1`-205&登录失败，您的用户名与密码错误或POP3与IMAP未开启", socketKey);
////                BillLogEntity billLoggerEntity = loggerMap.get(acc_phone);
////                if (null != billLoggerEntity){
////                    billLoggerEntity.setLogOn(MailBillTypeConstants.FAILED);
////                }
////            }
////            BillLogEntity billLoggerEntity = loggerMap.get(acc_phone);
////            if (null != billLoggerEntity){
////                billLoggerEntity.setLogoutTime(new Date());
////                billLogService.save(billLoggerEntity);
////            }
//            log.info("bind File",e);
//            socketConnectionMap.remove(socketKey);
//        }
        // 存储操作日志的推出数据的时间
//        BillLogEntity billLoggerEntity = loggerMap.get(acc_phone);
//        if (null != billLoggerEntity){
//            billLoggerEntity.setLogoutTime(new Date());
//            billLogService.save(billLoggerEntity);
//        }
//        log.info("e_mail:{} \taccount_id:{} \tphone_id:{} redirect:/findCycleInfo.do", new Object[]{emailUrl, logAccountId, logPhoneId});

        // 调用推送客户端接口
        this.send("BEGIN`SEND BEGIN", socketKey);
//        pushMailBill2App.pushMailBillEntrance(emailUrl, logAccountId, logPhoneId, csVersion, socketKey);
        this.send("END`SEND END", socketKey);
        socketConnectionMap.remove(socketKey); // 回收链接         TODO 最后要打开,本次修改是为了测试用例配置多个邮箱---------------------------------------
        log.info(
                "[c->s] action:{} \temailUrl:{} \tpassword:{} \tphoneId:{} \taccountId:{} \tcsVersion:{} \tsocketKey:{} end",
                new Object[] { ".../bindingMail.do", emailUrl, password,
                        phoneId, accountId, csVersion, socketKey });
        return "";
    }





}
