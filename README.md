#### [Lvshen项目-Demo[Java技术栈]](https://github.com/lvshen9/demo)

#### 模块一：自定义注解`com.lvshen.demo.annotation`

##### 注解定义

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

##### 切面编程实现注解功能

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

##### 使用注解

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

##### 求根号2的值

收录了阿里面试题`com.lvshen.demo.arithmetic.alibabatest`；

> ```java
> /**
>  * Description:面试题002 已知sqrt (2)约等于1.414，要求不用数学库，求sqrt (2)精确到小数点后10位。
>  *
>  * @author Lvshen
>  * @version 1.0
>  * @date: 2020/4/24 17:40
>  * @since JDK 1.8
>  */
> public class Test1 {
>     private static final double EPSINON = 0.0000000001;
> 
>     public static double sqrt2() {
>         double low = 1.4;
>         double high = 1.5;
> 
>         double mid = (low + high) / 2;
> 
>         while (high - low > EPSINON) {
>             if ((mid * mid > 2)) {
>                 high = mid;
>             } else {
>                 low = mid;
>             }
>             mid = (high + low) / 2;
>         }
>         return mid;
>     }
> 
>     public static void main(String[] args){
>         System.out.println(sqrt2());
>     }
> }
> ```
>

##### 队列

JDK的队列`com.lvshen.demo.arithmetic.deque`；

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

##### 抽奖

抽奖算法`com.lvshen.demo.arithmetic.lottery`；

```java
/**整体思想： 奖品集合 + 概率比例集合 将奖品按集合中顺序概率计算成所占比例区间，放入比例集合。并产生一个随机数   *加入其中，排序。 排序后，随机数落在哪个区间，就表示那个区间的奖品被抽中。
  *返回的随机数在集合中的索引，该索引就是奖品集合中的索引。 比例区间的计算通过概率相加获得。
  */
public static int draw(List<Double> giftProbList) {

   List<Double> sortRateList = new ArrayList<>();

   // 计算概率总和
   Double sumRate = 0D;
   for (Double prob : giftProbList) {
      sumRate += prob;
   }

   if (sumRate != 0) {
      double rate = 0D; // 概率所占比例
      for (Double prob : giftProbList) {
         rate += prob;
         // 构建一个比例区段组成的集合(避免概率和不为1)
         sortRateList.add(rate / sumRate);
      }

      // 随机生成一个随机数，并排序
      //double random = Math.random();
           ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
           double random = threadLocalRandom.nextDouble(0, 1);
      sortRateList.add(random);
      Collections.sort(sortRateList);

      // 返回该随机数在比例集合中的索引
      return sortRateList.indexOf(random);
   }

   return -1;
}
```

##### 最大公约数

最大公约数与最小公倍数算法`com.lvshen.demo.arithmetic.maxCommon`；

```java
/**
    * 欧几里得算法
    * 两个非零整数的最大公约数等于其中较小的那个数和两个数相除余数的最大公约数
    * @param n
    * @param m
    * @return
    */
private int getMaxCommonFromEuclid(int m, int n) {
    log.info("testStart....");
       // m和n的最大公约数 = n 和 m%n的最大公约数
   if (n == 0) {
      return m;
   } else {
      return getMaxCommonFromEuclid(n, m % n);
   }
}

@Test
public void test2() {
    int n = 6;
    int m = 20;

    int maxCommon = getMaxCommonFromEuclid(n, m);
    System.out.println("最大公约数：" + maxCommon);
    System.out.println("最小公倍数：" + n * m / maxCommon);
}
```

##### 队列解密

队列使用`com.lvshen.demo.arithmetic.queue`；

```java
/**
 * Description:算法 新学期开始了，小哈是小哼的新同桌（小哈是个小美女哦~），小哼向小哈询问 QQ 号， 小哈当然不会直接告诉小哼啦，原因嘛你懂的。所以小哈给了小哼一串加密过的数字，同
 * 时小哈也告诉了小哼解密规则。规则是这样的：首先将第 1 个数删除，紧接着将第 2 个数放到这串数的末尾，再将第 3个数删除并将第 4 个数再放到这串数的末尾，再将第 5
 * 个数删除……直到剩下最后一个数，将最后一个数也删除。按照刚才删除的顺序，把这些 删除的数连在一起就是小哈的 QQ 啦。现在你来帮帮小哼吧。小哈给小哼加密过的一串数 是“6 3 1 75 8 9 2 4”。
 */
 
 /**
     * 采用队列方法
     */
	@Test
	public void test2() {
		Queue<Integer> queue = new LinkedList<>();
        Integer[] q = { 6, 3, 1, 7, 5, 8, 9, 2, 4 };
		List<Integer> list = Lists.newArrayList();
        for (int i = 0; i < q.length; i++) {
            queue.offer(q[i]);
        }

		while (queue.element() != null) {
			list.add(queue.poll());
			queue.offer(queue.poll());
            System.out.println(queue);
        }
        System.out.println(list.toString());
	}
```

##### 搜索算法

搜索算法【广度优先与深度优先】`com.lvshen.demo.arithmetic.search`；

