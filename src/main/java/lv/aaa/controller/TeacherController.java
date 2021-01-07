package lv.aaa.controller;

import lv.aaa.entity.T_user;
import lv.aaa.service.ISubjectService;
import lv.aaa.util.CommonResult;
import lv.aaa.util.EncapsulationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
* 教员的controller，用来定义教员功能的接口
* */
@RestController
@RequestMapping("/Teacher")
public class TeacherController {

    @Autowired
    ISubjectService subjectService;

    /*
    * 生成题目
    * */
    @PostMapping("/generateSubject")
    public CommonResult generateSubject(@RequestBody EncapsulationData encapsulationData){
        //获取到登陆的用户
        T_user user = (T_user) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //生成题目换成修改题目信息
        CommonResult commonResult = subjectService.updateSubject(encapsulationData,user.getU_id());
        return commonResult;
    }

}