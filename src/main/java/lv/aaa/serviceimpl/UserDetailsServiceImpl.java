package lv.aaa.serviceimpl;

import lv.aaa.dao.IModularDao;
import lv.aaa.dao.IUserDao;
import lv.aaa.entity.T_modular;
import lv.aaa.entity.T_user;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    IUserDao userDao;

    @Resource
    IModularDao modularDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        T_user user = userDao.getUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        //根据用户id查询出该用户所对应的角色的功能。
        List<T_modular> oneModulars = modularDao.getOneModularByUserId(user.getU_id());
        //遍历添加角色功能aa
        for(T_modular modular:oneModulars){
            SimpleGrantedAuthority sga = new SimpleGrantedAuthority(modular.getM_ename());
            list.add(sga);
        }
        user.setCollection(list);

        return user;
    }
}