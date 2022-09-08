package com.zuochao.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zuochao.itheima.reggie.common.R;
import com.zuochao.itheima.reggie.entity.User;
import com.zuochao.itheima.reggie.service.UserService;
import com.zuochao.itheima.reggie.utils.SMSUtils;
import com.zuochao.itheima.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author Zuo Chao
 * @date 2022/9/6 20:27
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){

        //获取手机号
        String phone = user.getPhone();

        if(StringUtils.isNotEmpty(phone)){
            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}", code);

            //调用阿里云api发送短信
            //SMSUtils.sendMessage("瑞吉外卖","", phone, code);

            session.setAttribute(phone, code);

            return R.success("手机验证码发送成功");

        }


        return R.error("短信发送失败");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map<String, String> map, HttpSession session){
        log.info(map.toString());

        String phone = map.get("phone");
        String code = map.get("code");

        //从session中获取保存的验证码
        String sessionCode = (String) session.getAttribute(phone);

        if(sessionCode != null && sessionCode.equals(code)){
            //比对成功

            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);
            User user = userService.getOne(queryWrapper);
            //自动注册
            if (user == null){
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);

            }
            session.setAttribute("user", user.getId());
            return R.success(user);
        }

        return R.error("登录失败");


    }



}
