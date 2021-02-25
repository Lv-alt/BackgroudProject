package lv.aaa.controller;

import lv.aaa.service.IPostTableService;
import lv.aaa.entity.T_PostTable;
import lv.aaa.entity.T_user;
import lv.aaa.util.CommonResult;
import lv.aaa.util.Lv_Encapsulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/PostTable")
public class PostTableController {

    private final static String filePath = "http://localhost:8888/img/";

    @Autowired
    IPostTableService postTableService;

    @Autowired
    Lv_Encapsulation encapsulation;


    @PostMapping("/getPostTables")
    public CommonResult getPostTables(){
        return postTableService.getPostTables();
    }

    @PostMapping("/uploadPostImgage")
    public CommonResult uploadPostImgage(@RequestParam("file")MultipartFile multipartFile,T_PostTable postTableTwo){
        //获取到登陆的用户
        T_user user = (T_user) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, String> map = encapsulation.uploadFileMethod(multipartFile);
        //拼接文件路径
        String myFilePath = filePath+user.getU_username()+"/"+map.get("newfileName");
        //封装数据
        T_PostTable postTable = new T_PostTable();
        postTable.setP_contentPath(myFilePath);
        postTable.setUser(user);
        postTable.setP_id(postTableTwo.getP_id());
        return postTableService.generatePost(postTable);
    }

    @PostMapping("/updatePostContent")
    public CommonResult updatePostContent(@RequestBody T_PostTable postTable){
        CommonResult commonResult = postTableService.updatePostContent(postTable);
        return commonResult;
    }

    /*
    * 根据帖子id查询出该帖子的信息
    * */
    @PostMapping("/getPostTableByPostId")
    public CommonResult getPostTableByPostId(@RequestBody T_PostTable postTable){
        return postTableService.getPostTableByPostId(postTable.getP_id());
    }

    @PostMapping("/addFabulousCount")
    public CommonResult addFabulousCount(@RequestBody T_PostTable postTable){
        return postTableService.addFabulousCount(postTable);
    }

    @PostMapping("/getPostByUser")
    public CommonResult getPostByUser(){
        //获取到登陆的用户
        T_user user = (T_user) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return postTableService.getPostByUser(user);
    }

    @PostMapping("/deletePostByPostId")
    public CommonResult deletePostByPostId(@RequestBody T_PostTable postTable){
        return postTableService.deletePostByPostId(postTable);
    }


}