```java
public void BFSSearch(String startPoint) {
    queue.add(startPoint);
    status.put(startPoint,false);
    bfsLoop();
}

public void DFSSearch(String startPoint) {
    stack.push(startPoint);
    status.put(startPoint, true);
    dfsLoop();
}

//深度优先
private void dfsLoop() {
    if (stack.isEmpty()) {
        return;
    }
    String stackTopPoint = stack.peek();
    Map<String, List<String>> graphData = initGraphData();
    List<String> neighborPoints = graphData.get(stackTopPoint);
    neighborPoints.stream().forEach(x -> {
        if (status.getOrDefault(x,false)) {
            stack.push(x);
            status.put(x, true);
            dfsLoop();
        }
    });
    String popPoint = stack.pop();
    System.out.println(popPoint);
}

//广度优先
private void bfsLoop() {
    String currentQueueHeader = queue.poll();
    status.put(currentQueueHeader, true);
    System.out.println(currentQueueHeader);

    Map<String, List<String>> graphData = initGraphData();
    List<String> neighborPoints = graphData.get(currentQueueHeader);
    neighborPoints.stream().forEach(x -> {
        if (!status.getOrDefault(x, false)) {
            if (queue.contains(x)) {
                log.info("the point is in queue!");
            }
            queue.add(x);
            status.put(x, false);
        }
    });
    if (!queue.isEmpty()) {
        bfsLoop();
    }
}
```

##### 长链转短链

Redis长链转短链算法`com.lvshen.demo.arithmetic.shorturl`；

```Java
public String getShortUrl(String longUrl, Decimal decimal) {
    String cache = redisTemplate.opsForValue().get(CACHE_KEY_PREFIX + longUrl);
    if (cache != null) {
        log.info("从缓存【{}】中获取到：{}",CACHE_KEY_PREFIX + longUrl,cache);
        return LOCALHOST + toOtherBaseString(Long.valueOf(cache), decimal.x);
    }

    Long num = redisTemplate.opsForValue().increment(SHORT_URL_KEY);
    redisTemplate.opsForValue().set(SHORT_LONG_PREFIX + num, longUrl);

    redisTemplate.opsForValue().set(CACHE_KEY_PREFIX + longUrl, String.valueOf(num), CACHE_SECONDS, TimeUnit.SECONDS);
    return LOCALHOST + toOtherBaseString(num, decimal.x);
}

@Test
    public void testShortUrl() {
        String url = "www.google.com";

        String shortUrl = shortUrlUtil.getShortUrl(url, ShortUrlUtil.Decimal.D64);
        System.out.println("短链：" + shortUrl);
    }
```

##### 雪花算法

雪花算法`com.lvshen.demo.arithmetic.snowflake`；

```Java
public synchronized long next() {

    long nowTimestamp = timeGen();
    if (nowTimestamp < lastTimestamp) {
        throw new IllegalStateException("系统时钟有误");
    }
    // 当前时间戳跟上次的时间戳一样，毫秒内生成序列
    if (lastTimestamp == nowTimestamp) {

        /* 生成序列的掩码，127 (1111111=0x7f=127) */
        long sequenceMask = 127L;
        sequence = (sequence + 1) & sequenceMask;
        // 毫秒内序列溢出
        if (sequence == 0) {
            // 阻塞到下一个毫秒,获得新的时间戳
            nowTimestamp = timeGen();
            while (nowTimestamp <= lastTimestamp) {
                nowTimestamp = timeGen();
            }
            lastTimestamp = nowTimestamp;
        }

    } else {
        sequence = 0;
        lastTimestamp = nowTimestamp;
    }
    //Date formatDate = DateTimeUtils.getFormatDate("2027-09-03");
    //nowTimestamp = formatDate.getTime();
    //同一服务器上面的不同应用可能生成同一id
    long id2Long = ((nowTimestamp - baseTimestamp) << TIMESTAMP_LEFT_SHIFT) | workId | sequence;

    return id2Long;
}


@org.junit.Test
    public void test1() throws ParseException {
        String id = new SnowFlakeGenerator().nextId();
        System.out.println("id生成成功：" + id + ",长度：" + id.length());
    }
```

##### 排序算法

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

##### 回文数判断算法

栈使用`com.lvshen.demo.arithmetic.stack`；

```java
//回文数判断
private boolean palindromeTest(String param) {
   if (StringUtils.isBlank(param)) {
      return false;
   }
   Stack<String> stack = new Stack<>();
   char[] chars = param.toCharArray();
   int mid = chars.length / 2;

   for (int i = 0; i < mid; i++) {
      stack.push(String.valueOf(chars[i]));
   }

   for (int i = mid + 1; i <= chars.length - 1; i++) {
      // 栈首获取
      if (!stack.peek().equals(String.valueOf(chars[i]))) {
         break;
      }
      // 出栈
      stack.pop();
   }
   if (stack.size() == 0) {
      return true;
   } else {
      return false;
   }
}


@Test
	public void test() {
		String param = "席主席";
		System.out.println(palindromeTest(param));
	}
```

##### 订单编号生成算法

订单编号生成算法`com.lvshen.demo.arithmetic.tradno`；

```java
public static void main(String[] args) throws UnknownHostException {
   // 1.两位随机数+13位时间戳
   int r1 = (int) (Math.random() * (10));// 产生2个0-9的随机数
   int r2 = (int) (Math.random() * (10));
   long now = System.currentTimeMillis();// 一个13位的时间戳
   String paymentID = String.valueOf(r1) + String.valueOf(r2) + String.valueOf(now);// 订单ID
   System.out.println(paymentID);

   // 2.年月日+用户主键.hashCode()+ip
   InetAddress addrs = InetAddress.getLocalHost();
   System.out.println("Local HostAddress: " + addrs.getHostAddress());
   String hostName = addrs.getHostName();
   System.out.println("Local host name: " + hostName);
   String userId = "admin";

   // 当前时间
   Date date = new Date();
   // 转换格式
   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
   // 格式化
   String time = simpleDateFormat.format(date);
   // ip地址
   String ip = addrs.getHostAddress();
   ip = ip.replace(".", "");

   System.currentTimeMillis(); // 获取毫秒

   String id = time + ip + userId.hashCode() + System.currentTimeMillis();
   System.out.println("生成唯一订单号" + id);
   System.out.println("订单号" + ip + System.currentTimeMillis());
}
```

