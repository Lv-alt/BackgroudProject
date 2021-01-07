package lv.aaa.serviceimpl;

import lv.aaa.dao.IModularDao;
import lv.aaa.entity.T_modular;
import lv.aaa.entity.T_user;
import lv.aaa.service.IModularService;
import lv.aaa.util.CommonResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
* Modular service 实现
* */
@Service
public class IModularServiceImpl implements IModularService {

    @Resource
    IModularDao modularDao;

    /*
    * 方法实现：
    * */
    @Override
    public CommonResult getModularsByUser(T_user user) {
        //先根据用户id查询出该用户的一级菜单
        List<T_modular> oneModulars = modularDao.getOneModularByUserId(user.getU_id());
        //根据一级菜单
        for(T_modular modular:oneModulars){
            List<T_modular> twoModulars = modularDao.getTwoModularByUserIdAndModularId(user.getU_id(), modular.getM_id());
            modular.setModulars(twoModulars);
        }
        return new CommonResult(1,"成功",oneModulars);
    }
}