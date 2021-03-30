package com.ismayfly.coins.tools;

import com.alibaba.fastjson.JSONObject;
import com.ismayfly.coins.tools.base.BaseApplicationTest;
import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class RedisMockTest extends BaseApplicationTest {

   @Test
    public void  testIndex() throws Exception {
//        MvcResult mvcResult = mockMvc.perform(post("/index")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .headers(headers)
//                .content("")
//        ).andExpect(status().isOk())
//                .andExpect(jsonPath("code").value("1000"))
//                .andReturn();
//        String respJson = mvcResult.getResponse().getContentAsString();
//        System.out.println(respJson);
    }

    public static void main(String[] args) {
        String groupId= "jl-rf";
        System.out.println(Math.abs(groupId.hashCode()%50));
    }
}
