package lv.aaa.service;

import lv.aaa.entity.T_user;
import lv.aaa.util.CommonResult;
import lv.aaa.util.EncapsulationData;

import java.util.List;

public interface IUserService {
    //根据题目和班级查询出所有学生
    CommonResult getUserByClassAndSubject(EncapsulationData encapsulationData);

    //上传用户头像的路径
    CommonResult uploadHeadPath(T_user user);

    //根据用户id查询出用户信息
    CommonResult getUserByUserId(Integer userId);
}
