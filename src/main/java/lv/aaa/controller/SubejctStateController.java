package lv.aaa.controller;

import lv.aaa.service.ISubjectStateService;
import lv.aaa.util.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/SubjectState")
public class SubejctStateController {

    @Autowired
    ISubjectStateService subjectStateService;

    @PostMapping("/getSubjectStates")
    public CommonResult getSubjectStates(){
        return subjectStateService.getSubjectStates();
    }

}