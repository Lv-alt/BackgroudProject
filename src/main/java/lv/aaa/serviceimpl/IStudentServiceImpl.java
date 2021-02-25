package lv.aaa.serviceimpl;

import lv.aaa.dao.IStudentDao;
import lv.aaa.dao.IUserDao;
import lv.aaa.service.IStudentService;
import lv.aaa.util.CommonResult;
import lv.aaa.util.EncapsulationData;
import lv.aaa.util.Lv_Encapsulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IStudentServiceImpl implements IStudentService {

    @Resource
    IStudentDao studentDao;

    @Resource
    IUserDao userDao;

    @Autowired
    Lv_Encapsulation lv_encapsulation;

    @Override
    public CommonResult getStudentInfoByStudentId(EncapsulationData encapsulationData) {

        //判断是否有开始时间，如果没有设置时间为当前的这一天
        if(encapsulationData.getBeginDate() == null && encapsulationData.getEndDate() == null){
            lv_encapsulation.setDate(encapsulationData);
        }
        //判断用户是否选择了类型，没有默认就是查询课前测的。
        if(encapsulationData.getSubjectTypeId() == null){
            encapsulationData.setSubjectTypeId(1);
        }
        List<EncapsulationData> subjects = studentDao.getStudentInfoByStudentId(encapsulationData);
        //再根据题目表查询出 该题目中的出题人
        for(EncapsulationData encapsulationInfo:subjects){
            //根据题目查询出 出题人
            String teacherName = userDao.getTeacherBySubject(encapsulationInfo.getS_connectTeacher());
            encapsulationInfo.setTeacherName(teacherName);
            //转换时间格式
            lv_encapsulation.dateFormat(encapsulationInfo);
        }
        return new CommonResult(1,"成功",subjects);
    }

    @Override
    public CommonResult updateSubjectStateByStudent(EncapsulationData encapsulationData) {
        boolean result = studentDao.updateSubjectStateByStudent(encapsulationData);
        if(result){
            return new CommonResult(1,"成功",result);
        }
        return null;
    }

    /*
    * 添加学生的上传路径
    * */
    @Override
    public CommonResult addStudentUploadPath(EncapsulationData encapsulationData) {
        String  sumPath = "";
        String fileUploadPath = "http://localhost:8888/img/";
        //先查询出之前的上传路径，
        String oldFileUploadPath = studentDao.getStudentUploadPath(encapsulationData);
        if(oldFileUploadPath != null){
            String newFileUploadPath = encapsulationData.getS_subjectUploadPath();
            //在该文件上传之前可能还有其他的文件上传了，
            //拼接多文件路径。，用 ；号隔开
            sumPath = oldFileUploadPath + (fileUploadPath + newFileUploadPath);
        }else{
            sumPath = fileUploadPath + encapsulationData.getS_subjectUploadPath();
        }

        //修改文件上传路径
        encapsulationData.setS_subjectUploadPath(sumPath);
        boolean result = studentDao.addStudentUploadPath(encapsulationData);
        return new CommonResult(1,"成功",result);
    }

    /*
    * 添加学生上传作业的备注
    * */
    @Override
    public CommonResult addStudentUploadRemarks(EncapsulationData encapsulationData) {
        //因为上传作业备注的时候
        //该学生的作业肯定已经完成了，
        //所以修改该学生对应的该作业的状态为待批改，剩余时间为00：00：00
        boolean result = studentDao.addStudentUploadRemarks(encapsulationData);
        if(result){
            //
            return new CommonResult(1,"成功",result);
        }
        return null;
    }

    /*
    * 根据学生查询出该学生的上传的路径
    * */
    @Override
    public CommonResult getSubjectByStudent(EncapsulationData encapsulationData) {
        //查询出该学生上传的图片的路径
        EncapsulationData subjectByStudent = studentDao.getSubjectByStudent(encapsulationData);

        String[] imagePath = subjectByStudent.getSsm_studentUploadPath().split(";");
        subjectByStudent.setImagePaths(imagePath);
        return new CommonResult(1,"成功",subjectByStudent);
    }

}