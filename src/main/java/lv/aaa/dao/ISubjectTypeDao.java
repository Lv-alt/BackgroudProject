package lv.aaa.dao;

import lv.aaa.entity.T_SubjectType;
import lv.aaa.util.EncapsulationData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ISubjectTypeDao {

    //查询出所有的题目类型
    List<T_SubjectType> getSubjectTypes();

    //查询出所有的题目状态
    List<EncapsulationData> getSubjectState();

}
