package com.lsh.packagelibrary;

/**
 * Author:lsh
 * Version: 1.0
 * Description:
 * Date: 2018/4/26
 */

public class ResultBean {
    private String data;
    private String errmsg;
    private boolean jump;
    private int errno;

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isJump() {
        return jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }
}
