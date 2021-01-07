package lv.aaa.controller;

import lv.aaa.entity.T_modular;
import lv.aaa.entity.T_user;
import lv.aaa.service.IModularService;
import lv.aaa.util.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/*
* Menu Controller
* */
@RequestMapping("/Menu")
@RestController
public class MenuController {

    @Autowired
    IModularService modularService;

    @PostMapping("/getMenusByUser")
    public CommonResult<T_modular> getMenusByUser(Principal principal){
        //获取到登陆的用户
        T_user user = (T_user) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //根据用户查询出该用户的功能.
        CommonResult commonResult = modularService.getModularsByUser(user);
        return commonResult;
    }

}