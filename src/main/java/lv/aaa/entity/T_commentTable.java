package lv.aaa.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/*
* 评论表
* */
@Data
public class T_commentTable {
    private Integer c_id;
    private String c_content;
    private Date c_generate;
    private T_PostTable postTable;
    private T_user user;
    private String c_commentImagePath;
    private Integer p_id;
    private Integer c_state;
    private Integer c_replyPerson;
    private String formateReply;//表中没有该字段。用来格式化回复
    private List<T_commentTable> twoCommentTables;//一级评论下的二级评论
    private Integer c_pid;
    private String generateDateFormat;
    private Integer c_fkPost;
    //二级评论回复的一级的评论
    private T_commentTable twoCommentReplyOneComment;

}