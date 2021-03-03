package lv.GXK.com.aaa.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lv.GXK.com.aaa.dao.ClsDao;
import lv.GXK.com.aaa.entity.Cls;
import lv.GXK.com.aaa.service.ClsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther:GXK
 * @data: 2021/01/08/14:48
 */
@Service
public class ClsServiceImpl implements ClsService {
    @Resource
    ClsDao clsDao;
    @Override
    /*查询全部班级信息*/
    public List<Cls> getCls() {
        return clsDao.getCls();
    }

    @Override
    public int addTeacher(Cls cls) {
        return clsDao.addTeacher(cls);
    }

    @Override
    public int addClass(Cls cls) {
        return clsDao.addClass(cls);
    }

    @Override
    public int addCTeacher(Cls cls) {
        return clsDao.addCTeacher(cls);
    }

    @Override
    public int addCClass(Cls cls) {
        return clsDao.addCClass(cls);
    }

    @Override
    public List<Cls> getCTeacher() {
        return clsDao.getCTeacher();
    }

    @Override
    public List<Cls> getTeacher() {
        return clsDao.getTeacher();
    }

    @Override
    public int addTea(Integer t_connectClass, Integer t_connectUser) {
        return clsDao.addTea(t_connectClass, t_connectUser);
    }

    @Override
    public int addCTea(Integer t_connectClass, Integer t_connectUser) {
        return clsDao.addCTea(t_connectClass, t_connectUser);
    }

    @Override
    public List<Cls> getGoodsInfo() {
        return clsDao.getGoodsInfo();
    }

    @Override
    public List<Cls> getGoodsType() {
        return clsDao.getGoodsType();
    }

    @Override
    public int addGoodsinfo(Cls cls) {
        return clsDao.addGoodsinfo(cls);
    }


    @Override
    public boolean generateGoodsInfo(Cls cls) {
        return clsDao.generateGoodsInfo(cls);
    }

    @Override
    public int upGoodsInfo(Cls cls) {
        return clsDao.upGoodsInfo(cls);
    }

    @Override
    public PageInfo<Cls> getJTeacher(Integer pageSize , Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        List<Cls> jTeacher = clsDao.getJTeacher();
        return new PageInfo<>(jTeacher);
    }

    @Override
    public List<Cls> getBTeacher() {
        return clsDao.getBTeacher();
    }

    @Override
    public List<Cls> getClassInfo() {
        return clsDao.getClassInfo();
    }

    @Override
    public int delJTeacher(Cls cls) {
        return clsDao.delJTeacher(cls);
    }

    @Override
    public int upJTeacher(Cls cls) {
        return clsDao.upJTeacher(cls);
    }

    @Override
    public List<Cls> getRole() {
        return clsDao.getRole();
    }

}
