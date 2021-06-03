# MiniatureRedis
## Redis问题1  
 redis存在一个问题 就是对于list类型的数据，如果有新增一个同名的String，那么便无法继续操作这个list：lpush l "hdsa" -> set l a -> lrange l 0 -1 这样就会得到一个错误 (error) WRONGTYPE Operation against a key holding the wrong kind of value.

 使用keyPool (key字符串， datatype字符串) 来表示这个对应的是哪个数据类型 keyPool存放着所有类型数据的key，以及key对应的数据类型  storageMap则是(datatype字符串， 实际用于类型存储的hashmap) storageMap 则是存放在所有类型的存储hashmap  通过这种方式来避免不同类型间的覆盖，比如已经有一个string类型的key0 这个就不允许除了string类型以外的数据类型有key0这个关键字，同类型可以进行覆盖。
 采用keypool和storageMap 也可以迅速定位对应key的存储所在，可以不用去遍历检查每一个类型的存储是否有这个key，方便操作。

 之所以分开存储的是因为 如果只用一个hashmap来存数据 那么对于一个已经存在的key，较难判断是否是同类型可以覆盖。