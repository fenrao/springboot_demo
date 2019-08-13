package com.example.login.service;


import com.example.demo.dao.UsersMapper;
import com.example.demo.model.Users;
import com.example.demo.model.UsersExample;
import com.example.login.reqparam.LoginParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LoginService {

    @Autowired
    private UsersMapper usersMapper;

    public String login(LoginParam loginParam)throws Exception{
        if(StringUtils.isBlank(loginParam.getName())){
            throw new Exception("用户名不能为空");
        }
        if(StringUtils.isBlank(loginParam.getPassword())){
            throw new Exception("密码不能为空");
        }

        //根据用户名密码查找
        UsersExample usersExample = new UsersExample();
//        usersExample.createCriteria().andUsernameEqualTo(loginParam.getName()).andPasswordEqualTo(loginParam.getPassword());
        UsersExample.Criteria criteria = usersExample.createCriteria();
        criteria.andPasswordEqualTo(loginParam.getPassword());
        criteria.andUsernameEqualTo(loginParam.getName());


       List<Users> userList= usersMapper.selectByExample(usersExample);
       if (userList.size()>=1){
           return "ok";
       }else {
           throw  new Exception("用户名不存在或者用户名与密码不匹配");
       }


    }
}