#### 项目三：幂等注解

幂等注解`com.lvshen.demo.autoidempotent`;

```java
@AutoIdempotent
@PostMapping("/test/Idempotence")
public ServerResponse testIdempotence() {
    return testService.testIdempotence();
}

/**
     * 检验token
     *
     * @param request
     * @return
     */
    @Override
    public boolean checkToken(HttpServletRequest request) throws Exception {

        String token = request.getHeader(HEADER_KEY);
        if (StringUtils.isBlank(token)) {// header中不存在token
            token = request.getParameter(HEADER_KEY);
            if (StringUtils.isBlank(token)) {// parameter中也不存在token
                throw new ServiceException(ResponseCode.ILLEGAL_ARGUMENT.getMsg());
            }
        }

        if (!redisTemplate.hasKey(token)) {
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
        }

        boolean remove = redisTemplate.delete(token);
        if (!remove) {
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
        }
        return true;
    }

@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //被ApiIdempotment标记的扫描
        AutoIdempotent methodAnnotation = method.getAnnotation(AutoIdempotent.class);
        if (methodAnnotation != null) {
            try {
                return tokenService.checkToken(request);// 幂等性校验, 校验通过则放行, 校验失败则抛出异常, 并通过统一异常处理返回友好提示
            } catch (Exception ex) {
                ResultVo failedResult = ResultVo.getFailedResult(101, ((ServiceException) ex).getMsg());
                writeReturnJson(response, JSONObject.toJSONString(failedResult));
                throw ex;
            }
        }
        //必须返回true,否则会被拦截一切请求
        return true;
    }
```

#### 项目四：Java基础

##### BigDecimal

BigDecimal使用`com.lvshen.demo.bigdecimal`；

```Java
@Test
public void test(){
    BigDecimal bigDecimal = new BigDecimal(0.1);
    System.out.println(bigDecimal);

    BigDecimal bigDecimal1 = new BigDecimal(10);
    System.out.println(bigDecimal1);

    BigDecimal bigDecimal2 = new BigDecimal("0.1");
    System.out.println(bigDecimal2);

    BigDecimal bigDecimal3 = BigDecimal.valueOf(0.1);
    System.out.println(bigDecimal3);
}
```

##### CAS

CAS使用：`com.lvshen.demo.cas`；

```Java
public class MyAtomicInteger {
    private volatile int value;

    private static long offset;//偏移地址

    private static Unsafe unsafe;

    static {
        try {
            Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeField.setAccessible(true);
            unsafe = (Unsafe) theUnsafeField.get(null);
            Field field = MyAtomicInteger.class.getDeclaredField("value");
            offset = unsafe.objectFieldOffset(field);//获得偏移地址
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void increment(int num) {
        int tempValue;
        do {
            tempValue = unsafe.getIntVolatile(this, offset);//拿到值
        } while (!unsafe.compareAndSwapInt(this, offset, tempValue, value + num));//CAS自旋
    }

    public int get() {
        return value;
    }
}
```

##### volatile

volatile使用：`com.lvshen.demo.concurrent`；

```Java
public class VolatileVisibilityTest {
    //private static volatile boolean initFlag = false;
    private static boolean initFlag = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            System.out.println("waiting data");
            while (!initFlag) {
                //System.out.println();
                //里面添加锁共享变量就不会缓存
                synchronized (VolatileVisibilityTest.class) {

                }
            }
            System.out.println("========success!!!");
        }).start();


        Thread.sleep(2000);

        new Thread(() -> prepareData()).start();


    }

    private static void prepareData() {
        System.out.println("开始修改initFlag...");
        initFlag = true;
        System.out.println("initFlag修改成功...");
    }
}
```

##### Date

日期处理：`com.lvshen.demo.date`；

```Java
public Date getDate(String dateStr) throws ParseException {
    dateStr = dateStr.replace("Z", " UTC");//是空格+UTC
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//格式化的表达式
    return format.parse(dateStr);
}
```

instanceof使用：`com.lvshen.demo.extendsTest`；

```java
private void testType(Object o) {
   if (o instanceof Person) {
      System.out.println("this is person");
   } else if (o instanceof Student) {
      System.out.println("this is student");
   }

}
```

Integer使用：`com.lvshen.demo.integer`；

```java
public class IntegerTest {

   public static void main(String[] args) {
      // -128--127之间

      Integer i1 = 100;
      Integer i2 = 100;
      if (i1 == i2) {
         System.out.println("i1 == i2");
      } else {
         System.out.println("i1 != i2 ");
      }

      if (i1 < i2) {

        }

      // 大于127
      Integer i3 = 200;
      Integer i4 = 200;
      /*if (i3 == i4) {
         System.out.println("i3 == i4");
      } else {
         System.out.println("i3 != i4 ");   (√)}*/

      /*if (i3.compareTo(i4) == 0) {
            System.out.println("i3 == i4");    (√)
      } else {
            System.out.println("i3 != i4 ");
        }*/

        if (i3.intValue() == i4.intValue()) {
            System.out.println("i3 == i4");
        } else {
            System.out.println("i3 != i4 ");
        }

   }

   @Test
   public void testInteger() {
      String str = "12";
      int parseInt = Integer.parseInt(str);
      Integer valueOf = Integer.valueOf(str);
      System.out.println("parseInt" + parseInt);
      System.out.println("valueOf" + valueOf);
   }
}
```

