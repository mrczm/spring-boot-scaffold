package com.sj.modules.sys.web;

import com.sj.common.Result;
import com.sj.modules.sys.domain.Role;
import com.sj.modules.sys.domain.UserDetails;
import com.sj.modules.sys.repository.RoleRepository;
import com.sj.modules.sys.repository.UserDetailsRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.sj.common.ResultGenerator.error;
import static com.sj.common.ResultGenerator.ok;

/**
 * Created by yangrd on 2017/7/14.
 */
@RestController
@RequestMapping(value = "api/user", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserDetailsController {

    @Autowired
    private UserDetailsRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    @PostMapping
    public Result<String> add(@RequestBody UserDetails userDetails) {
        userDetails.setNickname(userDetails.getLoginName());
        userDetails.setPassword(bCryptPasswordEncoder.encode("123456"));
        Date now = new Date();
        userDetails.setCreatedTime(now);
        userDetails.setModifiedTime(now);
        userDetails.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
        repository.save(userDetails);
        return ok();
    }

    @Transactional
    @DeleteMapping
    public Result<String> delete(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(repository::delete);
        return ok();
    }

    @Transactional
    @PutMapping("/{id}")
    public Result<String> update(@PathVariable("id") UserDetails old, @RequestBody UserDetails userDetails) {
        if (Objects.isNull(old)) {
            return error();
        }
        old.setRoleSet(userDetails.getRoleSet());
        updateVal(old, userDetails);
        repository.save(old);
        return ok();
    }

    @Transactional
    @PutMapping(value = "/{id}/password", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Result<String> changPsw(@PathVariable("id") UserDetails old, String password) {
        if (Objects.nonNull(password)) {
            old.setPassword(bCryptPasswordEncoder.encode(password));
        }
        return ok();
    }

    @GetMapping("/{id}")
    public Result<UserDetails> get(@PathVariable("id") UserDetails userDetails) {
        return ok(userDetails);
    }

    @GetMapping
    public Result<Page<UserDetails>> getAll(UserDetails userDetails, Pageable pageable) {
        return ok(repository.findAll(whereSpec(userDetails), pageable));
    }

    private Specification<UserDetails> whereSpec(final UserDetails userDetails) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotEmpty(userDetails.getLoginName())) {
                predicates.add(cb.like(root.get("loginName"), "%" + userDetails.getLoginName() + "%"));  //根据登录名称模糊查询
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public void updateVal(UserDetails old, UserDetails userDetails) {
        Date now = new Date();
        old.setModifiedTime(now);
        old.setDescription(val(old::getDescription, userDetails::getDescription));
        old.setNickname(val(old::getNickname, userDetails::getNickname));
        old.setAvatar(val(old::getAvatar, userDetails::getAvatar));
        old.setSex(val(old::getSex, userDetails::getSex));
        old.setBirthday(val(old::getBirthday, userDetails::getBirthday));
        old.setPhone(val(old::getPhone, userDetails::getPhone));
        old.setEmail(val(old::getEmail, userDetails::getEmail));
        old.setRoleSet(val(old::getRoleSet, userDetails::getRoleSet));
        old.setStatus(val(old::getStatus, userDetails::getStatus));
    }

    public <T> T val(Supplier<T> oldVal, Supplier<T> newVal) {
        T oldV = oldVal.get();
        T newV = newVal.get();
        return Objects.nonNull(newV) ? newV : oldV;
    }
}
