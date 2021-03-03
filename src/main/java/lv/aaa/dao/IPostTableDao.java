package lv.aaa.dao;

import lv.aaa.entity.T_PostTable;
import lv.aaa.entity.T_user;
import lv.aaa.util.CommonResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
* 帖子表对应的dao
* */
@Mapper
public interface IPostTableDao {

    //查询出所有的帖子
    List<T_PostTable> getPostTables();

    //生成帖子
    boolean generatePost(T_PostTable postTable);

    boolean updatePostContent(T_PostTable postTable);

    //生成不带图片的帖子
    boolean generatePostNoImage(T_PostTable postTable);

    //根据帖子id查询出帖子
    T_PostTable getPostTableByPostId(Integer postId);

    //修改帖子上传图片的路径
    boolean updatePostImgPathByPostId(T_PostTable post);

    //添加帖子赞
    boolean addFabulousCount(T_PostTable postTable);

    //根据用户查询出该用户发布过的所有帖子
    List<T_PostTable> getPostByUser(T_user user);

    //根据帖子id删除该帖子(修改帖子状态)
    boolean deletePostByPostId(T_PostTable postTable);

}
