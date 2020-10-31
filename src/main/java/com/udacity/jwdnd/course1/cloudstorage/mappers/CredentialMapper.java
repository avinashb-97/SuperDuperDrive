package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("select * from CREDENTIALS where userid = #{userid}")
    List<Credential> getCredentials(int userid);

    @Insert("insert into CREDENTIALS (url, username, key, password, userid) values (#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Credential credential);

    @Update("update CREDENTIALS set url=#{url} , username=#{username}, key=#{key}, password=#{password} where credentialid=#{id}")
    int update(Credential credential);

    @Delete("delete from CREDENTIALS where credentialid=#{credentialId}")
    int delete(int credentialId);

}