地址传递与值传递：`com.lvshen.demo.javafoundation`；

```java
/**
 * 基本数据类型：值传递
 */
@Test
public void test() {
   int x = 10;
   int y = 20;
   swap(x, y);

   System.out.println("x[2] = " + x);
   System.out.println("y[2] = " + y);
}

/**
 * 引用数据类型：地址传递（数组）
 */
@Test
public void test2() {
   int[] a = { 10, 20 };
   System.out.println("a[0] :" + a[0] + ", a[1] : " + a[1]);
   swap(a, 0, 1);
   System.out.println("a[0] :" + a[0] + ", a[1] : " + a[1]);
}
```

SecureRandom使用：`com.lvshen.demo.securerandom`；

```java
@Test
public void test2() {
       byte[] bytes = new byte[128];
       SecureRandom secureRandom = new SecureRandom();
       secureRandom.setSeed(System.currentTimeMillis());
       secureRandom.nextBytes(bytes);
   }
```

StringBuilder使用：`com.lvshen.demo.stringbuildertest`；

#### 项目五：设计模式`com.lvshen.demo.design`

抽象工厂，适配器，桥接，建造者，组合，装饰者，外观，工厂，原型，代理，单列....

#### 项目六：Zookeeper分布式锁`com.lvshen.demo.distributelock`

> 见博客文章：[手写Zookeeper分布式锁](https://lvshen9.gitee.io/2020/04/19/1/)

```java
@Override
public void lock() {

    if (!tryLock()) {
        //没获得锁，阻塞自己
        log.info("线程{}没有获得锁，开始阻塞。",Thread.currentThread().getName());
        waitForLock();
        //再次尝试
        lock();
    }

}

@Override
    public boolean tryLock() {  //不会阻塞
        //创建节点
        if (this.currentPath == null) {
            currentPath = this.client.createEphemeralSequential(lockPath + "/", "lock");
        }
        List<String> children = this.client.getChildren(lockPath);
        Collections.sort(children);

        if (currentPath.equals(lockPath + "/" + children.get(0))) {
            return true;
        } else {
            int currentIndex = children.indexOf(currentPath.substring(lockPath.length() + 1));
            beforePath = lockPath + "/" + children.get(currentIndex - 1);
        }

        log.info("锁节点创建成功：{}", lockPath);
        System.out.println(String.format("锁节点创建成功：{%s}", lockPath));
        return false;
    }
    
    private void waitForLock() {
        CountDownLatch count = new CountDownLatch(1);
        IZkDataListener listener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                log.info("收到节点{}被删除了",s);
                System.out.println(String.format("收到节点[%s]被删除了",s));
                count.countDown();
            }
        };

        client.subscribeDataChanges(this.beforePath, listener);

        //自己阻塞自己
        if (this.client.exists(this.beforePath)) {
            try {
                count.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //取消注册
        client.unsubscribeDataChanges(this.beforePath, listener);
    }
```

#### 项目七：excel导出`com.lvshen.demo.export`

```java
public static void exportExcel(String exportPath) {
   XSSFWorkbook workbook = new XSSFWorkbook();
   XSSFSheet sheet = workbook.createSheet("测试");
   for (int i = 0; i < 9; i++) {
      sheet.setColumnWidth(i, 4300);
   }
   setTitleStyle(sheet, workbook);

   setExcelFooterName("test", 0, workbook);
   exportOutPutExcel(exportPath, workbook);

}
```

#### 项目八：Guava使用`com.lvshen.demo.guava.study`

`Optional`，`Collections`，`String`，`Primitives`

#### 项目九：kafka使用`com.lvshen.demo.kafka`

```java
/**
 * 基本数据类型：值传递
 */
@Test
public void test() {
   int x = 10;
   int y = 20;
   swap(x, y);

   System.out.println("x[2] = " + x);
   System.out.println("y[2] = " + y);
}

/**
 * 引用数据类型：地址传递（数组）
 */
@Test
public void test2() {
   int[] a = { 10, 20 };
   System.out.println("a[0] :" + a[0] + ", a[1] : " + a[1]);
   swap(a, 0, 1);
   System.out.println("a[0] :" + a[0] + ", a[1] : " + a[1]);
}
```

#### 项目十：spring-websocket使用`com.lvshen.demo.kafka`；

```java
@Component
@Slf4j
public class KafkaConsumer {
    @KafkaListener(topics = "${spring.kafka.topic}")
    public void listen(ConsumerRecord<?, ?> record) {
        log.info("topic={}, offset={}, message={}", record.topic(), record.offset(), record.value());
    }

}

@Component
@Slf4j
public class KafkaProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Value("${spring.kafka.topic}")
    private String topic;

    /**
     * 发送kafka消息
     *
     * @param jsonString
     */
    public void send(String jsonString) {
        ListenableFuture future = kafkaTemplate.send(topic, jsonString);
        future.addCallback(o -> log.info("kafka消息发送成功：" + jsonString), throwable -> log.error("kafka消息发送失败：" + jsonString));
    }

}

@Test
	public void testDemo() throws InterruptedException {
		log.info("start send");
		kafkaProducer.send("中文测试222");
		log.info("end send");
		// 休眠10秒，为了使监听器有足够的时间监听到topic的数据
		Thread.sleep(10);
	}
```

#### 项目十一：手写Lock  `com.lvshen.demo.lock`；

```java
@Override
public void lock() {
    if (!tryLock()) {
        //抢不到锁的线程放入阻塞队列中排队
        waiters.add(Thread.currentThread());
        while (true) {
            if (tryLock()) {
                waiters.poll();//抢到锁，自己移出队列
                return;
            } else {
                //阻塞
                LockSupport.park();
            }
        }
    }
}

@Override
    public boolean tryLock() {
        if (state.get() == 0) {
            if (state.compareAndSet(0, 1)) {
                ownerThread = Thread.currentThread();
                return true;
            }
            //可重入锁设计
        } else if (ownerThread == Thread.currentThread()) {
            state.set(state.get() + 1);
        }
        return false;
    }
    
    @Override
    public void unlock() {
        if (ownerThread != Thread.currentThread()) {
            throw new RuntimeException("非法调用，当前线程并没有持有该锁");
        }
        if (state.decrementAndGet() == 0) {
            ownerThread = null;
            //通知其他等待抢锁的线程
            Thread waiterThread = waiters.peek();
            if (waiterThread != null) {
                LockSupport.unpark(waiterThread);
            }
        }
    }
```

#### 项目十二：mybatis开发 `com.lvshen.demo.member`；

##### entity

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member implements Serializable {

    private String id;
    private String name;
    private Integer code;

    @Transient
    @NeedSetValue(beanClass = BeanClassTest.class, param = "code", method = "getMemberByCode", targetFiled = "name")
    private String annotationParam;
}
```

##### mapper

```java
@Mapper
public interface MemberMapper {

    List<Member> listByName(String name);
}
```

##### service

```java
@Service
public class MemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Cacheable(value = "member",key = "#name")
    public List<Member> listByName(String name) {
       return memberMapper.listByName(name);
    }
 }
