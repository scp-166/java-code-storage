package com.hpw.data;

import java.util.Arrays;

public class NetResponse extends NetMessage {
    @Override
    public String toString() {
        return "NetResponse{" +
                "userId=" + userId +
                ", cmd=" + cmd +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
