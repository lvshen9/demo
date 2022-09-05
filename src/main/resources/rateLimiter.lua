--获取key
local key1 = KEYS[1]

--原子自增操作
local val = redis.call('incr', key1)

--返回key的剩余生存时间
local ttl = redis.call('ttl', key1)

--获取java代码中的参数
--(Long) redisTemplate.execute(getRedisScript, keyList, expireTimes, limitTimes);
local expire = ARGV[1]
local times = ARGV[2]

local expireN = tonumber(expire,2)
local timesN = tonumber(times)

--日志打印
redis.log(redis.LOG_DEBUG,"times "..tostring(times))
redis.log(redis.LOG_DEBUG,"expire "..tostring(expire))

redis.log(redis.LOG_NOTICE, "incr "..key1.."  "..val);
--给指定的key设置生存时间
if val == 1 then
    redis.call('expire', key1, expireN)
else
   if ttl == -1 then
       redis.call('expire', key1, expireN)
   end
end

--超过限制次数，返回false
if val > tonumber(times) then
   return '0'
end

return '1'