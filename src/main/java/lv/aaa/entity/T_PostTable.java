package lv.aaa.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/*
* 帖子表
* */
@Data
public class T_PostTable {
    private Integer p_id;
    private String p_postTitle;
    private String p_content;
    private Date p_generate;
    private T_user user;
    private String p_contentPath;
    private T_PostType postType;
    private List<T_commentTable> commentTables;
    private String formatDate;
    private String[] postImages;
    private Integer p_fabulousCount;

}