```

##### controller

```java
@RestController
@RequestMapping(value = "/member", method = {RequestMethod.GET, RequestMethod.POST})
public class MemberController {
    @Autowired
    private MemberService memberService;

    @RequestMapping("/listByName")
    @ResponseBody
    public List<Member> listByName(String name) {
        return memberService.listByName(name);
    }

}
```

##### Mapper.xml

```xml
<mapper namespace="com.lvshen.demo.member.mapper.MemberMapper">
    <resultMap id="result" type="com.lvshen.demo.member.entity.Member">
        <result property="name" column="name"/>
        <result property="id" column="id"/>
        <result property="code" column="code"/>
    </resultMap>


    <select id="listByName" resultMap="result">
      SELECT * FROM member where name=#{name}
   </select>
    
</mapper>
```

#### 项目十三：redis开发 `com.lvshen.demo.redis`；

缓存注解，延迟队列，分布式锁，限流注解，可重入锁，分布式session，发布订阅

##### 分布式缓存

```java
@Component
@Aspect
@Slf4j
public class CacheAspect {
    @Autowired
    private RedisTemplate redisTemplate;

    @Pointcut("@annotation(com.lvshen.demo.redis.cache.CustomizeCache)")
    public void cachePointcut() {
    }

    @Around("cachePointcut()")
    public Object doCache(ProceedingJoinPoint point) {
        Object value = null;
        Semaphore semaphore = null;
        MethodSignature signature = (MethodSignature) point.getSignature();

        try {
            //获取方法上注解的类容
            Method method = point.getTarget().getClass().getMethod(signature.getName(), signature.getMethod().getParameterTypes());
            CustomizeCache annotation = method.getAnnotation(CustomizeCache.class);
            String keyEl = annotation.key();
            String prefix = annotation.value();
            long expireTimes = annotation.expireTimes();
            int semaphoreCount = annotation.semaphoreCount();

            //解析SpringEL表达式
            SpelExpressionParser parser = new SpelExpressionParser();
            Expression expression = parser.parseExpression(keyEl);
            StandardEvaluationContext context = new StandardEvaluationContext();

            //添加参数
            Object[] args = point.getArgs();
            DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
            String[] parameterNames = discoverer.getParameterNames(method);
            for (int i = 0; i < parameterNames.length; i++) {
                context.setVariable(parameterNames[i], args[i].toString());
            }

            //解析
            String key = prefix + "::" + expression.getValue(context).toString();

            //判断缓存中是否存在
            value = redisTemplate.opsForValue().get(key);
            if (value != null) {
                log.info("从缓存中读取到值：{}", value);
                return value;
            }

            //自定义组件，如：限流，降级。。。
            //创建限流令牌
            semaphore = new Semaphore(semaphoreCount);
            boolean tryAcquire = semaphore.tryAcquire(3000L, TimeUnit.MILLISECONDS);
            if (!tryAcquire) {
                //log.info("当前线程【{}】获取令牌失败,等带其他线程释放令牌", Thread.currentThread().getName());
                throw new RuntimeException(String.format("当前线程【%s】获取令牌失败,等带其他线程释放令牌", Thread.currentThread().getName()));
            }

            //缓存不存在则执行方法
            value = point.proceed();

            //同步value到缓存
            redisTemplate.opsForValue().set(key, value, expireTimes, TimeUnit.SECONDS);


        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (semaphore == null) {
                return value;
            } else {
                semaphore.release();
            }
        }
        return value;
    }
}
```

##### 分布式session

```java
@RequestMapping(value = "/testSession",method = RequestMethod.GET)
public String testSession(HttpSession session, Model model) {
    List<Member> members = memberService.listMember();
    System.out.println("sessionId------>" + session.getId());
    model.addAttribute("member", JSON.toJSONString(members));
    session.setAttribute("member",JSON.toJSONString(members));
    return "hello world";
}
```

##### 发布与订阅

```java
@Service
public class Publisher {

