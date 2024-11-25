package com.example.contactsbackend.controller;

import com.example.contactsbackend.entity.Phone;
import com.example.contactsbackend.entity.User;
import com.example.contactsbackend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
public class UserController {
    @Autowired
    UserMapper userMapper;
    //查询所有用户
    @GetMapping("/")
    public List<User> readAllUsers()
    {
        //MybatisPlus查询方法
        List<User> users = userMapper.selectList(null);
        System.out.println(users.get(1).getStar());
        return users;
    }

    //查询收藏用户
    @GetMapping("/star")
    public List<User> readStarUsers()
    {
        //mapper查询收藏方法
        List<User> users = userMapper.selectByStar();
        return users;
    }
    //取消或加入收藏
    @PutMapping("/star/change")
    public User updateStar(@RequestBody User user)
    {
        Boolean val = user.getStar();
        userMapper.updateStarById(user.getName(),user.getId(),val);
        return user;
    }

    //根据UID查询所有电话号码
    @GetMapping("/details/{uid}")
    public List<Phone> details(@PathVariable int uid) {
        List<User> userList = userMapper.selectUsersAndPhone(uid);
        List<Phone> phoneList = userList.get(0).getPhone();
        return phoneList;
    }

}
