package com.example.contactsbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.contactsbackend.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from users where is_star=true")
    List<User> selectByStar();

    @Update("update users set is_star=#{is_star} where id=#{id}")
    int updateStarById(@Param("name") String name, @Param("id") Integer id, @Param("is_star") Boolean star);

    @Select("select * from users where id=#{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(column = "id", property = "phone", javaType = List.class,
                    many = @Many(select = "com.example.contactsbackend.mapper.PhoneMapper.selectAllPhone")
            ),
    })
    public List<User> selectUsersAndPhone(Integer id);

    @Select("select * from users")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "star", column = "is_star"),
            @Result(column = "id", property = "phone", javaType = List.class,
                    many = @Many(select = "com.example.contactsbackend.mapper.PhoneMapper.selectAllPhone")
            ),
    })
    public List<User> selectAllUsersAndPhone();
}
