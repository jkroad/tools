package com.ismayfly.coins.tools.service.impl;

import com.ismayfly.coins.tools.core.thread.NameThreadFactory;
import com.ismayfly.coins.tools.model.po.MdSocialInformation;
import com.ismayfly.coins.tools.service.JdbcTemplateService;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service("jdbcTemplateService")
public class JdbcTemplateServiceImpl implements JdbcTemplateService {

    private ExecutorService jdbcExecutor = new ThreadPoolExecutor(7, 7, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(), new NameThreadFactory("jdbc"));

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public int batchSaveWbDate(List<MdSocialInformation> strList) {
        String updateSql = "insert into md_social_information(wb_uid,wb_url,mobile)values(?,?,?)";
        List<List<Object>> paramList = new ArrayList<>();
        for(MdSocialInformation mdSocialInformation:strList){
            List<Object> object = new ArrayList<>();
            object.add(mdSocialInformation.getWbUid());
            object.add(mdSocialInformation.getWbUrl());
            object.add(mdSocialInformation.getMobile());
            paramList.add(object);
        }
        boolean success = batchUpdate(updateSql, paramList);
        if (success) return 1;
        return 0;
    }

    private boolean batchUpdate(String sql, final List<List<Object>> paramList) {
        int[] ints = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                List<Object> objects = paramList.get(i);
                for (int index = 1; index <= objects.size(); index++) {
                    preparedStatement.setObject(index, objects.get(index - 1));
                }
            }

            @Override
            public int getBatchSize() {
                return paramList.size();
            }
        });
        for (int value : ints) {
            if (value < 1) {
                return false;
            }
        }
        return true;
    }

}
