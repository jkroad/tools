package com.ismayfly.coins.tools.model.query;

import lombok.Data;

@Data
public class MailBindQuery {
    private String password; //密码
    private String mailUrl;//邮件地址
    private Long accountId;//账号
    private String socketKey;//websocket长连接的标识
}
