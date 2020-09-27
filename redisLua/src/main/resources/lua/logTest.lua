-- 从 1 开始
local key = KEYS[1]
--[[
 这也是注释
]] --

--[[
 变量, 函数不写 local 默认为 global, 即使是在 function 中
]] --
-- 数字只有 double
local number = 0x12;

local text1 = "单引号多引号"
local text2 = [[ 带换行第一行
第二行]]
local text3 = "字符串拼接" .. "拼接~"

-- reids log
redis.log(redis.LOG_NOTICE, tostring(key))
redis.log(redis.LOG_NOTICE, text2)

-- 只有 false 和 nil 为 false, 其他(0 or 空字符串 算是 true)
local false1 = false;
local false2 = nil;

-- key-value
local table1 = {
    -- 数字需要包裹起来
    [1] = "one",
    [2] = "two",
    name = "dahehe"
}
redis.log(redis.LOG_NOTICE, table1.name)

-- 数组其实算是一种 table,只是他的 key 是索引下标(从 1 开始), 所以内容的类型不局限一种
local arr = { "one", "two", 2, function() redis.log(redis.LOG_NOTICE, "数组内匿名函数") end }
-- #arr 是数组长度
for i = 1, #arr, 1 do
    if i ~= 4 then
        redis.log(redis.LOG_NOTICE, tostring(i) .. "result" .. arr[i])
    else
        arr[i]();
    end
end

-- 1. 有闭包概念
-- 2. 参数不写默认为 nil
-- 3. 返回值可以有多个, 类似 py
-- 另外 redis 不支持普通的 global 函数
local function test1(param1)
    return "akarin", 1
end

-- 可以不带参数调用
local name, age = test1();
redis.log(redis.LOG_NOTICE, "name: " .. name .. ", age:" .. age)

local personA = {
    name = "A",
    score = 50
}

-- local ret = personA * 2; -- 直接调用会报错
-- 设置 metaTable 来"重载" 一些 metaMethod(lua 内建)
-- 这里反而是 local 会报错, 需要定义一个 table, 对其进行 global 函数声明, 疑问...
-- 1. 定义一个 table
local personAdd = {}
-- 2. 进行重载
function personAdd.__mul(personA, multipale)
    -- return { name = personA.name, score = tonumber(personA.score * multipale) } -- 后面的 tonumber 是多此一举
return { name = personA.name, score = personA.score * multipale }
end
-- 3. 需要指定 metaTable 影响的 table
setmetatable(personA, personAdd)
-- 4. 可以使用了
local mulPerson = personA * 2;
redis.log(redis.LOG_NOTICE, cjson.encode(mulPerson))
--[[
__add(a, b)                     对应表达式 a + b
__sub(a, b)                     对应表达式 a - b
__mul(a, b)                     对应表达式 a * b
__div(a, b)                     对应表达式 a / b
__mod(a, b)                     对应表达式 a % b
__pow(a, b)                     对应表达式 a ^ b
__unm(a)                        对应表达式 -a
__concat(a, b)                  对应表达式 a .. b
__len(a)                        对应表达式 #a
__eq(a, b)                      对应表达式 a == b
__lt(a, b)                      对应表达式 a < b
__le(a, b)                      对应表达式 a <= b
__index(a, b)                   对应表达式 a.b
__newindex(a, b, c)             对应表达式 a.b = c
__call(a, ...)                  对应表达式 a(...)
]]--
return -1