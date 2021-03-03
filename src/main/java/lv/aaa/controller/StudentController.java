package lv.aaa.controller;

import lv.aaa.entity.T_user;
import lv.aaa.service.IStudentService;
import lv.aaa.util.CommonResult;
import lv.aaa.util.EncapsulationData;
import lv.aaa.util.Lv_Encapsulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;


@RestController
@RequestMapping("/Student")
public class StudentController {

    @Autowired
    IStudentService studentService;

    @Autowired
    Lv_Encapsulation lv_encapsulation;


    @RequestMapping("/getStudentInfoByStudentId")
    public CommonResult getStudentInfoByStudentId(@RequestBody EncapsulationData encapsulationData){
        //获取到登陆的用户
        T_user user = (T_user) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        encapsulationData.setU_id(user.getU_id());
        return studentService.getStudentInfoByStudentId(encapsulationData);
    }

    /*
    * 根据学生修改学生对应的题目的状态
    * */
    @PostMapping("/updateSubjectStateByStudent")
    public CommonResult updateSubjectStateByStudent(@RequestBody EncapsulationData encapsulationData){
        //获取到登陆的用户
        T_user user = (T_user) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        encapsulationData.setU_id(user.getU_id());
        return studentService.updateSubjectStateByStudent(encapsulationData);
    }

    @PostMapping("/studentUploadSubject")
    public CommonResult studentUploadSubject(@RequestParam("file")MultipartFile file,EncapsulationData encapsulationData){
        //获取到登陆的用户
        T_user user = (T_user) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //  拼接上传路径，把学生上传的图片路径存入到数据库
        Map<String, String> map = lv_encapsulation.uploadFileMethod(file);
        String newfileName = map.get("newfileName");
        //拼接存放路径，
        String newFilePath = user.getU_username()+"\\"+ newfileName + ";";
        encapsulationData.setS_subjectUploadPath(newFilePath);

        return studentService.addStudentUploadPath(encapsulationData);
    }

    @PostMapping("/uploadRemarks")
    public CommonResult uploadRemarks(@RequestBody EncapsulationData encapsulationData){
        return studentService.addStudentUploadRemarks(encapsulationData);
    }

    /*
    * 根据学生查询出该学生提交的作业
    * */
    @PostMapping("/getSubjectByStudent")
    public CommonResult getSubjectByStudent(@RequestBody EncapsulationData encapsulationData){
        return studentService.getSubjectByStudent(encapsulationData);
    }


}