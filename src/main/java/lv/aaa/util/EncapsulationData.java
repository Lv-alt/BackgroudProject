package lv.aaa.util;

import lombok.Data;

import java.util.Date;

/*
* 专门用来封装数据的工具类
* */
@Data
public class EncapsulationData {
    private Integer classId;
    private Integer subjectTypeId;
    private Integer limitDate;
    private Integer u_id;
    private Integer ssm_fkstate;
    private String limitDateString;
    private Integer teacherId;
    private Integer subjectId;
    private Integer s_id;
    private String u_username;
    private Integer state_id;
    private String teacherName;
    private Integer pageNum;
    private String s_limitDate;
    private Date s_generateDate;
    private String beginDate;
    private Integer rowIndex;
    private String endDate;
    private String s_generateDateFormat;
    private String st_name;
    private String likeName;
    private Integer s_connectTeacher;
    private String state_name;
    private String s_subjectName;
    private String s_subjectUploadPath;

}