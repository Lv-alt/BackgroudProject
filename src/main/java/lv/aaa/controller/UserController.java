package lv.aaa.controller;

import lv.aaa.service.IUserService;
import lv.aaa.util.CommonResult;
import lv.aaa.util.EncapsulationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    IUserService userService;

    /*
    * 根据班级id查询出该班级下所有的学生
    * */
    @RequestMapping("/getUserByClassAndSubject")
    public CommonResult getUserByClassAndSubject(@RequestBody EncapsulationData encapsulationData){
        if(encapsulationData.getPageNum() == null){
            encapsulationData.setPageNum(1);
        }
        //默认的类型id 1（课前测）
        if(encapsulationData.getSubjectTypeId() == null || encapsulationData.equals("")){
            encapsulationData.setSubjectTypeId(1);
        }
        CommonResult comm = userService.getUserByClassAndSubject(encapsulationData);
        return comm;
    }

}