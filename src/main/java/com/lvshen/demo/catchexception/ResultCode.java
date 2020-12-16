package com.lvshen.demo.catchexception;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-12-16 10:25
 * @since JDK 1.8
 */
public enum ResultCode {
    /** 成功 */
    SUCCESS(200),

    /** 失败 */
    FAIL(400),

    /** token无效 */
    UNAUTHORIZED(401),

    /** 请求的接口不出存在 */
    NOT_FOUND(404),

    /** 接口服务器内部错误  */
    INTERNAL_SERVER_ERROR(500),

    /**参数错误*/
    PARAMETER_ERR(600);

    private int code;

    ResultCode(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
