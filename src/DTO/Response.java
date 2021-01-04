package DTO;

import java.util.Date;

public class Response {
    String msg;
    IPage data;
    Date serverTime;
    Integer responseCode;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public IPage getData() {
        return data;
    }

    public void setData(IPage data) {
        this.data = data;
    }

    public Date getServerTime() {
        return serverTime;
    }

    public void setServerTime(Date serverTime) {
        this.serverTime = serverTime;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public Response(String msg, IPage data, Date serverTime, Integer responseCode) {
        this.msg = msg;
        this.data = data;
        this.serverTime = serverTime;
        this.responseCode = responseCode;
    }

    @Override
    public String toString() {
        return "Response{" +
                "msg='" + msg + '\'' +
                ", data=" + data +
                ", serverTime=" + serverTime +
                ", responseCode=" + responseCode +
                '}';
    }
}
