package com.hpw.server.slot;

import com.alibaba.fastjson.JSON;
import com.hpw.server.slot.bean.*;
import com.hpw.server.slot.constant.XiaoyaogeConstant;
import com.hpw.server.slot.service.MachineService;
import com.hpw.server.slot.service.SnapshotService;
import com.hpw.server.slot.util.Random;
import com.hpw.server.slot.util.RandomUtil;
import com.hpw.server.slot.util.RollerGenerator;
import com.hpw.server.slot.util.SlotConfigCache;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

/**
 * @author lyl
 * @date 2020/8/26
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MainTest {

    @Autowired
    private MachineService machineService;

    @Autowired
    private SnapshotService snapshotService;

    @Before
    public void init() {
        SlotConfigCache.init();
        XiaoyaogeConstant.parse();
    }

    @Test
    public void test() {
        for (int i = 0; i < 5; i++) {
            machineService.buildMachine(new int[]{
                    RandomUtil.nextInt(3, 10),
                    RandomUtil.nextInt(3, 10),
                    RandomUtil.nextInt(3, 10),
                    RandomUtil.nextInt(3, 10),
                    RandomUtil.nextInt(3, 10)

            });
        }
    }

    @Test
    public void test2() {
        for (int i = 27; i < 37; i++) {
            for (int j = 0; j < 5; j++) {
                XiaoyaogeMachine machineInfo = machineService.getMachineInfo(i);
                snapshotService.buildDefaultWithSeed(machineInfo);
            }

        }
    }

    @Test
    public void test3() {
        XiaoyaogeSnapshot snapshotInfo = snapshotService.getSnapshotInfo(13);
        XiaoyaogeMachine machineInfo = machineService.getMachineInfo(17);
        SlotChassis.buildChassis(machineInfo.toRollerList(), new Random(snapshotInfo.getSeed()));

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test4() throws Exception {
        // XiaoyaogeMachine machineInfo = machineService.getMachineInfo(36);
        XiaoyaogeMachine machineInfo = machineService.getMachineInfo(35);
        // for (int i = 0; i < 1000; i++) {
        //     System.out.println(i + ": " +SnapshotRet.buildSnapshotRet(machineInfo, new Random(i), 1, false, 0));
        // }
        // System.out.println(JSON.toJSONString(XiaoyaogeSnapshot.buildSnapshotRet(machineInfo, new Random(887), 1)));
        XiaoyaogeSnapshot snapshot = XiaoyaogeSnapshot.buildSnapshotRet(machineInfo, 300);
        System.out.println(snapshot.getNormalPatternSingleLineList());


        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = LoggerFactory.getLogger(MainTest.class);

    @Test
    public void test5() {
        XiaoyaogeMachine machine = machineService.getMachineInfo(35);
        List<XiaoyaogeSnapshot> all = snapshotService.getAll();
        if (!CollectionUtils.isEmpty(all)) {
            XiaoyaogeSnapshotResultLog result = snapshotService.getResult(all.get(RandomUtil.nextInt(0, all.size())).getId(), 1);
            logger.error(result.toString());

        }
    }

    @Test
    public void test6() {
        XiaoyaogeSnapshot snapshotInfo = snapshotService.getSnapshotInfo(108);
        if (snapshotInfo != null) {
            snapshotInfo.restoreChassis(machineService.getMachineInfo(snapshotInfo.getMachineId()));
            System.out.println(snapshotInfo.getNormalPatternSingleLineList());
        }
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SlotConfigCache.init();
        XiaoyaogeConstant.parse();
        System.out.println(RollerGenerator.buildRoller(new int[]{4, 2, 3, 6, 6}));
    }
}
