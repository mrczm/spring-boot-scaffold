package com.sj.module.sys.web.api;

import com.sj.module.sys.constant.RequestConstant;
import com.sj.module.sys.domain.SiteLogger;
import com.sj.module.sys.repository.SiteLoggerRepository;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by yangrd on 2017/4/18.
 */
@RestController
@RequestMapping(RequestConstant.LOG_API)
public class SiteLoggerController {

    @Autowired
    private SiteLoggerRepository siteLoggerRepository;

    //此处以后可以添加更多查询维度
    @RequiresPermissions("sys:log:view")
    @GetMapping
    @Transactional(readOnly = true)
    public Page<SiteLogger> getAll(@RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "GET") String requestMethod, Date startDate, Date endDate, @PageableDefault(sort = "requestTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return siteLoggerRepository.findByUserLoginNameLikeAndRequestMethodAndRequestTimeBetween("%" + name + "%", requestMethod, startDate, endDate, pageable);
    }
}
