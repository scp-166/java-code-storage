--[[
    好友申请
]] --

local toId = KEYS[1]
local prefix = KEYS[2]

local applicantStr = ARGV[1]
local applicant = cjson.decode(applicantStr)

local toIdWithPrefix = prefix .. toId
local applicantIdWithPrefix = prefix .. (applicant.userId)

-- 申请人在对方列表中的信息
local applicantInsideToUserStr = redis.call("hget", toIdWithPrefix, applicant.userId)
-- 被申请人在对方列表中的信息
local toUserInsideApplicantStr = redis.call("hget", applicantIdWithPrefix, tonumber(toId))

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


local function isApplicantInToUserList()
    return applicantInsideToUserInfo ~= nil and applicantInsideToUserInfo.type == 0;
end

local function isFriend()
    return (applicantInsideToUserInfo ~= nil and applicantInsideToUserInfo.type == 1)
            and (toUserInsideApplicantInfo ~= nil and toUserInsideApplicantInfo.type == 1)
end

-- 1. 如果存在好友申请, 返回 0
if isApplicantInToUserList() then
    return 0
end

-- 2. 如果非好友 (双方完全无记录 or toUser 没有 applicant 的记录), 设置成功返回1,否则返回0
if not isFriend() then
    return redis.call("hsetnx", toIdWithPrefix, applicant.userId, applicantStr)
end

-- 3. 如果已是好友,返回 2
if isFriend() then
    return 2
end

if applicantInsideToUserStr == false then
    applicantInsideToUserStr = ""
end

if toUserInsideApplicantStr == false then
    toUserInsideApplicantStr = ""
end


-- unknown, 可能有部分数据丢失导致无法判断
redis.log(redis.LOG_NOTICE, "applyForFriend cause unknown: " ..
        "toId: " .. toId .. " ,prefix: " .. prefix .. ", applicantStr: " .. applicantStr .. ", applicantInsideToUserStr: " .. applicantInsideToUserStr .. ", toUserInsideApplicantStr: " .. toUserInsideApplicantStr)

return -2

