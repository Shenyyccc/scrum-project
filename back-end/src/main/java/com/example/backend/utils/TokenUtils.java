package com.example.backend.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.backend.pojo.User;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {
    /**
     * @param userId
     * @param password
     * @return 以userId作为载荷，获取一个token
     */


    /*
    * jwt就是个生成token（用户令牌）的一个规范。session id 也是一种用户令牌。
    * jwt可以看作是session_id类似的东西，jwt给服务器后，服务器通过密钥验证签名的方式来判断这个jwt是不是自己发出去的，
    * 而session_id是服务器通过查询自己有没有保存这个id来判断是不是自己生成的。
    * */
    public static String getToken(String userId, String password){
        //将userId保存到token作为载荷
        return JWT.create().withAudience(userId)
                .withExpiresAt(DateUtil.offsetHour(new Date(),1))
                .sign(Algorithm.HMAC256(password));
    }


}
