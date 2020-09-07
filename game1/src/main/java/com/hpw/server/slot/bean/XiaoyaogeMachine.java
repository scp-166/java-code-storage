package com.hpw.server.slot.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.*;
import java.util.List;

/**
 * @author lyl
 * @date 2020/8/27
 */
@TableName("g_xiaoyaoge_machine")
public class XiaoyaogeMachine {


    @TableId(value = "machineId", type = IdType.AUTO)
    private Integer machineId;

    @TableField("rollerStr")
    private String rollerStr;


    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    public String getRollerStr() {
        return rollerStr;
    }

    public void setRollerStr(String rollerStr) {
        this.rollerStr = rollerStr;
    }


    @Override
    public String toString() {
        return "XiaoyaogeMachine{" +
                "machineId=" + machineId +
                ", rollerStr='" + rollerStr + '\'' +
                '}';
    }

    /**
     * 将字符串的轴信息转化为 list
     *
     * @return 轴信息 list
     */
    public List<List<Integer>> toRollerList() {
        if (this.rollerStr == null || "".equals(this.rollerStr)) {
            return Collections.emptyList();
        }
        return JSON.parseObject(this.rollerStr, new TypeReference<List<List<Integer>>>() {
        });
    }


}
