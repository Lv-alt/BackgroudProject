package lv.aaa.serviceimpl;

import lv.aaa.dao.ISubjectDao;
import lv.aaa.dao.IUserDao;
import lv.aaa.entity.T_user;
import lv.aaa.service.ISubjectService;
import lv.aaa.util.CommonResult;
import lv.aaa.util.EncapsulationData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
            //如果是添加的作业的话，则给学生和题目的中间表中添加剩余时间
            if(result){
                //根据班级 查询出该班级下所有的学生
                List<T_user> students = userDao.getUserByClass(encapsulationData.getClassId());
                AtomicInteger integer = new AtomicInteger(0);
                //循环建立学生与题目的关系
                for(T_user user:students){
                    encapsulationData.setU_id(user.getU_id());
                    boolean addStudentAndSubjectContactResult = userDao.addStudentAndSubjectContactBySubject(encapsulationData);
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
        }else{
            //生成随堂测试，or 课前测
            result = subjectDao.generateSituation(encapsulationData);
            //如果是生成随堂测试，就直接建立关系，不添加剩余时间
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

    /*
    *
    * */
    @Override
    public void updateSurplusDate(EncapsulationData encapsulationData) throws ParseException {
        //先根据学生id and 题目id查询出该学生对应的这个题目的剩余时间
        String surplusDate = subjectDao.getSurplusDate(encapsulationData);
        //判断是否是第一次获取这个时间
        if(surplusDate.indexOf("分") > 0){
            //如果是，换算时间戳 这里获取到的是 分钟
            Integer minute = Integer.parseInt(surplusDate.substring(0,surplusDate.indexOf("分")));
            //分钟 * 60 * 1000得到 时间戳
            Long timeStamp = minute * 60 * 1000L;
            //调用方式，减少秒数
            this.decreasingDate(timeStamp,encapsulationData);

        }else{
            //else 就说明不是第一次获取这个时间了，
            //也就是说现在的surplusDate 是 00:00:00这种格式 换算时间戳
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date parse = sdf.parse(surplusDate);
            //获取时间戳 不能 直接 getTime时间戳，会是负数，不知道为啥
            //
            long timeStamp = (parse.getHours() * 60 * 60 * 1000)+
                    (parse.getMinutes() * 60 * 1000)+(parse.getSeconds() * 1000);
            //调用方式，减少秒数
            this.decreasingDate(timeStamp,encapsulationData);
        }
    }

    /*
    * 查询出剩余时间，发送到前台
    * */
    @Override
    public CommonResult getSurplusDate(EncapsulationData encapsulationData) {
        String surplusDate = subjectDao.getSurplusDate(encapsulationData);
        return new CommonResult(1,"成功",surplusDate);
    }

    //来个递减的方法，开启一个线程每秒更新一次数据
    public void decreasingDate(long timeStamp,EncapsulationData encapsulationData){
        //使用这种方式 该方式应该出不了栈，所以前台会一直等待这个方法执行完毕
        //使用自循的方式替代线程
        /*while(true){
            //循环一次减少一秒，这里是毫秒单位  既 1000
            timeStamp -= 1000;
        }*/
        // 单位: 毫秒
        final long timeInterval = 1000;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdfTwo = new SimpleDateFormat("mm:ss");
        Runnable runnable = new Runnable() {
            public void run() {
                long result = timeStamp;
                String text = "";
                while (true) {
                    //判断时间是否  >  1秒
                    if(result > 1000){
                        result -= 1000;
                        //如果剩余时间大于1小时的话
                        if(result > (60 * 60 * 1000)){
                            //截取并拼接时间
                            String format = sdf.format(new Date(result));
                            Integer AA = (Integer.parseInt(format.substring(0,format.indexOf(":"))) - 8);
                            String substring = format.substring(format.indexOf(":"), format.length());
                            text = (AA < 10?"0"+AA:AA)+substring;
                        }else{
                            String format = sdfTwo.format(new Date(result));
                            text = "00:"+format;
                        }

                        //往数据库更新数据
                        encapsulationData.setSurplusDate(text);
                        //一秒更新一次数据
                        subjectDao.updateSurplusDate(encapsulationData);
                    }else{
                        encapsulationData.setSurplusDate("00:00:00");
                        //一秒更新一次数据
                        subjectDao.updateSurplusDate(encapsulationData);
                        break;
                    }

                    //一秒减少一次
                    try {
                        Thread.sleep(timeInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

}