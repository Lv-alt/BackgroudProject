package lv.aaa.controller;

import lv.aaa.entity.T_user;
import lv.aaa.service.IStudentService;
import lv.aaa.util.CommonResult;
import lv.aaa.util.EncapsulationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

@RestController
@RequestMapping("/Student")
public class StudentController {

    @Autowired
    IStudentService studentService;

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


}