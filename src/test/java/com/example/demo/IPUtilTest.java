package com.example.demo;

import com.example.demo.uitls.IPUtil;
import org.junit.Test;

public class IPUtilTest {
    /* private IPUtil ipUtil;

     @Before
     public void setUp(){
         ipUtil=new IPUtil();
     }
     @After
     public void tearDown(){
         ipUtil=null;
     }*/
    @Test
    public void getCityInfo(){
        IPUtil ipUtil = new IPUtil();
        String ip = "220.248.12.158";
        /*Address cityInfo = ipUtil.getCityInfo(ip);
        System.out.println(cityInfo);*/
    }
}