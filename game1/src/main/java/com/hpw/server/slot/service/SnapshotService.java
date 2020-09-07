package com.hpw.server.slot.service;


import com.hpw.server.slot.bean.XiaoyaogeMachine;
import com.hpw.server.slot.bean.XiaoyaogeSnapshot;
import com.hpw.server.slot.bean.XiaoyaogeSnapshotResultLog;
import com.hpw.server.slot.mapper.XiaoyaogeSnapshotMapper;
import com.hpw.server.slot.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * @author lyl
 * @date 2020/8/27
 */
@Service
public class SnapshotService {
    private static final Logger logger = LoggerFactory.getLogger(SnapshotService.class);

    @Autowired
    private XiaoyaogeSnapshotMapper mapper;

    private static List<XiaoyaogeSnapshot> cache = new CopyOnWriteArrayList<>();

    public long buildDefaultWithSeed(XiaoyaogeMachine machineInfo) {
        XiaoyaogeSnapshot snapshot = XiaoyaogeSnapshot.buildSnapshotRet(machineInfo, RandomUtil.nextInt());
        mapper.insert(snapshot);
        return snapshot.getId();
    }

    public XiaoyaogeSnapshot getSnapshotInfo(long id) {
        XiaoyaogeSnapshot xiaoyaogeSnapshot = new XiaoyaogeSnapshot();
        xiaoyaogeSnapshot.setId(id);
        int index = Collections.binarySearch(cache, xiaoyaogeSnapshot, Comparator.comparing(XiaoyaogeSnapshot::getId));
        if (index < 0) {
            XiaoyaogeSnapshot ret = mapper.selectById(id);
            if (ret != null) {
                cache.add(ret);
                return ret;
            } else {
                return null;
            }
        } else {
            return cache.get(index);
        }
    }

    public List<XiaoyaogeSnapshot> getAll() {
        cache.clear();
        List<XiaoyaogeSnapshot> xiaoyaogeSnapshots = mapper.selectList(null);
        cache.addAll(xiaoyaogeSnapshots);
        logger.warn("getAll, 目前 cache: {}", cache);

        return CollectionUtils.isEmpty(xiaoyaogeSnapshots) ? Collections.emptyList() : xiaoyaogeSnapshots;
    }

    public XiaoyaogeSnapshotResultLog getResult(long snapshotId, long singleBet) {
        XiaoyaogeSnapshot snapshotInfo = getSnapshotInfo(snapshotId);
        if (snapshotInfo == null) {
            // log
            logger.warn("getResult, snapshotInfo 不存在, snapshotId: {}", snapshotId );
            return null;
        }

        XiaoyaogeSnapshotResultLog log = new XiaoyaogeSnapshotResultLog();
        log.setSnapshotId(snapshotId);
        log.setPatternScore(snapshotInfo.getMultiplyingPower() * singleBet);
        log.setJackpotScore(RandomUtil.nextInt(0, Integer.MAX_VALUE));
        return log;
    }

}
