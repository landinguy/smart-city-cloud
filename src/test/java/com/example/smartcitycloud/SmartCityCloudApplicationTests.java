package com.example.smartcitycloud;

import com.example.smartcitycloud.dao.CredentialsMapper;
import com.example.smartcitycloud.entity.Credentials;
import com.example.smartcitycloud.util.Helper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmartCityCloudApplicationTests {

    @Resource
    private CredentialsMapper credentialsMapper;

    @Test
    public void contextLoads() {
        for (int i = 0; i < 50; i++) {
            Credentials credentials = new Credentials();
            credentials.setCid(Helper.getUUID());
            credentialsMapper.insertSelective(credentials);
        }

    }

}
