--[[
    提交申请决定
]] --

local userId = KEYS[1]
local applicantId = KEYS[2]
local prefix = KEYS[3]

local decision = ARGV[1]
local updateTime = ARGV[2]

local userIdWithPrefix = prefix .. userId
local applicantIdWithPrefix = prefix .. applicantId


-- 申请人在对方列表中的信息
local applicantInsideToUserStr = redis.call("hget", userIdWithPrefix, tonumber(applicantId))
-- 被申请人在对方列表中的信息
local toUserInsideApplicantStr = redis.call("hget", applicantIdWithPrefix, tonumber(userId))

local applicantInsideToUserInfo;
if applicantInsideToUserStr == false then
    applicantInsideToUserInfo = nil
else
    applicantInsideToUserInfo = cjson.decode(applicantInsideToUserStr)
end

local toUserInsideApplicantInfo;
if toUserInsideApplicantStr == false then
    toUserInsideApplicantInfo = nil
else
    toUserInsideApplicantInfo = cjson.decode(toUserInsideApplicantStr)
end


-- 申请者未向 toUser 申请过好友
if (applicantInsideToUserInfo == nil) then
    redis.log(redis.LOG_NOTICE, "applicantId: " .. applicantId .. " is no exist in userId: " .. userId)
    return -1
end

-- 已是好友
if applicantInsideToUserInfo.type == 1 then
    return 0
end

if decision == "true" then
    -- 更新自身关于申请者的状态
    applicantInsideToUserInfo.type = 1
    applicantInsideToUserInfo.updateTime = updateTime
    redis.call("hset", userIdWithPrefix, tonumber(applicantId), cjson.encode(applicantInsideToUserInfo))

    if (toUserInsideApplicantInfo ~= nil) then
        -- 如果 toUser 曾经向申请者发送过好友申请, 变更状态后则返回 2
        toUserInsideApplicantInfo.type = 1
        toUserInsideApplicantInfo.updateTime = updateTime
        redis.call("hset", applicantIdWithPrefix, tonumber(userId), cjson.encode(toUserInsideApplicantInfo))
        return 2

    else
        -- 否则,向申请者好友列表中添加自身信息
        applicantInsideToUserInfo.userId = tonumber(userId)
        redis.call("hset", applicantIdWithPrefix, tonumber(userId), cjson.encode(applicantInsideToUserInfo))
        return 1
    end
else
    redis.call("hdel", userIdWithPrefix, tonumber(applicantId))
    return 1
end




