package lv.aaa.controller;

import lv.aaa.service.ISubjectTypeService;
import lv.aaa.util.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/SubjectType")
public class SubjectTypeController {

    @Autowired
    ISubjectTypeService subjectTypeService;

    @PostMapping("/getSubjectTypes")
    public CommonResult getSubjectTypes(){
        return subjectTypeService.getSubjectTypes();
    }
}