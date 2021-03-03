package lv.aaa.service;

import lv.aaa.util.CommonResult;
import lv.aaa.util.EncapsulationData;
import org.apache.ibatis.annotations.Param;

import java.text.ParseException;

public interface ISubjectService {
    //修改题目信息
    CommonResult updateSubject(EncapsulationData encapsulationData,Integer teacherId);

    //生成题目
    CommonResult generateSubject(EncapsulationData encapsulationData);

    //修改该题目对应的该学生的状态为已通过
    CommonResult updateStudentAndSubjectState(EncapsulationData encapsulationData);

    //根据学生id and 题目id查询出该学生对应的这个题目的剩余时间
    //更新剩余时间
    void updateSurplusDate(EncapsulationData encapsulationData) throws ParseException;

    CommonResult getSurplusDate(EncapsulationData encapsulationData);

    //根据班级查询出该班级在当天是否已经布置过作业
    CommonResult getSubjectByClassAndDate(Integer classId);
}
