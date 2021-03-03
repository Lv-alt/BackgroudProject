package lv.aaa.serviceimpl;

import lv.aaa.dao.ICommentTableDao;
import lv.aaa.dao.IPostTableDao;
import lv.aaa.dao.IUserDao;
import lv.aaa.entity.T_PostTable;
import lv.aaa.entity.T_commentTable;
import lv.aaa.entity.T_user;
import lv.aaa.service.ICommentTableService;
import lv.aaa.util.CommonResult;
import lv.aaa.util.Lv_Encapsulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
* 评论表对应的 service implement
* */
@Service
public class ICommentTableServiceImpl implements ICommentTableService {

    @Resource
    ICommentTableDao commentTableDao;

    @Autowired
    Lv_Encapsulation lv_encapsulation;

    @Resource
    IPostTableDao postTableDao;

    @Resource
    IUserDao userDao;

    /*
    * 添加评论
    * */
    @Override
    public CommonResult addComment(T_commentTable commentTable) {
        boolean result = commentTableDao.addComment(commentTable);
        if(result){
            return new CommonResult(1,"成功",result);
        }
        return null;
    }

    @Override
    public CommonResult addReply(T_commentTable commentTable) {
        boolean result = commentTableDao.addReply(commentTable);
        if(result){
            return new CommonResult(1,"成功",result);
        }
        return null;
    }

    @Override
    public CommonResult addTwoReply(T_commentTable commentTable) {
        boolean result = commentTableDao.addTwoReply(commentTable);
        if(result){
            return new CommonResult(1,"成功",null);
        }
        return null;
    }

    @Override
    public CommonResult getCommentByUser(T_user user) {
        List<T_commentTable> list = commentTableDao.getCommentByUser(user);
        if(list != null){
            for(T_commentTable commentTable:list){
                commentTable.setGenerateDateFormat(lv_encapsulation.formatDate(commentTable.getC_generate()));
                //进行判断，
                if(commentTable.getC_state() == 0){
                    //如果是一级评论，就查询出该评论对应的帖子
                    this.getPostAndSetPost(commentTable);
                }else if(commentTable.getC_state() == 1){
                    //如果是二级评论，就查询出该二级评论回复的那个一级评论
                    T_commentTable oneComment = commentTableDao.getCommentTableByCommentId(commentTable.getC_pid());
                    commentTable.setTwoCommentReplyOneComment(oneComment);
                    //全都查询出该评论所在的帖子
                    this.getPostAndSetPost(commentTable);
                }else if(commentTable.getC_state() ==  2){
                    //如果是二级评论回复的二级评论
                    T_user replyPerson = userDao.getUserByUserId(commentTable.getC_replyPerson());
                    //如果是二级评论，查询出该二级评论回复的二级评论
                    T_commentTable twoComment = commentTableDao.getCommentTableByCommentId(commentTable.getC_pid());
                    commentTable.setTwoCommentReplyOneComment(twoComment);
                    commentTable.setFormateReply("回复 "+replyPerson.getU_username()+" ："+commentTable.getC_content());
                    //全都查询出该评论所在的帖子
                    this.getPostAndSetPost(commentTable);
                }
            }
            return new CommonResult(1,"成功",list);
        }
        return null;
    }

    @Override
    public CommonResult deleteComment(T_commentTable commentTable) {
        boolean result = commentTableDao.deleteComment(commentTable);
        if(result){
            return new CommonResult(1,"成功",result);
        }
        return null;
    }

    public void getPostAndSetPost(T_commentTable commentTable){
        //全都查询出该评论所在的帖子
        T_PostTable post = postTableDao.getPostTableByPostId(commentTable.getC_fkPost());
        if(post != null){
            //判断该帖子是否有图片
            if(post.getP_contentPath() != null){
                //如果有
                String[] split = post.getP_contentPath().split(";");
                //只取第一个
                post.setP_contentPath(split[0]);
            }
            commentTable.setPostTable(post);
        }else{
            //说明该帖子被删除了
            commentTable.setPostTable(null);
        }

    }



}