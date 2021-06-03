# MiniatureRedis
## Redis问题1  
 redis存在一个问题 就是对于list类型的数据，如果有新增一个同名的String，那么便无法继续操作这个list：lpush l "hdsa" -> set l a -> lrange l 0 -1 这样就会得到一个错误 (error) WRONGTYPE Operation against a key holding the wrong kind of value.

 使用keyPool (key字符串， datatype字符串) 来表示这个对应的是哪个数据类型  storageMap则是(datatype字符串， 实际存储的hashmap)