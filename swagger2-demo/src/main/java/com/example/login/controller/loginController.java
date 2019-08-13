package com.example.login.controller;

import com.example.login.reqparam.LoginParam;
import com.example.login.service.LoginService;
import com.example.login.utils.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;



@RestController
public class loginController {
    @Autowired
    private LoginService loginService;


    @PostMapping("/login")
    @ApiOperation(value = "用户登录接口")
    public String Login(@RequestBody LoginParam param, HttpServletResponse response) throws Exception {
        LoginParam loginparam = new LoginParam();
        loginparam.setName(param.getName());
        loginparam.setPassword(param.getPassword());
        String bool = loginService.login(loginparam);
        if (bool == "ok") {
            //创建token
            String token = JwtUtil.generateToken(param);

            response.addHeader(JwtUtil.HEADER_STRING, JwtUtil.TOKEN_PREFIX + token);
            //返回登录成功用户的所有登录信息一般都在一个模型类里
            return "200";

        } else {
            return "400";
        }
    }

    @GetMapping("/hello")
    @ApiOperation(value = "测试验证token是否生效接口")
    public  String hello(){
        return "hello token";
    }
}
