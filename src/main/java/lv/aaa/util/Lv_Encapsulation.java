package lv.aaa.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

//封装重复代码的类
@Component
public class Lv_Encapsulation {

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

}