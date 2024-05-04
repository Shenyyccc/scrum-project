package com.example.backend.utils;

import cn.hutool.captcha.generator.RandomGenerator;
import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;


@Component
public class SMSutil {
    /*
    * 可以使用redis储存验证码
    * 给验证码设置时间，如果时间到，则失效
    * */

    /*
    * 生成短信验证码
    * */
    public static String generateSMS(String phone){
        String serverIp = "app.cloopen.com";
        //请求端口
        String serverPort = "8883";
        //主账号,登陆云通讯网站后,可在控制台首页看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN
        String accountSId = "2c94811c8cd4da0a018f37235db979ca";
        String accountToken = "dac4a1357d3e48899d273fac69d84aa9";

        //请使用管理控制台中已创建应用的APPID
        String appId = "2c94811c8cd4da0a018f37235f3e79d1";
        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(serverIp, serverPort);
        sdk.setAccount(accountSId, accountToken);
        sdk.setAppId(appId);
        sdk.setBodyType(BodyType.Type_JSON);
        String to = phone;
        //短信模板id
        String templateId= "1";
        //随机生成4位数
        RandomGenerator randomGenerator=new RandomGenerator("0123456789",4);
        //4位随机数
        String generate = randomGenerator.generate();
        //有效期
        Long expire=2l;
        String[] datas = {generate,expire.toString()};
        HashMap<String, Object> result = sdk.sendTemplateSMS(to,templateId,datas);
        if("000000".equals(result.get("statusCode"))){
            return generate;

        }else{
            //异常返回输出错误码和错误信息
            return null;
        }
    }


    /*
    * 校验短信验证码
    * */
    public boolean validateSMS(String phone,String sms){
        return false;
    }
}
