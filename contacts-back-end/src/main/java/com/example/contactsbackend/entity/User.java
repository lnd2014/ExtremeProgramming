package com.example.contactsbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

@TableName("Users")
public class User {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String name;
    private boolean is_star;
    @TableField(exist = false)
    private List<Phone> phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIs_star() {
        return is_star;
    }

    public void setIs_star(boolean is_star) {
        this.is_star = is_star;
    }

    public Integer getId() {
        return id;
    }


    public List<Phone> getPhone() {
        return phone;
    }

    public void setPhone(List<Phone> phone) {
        this.phone = phone;
    }
}
