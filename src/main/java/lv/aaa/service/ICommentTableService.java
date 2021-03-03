package lv.aaa.service;

import lv.aaa.entity.T_commentTable;
import lv.aaa.entity.T_user;
import lv.aaa.util.CommonResult;

import java.util.List;

/*
* 评论表对应的service
* */
public interface ICommentTableService {


    //添加评论的方法
    CommonResult addComment(T_commentTable commentTable);

    //添加回复
    CommonResult addReply(T_commentTable commentTable);

    //添加二级回复
    CommonResult addTwoReply(T_commentTable commentTable);

    //根据用户查询出该用户所发表过的所有评论
    CommonResult getCommentByUser(T_user user);

    //删除评论（修改评论状态)
    CommonResult deleteComment(T_commentTable commentTable);

}
