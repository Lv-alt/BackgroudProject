package lv.aaa.util;

/*
* 工具类，用来返回给前台的数据
* */
public class CommonResult<T> {
    private Integer code;//状态，1成功 0失败
    private String message;
    private T data;

    public CommonResult(){}

    public CommonResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}