package lv.aaa.dao;

import lv.aaa.util.EncapsulationData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ISubjectDao {

    //修改作业
    boolean updateSubject(EncapsulationData encapsulationData);

    //生成作业
    boolean generateSubject(EncapsulationData encapsulationData);

    //生成随堂测试 and 课前测
    boolean generateSituation(EncapsulationData encapsulationData);

    //修改该题目对应的该学生的状态为已通过
    boolean updateStudentAndSubjectState(EncapsulationData encapsulationData);

    //根据学生id  and  题目id  查询出剩余时间
    String getSurplusDate(EncapsulationData encapsulationData);

    //更新剩余时间
    boolean updateSurplusDate(EncapsulationData encapsulationData);

    //查询出剩余时间
}
