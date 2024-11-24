package com.example.contactsbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.contactsbackend.entity.Phone;
import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper
public interface PhoneMapper extends BaseMapper<Phone> {
    @Select("select * from user_phone where uid=#{uid}")
    //用result来设置jdbc拦截器类型, 让数据出表和入表的时候经过拦截器处理
    //其中DATE是只有日期的类型，TIMESTAMP是混合类型，TIME是只有时间的类型
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "number", property = "number"),
    })
    public List<Phone> selectAllPhone(int uid);

    @Update("UPDATE user_phone SET number = #{number} WHERE id = #{id}")
    int updateNumberByUid(@Param("uid") Integer uid,@Param("id") Integer id, @Param("number") String number);
}
