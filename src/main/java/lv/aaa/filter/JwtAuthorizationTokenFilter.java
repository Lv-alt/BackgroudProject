package lv.aaa.filter;


import lombok.extern.slf4j.Slf4j;
import lv.aaa.serviceimpl.UserDetailsServiceImpl;
import lv.aaa.util.JwtTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {

    @Resource
    private UserDetailsServiceImpl userDetailService;

    @Resource
    private JwtTokenUtil jwtTokenUtil;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        //获取请求头名称叫Authentication的请求头 的值
        String token=request.getHeader("Lv");
        if (token != null && StringUtils.isNoneEmpty(token)) {
            //获取token中的用户名
            String username=jwtTokenUtil.getUsernameFromToken(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails=userDetailService.loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(token, userDetails)) {
                    //校验通过 把 UsernamePasswordAuthenticationToken对象放置到SecurityContextHolder.getContext().setAuthentication
                    //属性中
                    UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

        }
        chain.doFilter(request,response);
    }
}