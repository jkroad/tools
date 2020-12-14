package com.ismayfly.coins.tools.model.query;

import lombok.Data;

@Data
public class BankMCCInfoQuery {
    private String bankName;
    private String mccStr;

    private String cardType;
}
