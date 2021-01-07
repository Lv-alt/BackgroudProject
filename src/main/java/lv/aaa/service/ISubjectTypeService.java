package lv.aaa.service;

import lv.aaa.entity.T_SubjectType;
import lv.aaa.util.CommonResult;

import java.util.List;

public interface ISubjectTypeService {
    //查询出所有的题目类型
    CommonResult getSubjectTypes();
}
