package lv.aaa.util;

import lv.aaa.entity.T_user;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//封装重复代码的类
@Component
public class Lv_Encapsulation {

    private final static String filePath = "D:\\Upload\\";

    public void setDate(EncapsulationData encapsulationData){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //默认设置当天时间
            //换算时间
            Date date = new Date();
            //当天时间的开始时间
            String beginDate = sdf.format(date) + " 00:00:00";
            //当天时间的结束时间
            String endDate = 1900+date.getYear()+"-"+(date.getMonth() < 10?("0"+(date.getMonth()+1)):(date.getMonth()+1))+"-"+(date.getDate()<10?("0"+(date.getDate()+1)):date.getDate()+1)+" 00:00:00";
            //封装数据
            encapsulationData.setBeginDate(beginDate);
            encapsulationData.setEndDate(endDate);
    }
    //转换时间格式
    public void dateFormat(EncapsulationData encapsulationData){
        SimpleDateFormat twoSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //转换时间格式
        encapsulationData.setS_generateDateFormat(twoSdf.format(encapsulationData.getS_generateDate()));
    }

    //封装 文件上传的方法
    public Map<String,String> uploadFileMethod(MultipartFile file){
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
        return map;
    }

    /*
    * 转换日期格式
    * */
    public String formatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

}   