package com.zixin.cryptography.mapper;

import com.zixin.cryptography.pojo.DH;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DHMapper {
    @Delete("delete from dh where id=#{id}")
    void delete(Integer id);

    @Insert("insert into dh(p, g, a, create_user, target_user) " +
            "values(#{p}, #{g}, #{a}, #{createUser},#{targetUser})")
    void add(DH dh);

    @Select("<script>" +
            "SELECT * FROM dh" +
            "<where>" +
            "<if test='createUser != null'> AND create_user = #{createUser} </if>" +
            "<if test='targetUser != null'> AND target_user = #{targetUser} </if>" +
            "<if test='createUser == null and targetUser == null'> AND (create_user = #{id} OR target_user = #{id}) </if>" +
            "</where>" +
            "</script>")
    List<DH> list(Integer id, Integer createUser, Integer targetUser);

    @Update("update dh set b=#{b} where id=#{id}")
    void update(DH dh);
}
