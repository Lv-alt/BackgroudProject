package lv.aaa.service;

import lv.aaa.util.CommonResult;
import lv.aaa.util.EncapsulationData;
import org.apache.ibatis.annotations.Param;

public interface ISubjectService {
    //修改题目信息
    CommonResult updateSubject(EncapsulationData encapsulationData,Integer teacherId);

    //生成题目
    CommonResult generateSubject(EncapsulationData encapsulationData);

    //修改该题目对应的该学生的状态为已通过
    CommonResult updateStudentAndSubjectState(EncapsulationData encapsulationData);
}
