package lv.aaa.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lv.aaa.dao.IUserDao;
import lv.aaa.entity.T_user;
import lv.aaa.service.IUserService;
import lv.aaa.util.CommonResult;
import lv.aaa.util.EncapsulationData;
import lv.aaa.util.Lv_Encapsulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class IUserServiceImpl implements IUserService {

    @Resource
    IUserDao userDao;

    @Autowired
    Lv_Encapsulation lv_encapsulation;

    @Override
    public CommonResult getUserByClassAndSubject(EncapsulationData encapsulationDataInfo) {

        //默认设置当天时间
        if(encapsulationDataInfo.getBeginDate() == null && encapsulationDataInfo.getEndDate() == null) {
            //调用设置当天时间的方法
            lv_encapsulation.setDate(encapsulationDataInfo);
        }

        PageHelper.startPage(encapsulationDataInfo.getPageNum(),5);
        List<EncapsulationData> users = userDao.getUserByClassAndSubject(encapsulationDataInfo);
        //循环根据状态判断信息
        for(EncapsulationData encapsulationData:users){
            lv_encapsulation.dateFormat(encapsulationData);
        }
        PageInfo pageInfo = new PageInfo(users);
        return new CommonResult(1,"成功",pageInfo);
    }

}