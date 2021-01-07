package lv.aaa.serviceimpl;

import lv.aaa.dao.ISubjectTypeDao;
import lv.aaa.entity.T_SubjectType;
import lv.aaa.service.ISubjectTypeService;
import lv.aaa.util.CommonResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ISubjectTypeServiceImpl implements ISubjectTypeService {


    @Resource
    ISubjectTypeDao subjectTypeDao;

    /*
    * 方法实现：查询出所有的题目类型
    * */
    @Override
    public CommonResult getSubjectTypes() {
        List<T_SubjectType> subjectTypes = subjectTypeDao.getSubjectTypes();
        return new CommonResult(1,"成功",subjectTypes);
    }
}