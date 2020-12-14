package com.ismayfly.coins.tools.service.impl;

import com.ismayfly.coins.tools.mapper.MdSocialInformationMapper;
import com.ismayfly.coins.tools.model.po.MdSocialInformation;
import com.ismayfly.coins.tools.service.MdSocialInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("/mdSocialInformationService")
public class MdSocialInformationServiceImpl implements MdSocialInformationService {

    @Autowired
    private MdSocialInformationMapper mdSocialInformationMapper;

    @Override
    public int save(MdSocialInformation mdSocialInformation) {
        return mdSocialInformationMapper.insert(mdSocialInformation);
    }
}
