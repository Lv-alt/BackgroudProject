package lv.GXK.com.aaa.dao;

import lv.GXK.com.aaa.entity.Cls;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Auther:GXK
 * @data: 2021/01/08/14:47
 */
@Mapper
public interface ClsDao {
    /*查询全部班级信息*/
    List<Cls> getCls();
    /*添加教员信息*/
    int addTeacher(Cls cls);
    /*添加教员班级*/
    int addClass(Cls cls);
    /*添加班主任信息*/
    int addCTeacher(Cls cls);
    /*添加班主任班级信息*/
    int addCClass(Cls cls);
    /*查询所有班主任信息*/
    List<Cls> getCTeacher();
    /*查询所有教员信息*/
    List<Cls> getTeacher();
    /*添加班级教员*/
    int addTea(Integer t_connectClass,Integer t_connectUser);
    /*添加班级班主任信息*/
    int addCTea(Integer t_connectClass,Integer t_connectUser);
    /*查询全部商品信息*/
    List<Cls> getGoodsInfo();
    /*查询全部商品类别信息*/
    List<Cls> getGoodsType();
    /*上传商品 */
    int addGoodsinfo(Cls cls);
    //生成图片
    boolean generateGoodsInfo(Cls cls);
    /*根据id修改上传图片的商品信息*/
    int upGoodsInfo(Cls cls);

    /*查询所有教员信息*/
    List<Cls> getJTeacher();

    /*查询所有班主任信息*/
    List<Cls> getBTeacher();

    /*查询所有班级信息 */
    List<Cls> getClassInfo();

    /*删除教员*/
    int delJTeacher(Cls cls);

    /*修改教员信息*/
    int upJTeacher(Cls cls);

    /*查询所有角色信息*/
    List<Cls> getRole();

}
