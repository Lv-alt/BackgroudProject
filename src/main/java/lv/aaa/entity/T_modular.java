package lv.aaa.entity;

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/*
*   模块（功能）表
* */
@Data
public class T_modular {
    private Integer m_id;
    private String m_name;
    private String m_url;
    private Integer m_lev;
    private Integer m_pid;
    private String m_ename;
    private List<T_modular> modulars;

}