    @Autowired
    private RedisTemplate redisTemplate;

    public void pushMessage(String topic, RedisMessage message) {
        redisTemplate.convertAndSend(topic,message);
    }
}


@Slf4j
public class UserReceiver extends AbstractReceiver {
    @Override
    public void receiveMessage(Object message) {
        log.info("接收到用户消息：{}", JSON.toJSONString(message));
    }
}

//redis发布订阅 pub/sub
    @Test
    public void pushMessage() {
        UserMessage userMessage = new UserMessage();
        userMessage.setMsgId(UUID.randomUUID().toString().replace("-", ""));
        userMessage.setUserId("1");
        userMessage.setUsername("admin");
        userMessage.setUsername("root");
        userMessage.setCreateStamp(System.currentTimeMillis());
        publisher.pushMessage("user", userMessage);
        GoodsMessage goodsMessage = new GoodsMessage();
        goodsMessage.setMsgId(UUID.randomUUID().toString().replace("-", ""));
        goodsMessage.setGoodsType("苹果");
        goodsMessage.setNumber("十箱");
        goodsMessage.setCreateStamp(System.currentTimeMillis());
        publisher.pushMessage("goods", goodsMessage);
    }
```

##### 延迟队列与限流实现

> [Redis实现延迟队列](https://lvshen9.gitee.io/2020/04/23/1/)
>
> [用Redis实现接口限流](https://lvshen9.gitee.io/2020/04/25/1/)

##### 可重入锁

```java
@Component
public class RedisWithReentrantLock {

    @Autowired
    private RedisTemplate redisTemplate;


    private static final String REDIS_VALUE = "r_lock";


    private ThreadLocal<Map<String, Integer>> lockers = new ThreadLocal<>();

    private boolean _lock(String key) {
        return redisTemplate.opsForValue().setIfAbsent(key, REDIS_VALUE, 20, TimeUnit.SECONDS);
    }

    private void _unlock(String key) {
        redisTemplate.delete(key);
    }

    private Map<String, Integer> currentLockers() {
        Map<String, Integer> refs = lockers.get();
        if (refs != null) {
            return refs;
        }

        lockers.set(new HashMap<>());
        return lockers.get();
    }

    public boolean lock(String key) {
        Map<String, Integer> refs = currentLockers();
        Integer refCnt = refs.get(key);
        if (refCnt != null) {
            refs.put(key, refCnt + 1);
            return true;
        }
        boolean ok = this._lock(key);
        if (!ok) {
            return false;
        }

        refs.put(key, 1);
        return true;
    }

    public boolean unlock(String key) {
        Map<String, Integer> refs = currentLockers();
        Integer refCnt = refs.get(key);
        if (refCnt == null) {
            return false;
        }

        refCnt = refCnt - 1;
        if ((refCnt > 0)) {
            refs.put(key, refCnt);
        } else {
            refs.remove(key);
            this._unlock(key);
        }
        return true;
    }


}
```

#### 项目十四：NIO demo `com.lvshen.demo.nio`；

> 见博客文章：[BIO与NIO与多路复用](https://lvshen9.gitee.io/2020/05/07/1/)

#### 项目十五：Postfix Completion 使用 `com.lvshen.demo.postfixcompletion`；

```java
public static void main(String[] args){

    Student student = new Student();//.var
    Main.student = new Student();// .filed 全局变量
    Student student1 = new Student(); //Student.new
    Student student2 = (Student) new Object(); //new Object().castvar

    if (student == null) {  //student.null

    }

    if (student != null) { //student.notnull

    }

    boolean flag = true;
    if (flag) {   //flag.if

    }

    while (flag) { //flag.while

    }

    System.out.println(flag); //flag.sout
    //return flag;     flag.return

    String[] strs = new String[5];
    for (int i = 0; i < strs.length; i++) { //strs.fori

    }

    for (String str : strs) {  //strs.for

    }

    for (int i = strs.length - 1; i >= 0; i--) { //strs.forr

    }

    try {
        main(new String[]{});  //main.try
    } catch (Exception e) {
        e.printStackTrace();
    }

    List<Member> members =new ArrayList<>(); //Member.list
}
```

#### 项目十六：手写Spring启动流程 `com.lvshen.demo.spring`；

```java
@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //6.调用
        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            resp.getWriter().write("500 Exception Detail " + Arrays.toString(e.getStackTrace()));
        }
    }

