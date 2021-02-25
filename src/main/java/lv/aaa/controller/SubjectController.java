package lv.aaa.controller;

import com.sun.deploy.net.HttpResponse;
import lv.aaa.entity.T_user;
import lv.aaa.service.ISubjectService;
import lv.aaa.util.CommonResult;
import lv.aaa.util.EncapsulationData;
import lv.aaa.util.Lv_Encapsulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/*
* Subject Controller
* */
@RestController
@RequestMapping("/Subject")
public class SubjectController {


    @Autowired
    ISubjectService subjectService;

    @Autowired
    Lv_Encapsulation lv_encapsulation;

    /*
    * 该方法用来做文件上传
    * */
    @RequestMapping("/uploadSubject")
    public CommonResult uploadSubject(@RequestParam("file") MultipartFile file){
        Map<String, String> map = lv_encapsulation.uploadFileMethod(file);
        //拼接文件上传路径
        String fileUploadPath = map.get("filePath") + map.get("newfileName");
        //上传完题目之后把题目名称，路径存放路径 存到数据库 生成一条数据库记录
        EncapsulationData encapsulationData = new EncapsulationData();
        encapsulationData.setS_subjectName(map.get("fileName"));
        encapsulationData.setS_subjectUploadPath(fileUploadPath);
        CommonResult commonResult = subjectService.generateSubject(encapsulationData);
        return commonResult;
    }

    /*
    * 文件下载
    * */
    @PostMapping("/fileDownload")
    public ResponseEntity<byte[]> fileDownload(@RequestBody EncapsulationData encapsulationData, HttpServletResponse response) throws IOException {
        //创建输入流
        InputStream inputStream  =new FileInputStream(new File(encapsulationData.getS_subjectUploadPath()));
        byte[] bytes = new byte[inputStream.available()];
        //把文件字节流读取到 byte 数组中
        inputStream.read(bytes);

        HttpHeaders headers = new HttpHeaders();
        String filename = new String(new String("中文.jpg").getBytes("utf-8"), "iso-8859-1");
        headers.setContentDispositionFormData("attachment", encapsulationData.getS_subjectName());
        headers.setContentLength(bytes.length);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        List<String> list =new ArrayList<>();
        list.add("Content-Disposition");
        //设置访问控制暴露的请求头
        headers.setAccessControlExposeHeaders(list);

        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
    }

    /*
    * //修改该题目对应的该学生的状态为已通过
    * */
    @PostMapping("/updateStudentAndSubjectState")
    public CommonResult updateStudentAndSubjectState(@RequestBody EncapsulationData encapsulationData){
        return subjectService.updateStudentAndSubjectState(encapsulationData);
    }

    /*
    *  开始 ⏲
    * */
    @PostMapping("/beginTimer")
    public CommonResult beginTimer(@RequestBody EncapsulationData encapsulationData) throws ParseException {
        //根据学生id and 题目id查询出该学生对应的这个题目的剩余时间
        //调用更新剩余时间方法
        subjectService.updateSurplusDate(encapsulationData);
        //返回结果
        return new CommonResult(1,"成功","开始计时了");
    }
    /*
    * 获取剩余时间
    * */
    @PostMapping("/getSurplusDate")
    public CommonResult getSurplusDate(@RequestBody EncapsulationData encapsulationData){
        CommonResult surplusDate = subjectService.getSurplusDate(encapsulationData);
        return surplusDate;
    }



}