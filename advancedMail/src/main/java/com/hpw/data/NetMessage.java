package com.hpw.data;

import java.util.Arrays;

public class NetMessage {
    protected long userId;
    protected int cmd;
    protected byte[] data;

    public static NetResponse convert2Response(NetMessage request) {
        NetResponse response = new NetResponse();
        response.setCmd(request.getCmd());
        response.setData(request.getData());
        response.setUserId(request.getUserId());
        return response;
    }

    public static NetRequest convert2Request(NetMessage response) {
        NetRequest request = new NetRequest();
        request.setCmd(response.getCmd());
        request.setData(response.getData());
        request.setUserId(response.getUserId());
        return request;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "NetMessage{" +
                "userId=" + userId +
                ", cmd=" + cmd +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
