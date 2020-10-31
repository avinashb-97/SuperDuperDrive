package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("select * from FILES where userid = #{userid}")
    List<File> getAllFiles(int userid);

    @Select("select * from FILES where fileid = #{fileId}")
    File getFile(int fileId);

    @Insert("insert into FILES (filename, contenttype, filesize, userid, filedata) values (#{filename}, #{contentType}, #{fileSize}, #{userid}, #{data})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(File file);

    @Delete("delete from FILES where fileId=#{fileId}")
    int delete(int fileId);

    @Select("select exists(select * from FILES where filename = #{filename} and userid = #{userid})")
    boolean isFileAlreadyExists(String filename, int userid);
}
