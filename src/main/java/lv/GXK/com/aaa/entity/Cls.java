package lv.GXK.com.aaa.entity;

/**
 * @Auther:GXK
 * @data: 2021/01/08/14:45
 */
/*班级实体类*/
public class Cls {
    /*班级信息表*/
    private Integer c_id;
    private String c_name;
    /*角色字段*/
    private Integer r_id;
    private String r_name;

    public Integer getR_id() {
        return r_id;
    }

    public void setR_id(Integer r_id) {
        this.r_id = r_id;
    }

    public String getR_name() {
        return r_name;
    }

    public void setR_name(String r_name) {
        this.r_name = r_name;
    }

    /*身份字段*/
    private Integer u_id;
    private String u_username;
    private Integer u_fkRole;
    private Integer u_password;
    private Integer u_state;
    /*商品信息字段*/
    private Integer gt_id;
    private String gt_name;
    private Integer gt_price;
    private Integer gt_fkGoodsType;
    private String gt_generate;
    private Integer gt_fkuser;
    private Integer gt_state;
    private String gt_introduce;
    private Integer gtype_id;
    private String gtype_name;

    /*添加图像*/
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getGt_id() {
        return gt_id;
    }

    public void setGt_id(Integer gt_id) {
        this.gt_id = gt_id;
    }

    public String getGt_name() {
        return gt_name;
    }

    public void setGt_name(String gt_name) {
        this.gt_name = gt_name;
    }

    public Integer getGt_price() {
        return gt_price;
    }

    public void setGt_price(Integer gt_price) {
        this.gt_price = gt_price;
    }

    public Integer getGt_fkGoodsType() {
        return gt_fkGoodsType;
    }

    public void setGt_fkGoodsType(Integer gt_fkGoodsType) {
        this.gt_fkGoodsType = gt_fkGoodsType;
    }

    public String getGt_generate() {
        return gt_generate;
    }

    public void setGt_generate(String gt_generate) {
        this.gt_generate = gt_generate;
    }

    public Integer getGt_fkuser() {
        return gt_fkuser;
    }

    public void setGt_fkuser(Integer gt_fkuser) {
        this.gt_fkuser = gt_fkuser;
    }

    public Integer getGt_state() {
        return gt_state;
    }

    public void setGt_state(Integer gt_state) {
        this.gt_state = gt_state;
    }

    public String getGt_introduce() {
        return gt_introduce;
    }

    public void setGt_introduce(String gt_introduce) {
        this.gt_introduce = gt_introduce;
    }

    public Integer getGtype_id() {
        return gtype_id;
    }

    public void setGtype_id(Integer gtype_id) {
        this.gtype_id = gtype_id;
    }

    public String getGtype_name() {
        return gtype_name;
    }

    public void setGtype_name(String gtype_name) {
        this.gtype_name = gtype_name;
    }

    public Integer getU_password() {
        return u_password;
    }

    public Integer getU_id() {
        return u_id;
    }

    public void setU_id(Integer u_id) {
        this.u_id = u_id;
    }

    public void setU_password(Integer u_password) {
        this.u_password = u_password;
    }

    public Integer getU_state() {
        return u_state;
    }

    public void setU_state(Integer u_state) {
        this.u_state = u_state;
    }

    private Integer t_connectClass;
    private Integer t_connectUser;



    public Integer getT_connectUser() {
        return t_connectUser;
    }

    public void setT_connectUser(Integer t_connectUser) {
        this.t_connectUser = t_connectUser;
    }

    public Integer getT_connectClass() {
        return t_connectClass;
    }

    public void setT_connectClass(Integer t_connectClass) {
        this.t_connectClass = t_connectClass;
    }

    public String getU_username() {
        return u_username;
    }

    public void setU_username(String u_username) {
        this.u_username = u_username;
    }

    public Integer getU_fkRole() {
        return u_fkRole;
    }

    public void setU_fkRole(Integer u_fkRole) {
        this.u_fkRole = u_fkRole;
    }

    public Integer getC_id() {
        return c_id;
    }

    public void setC_id(Integer c_id) {
        this.c_id = c_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }
}
