package com.zixin.cryptography.mapper;

import com.zixin.cryptography.pojo.Note;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Insert("insert into note(title, content, create_user, target_user, algorithm, signature,file_url,create_time, update_time) " +
            "values(#{title}, #{content}, #{createUser}, #{targetUser}, #{algorithm}, #{signature},#{fileUrl},now(), now())")
    void add(Note note);

    @Select("<script>" +
            "SELECT * FROM note" +
            "<where>" +
            "<if test='createUser != null'> AND create_user = #{createUser} </if>" +
            "<if test='targetUser != null'> AND target_user = #{targetUser} </if>" +
            "<if test='createUser == null and targetUser == null'> AND (create_user = #{id} OR target_user = #{id}) </if>" +
            "</where>" +
            "</script>")
    List<Note> list(Integer id, Integer createUser, Integer targetUser);

    @Delete("delete from note where id=#{id}")
    void delete(Integer id);
}
