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
@CrossOrigin
@RestController
public class PhoneController {
    @Autowired
    PhoneMapper phoneMapper;

    @PutMapping("/update-number/{id}")
    public Phone updateNumber(@PathVariable String id, @RequestBody Phone phone) {
        phoneMapper.updateNumberByUid(phone.getUid(),Integer.parseInt(id), phone.getNumber());
        return phone;
    }
    @DeleteMapping("details/{id}")
    public Boolean deleteById(@PathVariable String id) {
        phoneMapper.deleteById(Integer.parseInt(id.trim()));
        return true;
    }
    @PostMapping("/create-phone")
    public Boolean createPhone(@RequestBody Phone phone) {
        phoneMapper.insert(phone);
        return true;
    }


}
