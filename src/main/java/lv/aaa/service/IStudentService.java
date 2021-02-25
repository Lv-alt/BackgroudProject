package lv.aaa.service;

import lv.aaa.util.CommonResult;
import lv.aaa.util.EncapsulationData;


public interface IStudentService {
    //根据学生id查询出学生的题目信息
    CommonResult getStudentInfoByStudentId(EncapsulationData encapsulationData);

    //根据学生修改学生对应的题目的状态
    CommonResult updateSubjectStateByStudent(EncapsulationData encapsulationData);

    //添加学生上传的上传路径。
    CommonResult addStudentUploadPath(EncapsulationData encapsulationData);

    //添加学生上传作业的备注
    CommonResult addStudentUploadRemarks(EncapsulationData encapsulationData);

    //根据学生查询出该学生上传的图片的路径
    CommonResult getSubjectByStudent(EncapsulationData encapsulationData);
}
