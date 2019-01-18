package com.sj.boot.modules.sys.model;

import com.sj.boot.common.spring.data.AbstractEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Optional;
import java.util.Set;

/**
 * @author yangrd
 * @date 2019/1/9
 **/
@Entity
@Table(name = "sys_role")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Role extends AbstractEntity<Role> implements GrantedAuthority {

    private final static String ROLE_PREFIX = "ROLE_";

    private String name;

    private String description;

    private String authority;

    @ManyToMany
    private Set<Menu> menuSet;

    @Override
    public String getAuthority() {
        return Optional.ofNullable(authority).map(authority -> authority.contains(ROLE_PREFIX) ? authority : ROLE_PREFIX + authority).orElse(null);
    }
}
