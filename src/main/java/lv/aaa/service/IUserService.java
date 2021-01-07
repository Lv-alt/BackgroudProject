package lv.aaa.service;

import lv.aaa.entity.T_user;
import lv.aaa.util.CommonResult;
import lv.aaa.util.EncapsulationData;

import java.util.List;

public interface IUserService {
    //根据题目和班级查询出所有学生
    CommonResult getUserByClassAndSubject(EncapsulationData encapsulationData);
}
