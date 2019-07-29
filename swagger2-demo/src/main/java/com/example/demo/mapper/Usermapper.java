package com.example.demo.mapper;

import com.example.demo.enity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface Usermapper {
    @Select("SELECT * FROM users")
    @Results({@Result(property = "userPassWord",column = "passWord")})
    List<User> getAll();

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM users WHERE id = #{id}")
//    @Results({
//            @Result(property = "userSex",  column = "user_sex", javaType = UserSexEum.class),
//            @Result(property = "nickName", column = "nick_name")
//    })
    @Results({@Result(property = "userPassWord", column = "passWord")})
    User getUserById(Integer id);

    /**
     * 添加用户
     * @param user
     * @return
     */
    @Insert("INSERT into users(id,userName,passWord) VALUES(#{id},#{userName},#{userPassWord})")
    void insertUser(User user);


    @Delete("DELETE from users where id = #{id}")
    void deleteUser(Integer id);

    @Update("Update users set userName=#{userName},passWord=#{userPassWord} where id =#{id}")
    void updateUser(User user);

}
