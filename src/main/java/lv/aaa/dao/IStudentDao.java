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

    //添加学生上传的上传路径。
    boolean addStudentUploadPath(EncapsulationData encapsulationData);

    //根据学生id and 题目id 查询出该学生上传的作业的存放路径
    String getStudentUploadPath(EncapsulationData encapsulationData);

    //添加学生上传作业的备注
    boolean addStudentUploadRemarks(EncapsulationData encapsulationData);

    //根据学生查询出该学生上传的图片的路径
    EncapsulationData getSubjectByStudent(EncapsulationData encapsulationData);
}
