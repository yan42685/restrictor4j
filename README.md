# 限流器

## 配置

### 限流器配置

在 classpath:/restrictor/restrictor-config.yaml 写入限流器配置（没找到配置文件就会用默认配置）

默认配置:

```yaml
ruleSourceType: FILE  # 规则来源 可选 FILE，ZOO_KEEPER
algorithmType: FIXED_WINDOW  # 限流算法类型 可选 FIXED_WINDOW, SLIDING_WINDOW, LEAKY_BUCKET, TOKEN_BUCKET
redisConfig:
  address: null
  port: 6379
  maxWaitMillis: 10
  maxTotal: 50
  maxIdle: 50
  minIdle: 20
  testOnBorrow: true
zookeeperConfig:
  address: null
  rulePath: /restrictor/restrictor-rule
```

### 限流规则配置

在 classpath:/restrictor/restrictor-rule.yaml 写入限流规则配置（没找到规则文件也可以正常运行，只是不进行任何限流）

规则示例:

```yaml
unit: HOURS  # 时间单位, 可选 HOURS、MINUTES、SECONDS、MILLISECONDS, 默认 SECONDS
period: 1  # 限流周期包含多少个单位时间, 默认60
clientRules:
  - clientId: aaaa  # 客户端 id, 可以为不同的客户端设置不同的限流规则
    unit: MINUTES  # 限定范围小的配置能够覆盖范围大的配置，优先级如下： api配置 > client配置 > 全局配置
    period: 3
    apiRules:
      - api: /test/1
        limit: 10  # 限流周期内最大可调用次数
      - api: /test/2
        limit: 50
  - clientId: bbbb
    apiRules:
      - api: /test/1
        limit: 100
        unit: SECONDS
        period: 60
      - api: /test/2
        limit: 30
        period: 60
```

## 用法

```java
public class RequestFilter {
    public void beforeRequest(String clientId, String apiUrl) {
        // 单机限流器，计数器在服务器内存中。以后还会实现分布式限流器，计数器在Redis集群中
        Restrictor restictor = new MemoryRestictor();
        if (restictor.tryAcquire(clientId, apiUrl)) {
            // 调用成功后逻辑
        } else {
            // 被限流后的逻辑
        }
    }
}

```