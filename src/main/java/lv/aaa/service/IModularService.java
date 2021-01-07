package lv.aaa.service;

import lv.aaa.entity.T_modular;
import lv.aaa.entity.T_user;
import lv.aaa.util.CommonResult;

import java.util.List;

/*
* Modular 表的 service
* */
public interface IModularService {

    CommonResult getModularsByUser(T_user user);

}
