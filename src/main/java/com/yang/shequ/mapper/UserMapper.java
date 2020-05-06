package com.yang.shequ.mapper;

import com.yang.shequ.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author ywq
 * @date 2020/4/24 13:47
 */
@Mapper
public interface UserMapper {
    @Insert("insert into user(name,account_id,token, gmt_create,gmt_modified) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);

}
