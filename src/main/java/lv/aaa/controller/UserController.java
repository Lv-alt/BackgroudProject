package lv.aaa.controller;

import lv.aaa.entity.T_user;
import lv.aaa.service.IUserService;
import lv.aaa.util.CommonResult;
import lv.aaa.util.EncapsulationData;
import lv.aaa.util.Lv_Encapsulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    IUserService userService;

    @Autowired
    Lv_Encapsulation lv_encapsulation;

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

    /*
    * 用户头像上传
    * */
    @RequestMapping("/userHeadUpload")
    public CommonResult userHeadUpload(@RequestParam("file")MultipartFile file){
        //文件上传。把地址写入数据库
        Map<String, String> map = lv_encapsulation.uploadFileMethod(file);
        //获取到登陆的用户
        T_user user = (T_user) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //拼接存放路径
        String uploadPath = "http://localhost:8888/img/"+user.getU_username()+"\\"+map.get("newfileName");
        user.setU_headUrl(uploadPath);
        return userService.uploadHeadPath(user);
    }

}