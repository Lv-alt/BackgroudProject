package lv.aaa.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/*
* 用户表
* */
@Data
public class T_user implements UserDetails {
    private Integer u_id;
    private String u_username;
    private String u_password;
    private T_modular t_modular;
    private Integer u_state;
    private Collection<? extends GrantedAuthority> collection;
    private String u_headUrl;
    private T_role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return collection;
    }

    @Override
    public String getPassword() {
        return this.u_password;
    }

    @Override
    public String getUsername() {
        return this.u_username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}