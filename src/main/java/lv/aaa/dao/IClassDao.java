package lv.aaa.dao;

import lv.aaa.entity.T_class;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IClassDao {

    //根据用户查询出该用户对应的班级
    List<T_class> getClassByUser(Integer userId);

}
