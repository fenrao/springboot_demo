package com.example.demo.controller

import com.example.demo.dao.UsersMapper
import com.example.demo.enity.User
import com.example.demo.mapper.Usermapper
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMapping
import java.sql.SQLIntegrityConstraintViolationException


@RestController
@Api("测试Api")
class SwaggerController {

    @Autowired
    var userMapper: Usermapper? = null

    //mybatis 自动生成的代码
    @Autowired
    var usersMapper: UsersMapper? = null

    @ApiOperation(value = "无参数值", notes = "提示")
    @GetMapping(value = ["/swagger"])
    fun swagger(): String {
        return "swager"
    }

    @ApiOperation(value = "获取全部用户的信息", notes = "查询说有用户list")
    @GetMapping(value = "/user/selctAll")
    fun selectAll(): MutableList<User>? {
        var list = userMapper?.getAll()
        return list
    }

    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Int", paramType = "path")
    @GetMapping(value = ["/user/select/{id}"])
    fun getUserId(@PathVariable(value = "id") id: Int): User? {
        var user = userMapper?.getUserById(id)
        return user
    }


    @ApiOperation(value = "添加用户信息", notes = "根据提交的post新增用户")
    @ApiImplicitParam(name = "user", value = "添加账户信息", required = true, dataType = "User")
    @PostMapping(value = ["/user"])
    fun insertUser(@RequestBody user: User): String? {
        try {

            userMapper?.insertUser(user)
            return "ok"
        } catch (e: Exception) {
            return e.message
        }
    }


    @ApiOperation(value = "修改用户信息", notes = "根据提交的post新增用户")
    @ApiImplicitParams(
            ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Int", paramType = "path"),
            ApiImplicitParam(name = "user", value = "用户实体user", required = true, dataType = "User")
    )
    @RequestMapping(value = ["user/update/{id}"], method = [RequestMethod.PUT])
    fun updateUser(@RequestBody user: User): String? {
        try {

            userMapper?.updateUser(user)
            return "修改成功"
        } catch (e: Exception) {
            return e.message

        }
    }

    @ApiOperation(value = "删除用户", notes = "根据提交的用户id删除用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Int", paramType = "path")
    @RequestMapping(value = ["user/delete/{id}"], method = [RequestMethod.DELETE])
    fun deleteUser(@PathVariable(value = "id") id: Int): String? {
        try {

            userMapper?.deleteUser(id)
            return "修改成功"
        } catch (e: Exception) {
            return e.message

        }

    }


}