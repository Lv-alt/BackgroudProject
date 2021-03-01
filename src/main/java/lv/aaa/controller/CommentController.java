package lv.aaa.controller;

import lv.aaa.entity.T_PostTable;
import lv.aaa.entity.T_commentTable;
import lv.aaa.entity.T_user;
import lv.aaa.service.ICommentTableService;
import lv.aaa.util.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Comment")
public class CommentController {

    @Autowired
    ICommentTableService commentTableService;

    @PostMapping("/addComment")
    public CommonResult addComment(@RequestBody T_commentTable commentTable){

        T_user user = (T_user) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        commentTable.setUser(user);

        return commentTableService.addComment(commentTable);
    }

    @PostMapping("/addReply")
    public CommonResult addReply(@RequestBody T_commentTable commentTable){
        //添加回复（二级评论，要添加回复的是哪一个一级评论），
        T_user user = (T_user) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        commentTable.setUser(user);
        return commentTableService.addReply(commentTable);
    }

    @PostMapping("/addTwoReply")
    public CommonResult addTwoReply(@RequestBody T_commentTable commentTable){
        T_user user = (T_user) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        commentTable.setUser(user);
        return commentTableService.addTwoReply(commentTable);
    }

    /*
    * 根据用户查询出该用户所发表过的所有评论
    * */
    @PostMapping("/getCommentByUser")
    public CommonResult getCommentByUser(){
        T_user user = (T_user) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return commentTableService.getCommentByUser(user);
    }

    @PostMapping("/deleteComment")
    public CommonResult deleteComment(@RequestBody T_commentTable commentTable){
        return commentTableService.deleteComment(commentTable);
    }


}