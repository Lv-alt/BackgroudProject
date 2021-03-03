package lv.GXK.com.aaa.controller;

import com.github.pagehelper.PageInfo;
import lv.GXK.com.aaa.entity.Cls;
import lv.GXK.com.aaa.service.ClsService;
import lv.aaa.entity.T_user;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Auther:GXK
 * @data: 2021/01/08/14:54
 */
@RestController
@CrossOrigin
@RequestMapping("/con")
public class ClsController {

    private final static String filePath = "D:\\Upload\\";
    @Resource
    ClsService clsService;



    /*查询全部班级信息*/
    @RequestMapping("/getCls")
    public List<Cls> getCls(){
        List<Cls> cls = clsService.getCls();
        System.out.println("111");
        System.out.println(cls.toString());
        return cls;
    }

    /*添加教员信息*/
    @RequestMapping("/addTeacher")
    public int addTeacher(Cls cls){
        int i = clsService.addTeacher(cls);
        int i1 = clsService.addClass(cls);
        return i;
    }
    /*添加班主任信息*/
    @RequestMapping("/addCTeacher")
    public int addCTeacher(Cls cls){
        int i = clsService.addCTeacher(cls);
        clsService.addCClass(cls);
        return i;
    }

    /*查询所有班主任信息*/
    @RequestMapping("/getCTeacher")
    public List<Cls> getCTeacher(){
        List<Cls> cTeacher = clsService.getCTeacher();
        System.out.println(cTeacher.toString());
        return cTeacher;
    }
    /*查询所有教员信息*/
    @RequestMapping("/getTeacher")
    public List<Cls> getTeacher(){
        List<Cls> teacher = clsService.getTeacher();
        return teacher;
    }

    /*添加班级班主任 教员信息*/
    @RequestMapping("/addTT")
    public int addTT(Integer t_connectClass,Integer t_connectUser,Integer id){
        int i = clsService.addCTea(t_connectClass, t_connectUser);
        clsService.addTea(t_connectClass,id);
        return i;
    }
    /*查询全部商品信息及商品类别信息*/
    @RequestMapping("/getGoodsInfo")
    public List<Cls> getGoodsInfo(){
        List<Cls> goodsInfo = clsService.getGoodsInfo();
        System.out.println("商品信息"+goodsInfo);
        return goodsInfo;
    }

    /*查询全部商品类别信息*/
    @RequestMapping("/getGoodsType")
    public List<Cls> getGoodsType(){
        List<Cls> goodsType = clsService.getGoodsType();
        return goodsType;
    }
    /*上传商品信息*/
   @RequestMapping("/addGoodsinfo")
    public int addGoodsinfo(Cls cls){
       int i = clsService.addGoodsinfo(cls);
       return i;
   }

   @RequestMapping("/uploadFile")
   public Integer uploadFile(MultipartFile file){
       //获取到登陆的用户
       T_user user = (T_user) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       //获取上传的文件名
       String fileName = file.getOriginalFilename();
       //获取后缀
       String suffixName = fileName.substring(fileName.lastIndexOf("."));
       //生成新的文件名
       String newfileName = UUID.randomUUID()+suffixName;
       //创建一个File用来判断文件夹是否存在
       //为每一个用户都分配一个目录
       File file1 = new File("D:\\Upload\\"+user.getU_username());
       //创建文件资源
       File dest = new File((filePath+user.getU_username()+"\\")+newfileName);

       try {
           if(file1.exists()){
               //写入到指定路径
               file.transferTo(dest);
           }else{
               file1.mkdirs();
               //写入到指定路径
               file.transferTo(dest);
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
       //把原文件名，保存路径，新文件名返回出去
       Map<String,String> map = new HashMap<>();
       map.put("filePath",filePath+user.getU_username()+"\\");
       map.put("newfileName",newfileName);
       map.put("fileName",fileName);
       //图片保存路径，拼接起来
       String port = "http://localhost:8888/img/"+user.getU_username()+"/"+newfileName;
       Cls cls = new Cls();
       cls.setImg(port);
       boolean result = clsService.generateGoodsInfo(cls);
       return cls.getC_id();
   }

   /*根据id修改上传图片的商品信息*/
    @RequestMapping("/upGoodsInfo")
    public int upGoodsInfo(Cls cls){
        int i = clsService.upGoodsInfo(cls);
        return i;
    }

    /*查询所有教员信息*/
    @RequestMapping("/getJTeacher")
    public PageInfo<Cls> getJTeacher(@RequestParam(defaultValue = "2") Integer pageSize , @RequestParam(defaultValue = "1") Integer pageNum){
        PageInfo<Cls> page = clsService.getJTeacher(pageSize,pageNum);
        return page;
    }

    /*查询所有班主任信息*/
    @RequestMapping("/getBTeacher")
    public List<Cls> getBTeacher(){
        List<Cls> bTeacher = clsService.getBTeacher();
        return bTeacher;
    }

    /*查询所有班级信息*/
    @RequestMapping("/getClassInfo")
    public List<Cls> getClassInfo(){
        List<Cls> classInfo = clsService.getClassInfo();
        return classInfo;
    }

    /*根据id删除教员信息*/
    @RequestMapping("/delJTeacher")
    public int delJTeacher(Cls cls){
        System.out.println(cls.getU_id());
        int i = clsService.delJTeacher(cls);
        return i;
    }

    /*修改教员信息*/
    @RequestMapping("/upJTeacher")
    public int upJTeacher(Cls cls){
        System.out.println("进来了");
        int i = clsService.upJTeacher(cls);
        return i;


    }
    /*查询所有角色信息*/
    @RequestMapping("/getRole")
    public List<Cls> getRole(){
        List<Cls> role = clsService.getRole();
        return role;
    }



}
