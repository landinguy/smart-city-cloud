package com.example.smartcitycloud.dao;

import com.example.smartcitycloud.entity.Credentials;
import com.example.smartcitycloud.entity.CredentialsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CredentialsMapper {
    long countByExample(CredentialsExample example);

    int deleteByExample(CredentialsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Credentials record);

    int insertSelective(Credentials record);

    List<Credentials> selectByExample(CredentialsExample example);

    Credentials selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Credentials record, @Param("example") CredentialsExample example);

    int updateByExample(@Param("record") Credentials record, @Param("example") CredentialsExample example);

    int updateByPrimaryKeySelective(Credentials record);

    int updateByPrimaryKey(Credentials record);
}