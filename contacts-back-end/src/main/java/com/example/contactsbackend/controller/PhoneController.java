package com.example.contactsbackend.controller;

import com.example.contactsbackend.entity.Phone;
import com.example.contactsbackend.entity.User;
import com.example.contactsbackend.mapper.PhoneMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PhoneController {
    @Autowired
    PhoneMapper phoneMapper;

    @PutMapping("/update-number/{id}")
    public String updateNumber(@PathVariable Integer id, @RequestBody Phone phone) {
        phoneMapper.updateNumberByUid(phone.getUid(),id, phone.getNumber());
        return "更新成功";
    }
    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable Integer id) {
        phoneMapper.deleteById(id);
        return "删除成功";
    }


}