@Override
public void init(ServletConfig config) {
    //1.加载配置文件
    doLoadConfig(config.getInitParameter("contextConfigLocation"));

    //2.扫描相关的类
    doScanner(configProperties.getProperty("scanPackage"));

    //3.实例化相关的类
    doInstance();

    //4.完成依赖注入
    doAutowired();

    //5.初始化HandlerMapping
    doInitHandlerMapping();
}
```

#### 项目十七： SpringCloud Alibaba使用 `com.lvshen.demo.springcloudalibaba`；

#### 项目十八：面试题 `com.lvshen.demo.test`；

##### 阿里面试题

```
//评测题目: 三个线程A、B、C，实现一个程序让线程A打印“A”，线程B打印“B”，线程C打印“C”，
//三个线程输出ABCABCABC......ABC，循环10次“ABC”
//30分钟
```

##### 华为面试题

```
二分查找和冒泡排序
```

##### 腾讯面试题

```
/**
 * Description:给出有序数组(非递减)和闭区间, 找出数组中在区间之内的元素起始位置和结束位置
 * 输入：
 * 1. 有序数组[1,1,2,3,4,5,5]
 * 2. 闭区间[-3,3]
 * 输出：[0,3]
 * 解释：在数组中，前4个元素在区间之内，则起始位置为0，结束位置为3
 * 要求：最坏情况时间复杂度小于O(n)
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/27 18:56
 * @since JDK 1.8
 */
```

#### 项目十九：Thread多线程`com.lvshen.demo.test`；

##### AQS

###### `Semaphone`

```java
public class SemaphoreDemo {
    public static void main(String[] args) {
        SemaphoreDemo semaphoreDemo = new SemaphoreDemo();
        int count = 9;    //数量

        //循环屏障
        CyclicBarrier cyclicBarrier = new CyclicBarrier(count);

        Semaphore semaphore = new Semaphore(5);//限制请求数量
        for (int i = 0; i < count; i++) {
            String vipNo = "vip-00" + i;
            new Thread(() -> {
                try {
                    cyclicBarrier.await();
                    //semaphore.acquire();//获取令牌
                    boolean tryAcquire = semaphore.tryAcquire(3000L, TimeUnit.MILLISECONDS);
                    if (!tryAcquire) {
                        System.out.println("获取令牌失败：" + vipNo);
                    }

                    //执行操作逻辑
                    System.out.println("当前线程：" + Thread.currentThread().getName());
                    semaphoreDemo.service(vipNo);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }).start();
        }

    }

    // 限流控制5个线程同时访问
    public void service(String vipNo) throws InterruptedException {
        System.out.println("楼上出来迎接贵宾一位，贵宾编号" + vipNo + ",...");
        Thread.sleep(new Random().nextInt(3000));
        System.out.println("欢送贵宾出门，贵宾编号" + vipNo);
    }

}
```

###### `CountDownLatch`

```java
public class CountDownLatchDemo {
    static final int COUNT = 20;

    static CountDownLatch cdl = new CountDownLatch(COUNT);

    public static void main(String[] args) throws Exception {
        new Thread(new Teacher(cdl)).start();
        Thread.sleep(1);
        for (int i = 0; i < COUNT; i++) {
            new Thread(new Student(i, cdl)).start();
        }
        synchronized (CountDownLatchDemo.class) {
            CountDownLatchDemo.class.wait();
        }
    }

    static class Teacher implements Runnable {

        CountDownLatch cdl;

        Teacher(CountDownLatch cdl) {
            this.cdl = cdl;
        }

        @Override
        public void run() {
            System.out.println("老师发卷子。。。");
            try {
                cdl.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("老师收卷子。。。");
        }

    }

    static class Student implements Runnable {

        CountDownLatch cdl;
        int num;

        Student(int num, CountDownLatch cdl) {
            this.num = num;
            this.cdl = cdl;
        }

        @Override
        public void run() {
            System.out.println(String.format("学生(%s)写卷子。。。",num));
            //doingLongTime();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("学生(%s)写卷子。。。",num));
            cdl.countDown();
        }

    }
}
```

###### `CyclicBarrier`

```java
public class CyclicBarrierDemo {
    static final int COUNT = 5;

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < COUNT; i++) {
            new Thread(new Staff(i, cb)).start();
        }
        synchronized (CyclicBarrierDemo.class) {
            CyclicBarrierDemo.class.wait();
        }
    }

    static CyclicBarrier cb = new CyclicBarrier(COUNT, new Singer());

    static class Singer implements Runnable {

        @Override
        public void run() {
            System.out.println("为大家唱歌。。。");
        }

    }

    static class Staff implements Runnable {

        CyclicBarrier cb;
        int num;

        Staff(int num, CyclicBarrier cb) {
            this.num = num;
            this.cb = cb;
        }

