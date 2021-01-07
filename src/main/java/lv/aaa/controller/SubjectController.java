package lv.aaa.controller;

import com.sun.deploy.net.HttpResponse;
import lv.aaa.service.ISubjectService;
import lv.aaa.util.CommonResult;
import lv.aaa.util.EncapsulationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*
* Subject Controller
* */
@RestController
@RequestMapping("/Subject")
public class SubjectController {

    private final static String filePath = "F:\\Upload\\";

    @Autowired
    ISubjectService subjectService;

    /*
    * 该方法用来做文件上传
    * */
    @RequestMapping("/uploadSubject")
    public CommonResult uploadSubject(@RequestParam("file") MultipartFile file){
        //获取上传的文件名
        String fileName = file.getOriginalFilename();
        //获取后缀
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //生成新的文件名
        String newfileName = UUID.randomUUID()+suffixName;
        //创建一个File用来判断文件夹是否存在
        File file1 = new File("F:\\Upload");
        //创建文件资源
        File dest = new File(filePath+newfileName);

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
        //拼接文件上传路径
        String fileUploadPath = filePath + newfileName;
        //上传完题目之后把题目名称，路径存放路径 存到数据库 生成一条数据库记录
        EncapsulationData encapsulationData = new EncapsulationData();
        encapsulationData.setS_subjectName(fileName);
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
}