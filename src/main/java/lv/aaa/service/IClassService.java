package lv.aaa.service;

import lv.aaa.entity.T_class;
import lv.aaa.entity.T_user;
import lv.aaa.util.CommonResult;

import java.util.List;

public interface IClassService {
    //根据用户查询出该用户对应的班级
    CommonResult getClassByUser(T_user user);


}
