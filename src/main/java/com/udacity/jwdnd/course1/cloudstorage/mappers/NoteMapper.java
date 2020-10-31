package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("select * from NOTES where userid = #{userid}")
    List<Note> getNotes(int userid);

    @Insert("insert into NOTES (notetitle, notedescription, userid) values (#{title}, #{description}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Note note);

    @Update("update NOTES set notetitle=#{title} , notedescription=#{description} where noteid=#{id}")
    int update(Note note);

    @Delete("delete from NOTES where noteid=#{noteId}")
    int delete(int noteId);
}
