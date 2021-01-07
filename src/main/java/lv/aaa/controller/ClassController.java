package lv.aaa.controller;

import lv.aaa.entity.T_class;
import lv.aaa.entity.T_user;
import lv.aaa.service.IClassService;
import lv.aaa.util.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Class")
public class ClassController {

    @Autowired
    IClassService classService;

    @PostMapping("/getClassByUser")
    public CommonResult getClassByUser(){
        //获取到登陆的用户
        T_user user = (T_user) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CommonResult comm = classService.getClassByUser(user);
        return comm;
    }

}