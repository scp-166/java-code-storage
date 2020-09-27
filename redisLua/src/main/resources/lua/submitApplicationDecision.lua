--[[
    提交申请决定
]] --

local userId = KEYS[1]
local applyUserId = KEYS[2]
local decision = ARGV[1]
local updateTime = ARGV[2]

userId = tonumber(userId)
applyUserId = tonumber(applyUserId)

if (redis.call("hexists", userId, applyUserId) == 0) then
    return -1
end

--[[
    1. 同意,则在自身更新好友状态,而在对方好友列表添加自身好友信息
    2. 拒绝,则删除自身关于该申请的信息
]] --
if decision == "true" then
    local info = cjson.decode(redis.call("hget", userId, applyUserId));
    info.type = 1
    info.updateTime = updateTime;
    redis.call("hset", userId, applyUserId, cjson.encode(info))

    info.userId = userId
    redis.call("hsetnx", applyUserId, userId, cjson.encode(info))
else
    redis.call("hdel", userId, applyUserId)
end

return 1


