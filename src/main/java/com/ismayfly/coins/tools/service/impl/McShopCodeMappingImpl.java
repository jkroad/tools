package com.ismayfly.coins.tools.service.impl;

import com.ismayfly.coins.tools.mapper.McShopCodeMappingMapper;
import com.ismayfly.coins.tools.model.po.McShopCodeMapping;
import com.ismayfly.coins.tools.service.McShopCodeMappingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("mcShopCodeMappingService")
public class McShopCodeMappingImpl implements McShopCodeMappingService {

    @Resource
    private McShopCodeMappingMapper mcShopCodeMappingMapper;



    @Override
    public boolean save(McShopCodeMapping mcShopCodeMapping) {
         return  mcShopCodeMappingMapper.insert(mcShopCodeMapping) >= 1;
    }
}
