package com.ismayfly.coins.tools;

import com.alibaba.fastjson.JSONObject;
import com.ismayfly.coins.tools.model.query.BankMCCInfoQuery;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.security.RunAs;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureDataMongo
@Slf4j
public class AddBankMccTest {

    @Autowired
    public WebApplicationContext context;
    @Autowired
    public MockMvc mvc;


    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .build();  //构造MockMvc
//        BeanUtil.setContext(context);
    }
    /**
     * 增加mcc黑名单
     */
    @Test
    public void testAddMccBank() throws Exception {
        BankMCCInfoQuery bankMCCInfoQuery = new BankMCCInfoQuery();
        bankMCCInfoQuery.setBankName("中信银行");
        bankMCCInfoQuery.setCardType("IHG联名金卡");
        bankMCCInfoQuery.setMccStr("7011,3015,5541,7012,3016,5542,3501,3020,4112,3503,3022,4784,3509,3026,4111" +
                ",3512,3034,4121" +
                ",3520,3037,4131" +
                ",3530,3042,4782" +
                ",3533,3051,3998" +
                ",3545,3058,3366" +
                ",3594,3061,3389" +
                ",3638,3068,3399" +
                ",3640,3072,4814" +
                ",3641,3075,4899" +
                ",3668,3077,4722" +
                ",3722,3079,4733" +
                ",3742,3082,4815" +
                ",3750,3084,4011" +
                ",5812,3099,4411" +
                ",5813,3100,4582" +
                ",4511,3110,5422" +
                ",3001,3136,5441" +
                ",3005,3144,5451" +
                ",3007,3161,5462" +
                ",3008,3294,5499" +
                ",3009,3296,5814" +
                ",3010,3298,5962");


        MockHttpServletRequestBuilder mockrequest = MockMvcRequestBuilders.post("/link/addBankMcc")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                .content(JSONObject.toJSONString(bankMCCInfoQuery));
        ResultActions perform = mvc.perform(mockrequest);
        // 请求结果校验
        perform.andExpect(MockMvcResultMatchers.status().isOk());
        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
    }
}
