package com.service;

import com.bean.FriendInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author lyl
 * @date 2020/9/21
 */
@Service
public class FriendService {
    @Autowired
    private RedisTemplate<String, Object> jacksonRedisTemplate;

    /**
     * <h2>好友申请</h2>
     *
     * @param userId      用户id
     * @param applicantId 申请人id
     * @return 如果是陌生人/没有好友申请记录, 添加申请后返回 1  <br>
     * 如果存在好友申请, 返回 0             <br>
     * 如果已是好友,返回 2                  <br>
     */
    public long applyForFriend(long userId, long applicantId) {
        return executeLua(Long.class, "lua/applyForFriend.lua",
                Collections.singletonList(String.valueOf(userId)),
                FriendInfo.createApplyingFriendInfo(applicantId));
    }


    /**
     * <h2>提交申请决定</h2>
     *
     * @param userId      用户id
     * @param applicantId 申请人id
     * @param isAgree     是否同意
     * @return 操作成功, 返回 1 <br>
     * 不存在申请记录,返回 -1
     */
    public long submitApplicationDecision(long userId, long applicantId, boolean isAgree) {
        List<String> keys = new ArrayList<>(2);
        keys.add(String.valueOf(userId));
        keys.add(String.valueOf(applicantId));
        return executeLua(Long.class, "lua/submitApplicationDecision.lua",
                keys, isAgree, System.currentTimeMillis());
    }

    /**
     * 执行脚本
     *
     * @param retClass      Should be one of Long, Boolean, List, or deserialized value type
     * @param luaScriptPath lua 脚本路径
     * @param keys          对应 lua 中的 KEYS, 下标从 1 开始
     * @param args          对应 lua 中的 ARGV, 下标从 1 开始
     * @param <R>           Should be one of Long, Boolean, List, or deserialized value type
     * @return 脚本返回值
     */
    public <R> R executeLua(Class<R> retClass, String luaScriptPath, List<String> keys, Object... args) {
        DefaultRedisScript<R> luaScript = new DefaultRedisScript<>();
        luaScript.setResultType(retClass);
        luaScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(luaScriptPath)));
        return jacksonRedisTemplate.execute(luaScript, keys, args);
    }
}
