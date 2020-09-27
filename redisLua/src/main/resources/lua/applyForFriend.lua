--[[
    好友申请
]] --

local userId = KEYS[1]
local addFriendStr = ARGV[1]
local addFriend = cjson.decode(addFriendStr)

userId = tonumber(userId)

local infoStr = redis.call("hget", userId, addFriend.userId)

local info;
-- redis 如果返回 null, lua 使用 false 接收
if infoStr == false then
    info = nil
else
    info = cjson.decode(infoStr)
end


local function isStanger()
    return info == nil or info.type == 2
end

local function isAlreadyApply()
    return info ~= nil and info.type == 0;
end

local function isFriend()
    return info ~= nil and info.type == 1
end

-- 1. 如果是陌生人/没有好友申请记录, 设置且返回 1
if isStanger() then
    return redis.call("hsetnx", userId, addFriend.userId, addFriendStr)
end

-- 2. 如果存在好友申请, 返回 0
if isAlreadyApply() then
    return 0
end

-- 3. 如果已是好友,返回 2
if isFriend() then
    return 2
end



