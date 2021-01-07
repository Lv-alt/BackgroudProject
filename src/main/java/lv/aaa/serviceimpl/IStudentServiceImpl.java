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
}