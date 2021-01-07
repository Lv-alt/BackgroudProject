package lv.aaa.dao;

import lv.aaa.entity.T_modular;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
 * 模块对应的dao
 * */
@Mapper
public interface IModularDao {
    //根据用户id。查询出该用户对应的一级模块
    List<T_modular> getOneModularByUserId(Integer userId);

    //根据用户id，查询出该用t户对应的二级模块
    List<T_modular> getTwoModularByUserIdAndModularId(@Param("userId") Integer userId, @Param("OneModularId") Integer OneModularId);
}
