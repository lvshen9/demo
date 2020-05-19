#### Lvshen项目-Demo

#### 模块一：自定义注解`com.lvshen.demo.annotation`

注解定义

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NeedSetValue {
    Class<?> beanClass();
    String param();
    String method();
    String targetFiled();

}
```

切面编程实现注解功能

```java
@Component
@Aspect
public class SetFieldValueAspect {

    @Autowired
    BeanUtil beanUtil;

    @Around("@annotation(com.lvshen.demo.annotation.NeedSetValueField)")
    public Object doSetFieldValue(ProceedingJoinPoint point) throws Throwable {
        Object o = point.proceed();

        if (o instanceof Collection) {
            this.beanUtil.setFieldValueForCol(((Collection) o));
        } else {
            //返回结果不是集合的逻辑
        }
        return o;
    }
}
```

使用注解

```java
@NeedSetValueField
public List<Member> listMemberVO(List<Integer> codes) {
    List<Member> members = Lists.newArrayList();
    for (Integer code : codes) {
        Member memberByCode = getMemberByCode(code);
        members.add(memberByCode);
    }
    return members;
}
```

#### 项目二：算法

收录了阿里面试题`com.lvshen.demo.arithmetic.alibabatest`；JDK的队列`com.lvshen.demo.arithmetic.deque`；

```java
@Test
public void test1() {
    Deque<String> arrayDeque = new ArrayDeque<>();
    //arrayDeque.addLast("t1");
    arrayDeque.addFirst("t2");
    arrayDeque.addFirst("t3");
    arrayDeque.addFirst("t4");
    arrayDeque.addFirst("t5");
    //arrayDeque.addLast("t6");

    log.info(arrayDeque.toString());
    //String pop = arrayDeque.pop();
    String removeLast = arrayDeque.removeLast();
    log.info(arrayDeque.toString());
    log.info(removeLast);
}
```

抽奖算法`com.lvshen.demo.arithmetic.lottery`；

最大公约数与最小公倍数算法`com.lvshen.demo.arithmetic.maxCommon`；

队列使用`com.lvshen.demo.arithmetic.queue`；

搜索算法【广度优先与深度优先】`com.lvshen.demo.arithmetic.search`；

Redis长链转短链算法`com.lvshen.demo.arithmetic.shorturl`；

雪花算法`com.lvshen.demo.arithmetic.snowflake`；

排序算法`com.lvshen.demo.arithmetic.sort`；

```Java
@Test
public void test1() {
   // 1.插入排序
   // printArrays(insertionSort(array));
   // 2.冒泡排序
   // printArrays(bubbleSort(array));
   // 3.选择排序
   // printArrays(selectionSort(array));
   // 4.希尔排序
   // printArrays(shellSort(array));
   // 5.快速排序
   printArrays(quickSort(array,0,4));
   /*// 6.归并排序
   printArrays(mergeSort(array));
   // 7.计数排序
   printArrays(countingSort(array));
   // 8.堆排序
   printArrays(heapSort(array));*/
   // 9.桶排序
   // printLists(bucketSort(Ints.asList(array), 5));
   //10.基数排序
   // printArrays(radixSort(array));
}
```

栈使用`com.lvshen.demo.arithmetic.stack`；

订单编号生成算法`com.lvshen.demo.arithmetic.tradno`；

#### 项目三：幂等注解

幂等注解`com.lvshen.demo.autoidempotent`;

```java
@AutoIdempotent
@PostMapping("/test/Idempotence")
public ServerResponse testIdempotence() {
    return testService.testIdempotence();
}
```

#### 项目四：Java基础

BigDecimal使用`com.lvshen.demo.bigdecimal`；

CAS使用：`com.lvshen.demo.cas`；

volatile使用：`com.lvshen.demo.concurrent`；

日期处理：`com.lvshen.demo.date`；

instanceof使用：`com.lvshen.demo.extendsTest`；

Integer使用：`com.lvshen.demo.integer`；

地址传递与值传递：`com.lvshen.demo.javafoundation`；

SecureRandom使用：`com.lvshen.demo.securerandom`；

StringBuilder使用：`com.lvshen.demo.stringbuildertest`；

#### 项目五：设计模式`com.lvshen.demo.design`

抽象工厂，适配器，桥接，建造者，组合，装饰者，外观，工厂，原型，代理，单列....

#### 项目六：Zookeeper分布式锁`com.lvshen.demo.distributelock`

#### 项目七：excel导出`com.lvshen.demo.export`

#### 项目八：Guava使用`com.lvshen.demo.guava.study`

#### 项目九：kafka使用`com.lvshen.demo.kafka`

#### 项目十：spring-websocket使用`com.lvshen.demo.kafka`；

#### 项目十一：手写Lock  `com.lvshen.demo.lock`；

#### 项目十二：mybatis开发 `com.lvshen.demo.member`；

#### 项目十三：redis开发 `com.lvshen.demo.redis`；

缓存注解，延迟队列，分布式锁，限流注解，可重入锁，分布式session，发布订阅

#### 项目十四：NIO demo `com.lvshen.demo.nio`；

#### 项目十五：Postfix Completion 使用 `com.lvshen.demo.postfixcompletion`；

#### 项目十六：手写Spring启动流程 `com.lvshen.demo.spring`；

#### 项目十七： SpringCloud Alibaba使用 `com.lvshen.demo.springcloudalibaba`；

#### 项目十八：面试题 `com.lvshen.demo.test`；

#### 项目十九：Thread多线程`com.lvshen.demo.test`；

AQS，线程状态，手写future类，LockSupport使用

#### 项目二十： 本地线程 `com.lvshen.demo.threadlocal`；

