package lv.aaa.dao;

import lv.aaa.entity.T_user;
import lv.aaa.util.EncapsulationData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IUserDao {

    //根据用户名查询出用户信息
    T_user getUserByUsername(String username);

    //建立学生跟题目的关系
    //添加完题目之后要获取到添加的题目id，
    boolean addStudentAndSubjectContact(@Param("studentId")Integer studentId,
                                        @Param("subjectId")Integer subjectId);

    //根据班级查询出该班级下所有的学生
    List<T_user> getUserByClass(Integer classId);

    //根据题目和班级查询出所有学生
    List<EncapsulationData> getUserByClassAndSubject(EncapsulationData encapsulationData);

    //根据题目查询出 出题人
    String getTeacherBySubject(Integer subjectId);

    //建立学生和题目之间的关系，并往学生和题目的关系表中添加剩余时间
    boolean addStudentAndSubjectContactBySubject(EncapsulationData encapsulationData);

    //根据用户id查询出用户信息
    T_user getUserByUserId(Integer userId);

    //上传用户头像的路径
    boolean uploadHeadPath(T_user user);



}
