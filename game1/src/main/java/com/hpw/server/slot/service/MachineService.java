package com.hpw.server.slot.service;

import com.alibaba.fastjson.JSON;
import com.hpw.server.slot.bean.XiaoyaogeMachine;
import com.hpw.server.slot.constant.XiaoyaogeConstant;
import com.hpw.server.slot.mapper.XiaoyaogeMachineMapper;
import com.hpw.server.slot.util.RollerGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lyl
 * @date 2020/8/27
 */
@Service
public class MachineService {
    @Autowired
    private XiaoyaogeMachineMapper machineMapper;


    public int buildMachine(int[] rowNum) {
        if (rowNum.length > XiaoyaogeConstant.COLUMN_SIZE) {
            throw new RuntimeException("轴长度不能超过" + XiaoyaogeConstant.COLUMN_SIZE);
        }

        List<List<Integer>> rollerList = RollerGenerator.buildRoller(rowNum);
        String rollerStr = JSON.toJSONString(rollerList);

        XiaoyaogeMachine machine = new XiaoyaogeMachine();
        machine.setRollerStr(rollerStr);

        machineMapper.insert(machine);

        return machine.getMachineId();
    }

    public XiaoyaogeMachine getMachineInfo(int id) {
        return machineMapper.selectById(id);
    }

}
