package lv.aaa.service;

import lv.aaa.entity.T_PostTable;
import lv.aaa.entity.T_user;
import lv.aaa.util.CommonResult;

import java.util.List;

public interface IPostTableService {
    //查询出所有的帖子
    CommonResult getPostTables();

    CommonResult generatePost(T_PostTable postTable);

    CommonResult updatePostContent(T_PostTable postTable);

    //根据帖子id查询出帖子
    CommonResult getPostTableByPostId(Integer postId);

    //添加帖子赞
    CommonResult addFabulousCount(T_PostTable postTable);

    //根据用户查询出该用户发布过的所有帖子
    CommonResult getPostByUser(T_user user);

    //根据帖子id删除该帖子(修改帖子状态)
    CommonResult deletePostByPostId(T_PostTable postTable);
}
