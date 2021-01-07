package lv.aaa.serviceimpl;

import lv.aaa.dao.IClassDao;
import lv.aaa.entity.T_class;
import lv.aaa.entity.T_user;
import lv.aaa.service.IClassService;
import lv.aaa.util.CommonResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IClassServiceImpl implements IClassService {

    @Resource
    IClassDao classDao;

    /*
    * 方法实现：根据用户查询出该用户对应的班级
    * */
    @Override
    public CommonResult getClassByUser(T_user user) {
        List<T_class> classes = classDao.getClassByUser(user.getU_id());
        return new CommonResult(1,"成功",classes);
    }
}