package lv.aaa.dao;

import lv.aaa.util.EncapsulationData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IStudentDao {
    //根据学生id查询出学生的题目信息
    List<EncapsulationData> getStudentInfoByStudentId(EncapsulationData encapsulationData);

    //根据学生修改学生对应的题目的状态
    boolean updateSubjectStateByStudent(EncapsulationData encapsulationData);
}
