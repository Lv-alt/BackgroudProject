package lv.aaa.dao;

import lv.aaa.entity.T_commentTable;
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
}