        @SneakyThrows
        @Override
        public void run() {
            System.out.println(String.format("员工(%s)出发。。。", num));
            Thread.sleep(2000);
            System.out.println(String.format("员工(%s)到达地点一。。。", num));
            try {
                cb.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(String.format("员工(%s)再出发。。。", num));
            Thread.sleep(2000);
            //doingLongTime();
            System.out.println(String.format("员工(%s)到达地点二。。。", num));
            try {
                cb.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(String.format("员工(%s)再出发。。。", num));
            Thread.sleep(2000);
            //doingLongTime();
            System.out.println(String.format("员工(%s)到达地点三。。。", num));
            try {
                cb.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(String.format("员工(%s)结束。。。", num));
        }

    }
}
```

##### 线程状态

##### sleep

```
sleep -> TIMED_WAITING
```



##### join

```
t2中执行t1.join(5000L)
t2的状态：TIMED_WAITING
t2中执行t1.join()
t2的状态：WAITING
```

##### synchronized

```
t1抢不到锁的状态：BLOCKED
```

##### wait

```
WATTING || BLOCKING
```

##### park

```
t1 park后的状态：WAITING
t1 unpark后的状态：WAITING
```



##### 手写future类

```java
public class MyFutureTask<T> implements Runnable {

    Callable<T> callable;

    T result;

    volatile String state = "NEW";

    LinkedBlockingQueue<Thread> queue = new LinkedBlockingQueue<Thread>();

    public MyFutureTask(Callable<T> callable) {
        this.callable = callable;
    }

    public T get() {
        if ("END".equals(state)) {
            return result;
        }

        while (!"END".equals(state)) {
            queue.add(Thread.currentThread());
            LockSupport.park();
        }
        return result;
    }

    @Override
    public void run() {

        try {
            result = callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            state = "END";
        }

        Thread th = queue.poll();
        if (queue != null) {
            LockSupport.unpark(th);
            th = queue.poll();
        }
    }
}
```

##### LockSupport使用

```java
public class LockSupportDemo {
    static int i = 0;
    static Thread t1, t2, t3;

    public static void main(String[] args) {
        t1 = new Thread(() -> {
            while (i < 10) {
                System.out.println("t1:" + (++i));
                LockSupport.unpark(t2);     //t2变为非阻塞
                LockSupport.park();
            }
        });
        t2 = new Thread(() -> {
            while (i < 10) {
                System.out.println("   t2:" + (++i));
                LockSupport.unpark(t3);
                LockSupport.park();
            }
        });
        t3 = new Thread(() -> {
            while (i < 10) {
                System.out.println("t3:" + (++i));
                LockSupport.unpark(t1);
                LockSupport.park();
            }
        });

        t1.start();
        t2.start();
        t3.start();

    }
}
```

#### 项目二十： 本地线程 `com.lvshen.demo.threadlocal`；

```java
public class ThreadLocalTest {

   private static ThreadLocal<Student> threadLocal = new ThreadLocal<>();

   @Test
   public void test() {
      Student student1 = new Student(11, "lvshen");
      Student student2 = new Student(22, "niumo");

      threadLocal.set(student1);
      threadLocal.set(student2);

   }

   @Test
   public void test1() {
      printAllSlot(16);
   }

   private static void printAllSlot(int len) {
      System.out.println("************* len = " + len + " ***************");
      for (int i = 1; i <= 64; i++) {
         ThreadLocal<String> local = new ThreadLocal<>();
         int slot = getSlot(local, len);
         System.out.print(slot + " ");
         if (i % len == 0) {
            System.out.println();
         }
      }

   }

   static int getSlot(ThreadLocal<?> t, int len) {
      int hashCode = getHashCode(t);
      return hashCode & (len - 1);
   }

   static int getHashCode(ThreadLocal<?> t) {
      Field field;
      try {
         field = t.getClass().getDeclaredField("threadLocalHashCode");
         field.setAccessible(true);
         return (int) field.get(t);
      } catch (Exception e) {
         e.printStackTrace();
      }
      return 0;
   }
}
```

#### 项目二十一：手写线程池 `com.lvshen.demo.threadpool`；

> [手写线程池](https://lvshen9.gitee.io/2020/04/17/1/)

#### 项目二十二：递归实现树状结构 `com.lvshen.demo.treenode`；

#### 项目二十三：Java8使用 `com.lvshen.demo.yjh.java8`；

> 见博客：[我所用到的Java8](https://lvshen9.gitee.io/2019/08/16/1/)

LongAddr使用

```java
private LongAdder longAdderVal = new LongAdder();
longAdderVal.increment();
```

Lambda表达式，foreach

LocalDate

```Java
@Test
public void testLocalDate() {
   LocalDate localDate = LocalDate.now();
   LocalTime localTime = LocalTime.of(12, 20);
   LocalDateTime localDateTime = LocalDateTime.now();
   OffsetDateTime offsetDateTime = OffsetDateTime.now();
   ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Europe/Paris"));
   System.out.println("program execute Successful!!!");
}
```



#### 项目二十四：Zookeeper监听机制使用 `com.lvshen.demo.zookeeperdemo`；

```java
@Slf4j
public class WatcherApi implements Watcher {
    @Override
    public void process(WatchedEvent watchedEvent) {
        log.info("【Watcher监听事件】={}",watchedEvent.getState());
        log.info("【监听路径为】={}",watchedEvent.getPath());
        log.info("【监听的类型为】={}",watchedEvent.getType()); //  三种监听类型： 创建，删除，更新
    }
}

/**
     * 测试方法  初始化
     */
    @PostConstruct
    public void init() {
        String path = "/test/lvshen";
        log.info("【执行初始化测试方法。。。。。。。。。。。。】");
        //createNode(path, "测试");
        String value = getData(path, new WatcherApi());
        log.info("【执行初始化测试方法getData返回值。。。。。。。。。。。。】={}", value);

        // 删除节点出发 监听事件
        //deleteNode(path);

    }
    
    @Bean(name = "zkClient")
    public ZooKeeper zkClient(){
        ZooKeeper zooKeeper=null;
        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            //连接成功后，会回调watcher监听，此连接操作是异步的，执行完new语句后，直接调用后续代码
            //  可指定多台服务地址 127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183
            zooKeeper = new ZooKeeper(connectString, timeout, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if(Event.KeeperState.SyncConnected==event.getState()){
                        //如果收到了服务端的响应事件,连接成功
                        countDownLatch.countDown();
                    }
                }
            });
            countDownLatch.await();
            log.info("【初始化ZooKeeper连接状态....】={}",zooKeeper.getState());

        }catch (Exception e){
            log.error("初始化ZooKeeper连接异常....】={}",e);
        }
        return  zooKeeper;
    }
```

#### 项目二十五：测试类 `test.java.com.lvshen.demo`；