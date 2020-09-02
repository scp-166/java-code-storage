package com.hpw.data;

import java.util.Arrays;

public class NetRequest extends NetMessage {
    @Override
    public String toString() {
        return "NetRequest{" +
                "userId=" + userId +
                ", cmd=" + cmd +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
