package com.sj.boot.modules.sys.web;

import com.sj.boot.modules.sys.model.Log;
import com.sj.boot.modules.sys.repository.LogRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangrd
 * @date 2019/1/15
 **/
@RestController
@RequestMapping("api/logs")
@AllArgsConstructor
public class LogController {

    private LogRepository logRepository;

    @GetMapping
    public Page<Log> findAll(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return logRepository.findAll(pageable);
    }
}
