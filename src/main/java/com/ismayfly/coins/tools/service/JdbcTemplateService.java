package com.ismayfly.coins.tools.service;

import com.ismayfly.coins.tools.model.po.MdSocialInformation;

import java.util.List;

public interface JdbcTemplateService {

    /**
     * 批量更新数据
     * @param strList
     * @return
     */
    int batchSaveWbDate(List<MdSocialInformation> strList);
}
