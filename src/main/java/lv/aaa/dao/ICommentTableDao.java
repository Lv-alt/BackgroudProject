package lv.aaa.dao;

import lv.aaa.entity.T_commentTable;
import lv.aaa.entity.T_user;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ICommentTableDao {

    //根据帖子id查询出该帖子下所有的一级评论
    List<T_commentTable> getCommentTablesByPostId(Integer postId);

    boolean addComment(T_commentTable commentTable);

    //添加回复
    boolean addReply(T_commentTable commentTable);

    //根据一级评论查询出该一级评论下的所有二级评论

    List<T_commentTable> getTwoCommentByOneComment(@Param("postId")Integer postId,
                                                   @Param("OneCommentId")Integer OneCommentId);

    //添加二级回复
    boolean addTwoReply(T_commentTable commentTable);

    //根据用户查询出该用户所发表过的所有评论
    List<T_commentTable> getCommentByUser(T_user user);

    //根据评论id查询出该id对应的评论
    T_commentTable getCommentTableByCommentId(Integer commentId);

    //删除评论（修改评论状态)
    boolean deleteComment(T_commentTable commentTable);
}
