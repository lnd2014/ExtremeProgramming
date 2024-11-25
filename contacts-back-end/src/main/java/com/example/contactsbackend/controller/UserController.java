package com.example.contactsbackend.controller;

import com.example.contactsbackend.entity.Phone;
import com.example.contactsbackend.entity.User;
import com.example.contactsbackend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.Integer.parseInt;

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
    //接收int参数报错，尝试在controller里接收String传给mapper层int
    public List<Phone> details(@PathVariable String uid) {
        List<User> userList = userMapper.selectUsersAndPhone(Integer.parseInt(uid.trim()));
        List<Phone> phoneList = userList.get(0).getPhone();
        return phoneList;
    }

    @GetMapping("/withPhone")
    public List<User> readAllUserswithPhone()
    {
        List<User> users = userMapper.selectAllUsersAndPhone();
        System.out.println("号码是"+users.get(0).getPhone().get(0).getNumber());
        return users;
    }

}
