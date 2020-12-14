package com.ismayfly.coins.tools.service.impl;

import com.ismayfly.coins.tools.mapper.MpPayConfigBankMapper;
import com.ismayfly.coins.tools.model.po.MpPayConfigBank;
import com.ismayfly.coins.tools.service.MpPayConfigBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("mpPayConfigBankService")
public class MpPayConfigBankServiceImpl implements MpPayConfigBankService {

    @Autowired
    private MpPayConfigBankMapper mpPayConfigBankMapper;

    @Override
    public Integer save(MpPayConfigBank mpPayConfigBank) {
        return mpPayConfigBankMapper.insert(mpPayConfigBank);
    }
}
