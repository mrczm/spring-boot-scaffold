package com.sj.module.sys.web.api;

import com.sj.module.sys.constant.RequestConstant;
import com.sj.module.sys.domain.SiteLogger;
import com.sj.module.sys.repository.SiteLoggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yangrd on 2017/4/18.
 */
@RestController
@RequestMapping(RequestConstant.LOG_API)
public class SiteLoggerController {

    @Autowired
    private SiteLoggerRepository siteLoggerRepository;

    //此处以后可以添加更多查询维度
    @GetMapping
    @Transactional(readOnly = true)
    public Page<SiteLogger> getAll(Pageable pageable) {
        return siteLoggerRepository.findAll(pageable);
    }
}
