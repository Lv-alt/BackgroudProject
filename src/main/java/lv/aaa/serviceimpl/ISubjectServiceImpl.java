package lv.aaa.serviceimpl;

import lv.aaa.dao.ISubjectDao;
import lv.aaa.dao.IUserDao;
import lv.aaa.entity.T_user;
import lv.aaa.service.ISubjectService;
import lv.aaa.util.CommonResult;
import lv.aaa.util.EncapsulationData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ISubjectServiceImpl implements ISubjectService {

    @Resource
    ISubjectDao subjectDao;

    @Resource
    IUserDao userDao;

    @Override
    public CommonResult updateSubject(EncapsulationData encapsulationData,Integer teacherId) {
        //转换类型
        encapsulationData.setLimitDateString(encapsulationData.getLimitDate()+"分钟");
        //添加老师id
        encapsulationData.setTeacherId(teacherId);
        //
        boolean result = false;
        //判断是要修改题目信息还是生成
        if(encapsulationData.getSubjectId() != null){
            //修改生成上传文件时添加的那一条的数据
            result = subjectDao.updateSubject(encapsulationData);
        }else{
            //生成随堂测试，or 课前测
            result = subjectDao.generateSituation(encapsulationData);
        }

        if(result){
            //根据班级 查询出该班级下所有的学生
            List<T_user> students = userDao.getUserByClass(encapsulationData.getClassId());
            AtomicInteger integer = new AtomicInteger(0);
            //循环建立学生与题目的关系
            for(T_user user:students){
                boolean addStudentAndSubjectContactResult = userDao.addStudentAndSubjectContact(user.getU_id(), encapsulationData.getSubjectId());
                if(addStudentAndSubjectContactResult){
                    integer.getAndIncrement();
                }
            }
            //判断是否添加成功
            if(integer.get() == students.size()){
                return new CommonResult(1,"成功",null);
            }else{
               return null;
            }
        }
        return new CommonResult(1,"成功",encapsulationData);
    }

    @Override
    public CommonResult generateSubject(EncapsulationData encapsulationData) {
        boolean result = subjectDao.generateSubject(encapsulationData);
        return new CommonResult(1,"成功",encapsulationData.getSubjectId());
    }


    @Override
    public CommonResult updateStudentAndSubjectState(EncapsulationData encapsulationData) {
        boolean result = subjectDao.updateStudentAndSubjectState(encapsulationData);
        if(result){
            return new CommonResult(1,"成功",result);
        }
        return null;
    }

/*    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            public void run() {
                // task to run goes here
                System.out.println("Hello !!");
            }
        };
        ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        service.scheduleAtFixedRate(runnable, 10, 1, TimeUnit.SECONDS);

    }*/
    public static void main(String[] args) {
        System.out.println("我是测试加入进去的代码");
        System.out.println("我是第二行");
    }
}