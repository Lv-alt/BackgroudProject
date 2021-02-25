package lv.aaa.serviceimpl;

import lv.aaa.dao.ICommentTableDao;
import lv.aaa.dao.IPostTableDao;
import lv.aaa.service.IPostTableService;
import lv.aaa.dao.IUserDao;
import lv.aaa.entity.T_PostTable;
import lv.aaa.entity.T_commentTable;
import lv.aaa.entity.T_user;
import lv.aaa.util.CommonResult;
import lv.aaa.util.Lv_Encapsulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IPostTableServiceImpl implements IPostTableService {

    @Resource
    private IPostTableDao postTableDao;

    @Resource
    private ICommentTableDao commentTableDao;

    @Resource
    private IUserDao userDao;

    @Autowired
    private Lv_Encapsulation lv_encapsulation;

    /*
    * 查询出所有的帖子
    * */
    @Override
    public CommonResult getPostTables() {
        //查询出所有的帖子，根据帖子查询出该帖子下所有的评论
        List<T_PostTable> postTables = postTableDao.getPostTables();
        //查询出该帖子下的所有评论
        for(T_PostTable postTable:postTables){
            List<T_commentTable> commentTablesByPostId = commentTableDao.getCommentTablesByPostId(postTable.getP_id());

            postTable.setCommentTables(commentTablesByPostId);
            String formatDate = lv_encapsulation.formatDate(postTable.getP_generate());
            postTable.setFormatDate(formatDate);
            //判断该帖子是否有图片
            if(postTable.getP_contentPath() != null){
                if(postTable.getP_contentPath().indexOf(";") > 0){
                    //只显示第一个帖子图片
                    String result = postTable.getP_contentPath().substring(0, postTable.getP_contentPath().indexOf(";"));
                    postTable.setP_contentPath(result);
                }
            }

        }
        //根据登陆的用户查询出该用户的信息
        //获取到登陆的用户
        T_user user = (T_user) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        T_user userInfo = userDao.getUserByUserId(user.getU_id());
        Map<String,Object> map = new HashMap<>();
        map.put("postTables",postTables);
        map.put("user",userInfo);
        return new CommonResult(1,"成功",map);
    }

    /*
    * 生成帖子
    * */
    @Override
    public CommonResult generatePost(T_PostTable postTable) {
        //判断p_id 是否为null，如果不是null，说明不是第一次上传图片
        // 则根据id 修改图片上传的路径
        boolean result = false;
        if(postTable.getP_id() != null){
            //先查询出原来的路径。进行拼接
            T_PostTable post = postTableDao.getPostTableByPostId(postTable.getP_id());
            //老的路径
            String oldImgPath = post.getP_contentPath();
            //拼接路径
            String newImgPath = oldImgPath+";"+postTable.getP_contentPath();
            //进行修改
            postTable.setP_contentPath(newImgPath);
            result = postTableDao.updatePostImgPathByPostId(postTable);
        }else{
            result = postTableDao.generatePost(postTable);
        }
        if(result){
            return new CommonResult(1,"成功",postTable.getP_id());
        }
        return null;
    }

    /*
    * 修改帖子内容
    * */
    @Override
    public CommonResult updatePostContent(T_PostTable postTable) {
        boolean result = false;
        //判断帖子是否带了图片，如果带了就修改帖子内容，如果没有就添加帖子额
        if(postTable.getP_id() == null){
            //获取到登陆的用户
            T_user user = (T_user) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            postTable.setUser(user);
            result = postTableDao.generatePostNoImage(postTable);
        }else{
            result = postTableDao.updatePostContent(postTable);
        }

        if(result){
            return new CommonResult(1,"成功",result);
        }
        return null;
    }

    /*
    * 根据帖子id查询出帖子 and  该帖子下的所有评论
    * */
    @Override
    public CommonResult getPostTableByPostId(Integer postId) {
        T_PostTable post = postTableDao.getPostTableByPostId(postId);
        //根据帖子查询出该帖子的所有评论
        List<T_commentTable> comments = commentTableDao.getCommentTablesByPostId(post.getP_id());
        post.setCommentTables(comments);
        if(post.getCommentTables() != null){
            //判断评论是一级评论还是二级评论
            for(T_commentTable commentTable:post.getCommentTables()){
                //转换日期格式
                String formatDate = lv_encapsulation.formatDate(commentTable.getC_generate());
                commentTable.setGenerateDateFormat(formatDate);
                //根据一级评论查询出该一级评论的所有二级评论
                List<T_commentTable> list = commentTableDao.getTwoCommentByOneComment(post.getP_id(), commentTable.getC_id());
                for(T_commentTable twoComment:list){
                    String twoCommentFormatDate = lv_encapsulation.formatDate(twoComment.getC_generate());
                    twoComment.setGenerateDateFormat(twoCommentFormatDate);
                    //判断该二级评论是回复 还是直接评论的
                    if(twoComment.getC_replyPerson() != null){
                        //根据外键回复人查询出要回复的人
                        T_user replyUser = userDao.getUserByUserId(twoComment.getC_replyPerson());
                        twoComment.setFormateReply("回复 @"+replyUser.getU_username()+" :"+twoComment.getC_content());
                    }
                }
                commentTable.setTwoCommentTables(list);
            }
        }
        //转换日期格式
        String formatDate = lv_encapsulation.formatDate(post.getP_generate());
        post.setFormatDate(formatDate);

        //判断该帖子的图片是否为多个
        if(post.getP_contentPath() != null){
            if(post.getP_contentPath().indexOf(";") > 0){
                post.setPostImages(post.getP_contentPath().split(";"));
            }else{
                String[] str = new String[]{post.getP_contentPath()};
                post.setPostImages(str);
            }
        }

        return new CommonResult(1,"成功",post);
    }

    /*
    * 添加帖子赞
    * */
    @Override
    public CommonResult addFabulousCount(T_PostTable postTable) {
        boolean result = postTableDao.addFabulousCount(postTable);
        if(result){
            return new CommonResult(1,"成功",result);
        }
        return null;
    }

    @Override
    public CommonResult getPostByUser(T_user user) {
        List<T_PostTable> posts = postTableDao.getPostByUser(user);
        //根据帖子查询出该帖子下的所有评论
        for(T_PostTable postTable:posts){
            List<T_commentTable> comments = commentTableDao.getCommentTablesByPostId(postTable.getP_id());
            postTable.setCommentTables(comments);
            //转换日期格式
            postTable.setFormatDate(lv_encapsulation.formatDate(postTable.getP_generate()));
        }
        return new CommonResult(1,"成功",posts);
    }

    @Override
    public CommonResult deletePostByPostId(T_PostTable postTable) {
        boolean result = postTableDao.deletePostByPostId(postTable);
        if(result){
            return new CommonResult(1,"成功",result);
        }
        return null;
    }
}