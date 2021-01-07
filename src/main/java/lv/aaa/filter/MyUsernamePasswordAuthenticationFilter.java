package lv.aaa.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Map;

/**
 *  自定义 认证过滤器
 *
 *  修改原过滤器中获取用户名和密码的方法
 */

public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    //ThreadLocal  线程本地
    private  ThreadLocal<Map<String,String>> threadLocal =  new ThreadLocal();


    @Override
    protected String obtainPassword(HttpServletRequest request) {
       String password =  super.obtainPassword(request);
       if(password==null){
           Map<String, String> bodyParams = this.getBodyParams(request);
           password =bodyParams.get(this.getPasswordParameter());
       }

       // threadLocal.remove();
        return password;
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        String username = super.obtainUsername(request);
        if(username==null){
            Map<String, String> bodyParams = this.getBodyParams(request);
            username =bodyParams.get(this.getUsernameParameter());
        }

        return username;
    }

    /**
     * 获取body参数  body中的参数只能获取一次
     * @param request
     * @return
     */
    private  Map<String,String> getBodyParams(HttpServletRequest request) {
        Thread thread = Thread.currentThread();
        Map<String, String> bodyParams = threadLocal.get();
        if(bodyParams==null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try (InputStream is = request.getInputStream()) {
                bodyParams = objectMapper.readValue(is, Map.class);

                threadLocal.set(bodyParams);
            } catch (Exception e) {
                e.printStackTrace();
            }
         //   if(bodyParams==null) bodyParams = new HashMap();
        }
        System.out.println(Thread.currentThread());
        return bodyParams;
    }



}
