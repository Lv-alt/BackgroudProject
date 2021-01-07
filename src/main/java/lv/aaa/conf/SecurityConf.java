package lv.aaa.conf;

import com.google.gson.Gson;
import lv.aaa.entity.T_user;
import lv.aaa.filter.JwtAuthorizationTokenFilter;
import lv.aaa.filter.MyUsernamePasswordAuthenticationFilter;
import lv.aaa.serviceimpl.UserDetailsServiceImpl;
import lv.aaa.util.CommonResult;
import lv.aaa.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConf extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    JwtAuthorizationTokenFilter wwtAuthorizationTokenFilter;

    // 配置使用自己的数据库账号密码验证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    protected MyUsernamePasswordAuthenticationFilter getMyUsernamePasswordAuthenticationFilter() throws Exception {
        MyUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter = new MyUsernamePasswordAuthenticationFilter();
        myUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

                T_user user = (T_user)authentication.getPrincipal();
                String token = jwtTokenUtil.generateToken(user);

                response.setCharacterEncoding("UTF-8");
                response.setHeader("Content-Type","application/json;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                CommonResult defaultMsg = new CommonResult();
                Map<String,Object> map = new HashMap<>();
                map.put("token",token);
                map.put("username",user.getUsername());
                defaultMsg.setData(map);
                String json =  new Gson().toJson(defaultMsg);
                writer.print(json);
                writer.close();
            }
        });
        myUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                response.setCharacterEncoding("UTF-8");
                response.setHeader("Content-Type","application/json;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                CommonResult defaultMsg = new CommonResult();
                defaultMsg.setMessage("用户名或者密码错误");
                String json =  new Gson().toJson(defaultMsg);
                writer.print(json);
                writer.close();
            }
        });

        myUsernamePasswordAuthenticationFilter.setAuthenticationManager(super.authenticationManager());
        return myUsernamePasswordAuthenticationFilter ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
// 把wwtAuthorizationTokenFilter过滤器加到UsernamePasswordAuthenticationFilter过滤器之前
        http.addFilterAt(wwtAuthorizationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(this.getMyUsernamePasswordAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class);
        //禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //把现在我们自己生成的MyUsernamePasswordAuthenticationFilter替代原先的过滤器
        http.authorizeRequests().anyRequest().authenticated().and().formLogin();
        //http.addFilterAt(myUsernamePasswordAuthenticationFilter,JwtAuthorizationTokenFilter.class);
        //如果用户没有登陆不往登陆页面跳了，返回json数据
        http.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
            public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                httpServletResponse.setCharacterEncoding("utf-8");
                httpServletResponse.setHeader("Content-type","application/json;charset=utf-8");
                CommonResult<String> commonResult = new CommonResult<String>();
                commonResult.setCode(0);
                commonResult.setData("No");
                commonResult.setMessage("还没有登陆");
                String result = new Gson().toJson(commonResult);
                PrintWriter writer = httpServletResponse.getWriter();
                writer.print(result);
                writer.close();
            }
        });
        //关闭防范跨域请求伪造的代码
        http.csrf().disable();
        http.cors();
    }
}