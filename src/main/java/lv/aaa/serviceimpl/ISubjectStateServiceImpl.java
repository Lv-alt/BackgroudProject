package lv.aaa.serviceimpl;

import lv.aaa.dao.ISubjectStateDao;
import lv.aaa.service.ISubjectService;
import lv.aaa.service.ISubjectStateService;
import lv.aaa.util.CommonResult;
import lv.aaa.util.EncapsulationData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ISubjectStateServiceImpl implements ISubjectStateService {

    @Resource
    ISubjectStateDao subjectStateDao;

    @Override
    public CommonResult getSubjectStates() {
        List<EncapsulationData> subjectStates = subjectStateDao.getSubjectStates();
        return new CommonResult(1,"成功",subjectStates);
    }
}