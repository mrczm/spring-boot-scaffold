package com.sj.module.sys.listener;

import com.alibaba.fastjson.JSON;
import com.sj.module.sys.event.SiteLoggerEvent;
import com.sj.module.sys.repository.SiteLoggerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yangrd on 2017/4/18.
 */
@Component
public class SiteListener {

    private static final Logger logger = LoggerFactory.getLogger(SiteListener.class);


    @Autowired
    private SiteLoggerRepository siteLoggerRepository;

    @EventListener(SiteLoggerEvent.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleSiteLoggerEvent(SiteLoggerEvent siteLoggerEvent) {
        logger.debug("网站权限日志 => {}", JSON.toJSONString(siteLoggerEvent.getSiteLogger()));
        siteLoggerRepository.save(siteLoggerEvent.getSiteLogger());
    }
}
