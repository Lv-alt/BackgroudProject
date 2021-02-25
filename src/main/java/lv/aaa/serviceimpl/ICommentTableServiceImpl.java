package lv.aaa.serviceimpl;

import lv.aaa.dao.ICommentTableDao;
import lv.aaa.entity.T_commentTable;
import lv.aaa.service.ICommentTableService;
import lv.aaa.util.CommonResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/*
* 评论表对应的 service implement
* */
@Service
public class ICommentTableServiceImpl implements ICommentTableService {

    @Resource
    ICommentTableDao commentTableDao;

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
}