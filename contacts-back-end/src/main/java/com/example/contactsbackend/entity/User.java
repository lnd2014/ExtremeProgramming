package com.example.contactsbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.experimental.FieldNameConstants;

import java.util.List;

@TableName("Users")
public class User {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String name;
    @JsonProperty("is_star")
    //Boolean类型为了避免读取错误，不要用is前缀！并且一定要使用包装类！
    @TableField(value="is_star")
    private Boolean star;
    @TableField(exist = false)
    private List<Phone> phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty(value = "is_star")
    public Boolean getStar() {
        return star;
    }

    @JsonProperty(value = "is_star")
    public void setStar(Boolean star) {
        this.star = star;
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
