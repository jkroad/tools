package com.ismayfly.coins.tools.base;

import com.ismayfly.coins.tools.ToolsApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * webEnvironment = SpringBootTest.WebEnvironment.RANDOM_POR 不对拦截器进行初始化
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ToolsApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BaseApplicationTest {

    public final HttpHeaders headers = new HttpHeaders();

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;


    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @After
    public void destory() {
    }


}
