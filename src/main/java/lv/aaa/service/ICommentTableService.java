package lv.aaa.service;

import lv.aaa.entity.T_commentTable;
import lv.aaa.util.CommonResult;

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

}
