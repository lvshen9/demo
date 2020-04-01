###学习笔记
#### @Converted注解使用

```java
  @ApiModelProperty("会员昵称")
  @Converted(feign = ShopMemberApi.class, dependProperty = "memberId", refLabel = "nickName")
  private String nickName;

  @ApiModelProperty("会员头像")
  @Converted(feign = ShopMemberApi.class, dependProperty = "memberId", refLabel = "icon")
  private String icon;

  @ApiModelProperty("会员ID")
  private String memberId;

//----------------
@ApiModelProperty("状态,未开始-FUTURE，进行中-USE，已结束-END，已停用-STOPPED")
private String status;
@ApiModelProperty("状态")
@Converted(dependProperty = "status", type = "marketing_activity_status")
private String statusName;
```

说明：远程调用ShopMemberApi接口，通过memberId自动获取nickName,icon的值。

注意Controller的相应方法上面需要加注解：@FieldConversion

```java
@RequiresPermissions("marketing:redeemMobile:view")
@PostMapping("listMobileRecordPage")
@ApiOperation(value = "发放总量-领取记录", tags = SwaggerTagConstants.PC)
@FieldConversion
public PageInfo<RedeemMobileRecordVO> listMobileRecordPage(@RequestBody PageRequest<RedeemMobileRecordQuery> request) {
   return redeemMobileRecordQueryService.listPage(request);
}
```

集合可以用CollectionUtils.isNotEmpty(redeemActivityPrizes)来判空

```java
//lambda表达式和forEach循环
if (CollectionUtils.isNotEmpty(redeemActivityPrizes)) {
   redeemActivityPrizes.forEach(x -> {
      if (PrizeType.COUPON.equals(x.getPrizeType())) {
          couponTotals.add(getByActivityId(x.getPrizeId()));
      }
      if (PrizeType.PRESENT.equals(x.getPrizeType())) {
          presentVOS.add(presentQueryService.get(x.getPrizeId()));
      }
  });
}
```

#### 集合创建

```java
if (CollectionUtils.isEmpty(memberBriefVOList)) {
    return new PageInfo<>(ImmutableList.of());
}
```

#### Java8集合使用

```java
//filter过滤
List<RedeemActivity> redeemActivitiesOfPrize = activities.stream()
      .filter(x -> activityIdsOfPrize.contains(x.getId())).collect(Collectors.toList());

//使用流降序排序
spokesmanUserCOS.stream()             .sorted(Comparator.comparing(SpokesmanUserCO::getCreateDate).reversed()).collect(Collectors.toList());

//通过昵称获取userId列表
 List<String> userIds = users.stream().collect(() -> new ArrayList<String>(), (list, user) -> list.add(user.getId()), (list1, list2) -> list1.addAll(list2));

 List<String> orderNos = orderDetailCheckoutStatus.stream().map(OrderDetail::getOrderNo).collect(toList());

//Optional.ofNullable使用
List<CommodityActivityRule> excludedCommodityActivityRules = Optional.ofNullable(commodityActivityRules)
				.orElseGet(ArrayList::new).stream()
				// 排除当前活动
				.filter(commodityActivityRule -> !commodityActivityRule.getActivity().getId().equals(activityId))
				.collect(Collectors.toList());
//对象不为null时使用of,对象可能为null也可能不为null时用ofNullable
//orElseGet:对象不为null不会重新创建；orElse：对象为不为null也会重新创建
 Optional.of(marketingConfigRepository.listAll(new MarketingConfig())).orElseGet(ArrayList::new).stream()
				.filter(marketingConfig -> excludedMarketingConfigTypes.contains(marketingConfig.getType()))
				.map(MarketingConfig::getId).collect(Collectors.toList());
//返回对象的时候可以用
Optional.ofNullable(usageRules).orElseGet(ArrayList::new)

//BigDecimal累加
BigDecimal commodityAmount = orderDetails.stream().map(OrderDetail::getCommodityAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

//判断Optional里面是否有值
Optional<GradeThresholdInfoCO> nextOptional = gradeThresholdList.stream().filter(x -> x.getLevel() != null && x.getLevel() == level + 1).findFirst();
        if (nextOptional.isPresent()) {
            return nextOptional.get();
        }

//findFirst()
List<Cookie> targetCookies = Optional.ofNullable(cookies).orElseGet(ArrayList::new).stream()
				.filter(e -> "jsessionid".equalsIgnoreCase(e.getName())).collect(Collectors.toList());

		log.info("Cookie名称为：{}的值为:{}", "jsessionid",
				Optional.ofNullable(targetCookies).orElseGet(ArrayList::new).get(0).getValue());

		Optional<Cookie> first = Optional.ofNullable(cookies).orElseGet(ArrayList::new).stream()
				.filter(e -> "jsessionid".equalsIgnoreCase(e.getName())).findFirst();
		if (first.isPresent()) {
			log.info("Cookie名称为：{}的值为:{}", "jsessionid",
					first.get().getValue());
		}

//流大小比较
Optional<Optional<Integer>> maxLevelOptional = ruleList.stream().map(x -> x.getEarningRuleMap().keySet().stream().max((o1, o2) -> o1.compareTo(o2))).max((o1, o2) -> {
            Integer value1 = o1.isPresent() ?  o1.get() : 0;
            Integer value2 = o2.isPresent() ?  o2.get() : 0;
            return value1.compareTo(value2);
        });

//日期获取最值（最大，最小）
Optional<ActivityVO> max = activityVOS.stream().max(Comparator.comparing(ActivityVO::getEndDate));
Optional<ActivityVO> min = activityVOS.stream().min(Comparator.comparing(ActivityVO::getStartDate));
Date maxEndDate = max.get().getEndDate();
Date minStartDate = min.get().getStartDate();

//groupingBy分组1
 Map<PrizeType, List<RedeemActivityPrize>> prizeTypeAndPrizesMap = redeemActivityPrize.stream().collect(Collectors.groupingBy(RedeemActivityPrize::getPrizeType));
        List<RedeemActivityPrize> couponPrizes = prizeTypeAndPrizesMap.get(PrizeType.COUPON);
        List<RedeemActivityPrize> presentPrizes = prizeTypeAndPrizesMap.get(PrizeType.PRESENT);

//groupingBy分组2
prizes.stream().collect(Collectors.groupingBy(RedeemActivityPrize::getPrizeId))
				.forEach((prizeId, activityPrizes) -> {
					List<String> activityIdsOfPrize = activityPrizes.stream()
							.map(RedeemActivityPrize::getRedeemActivityId).collect(Collectors.toList());

					List<RedeemActivity> redeemActivitiesOfPrize = activities.stream()
							.filter(x -> activityIdsOfPrize.contains(x.getId())).collect(Collectors.toList());

					List<RedeemActivityBriefCO> briefCOS = ModelConvertorHelper.convertList(redeemActivitiesOfPrize,
							RedeemActivityBriefCO.class);

					RedeemUsedPresentCO co = new RedeemUsedPresentCO();
					co.setPresentId(prizeId);
					co.setActivities(briefCOS);
					result.add(co);
				});
//Java8 Function函数的使用
//T-入参类型，R-出参类型 
 private static <T, R> List<R> transform(List<T> list, Function<T, R> fx) {
        List<R> result = new ArrayList<>();
        for (T element : list) {
            result.add(fx.apply(element));
        }
        return result;
  }
List<Integer> namesLength = transform(names, String::length);

//集合添加..
Stream.of(task1, task2, task3, task4, task5).collect(toList())

//显示前几位的集合数据
tasks.stream().filter(task -> task.getType() == TaskType.READING).sorted(comparing(Task::getCreatedOn)).map(Task::getTitle).
limit(n).collect(toList());

//Java8循环遍历
couponIds.forEach(x -> {
    RedeemActivityPrize presentRedeem = new RedeemActivityPrize();
    presentRedeem.setRedeemActivityId(redeemActivityId);
    presentRedeem.setPrizeType(PrizeType.COUPON);
    presentRedeem.setPrizeId(x);
    redeemActivityPrizeList.add(presentRedeem);
});

//map集合获取value  获取新方式
map.getOrDefault(goodsId, 0L)
default V getOrDefault(Object key, V defaultValue) {
   V v;
   return (((v = get(key)) != null) || containsKey(key)) ? v : defaultValue;
}

//flatMap使用
List<OrderSetDetail> setDetails = order.getDetails().stream()
                .filter(x -> CollectionUtils.isNotEmpty(x.getOrderSetDetails()))
                .flatMap(orderDetail -> orderDetail.getOrderSetDetails().stream())
                .collect(Collectors.toList());

//distinct
distinct主要用来去重，以下代码片段使用 distinct 对元素进行去重：
List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
numbers.stream().distinct().forEach(System.out::println);
//3,2,7,5

//groupingBy&count分组统计
Map<String, Long> collect = lists.stream().collect(Collectors
                .groupingBy(Student::getName, Collectors.counting()));

//Java8 Map集合遍历
 Map<String, Integer> updateStockMap = Optional.ofNullable(updateActivityStockList).orElseGet(ArrayList::new)
                .stream().collect(Collectors.groupingBy(UpdateActivityStock::getId,
                        Collectors.reducing(Integer.valueOf(0), UpdateActivityStock::getStock, Integer::sum)));

updateStockMap.forEach((id, stockNum) -> {
    ActivityStock oldStock = stockMap.get(id);
    if (oldStock != null) {
        ActivityStock stock = new ActivityStock();
        stock.setId(id);
        stock.setStock(stockNum);
    }
});
//Java8 Map集合遍历2
public class IterateHashMapExample {  
    public static void main(String[] args) {  
        Map < Integer, String > coursesMap = new HashMap < Integer, String > ();  
        coursesMap.put(1, "C");  
        coursesMap.put(2, "C++");  
        coursesMap.put(3, "Java");  
        coursesMap.put(4, "Spring Framework");  
        coursesMap.put(5, "Hibernate ORM framework");  

        // 5. 使用 Stream API 遍历 HashMap  
        coursesMap.entrySet().stream().forEach((entry) - > {  
            System.out.println(entry.getKey());  
            System.out.println(entry.getValue());  
        });  
    }
}
// java8之后。上面的操作可以简化为一行，若key对应的value为空，会将第二个参数的返回值存入并返回
Object key2 = map.computeIfAbsent("key", k -> new Object());

//相当于：
  if(map.get("key") == null) {
      map.put("key", new Object());
  } 
```

#### 原子操作

```java
/**
 * 获取租户下最大的用户编号
 *
 * @return
 */
public int getMaxUserCode() {
    String key = String.format(USER_CODE_KEY_TEMPLATE,
            BizContext.getBizIdentity().getTenantId()
            , BizContext.getBizIdentity().getExtTenantId());
    RAtomicLong atomicLong = redissonHandler.getRedisson().getAtomicLong(key);
    if (!atomicLong.isExists()) {
        int startUserCode = INIT_START_USERCODE;
        PageRequest<SpokesmanUser> request = new PageRequest<>();
        request.setPageNo(1);
        request.setPageSize(1);
        request.setOrderBy("user_code DESC");

        PageInfo<SpokesmanUser> oneMaxUserCodePage = this.listPage(request);
        List<SpokesmanUser> oneMaxUserCode = oneMaxUserCodePage.getList();
        if (CollectionUtils.isNotEmpty(oneMaxUserCode)) {
            startUserCode = oneMaxUserCode.get(0).getUserCode();
        }
        atomicLong.set(startUserCode);
    }
    atomicLong.incrementAndGet();
    return (int) atomicLong.get();
}
```

#### Spring创建对象

```java
private final static RedeemActivityRepository redeemActivityRepository = SpringContextHolder
        .getBean(RedeemActivityRepository.class);
```

#### Swagger不显示字段

```java
@JsonIgnore    //Response Model不会显示出来
@JSONField(serialize = false)    //Response Message不会显示出来
private String id;
```

#### 集合非空判断

```java
集合：usageRuleItems
if (null != usageRuleItems && !usageRuleItems.isEmpty){}
等价于：
if(CollectionUtils.isEmpty(usageRuleResults)){}

//创建集合
 List<Coupon> coupons = Collections.EMPTY_LIST;
```

#### 字段校验

```java
//Integer整型字段校验
@ApiModelProperty("活动批次库存，-1为实际商品库存")
@NotNull(message = "请输入活动批次库存")
@Range(min = -1, max = Integer.MAX_VALUE, message = "请输入有效的商品库存" )
private Integer stock;

//BigDecimal类型字段校验
@ApiModelProperty("折扣，10为没有折扣")
@NotNull(message = "请输入折扣")
@DecimalMin(value = "0.1", message = "折扣不能小于0.1")
@DecimalMax(value = "10", message = "折扣不能大于10")
private java.math.BigDecimal priceDiscount;

//String类型字段校验
@ApiModelProperty(value = "活动名称", required = true)
@NotBlank(message = "请输入活动名称")
@Length(min = 1, max = 64, message = "活动名称文字长度范围[1-64]")
private String name;

//日期类校验
@ApiModelProperty(value = "活动开始时间", required = true)
@NotNull(message = "请输入活动开始时间")
private Date startDate;


```

#### Example条件查询

```java
public RedeemActivity getEffectiveRedeemMobileActivity() {
   Date now = new Date();
   Example example = new Example(RedeemActivity.class);
   example.createCriteria().andEqualTo("isEnable", SystemConstants.YES)
         .andEqualTo("activityType", RedeemActivityType.MOBILE)
         .andLessThanOrEqualTo("startDate", now)
         .andGreaterThanOrEqualTo("endDate", now);

   List<RedeemActivity> redeemActivities = listAllByExample(example);
   if(!redeemActivities.isEmpty()) {
      return redeemActivities.get(0);
   } else {
      return null;
   }
}
```

#### 获取新用户

```java
@Override
public BaseResult check(Object o) {
    if(redeemActivity.getOnlyForNewuser().equals(SystemConstants.YES)) {
        boolean createdOrder = shopMemberApi.isCreatedOrder(memberId);
        if(createdOrder) {
            return new BaseResult("700", "活动仅新用户能参与");
        }
    }
    return BaseResult.buildSuccessResult();
}
```

#### Rule规则校验

```java
//外面校验
RedeemMobileActivityValidRule redeemActivityValidRule = new RedeemMobileActivityValidRule(activityId);
BaseResult<RedeemActivity> activityResult = redeemActivityValidRule.check(null);
BizAssert.isTrue(activityResult.isSuccess(), activityResult.getMsg());
...
redeemActivity.validActivity(rule);

//RedeemMobileActivityValidRule 内容
@RuleComment(group = "marketing", bizType = "redeemMobileActivity", desc = "手机兑换活动有效校验")
public class RedeemMobileActivityValidRule extends AbstractRule {
    private String activityId;

    private final static RedeemActivityRepository redeemActivityRepository = SpringContextHolder
            .getBean(RedeemActivityRepository.class);

    public RedeemMobileActivityValidRule(String activityId) {
        this.activityId = activityId;
    }
    @Override
    public BaseResult check(Object o) {
        RedeemActivity redeemActivity = redeemActivityRepository.getRedeemMobileActivity(activityId);
        if(redeemActivity == null) {
            return new BaseResult("700", "当前无有效活动");
        }
         return BaseResult.success(redeemActivity);
    }
}

//redeemActivity.validActivity
public void validActivity(Rule rule) {
		BaseResult baseResult = rule.check(null);
		BizAssert.isTrue(baseResult.isSuccess(), baseResult.getMsg());
	}
```

#### sql语句写在方法上

```java
//打印sql
logging.level.com.yjh.mushroom.spokesman.infrastructure.dao.SpokesmanUserDao=DEBUG

@MyBatisDao
public interface UserCouponDao extends BaseDao<UserCoupon>, InsertHisMapper<UserCoupon> {

    @Update("update user_coupon set user_coupon_status = 'UNUSED',order_id = null,order_amount = 0, use_date = null where order_id = #{orderId}")
    void cancelCoupon(String orderId);

}
```

#### 逻辑删除

```java
@Data
@EqualsAndHashCode(callSuper = true)
@EnableLogicDelete     //逻辑删除注解
@EnableOperateLog      //操作日志
public class GradeDealer extends DataEntity implements ExtTenantIdEntity {
```

#### BigDecimal使用注意

```
你可能认为java中用new BigDecimal(0.1)创建的BigDecimal应该等于0.1（一个是1的无精度的值，一个是有精度的值），但实际上精确的是等于0.1000000000000000055511151231257827021181583404541015625。这是因为0.1不能被double精确的表示（下面大概描述一下原理）。因此，传入构造函数的值不是精确的等于0.1。

当遇到需要涉及到精确计算的时候，如上面代码所示，要注意该构造函数是一个精确的转换，它无法得到与先调用Double.toString(double)方法将double转换成String，再使用BigDecimal(String)构造函数一样的结果。如果要达到这种结果，应该使用new BigDecimal(String value) 或 BigDecimal.valueof(double value)
```

#### 使用SecureRandom

```
由于java.util.Random类依赖于伪随机数生成器，因此该类和相关的java.lang.Math.random（）方法不应用于安全关键应用程序或保护敏感数据。 在这种情况下，应该使用依赖于加密强随机数生成器（RNG）的java.security.SecureRandom类。

PRNG(伪随机数)：
伪随机数， 计算机不能生成真正的随机数，而是通用一定的方法来模拟随机数。伪随机数有一部分遵守一定的规律，另一部分不遵守任何规律。

RNG(随机数)：
随机数是由“随机种子”产生的，“随机种子”是一个无符号整形数。

反例：
Random random = new Random(); // Questionable use of Random
byte bytes[] = new byte[20];
random.nextBytes(bytes);
正例：
SecureRandom random = new SecureRandom(); 
byte bytes[] = new byte[20];
```

#### count

```java
//单一计算
SpokesmanUser entity = new SpokesmanUser();
entity.setParentId(userId);
return this.dao.selectCount(entity);// 租户之间用户id保证唯一

//多条件统计
Example example = new Example(SpokesmanUser.class);
Example.Criteria criteria = example.createCriteria();
criteria.andEqualTo("parentId", userId);
criteria.andNotEqualTo("id", excludChildId);
return this.dao.selectCountByExample(example);// 租户之间用户id保证唯一
```

#### List集合的Entity与VO之间的转换

```java
//orderMarketingTotals -> OrderMarketingTotalVO
ModelConvertorHelper.convertList(Optional.ofNullable(orderMarketingTotals).orElseGet(LinkedList::new), OrderMarketingTotalVO.class);
```

#### PageInfo里面的vo与entity之间的转换

```java
PageInfo<InviteActivityVO> voPageInfo = ModelConvertorHelper
      .convertPageInfo(inviteActivityRepository.listPageByExample(pageRequest), InviteActivityVO.class);
```

MySQL普通索引创建

```mysql
mysql > ALTER TABLE {table_name} ADD INDEX index_name ( {column} )
```

#### Java8 List转Map

```java
//1.这里有个实体对象(员工对象)
public class Employee {
    // member variables
    private int empId;
    private String empName;
    private int empAge;
    private String empDesignation;
//2.比如式样员工对象的empId作为key，值是员工姓名
 Map<Integer, String> mapOfEmployees = employees.stream().collect(
        Collectors.toMap(e -> e.getEmpId(),e -> e.getEmpName()));
//3.Map的Key是empId，整个对象为Map的值,但如果List中有重复的empId，映射到Map时，Key时不能重复的
 Map<Integer, String> mapOfEmployees = employees.stream().collect(
     Collectors.toMap(
         e -> e.getEmpId(), 
         e -> e.getEmpName(), 
         (e1, e2) -> e1 )); // Merge Function
//4.将List转换为Map - 使用TreeMap对键进行自然排序
 Map<Integer, String> mapOfEmployees = employees.stream().collect(
                Collectors.toMap(
                        e -> e.getEmpId(), 
                        e -> e.getEmpName(), 
                        (e1, e2) -> e1 , // Merge Function
                        TreeMap<Integer, String>::new)); // Map Supplier
//如果你的TreeMap实现需要加入比较器，将上面代码中TreeMap<Integer, String>::new替换成：() -> new TreeMap<Integer, String>(new MyComparator())
Map<String, Student> studentMap = list1.stream().collect(Collectors.toMap(Student::getName, value -> value, (a1, a2) -> a1));
```

#### 循环分页

```java
public List<RedeemCodeExcel> listRedeemCodeExcel(RedeemCodeDrawQuery query) {

		RedeemCodeUnique redeemCodeUnique = new RedeemCodeUnique();
		redeemCodeUnique.setRedeemActivityId(query.getRedeemActivityId());
		redeemCodeUnique.setIsDraw(query.getIsDraw());

		PageRequest<RedeemCodeUnique> request = new PageRequest<>();
		request.setOrderBy("create_date desc");
		request.setData(redeemCodeUnique);

		List<RedeemCodeUnique> redeemCodeUniqueList = Lists.newArrayList();
		boolean isHasNextPage = true;
		int pageNo = 0;
		while (isHasNextPage) {
			pageNo++;
			request.setPageNo(pageNo);
			request.setPageSize(10000);
			PageInfo<RedeemCodeUnique> pageInfo = redeemCodeUniqueRepository.listPage(request);
			redeemCodeUniqueList.addAll(pageInfo.getList());
			isHasNextPage = pageInfo.isHasNextPage();
		}
		return ModelConvertorHelper.convertList(redeemCodeUniqueList, RedeemCodeExcel.class);
	}
}
```

```
Assert.notNull(redeemActivity, String.format("当前活动id[%s]无匹配的活动", activityId));
```

#### arthas命令

```shell
wget https://alibaba.github.io/arthas/arthas-boot.jar

#启动示例
java -jar arthas-boot.jar

#获取trace调用链
trace -j com.yjh.mushroom.marketing.application.command.WechatLotteryRunService drawPrize '#cost > 300'

# 获取Servlet调用路径，并过滤掉JDK的函数
trace -j javax.servlet.Servlet * >> test.log

# 获取Filter调用路径，并过滤掉JDK的函数
trace -j javax.servlet.Filter * >> test.log

trace -j org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient * >> test.log
watch org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient execute "{params,target,throwExp,returnObj}" -e -x 2 -b -s -n 2

trace -j com.yjh.erp.action.autocalculate.WarehouseAction|com.yjh.erp.action.autocalculate.ExpressAction * '#cost > 100'

# 录制重放
tt -t -n 2 org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient execute
# 查看id
tt -l
# 详情
tt -i 1000
# 重放
tt -i 1000 -p

# 获取spring 容器
tt -t org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter invokeHandlerMethod
tt -i 1000 -w 'target.getApplicationContext()'
tt -i 1000 -w 'target.getApplicationContext().getBean("helloWorldService").getHelloMessage()'

ognl '#context=@com.yjh.mushroom.common.spring.SpringContextHolder@applicationContext, #context.getBean("idGenerator").nextId()'


# 快速排查Spring Boot应用404/401问题
stack -E javax.servlet.http.HttpServletResponse sendError|setStatus params[0]==404

# mushroom 800错误排查
watch com.yjh.mushroom.framework.spring.web.GlobalExceptionHandler handleUncatchedException params[2]

#oms 异常排查
watch com.yjh.oms.main.order.web.OrderController * "{clazz.name, params[0],throwExp}" -e -x 2

#oms调用eureka地址检查
watch com.netflix.client.ClientRequest replaceUri "params[0]"

#oms数据库连接排查
watch com.mysql.jdbc.MysqlIO doHandshake "{params[0],params[1],params[2],target.host}" -b

#oms订单任务判断
watch com.yjh.oms.sync.domain.service.PlatformDomainEventService handlePendingEvent params[0]
```

##### 自动化脚本模拟并发

```shell
cd wrk/wrk-master/

wrk -t4 -c1000 -d60s -T10s --latency http://10.2.1.175:9016 --script=scripts/20190621/hongbaolotteryLogin.lua

wrk -t4 -c1000 -d10s -T10s --latency http://10.2.1.175:9016 --script=scripts/20190621/hongbaolotteryLogin_local.lua

wrk -t4 -c1000 -d10s -T10s --latency http://10.2.1.175:9016 --script=scripts/20190621/hongbaodrawLottery_local.lua
```




#### 查询条件

```java
if (query.getStartTime() != null && query.getEndTime() != null
      && query.getEndTime().after(query.getStartTime())) {
   String startTime = DateTimeUtils.formatDateTime(query.getStartTime());
   String endTime = DateTimeUtils.formatDateTime(query.getEndTime());
   criteria.andCondition(String.format(
         "((start_time >= '%s' AND '%s' >= start_time) OR ('%s' >= start_time AND end_time >= '%s') OR (end_time >= '%s' AND '%s' >= end_time))",
         startTime, endTime, startTime, endTime, startTime, endTime));
}
```

#### 批量获取goods

```
List<Goods> goodsList = goodsRepository.listByIds(ids);
```

#### Mushroom redis（缓存）操作

```java
private static final String CACHE_EMAIL_VERIFY = "employee_email_verify.%s.%s";

//创建缓存
 public Boolean isEnableEmailVerifyFromCache() {
        BizIdentity bizIdentity =  BizContext.getBizIdentity();
        String cacheKey = String.format(CACHE_EMAIL_VERIFY, bizIdentity.getTenantId(), bizIdentity.getExtTenantId());
        String flag = cacheService.get(cacheKey);
        if(flag == null) {
            Boolean isEnable = isEnableEmailVerifyFromDb();
            cacheService.set(cacheKey, isEnable, 1, TimeUnit.HOURS);
            return isEnable;
        } else {
            return Boolean.parseBoolean(flag);
        }
    }
//清除缓存
 public void evictCacheOfEnableEmailVerify() {
        BizIdentity bizIdentity = BizContext.getBizIdentity();
        String cacheKey = String.format(CACHE_EMAIL_VERIFY, bizIdentity.getTenantId(), bizIdentity.getExtTenantId());
        cacheService.delete(cacheKey);
    }

//注解缓存
//创建缓存
 @Cacheable(name = CACHE_NAME,key = "#configType + \".\" + #tenantId +\".\"+ #extTenantId")
    public ShopMemberConfig getByType(ShopMemberConfigTypeEnum configType,String tenantId, String extTenantId) {
        log.info("cache {} key:{}",CACHE_NAME,configType+"."+tenantId+"."+extTenantId);
        return this.getByType(configType);
    }

//清除缓存
    @CacheEvict(name = CACHE_NAME, key = "#configType + \".\" + #tenantId + \".\"+ #extTenantId")
    public void refreshCache(ShopMemberConfigTypeEnum configType,String tenantId, String extTenantId){
        log.info("refresh {} Cache, key:{}",CACHE_NAME,configType+"."+tenantId+"."+extTenantId);
    }
```

#### 领域事件使用例子

```java
//发布事件
private static final String CONFIRM_FREEZE_CREDIT_EVENT = "ConfirmFreezeCreditEvent";

public void publishConfirmEvent(GoodsExchangeRecord record) {
		EventConfirmFreezeCreditVO vo = new EventConfirmFreezeCreditVO();
		vo.setBizId(record.getId());
		vo.setBizType(CreditFreezeEnum.GOODS_EXCHANGE);
		vo.setMemberId(record.getUserId());
		vo.setPoint(record.getCredit());
		vo.setRemarks("积分商城确认扣除冻结积分");
		Event event = new Event(record.getId(), CONFIRM_FREEZE_CREDIT_EVENT, CONFIRM_FREEZE_CREDIT_EVENT,vo);
		domainEventPublisher.publish(event);
	}

//另一个服务消费
@MQListener(topics = "ConfirmFreezeCreditEvent")
@IdempotentLimiter(key = "\"ConfirmFreezeCreditEvent.eventId.\" + new String(#eventId)")
	public void receiveOrder(@Payload String data, @Header(MQHeaders.EVENT_ID) byte[] eventId,
			@Header(MQHeaders.TENANT_ID) byte[] tenantId, @Header(MQHeaders.EXT_TENANT_ID) byte[] extTenantId) {
		BizContext.setBizIdentity(new BizIdentity(new String(tenantId), new String(extTenantId)));
		String eventIdStr = new String(eventId);
		log.info("冻结积分确认相关领域事件，data：{},eventId：{}", data, eventIdStr);
		EventConfirmFreezeCreditVO eventConfirmFreezeCreditVO = JsonUtils.toBean(data,
				EventConfirmFreezeCreditVO.class);

		creditService.confirmFreezeCredit(eventConfirmFreezeCreditVO);
	}
```

#### 邮箱发送

```java
public class EmployeeMailSender {

    @Value("${employee.mail-content-prefix.url}")
    private String mailContentPrefixUrl;

    @Value("${spring.mail.username}")
    private String mailUser;

    private final static String CONTENT_TEMPLATE =
            "尊敬的%s：</br>&nbsp;&nbsp;&nbsp;&nbsp;您正在进行%s的员工身份认证。" +
            "请点击以下链接完成认证（链接有效期30分钟）： </br>%s </br>&nbsp;&nbsp;&nbsp;&nbsp;" +
            "%s &nbsp;&nbsp;&nbsp;&nbsp;祝您工作愉快，生活顺心！</br>&nbsp;&nbsp;&nbsp;&nbsp;" +
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;御家科技团队";

    private final JavaMailSender mailSender;

    @Autowired
    public EmployeeMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(MailMessageCO msg) throws MessagingException {
        log.info("发送邮件触发 请求参数:{}", msg);

        String shopName = msg.getShopName();
        String nickName = msg.getNickName();
        String verifyUrl = mailContentPrefixUrl + msg.getToken();
        Integer verifyDays = msg.getVerifyDays();
        String verifyTip = "";
        if(verifyDays != null) {
            verifyTip = String.format("认证成功后，员工身份的有效期为%s天。每%s天需重新认证一次。", verifyDays, verifyDays);
        }
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        String nick="";
        try {
            nick=javax.mail.internet.MimeUtility.encodeText("御家科技团队");
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
        helper.setFrom(new InternetAddress(nick+" <"+mailUser+">"));

        helper.setTo(msg.getToEmail());

        helper.setSubject("员工身份认证");

        String text = String.format(CONTENT_TEMPLATE, nickName, shopName, verifyUrl, verifyTip);
        helper.setText(text, true);
        mailSender.send(mimeMessage);
    }
}
```

#### 时间差计算

```java
long diffMinutes = ChronoUnit.MINUTES.between(Instant.now(), sendDate.toInstant());
```

#### 采用StringBuilder拼接字符串

```java
StringBuilder message = new StringBuilder("该商品限兑").append(goods.getRestrictionNum()).append("件");

//说明：如果直接采用String拼接字符串，每拼接一次，就会new出一个StringBuilder对象
```

#### 测试结果

![StringBuilderTest](E:\work\study\相关截图\StringBuilderTest.png)

#### 分组展示

```
List<UsageRuleItem> usageRuleItems = usageRuleItemRepository.listByUsageRuleIds(usageRuleIds);
List<UsageRuleResult> usageRuleResults = usageRuleResultRepository.listByUsageRuleIds(usageRuleIds);

Map<String, List<UsageRuleItem>> usageItemMap = usageRuleItems.stream().collect(Collectors.groupingBy(UsageRuleItem::getUsageRuleId));
Map<String, List<UsageRuleResult>> usageResultMap = usageRuleResults.stream().collect(Collectors.groupingBy(UsageRuleResult::getUsageRuleId));
```

#### Java8 Duration

```
Duration duration = Duration.ofDays(verifyDays * -1);
Instant lastValidDate = Instant.now().plus(duration);
```

#### RestTemplate调用接口示列

```java
JSONObject param = new JSONObject();
param.put("type", request.getData()); // 素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
param.put("offset", (request.getPageNo() - 1) * request.getPageSize()); // 从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
param.put("count", request.getPageSize()); // 返回素材的数量，取值在1到20之间
RestTemplate restTemplate = new RestTemplate();
restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8)); // 处理中文乱码
ResponseEntity<String> re = restTemplate.postForEntity("https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=" + token, param, String.class);
if (HttpStatus.OK.equals(re.getStatusCode()) && re.getBody() != null) {
    JSONObject result = JSONObject.parseObject(re.getBody());
    if (!result.containsKey("errcode")) {
        pageInfo.setTotal(result.getLong("total_count"));
        List<MpMaterialVO> list = Lists.newArrayList();
        JSONArray itemArr = result.getJSONArray("item");
        itemArr.forEach(item -> list.add(new MpMaterialVO((JSONObject) item)));
        pageInfo.setList(list);
        return pageInfo;
    }
}
```

#### httpclient调用

```java
public static String sendGet(String url, PayAccountBizCodeType bizCodeType) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("User-Agent", getUserAgent());
        httpGet.setHeader("Authorization", getAuthorization("GET", url, "", bizCodeType));
        String result = executeRequest(httpGet);
        logger.info("调用微信GET接口，url:{} ,result:{}", url, result);
        return result;
    }

private static String executeRequest(HttpUriRequest request) {
    CloseableHttpClient client = HttpClients.createDefault();
    String result = "";
    try {
        CloseableHttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        result = EntityUtils.toString(entity, "UTF-8");
    } catch (IOException e) {
        logger.error("调用微信接口失败，失败原因：" + e.getMessage(), e);
    } finally {
        try {
            client.close();
        } catch (IOException e) {
            logger.error("关闭httpclient失败，失败原因:{}", e);
        }
    }
    return result;
}
```

获取Json数据

```java
public MpMaterialVO(JSONObject jsonObject) {
    this.mediaId = jsonObject.getString("media_id");
    JSONObject news = (JSONObject) jsonObject.getJSONObject("content").getJSONArray("news_item").get(0);
    this.title = news.getString("title");
    this.digest = news.getString("digest");
    this.thumbUrl = news.getString("thumb_url");
}
```

Json与VO相互转换

```java
/**
 * 对应VO转为内容json
 */
public void voToJson() {
    if (type == MaterialTemplateTypeEnum.MINI_CARD) {
        contentJson = JsonUtils.toJsonString(miniCardVO);
    } else if (type == MaterialTemplateTypeEnum.ADVANCED_IMAGE_TEXT) {
        contentJson = JsonUtils.toJsonString(advancedImageTextVO);
    } else {
        contentJson = JsonUtils.toJsonString(content);
    }
}

/**
 * 内容json转为对应VO
 */
public void jsonToVO() {
    if (type == MaterialTemplateTypeEnum.MINI_CARD) {
        miniCardVO = JsonUtils.toBean(contentJson, MiniCardVO.class);
    } else if (type == MaterialTemplateTypeEnum.ADVANCED_IMAGE_TEXT) {
        advancedImageTextVO = JsonUtils.toBean(contentJson, AdvancedImageTextVO.class);
    } else {
        content = JsonUtils.toBean(contentJson, String.class);
    }
}
```

#### 微信发送图文消息举例

```java
{
    "touser":"OPENID",
    "msgtype":"news",
    "news":{
        "articles": [
         {
             "title":"Happy Day",
             "description":"Is Really A Happy Day",
             "url":"URL",
             "picurl":"PIC_URL"
         }
         ]
    }
}

public JSONObject initAdvancedImageText(AdvancedImageTextVO advancedImageTextVO) {
    JSONObject param = new JSONObject();
    param.put(MSG_TYPE, "news");
    JSONObject news = new JSONObject();
    JSONArray articles = new JSONArray();
    JSONObject article = new JSONObject();
    article.put(TITLE, advancedImageTextVO.getTitle());
    article.put("description", advancedImageTextVO.getDescription());
    article.put("url", advancedImageTextVO.getHref());
    article.put("picurl", advancedImageTextVO.getUrl());
    articles.add(article);
    news.put("articles", articles);
    param.put("news", news);
    return param;
}
```

#### Map

```
public Map<String, Long> countLabelUsersByChannelIdsMap(List<String> channelIdList) {
    List<Pair<String, Long>> pairs = countLabelUsersByChannelIds(channelIdList);
    if (CollectionUtils.isEmpty(pairs)) {
        return ImmutableMap.of();
    }
    return pairs.stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
}
```

```sql
##创建索引、删除索引
ALTER TABLE `marketing`.`redeem_code_unique` 
DROP INDEX `uk_activity_id_code` ,
ADD UNIQUE INDEX `uk_activity_id_code` (`code` ASC, `redeem_activity_id` ASC);

##创建索引
ALTER TABLE `trade`.`user_consignee_address` ADD INDEX `idx_create_by` (`create_by` ASC);

##更新
select * from refund_order where order_type='GLOBAL';
update `refund_order` a, `order_global` b set a.customs=b.customs where a.order_id=b.id and a.`order_type`='GLOBAL';

##添加字段
ALTER TABLE `trade`.`order` 
ADD COLUMN `customs` VARCHAR(64) NULL COMMENT '海淘口岸类型' AFTER `type`;
```

#### Debug日志打印

```
if (logger.isDebugEnabled()) {
      logger.debug("删除订单,result={}", JSON.toJSONString(result));
}
```

mybatis的Sql使用

```sql
<sql id="memberColumns">
              a.id AS "id",
   a.card_no AS "cardNo",
   a.mobile AS "mobile",
   a.login_name AS "loginName",
   a.password AS "password",
   a.status AS "status",
   a.sex AS "sex",
   a.birthday AS "birthday",
   a.nick_name AS "nickName",
   a.icon AS "icon",
   a.is_created_order AS "isCreatedOrder",
   a.binding_status AS "bindingStatus",
   a.province AS "province",
   a.city AS "city",
   a.create_date AS "createDate",
   a.create_by AS "createBy",
   a.update_date AS "updateDate",
   a.update_by AS "updateBy",
   a.del_flag AS "delFlag",
   a.remarks AS "remarks",
   a.tenant_id AS "tenantId",
   a.ext_tenant_id AS "extTenantId"
</sql>

   <select id="listMember" resultMap="memberCO">
       SELECT b.channel,
       <include refid="memberColumns"/>
       ....
   </select>
```

```
List<MemberChannelExt> memberChannelExtList = memberChannelExtRepository.listByFieldValues("memberId",
      memberIds);
```

```
1 调试
2 理解设计意图
3 想想如果你来写，会怎么写
4 你看的这份代码有没有设计上的缺陷，是否有更好的方式
5 代码先看主流程，不要受细节干扰
6 看不懂的地方翻资料，看书，问别人
```

#### ES相关使用

```java
//范围查询
if (null != query.getCancelTimeStart()) {
    RangeQueryBuilder cancelTimeStartQuery = QueryBuilders.rangeQuery("cancelTime")
    .gte(DateTimeUtils.formatDateTime(query.getCancelTimeStart()));
    filter.must(cancelTimeStartQuery);
}
if (null != query.getCancelTimeEnd()) {
    RangeQueryBuilder cancelTimeEndQuery = QueryBuilders.rangeQuery("cancelTime")
    .lte(DateTimeUtils.formatDateTime(query.getCancelTimeEnd()));
    filter.must(cancelTimeEndQuery);
}

//集合查询
if (ValidateUtils.isNotEmptyCollection(query.getPaymentStatuses())) {
    List<String> paymentStatuses = query.getPaymentStatuses().stream()
        .map(paymentStatus -> paymentStatus.toString()).collect(Collectors.toList());
    TermsQueryBuilder paymentStatusesQuery = QueryBuilders.termsQuery("paymentStatus", paymentStatuses);
    filter.must(paymentStatusesQuery);
}

//精确查询
 TermsQueryBuilder originalExpressIdQuery = QueryBuilders
                    .termsQuery("originalExpressId", query.getOriginalExpressId());
            filter.must(originalExpressIdQuery);

//
QueryBuilder queryBuilder = QueryBuilders.wildcardQuery("user", "ki*hy");

//模糊查询
RegexpQueryBuilder externalCodeQuery = QueryBuilders.regexpQuery("externalCode",
                    “*”+ query.getExternalCode() + “*”);

WildcardQueryBuilder externalCodeQuery = QueryBuilders.wildcardQuery("externalCode",
                    "*"+query.getExternalCode()+"*");
```

```
Integer dailySignPointToInt = Integer.parseInt(resultData);
//推荐2，有缓存
Integer dailySignPointToInt = Integer.valueOf(resultData);
```

#### 缓存

```java
private static final String GOODS_CACHE = "goods_cache";

private static final String GOODS_LIST_CACHE = "goods_list_cache";

@Cacheable(name = GOODS_CACHE, key = "\"id.\" + #id")
public Goods getCache(String id) {
    return get(id);
}

//缓存清除不要写空方法，可以加在需要清楚缓存的方法上
@CacheEvict(name = GOODS_CACHE, key = "\"id.\" + #id", allEntries = true)
public void refreshCache(String id) {
    log.info("refresh goods_cache {} !!!", id);
}


private static final String CACHE_SHOP_ACTIVITY = "pay_activity_shop";

@CacheEvict(name = CACHE_SHOP_ACTIVITY,key = "#extTenantId")
public void createAndRefreshCache(List<PayActivityPrize> prizeList,String extTenantId){
create(prizeList);
}

@CacheEvict(name = CACHE_SHOP_ACTIVITY,key = "#extTenantId")
public void deleteByIdsAndRefreshCache(List<String> removePrizeIds,String extTenantId){
deleteByIds(removePrizeIds);
}
```

#### Mybatis符号转义

| 原符号   | <    | <=    | >    | >=    | &     | '      | "      |
| -------- | ---- | ----- | ---- | ----- | ----- | ------ | ------ |
| 替换符号 | &lt; | &lt;= | &gt; | &gt;= | &amp; | &apos; | &quot; |

#### 本地缓存使用

```java
private static final String CACHE_NAME_STAFF_ID = "localcache.chat.customerid.topic";
ShopStaffBriefVO shopStaffBriefVO = CacheUtils.get(CACHE_NAME_STAFF_ID, userId, expireTimeMillis, new CacheProvider<String, ShopStaffBriefVO>() {
        @Override
        public ShopStaffBriefVO getData(String key) throws Exception {
             return shopStaffApi.getByUserId(userId);
             }
});
        if (shopStaffBriefVO != null) {
            return shopStaffBriefVO.getId();
        }
}
```

#### 异常返回

```
return new BaseResult("700", String.format("优惠券【%s】领券次数达到上限", name));
```

#### 接口限流

```java
 /**
 * 每分钟只允许调用一次
 */
 @ApiRateLimiter(limit = 1, expire = 1, timeUnit = TimeUnit.MINUTES)
 @Override
 @RequiresPermissions("demo:demo:view")
 @GetMapping("get")
 @ApiOperation("获取demo对象")
 public Demo get(@RequestParam String id) {
 	return demoService.get(id);
 }
```

#### Arrays.asList()与Collections.singletonList()

```
Arrays.asList(strArray)返回值是仍然是一个可变的集合，但是返回值是其内部类，不具有add方法，可以通过set方法进行增加值，默认长度是10
Collections.singletonList()返回的同样是不可变的集合，但是这个长度的集合只有1，可以减少内存空间。但是返回的值依然是Collections的内部实现类，同样没有add的方法，调用add，set方法会报错
```

#### 条件查询

```java
criteria.andCondition(String.format("((is_auto = '1' AND start_time > '%s') OR (is_auto = '0' AND is_immediately = '0' AND notify_date > '%s'))", now, now));
```

#### idea快捷键

```
ALT + F                          ：控制台查找
CTRL + O                         ：显示类里面的方法列表
ALT + T                          ：测试类创建
SHIFT + ALT + M                  ：抽象出方法
CTRL + ALT + B                   ：查看被实现的方法
CTRL + K                         ：查询跳到下一个
CTRL + SHIFT + T                 ：查类
CTRL + SHIFT + R                 ：查文件
CTRL + SHIFT + ALT + N           ：函数变量，查询函数使用
ALT + 1；CTRL + SHIFT + 左右方向键：移动分割线
CTRL + L                         ：定位到行
CTRL + SHIFT + U                 ：大小写切换
//自定义
Alt + A                           : back
Alt + D                           : forward
Alt + W                           : 进入方法，相当于ctrl + 鼠标左键

```

#### 业务id存入

```
BizContext.setBizIdentity(new BizIdentity(tenantId, extTenantId));
```

#### 异常说明

![异常分类](E:\work\study\相关截图\异常分类.png)

```
<![CDATA[ <> ]]> 
```

#### @Transient

```
@Transient表示该属性并非一个到数据库表的字段的映射,ORM框架将忽略该属性. 如果一个属性并非数据库表的字段映射,就务必将其标示为@Transient,否则,ORM框架默认其注解为@Basic
```

```java
IdGenUtils.uuid()

//有参构造，无参构造注解
@AllArgsConstructor
@NoArgsConstructor
```

排序查询

```java
public List<ChatSession> listByCsId(String csId) {
    Example example = new Example(ChatSession.class);
    example.createCriteria().andEqualTo("csId", csId)
            .andEqualTo("isAssignCs", SystemConstants.YES)
            .andEqualTo("isEndSession", SystemConstants.NO);
    example.setOrderByClause("update_date DESC");
    return this.listAllByExample(example);
}
```

#### 抛出业务异常

```java
throw new BusinessException(String.format("会话属于用户:[%s],当前用户不能操作，等待对方关闭", csName));
```

getAny，查询一条记录（如果有多条，只会获取其中的一条）

```java
ChatSession chatSession = new ChatSession();
chatSession.setCsId(csId);
chatSession.setIsEndSession(isEndSession);
return chatSessionRepository.getAny(chatSession);
```

sql使用Triple

```sql
<!--xml配置-->
<select id="listUnreadQuantityBySessionIdWaitDate" resultType="com.yjh.mushroom.common.dto.tuple.Triple">
    <foreach collection="sessionIdWaitDateList" item="item" separator=" union all ">
        SELECT MIN(t.session_id) AS first, MIN(t.create_date) AS second, COUNT(1) AS third FROM chat_record t WHERE t.session_id = #{item.first} AND t.create_date > #{item.second}
    </foreach>
</select>


//dao
List<Triple<String, Date, Integer>> listUnreadQuantityBySessionIdWaitDate(@Param("sessionIdWaitDateList") List<Pair<String, Date>> sessionIdWaitDateList);

//Pair使用
List<Pair<String, Date>> sessionIdWaitDateList = csLastChatRecords.stream().map(chatRecord -> new Pair<>(chatRecord.getSessionId(), chatRecord.getCreateDate())).collect(Collectors.toList());
```

线程安全的集合，可以作全局变量

```java
private Set<String> hasUserNames = new ConcurrentSet<>();
private Map<String, Set<String>> hasCsNames = new ConcurrentHashMap<>();
```

#### 延迟队列使用

push数据

```java
public static final String DELAY_TOPIC_CHAT_SESSION = "chat-service.delay_topic_chat_session";


private static final long HOUR_MILLIS = 1000L * 3600;

@Autowired
private DelayQueueService delayQueueService;

public void pushChatSessionDelayQueue(ChatSession record) {
    long delayTimeMillis = System.currentTimeMillis() + HOUR_MILLIS;
    DelayQueueJob delayQueueJob = new DelayQueueJob();
    delayQueueJob.setDelayTime(delayTimeMillis);
    delayQueueJob.setMessage(JsonUtils.toJsonString(record));
    delayQueueJob.setTopic(DELAY_TOPIC_CHAT_SESSION);
    delayQueueJob.setTtrTime(20L);

    delayQueueService.push(delayQueueJob);
    log.info("pushChatSessionDelayQueue，record:{}", record);
}
```

##### handler接收

```java
@Slf4j
@Component
@Profile({ TEST, PRODUCT })
public class ChatSessionDelayHandler implements DelayQueueHandler {

    @Autowired
    private ChatSessionService chatSessionService;

    @Override
    public String getTopic() {
        return ChatSessionDelayProcessService.DELAY_TOPIC_CHAT_SESSION;
    }

    @Override
    public void handle(String message) {
        log.info("客服会话过期处理: message:{}", message);
        ChatSession record = JsonUtils.toBean(message, ChatSession.class);
        BizContext.setBizIdentity(new BizIdentity(record.getTenantId(), record.getExtTenantId()));
        chatSessionService.autoInvalid(record);
    }

}
```

#### FieldConvertUtils 和 PageRequest

```java
List<CustomerServiceVO> voList = customerServiceList.stream().map(x -> {
   CustomerServiceVO vo = new CustomerServiceVO();
   BeanUtils.copyProperties(vo, x);
   Long todayEndSession = todayEndSessionMap.containsKey(x.getId()) ? todayEndSessionMap.get(x.getId()) : 0L;
   Long todayNotEndSession = todayNotEndSessionMap.containsKey(x.getId())
         ? todayNotEndSessionMap.get(x.getId())
         : 0L;
   vo.setTodayChatSessionNumber(todayEndSession + todayNotEndSession);
   vo.setTodayOnChatSessionNumber(todayNotEndSession);
   return vo;
}).collect(Collectors.toList());
FieldConvertUtils.convertList(voList);


// 注意这里Query 可能和entity 字段不一致！！
        PageRequest<CustomerService> entityRequest = ModelConvertorHelper.convertPageRequest(request,
                CustomerService.class);

        PageRequest<Example> pageRequest = customerServiceRepository.convert2ExamplePageRequest(entityRequest,
                new Example(CustomerService.class));
        Example example = pageRequest.getData();
        // 添加你的查询条件...
        CustomerServiceQuery customerServiceQuery = request.getData();
        Example.Criteria criteria = example.and();

```

#### JsonTypeHandler使用

```java
@ColumnType(typeHandler = JsonTypeHandler.class)
private List<String> suffix;
```

#### 订单查询改为ES查询

##### 风格1

```java
QueryBuilder buyerQuery = QueryBuilders.termQuery("buyerId", query.getBuyerId());
ExistsQueryBuilder existsField = QueryBuilders.existsQuery("refundOrders.id");
QueryBuilder queryBuilder = QueryBuilders.boolQuery().filter(buyerQuery).must(existsField);

SearchSourceBuilder builder = new SearchSourceBuilder();
builder.query(queryBuilder);
builder.from(0).size(10000);
builder.sort("createDate", SortOrder.DESC);

SearchResponse response = esOrderRepository.getEsDao().search(builder);

List<OrderEs> list = Lists.newArrayList();
if (response.getHits().totalHits > 0) {
    list = Arrays.stream(response.getHits().getHits()).map(SearchHit::getSourceAsString)
            .map(x -> JsonUtils.toBean(x, OrderEs.class)).collect(Collectors.toList());
}
```

##### 风格2

```java
OrderQuery cond = request.getData();
if (null == cond) {
   cond = new OrderQuery();
}
SearchSourceBuilder searchSourceBuilder = generateOrderQuery(cond);
ESDao esDao = getEsDao();
searchSourceBuilder.from((request.getPageNo() - 1) * request.getPageSize());
String orderBy = request.getOrderBy();
if (StringUtils.isNotEmpty(orderBy)) {
   String[] order = orderBy.split(" ");
   Assert.isTrue(order.length == 2, "排序参数格式错误");
   searchSourceBuilder.sort(order[0], SortOrder.fromString(order[1]));
}
// customSort(searchSourceBuilder, request);

searchSourceBuilder.size(request.getPageSize());

SearchResponse response = esDao.search(searchSourceBuilder);
long totalCount = response.getHits().totalHits;
SearchHit[] searchHits = response.getHits().getHits();
List<EsOrder> datas = Lists.newArrayList();
for (SearchHit searchHit : searchHits) {
   EsOrder data = (EsOrder) JsonUtils.toBean(searchHit.getSourceAsString(), getClassGenricType());
   datas.add(data);
}

Page<EsOrder> page = new Page<>();
page.addAll(datas);
page.setPageNum(request.getPageNo());
page.setPageSize(request.getPageSize());
page.setTotal(totalCount);
```

#### 本地导出文件测试

```shell
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/octet-stream' --header 'X-Client-Id: swagger' --header 'X-Client-Token: 852311eac642cbf8059928c371784461' --header 'X-Tenant-Id: newretail' --header 'X-Ext-Tenant-Id: 1275854953286784' -d '{}' 'http://localhost:9017/api/earningRuleRetail/exportExcel' -o aa.xlsx
```

#### 滑动式分页

```java
 public ScrollPageInfo<DemoCO> listScrollPage(ScrollPageRequest<DemoQuery> request) {
    ScrollPageRequest<Example> pageRequest = (ScrollPageRequest<Example>) demoRepository
        .convert2ExamplePageRequest(request);
    Example example = pageRequest.getData();
    // 添加你的查询条件...
    DemoQuery demoQuery = request.getData();
    if(demoQuery != null) {
      Example.Criteria criteria = example.and();
      this.andEqualTo(criteria, "key", demoQuery.getKey());
      this.andEqualTo(criteria, "value", demoQuery.getValue());
    }

    ScrollPageInfo<Demo> demoPageInfo = demoRepository.listScrollPageByExample(pageRequest);
    return (ScrollPageInfo) this.convertPageInfo(demoPageInfo, demoConverter);
  }  
```

#### updateByExample使用

```java
ChatSession updateEntity = new ChatSession();
updateEntity.setId(chatSession.getId());
updateEntity.setServiceEndDate(new Date());
updateEntity.setIsEndSession(SystemConstants.YES);
updateEntity.setRemark(remark);
updateEntity.setUpdateDate(new Date());
updateEntity.setUpdateBy(operator);
Example example = new Example(ChatSession.class);
example.createCriteria().andEqualTo("id", chatSession.getId()).andEqualTo("isEndSession", SystemConstants.NO);
int result = chatSessionRepository.updateByExample(updateEntity, example);
```

#### Kafka操作

```shell
//消费者
bin/windows/kafka-console-consumer.bat --zookeeper localhost:2181 --topic lvshen_demo_test --from-beginning

//生产者
bin/windows/kafka-console-producer.bat --broker-list localhost:9092 --topic lvshen_demo_test
```

#### 订单编号随机生成器

```java
CodeGenerator codeGenerator = new CodeGenerator("HO", "yyMMddHHmmss", 4);
String generateCode = codeGenerator.generateCode();
```

#### 判断小程序或h5

```
String clientType = ServletUtils.getRequest().getHeader("X-Client-Type");
```

#### InitializingBean

```java
InitializingBean接口为bean提供了初始化方法的方式，它只包括afterPropertiesSet方法，凡是继承该接口的类，在初始化bean的时候都会执行该方法。

//示例
public class SensitiveWordFilterHelper implements InitializingBean {		
	@Override
	public void afterPropertiesSet() throws Exception {
		refreshAllWord();
	}
	...
}
```

#### kafka重试

```
1. new KafkaProducer()后创建一个后台线程KafkaThread扫描RecordAccumulator中是否有消息； 
2. 调用KafkaProducer.send()发送消息，实际上只是把消息保存到RecordAccumulator中； 
3. 后台线程KafkaThread扫描到RecordAccumulator中有消息后，将消息发送到kafka集群； 
4. 如果发送成功，那么返回成功； 
5. 如果发送失败，那么判断是否允许重试。如果不允许重试，那么返回失败的结果；如果允许重试，把消息再保存到RecordAccumulator中，等待后台线程KafkaThread扫描再次发送；
```

#### ArrayDeque方法（双队列）

##### addFirst(E e)

```java
//addFirst(E e)
public void addFirst(E e) {
    if (e == null)//不允许放入null
    throw new NullPointerException();
    elements[head = (head - 1) & (elements.length - 1)] = e;//2.下标是否越界
    if (head == tail)//1.空间是否够用
    doubleCapacity();//扩容
}
```

#### 时间单位转换

```java
//3600分钟 转换成 小时 是多少
System.out.println(TimeUnit.HOURS.convert(3600, TimeUnit.MINUTES));

//3600分钟 转换成 天 是多少
System.out.println(TimeUnit.DAYS.convert(3600, TimeUnit.MINUTES));

//3600分钟 转换成 秒 是多少
System.out.println(TimeUnit.SECONDS.convert(3600, TimeUnit.MINUTES));
```

#### 事务结束后代码处理逻辑

```java
afterTransactionExecutor.execute(() -> clearCacheCommodityByIds(ids));
```

#### 地址路径为方法参数

```java
@PostMapping("/changeSort/{id}/{type}")
@ApiOperation(value = "调整排序 TOTOP/UP", tags = TagConstant.PC)
@RequiresPermissions("trade-commodity:commodityParam:edit")
public void changeSort(@PathVariable("id") String id, @PathVariable("type") String type) {
    Assert.hasLength(id, "属性id有误");
    commodityParamQueryService.get(id);
    commodityParamService.changeSort(id, type);
}
```

#### 条件查询

```java
public List<CommodityHotTag> listAllHotTag(){
    Example example = commodityHotTagRepository.convertEntity2Example(new CommodityHotTag());
    PageHelper.startPage(1, 20, "sort desc");
    return commodityHotTagRepository.listAllByExample(example);
}
```

#### @Deprecated注解

```java
//表示已经废弃，推荐不用
@Deprecated
```

在类上面添加此注解，说明该类为废弃类

#### MySQL操作

```sql
#查询事务隔离级别
select @@global.tx_isolation;
```

##### linux环境下部署mysql

```shell
#初始化data

./mysql_install_db --user=mysql --basedir==/home/lvshen/support/mysql/ --datadir=/home/lvshen/data (不用了)

./mysqld --initialize --user=mysql --basedir=/home/lvshen/support/mysql/ --datadir=/home/lvshen/data --explicit_defaults_for_timestamp

#可用
bin/mysqld --defaults-file=/home/lvshen/support/mysql/my.cnf --initialize --user=mysql --basedir=/home/lvshen/support/mysql --datadir=/home/lvshen/data
#可用
bin/mysqld_safe --defaults-file=/home/lvshen/support/mysql/my.cnf --user=mysql &

update user set host = '%' where user ='root';


piw-coFMf5eB
#安全启动mysqld服务
bin/mysqld_safe --defaults-file=/home/lvshen/lvshen/mysql/my.cnf &

bin/mysqladmin -u root password 'root' -S /tmp/mysql.sock

bin/mysql -u root -p -S /home/lvshen/support/mysql/mysql.sock
./mysql -uroot -proot -S /home/lvshen/support/mysql/mysql.sock

ln -s /home/lvshen/support/mysql/mysql.sock /tmp/mysql.sock

source /home/log4x/support/mysql/sql/log4x_sql/mysql/
source /home/mq/file/appframe
source /home/mq/file/console.sql

#创建用户
CREATE USER 'l4x'@'%' IDENTIFIED BY '123';
#查看表更新时间
SELECT
   `TABLE_NAME`, `UPDATE_TIME`
FROM
   `information_schema`.`TABLES`
WHERE
   `information_schema`.`TABLES`.`TABLE_SCHEMA` = 'log4x'
AND
   `information_schema`.`TABLES`.`TABLE_NAME` = 'l4x_svc_monitor_conf';
#mysql启停
cd ~/support/mysql/bin
mysqld_safe& --defaults-file=/app/mysql/mysql/my.cnf & 启动
mysqladmin shutdown -uroot -proot 停

#MySQL 输入任何语句都提示You must reset your password using ALTER USER 解决方法
SET PASSWORD = PASSWORD('root');
ALTER USER 'root'@'localhost' PASSWORD EXPIRE NEVER;
FLUSH PRIVILEGES;
#MySQL 非root用户创建database
#grant -权限- ON 1.库名.表名(全部*) 2.所有数据库 *.* TO '用户名'@'允许的ip(所有%)' IDENTIFIED BY '用户密码';最后，不要忘了 刷新权限：FLUSH PRIVILEGES;
grant all on l4x.* to 'l4x'@'%' identified by '123';
FLUSH PRIVILEGES;
1 登陆失败,mysqladmin修改密码失败
[root@mysql var]# mysqladmin -u root password 'root'
mysqladmin: connect to server at 'localhost' failed
error: 'Access denied for user 'root'@'localhost' (using password: NO)'
2 停止mysql服务
[root@mysql var]# /etc/init.d/mysqld stop
Shutting down MySQL.... SUCCESS!

3 安全模式启动
[root@mysql var]# mysqld_safe --skip-grant-tables &
/opt/mysql/product/5.5.25a/bin/mysqld_safe --skip-grant-tables &
[1] 10912
[root@mysql var]# 110407 17:39:28 mysqld_safe Logging to '/usr/local/mysql/var//mysql.chinascopefinanical.com.err'.
110407 17:39:29 mysqld_safe Starting mysqld daemon with databases from /usr/local/mysql/var/
4 无密码root帐号登陆
[root@mysql var]# /usr/bin/mysql -uroot -p 【注释，在下面的要求你输入密码的时候，你不用管，直接回车键一敲就过去了】
Enter password:
Welcome to the MySQL monitor. Commands end with ; or \g.
Your MySQL connection id is 48
Server version: 5.1.41-log Source distribution

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> use mysql;
Database changed
设置密码：
mysql>use mysql;
mysql>update mysql.user set authentication_string=password('lvshen') where user='root' ;
旧版：
mysql>update user set password=password('root') where user='root' and host='localhost';
mysql> set global validate_password_policy=0;
mysql> set global validate_password_length=1;
```

##### 批量操作

###### java

```java
//批量操作时，先将索引字段排序，有利于避免死锁.按顺序加锁不会产生锁环
stockAllocateList.sort(Comparator.comparing(StockAllocate::getSkuId));
stockAllocateList.forEach(item -> {
    int size = this.dao.updateSubAllocateStock(item);
    if (size <= 0) {
        throw new BusinessException("stockSubAllocate_error", stockAllocateVOS.toString());
    }
});
```

###### sql

```sql
<update id="updateSubAllocateStock" >
   update stock set allocate_stock = allocate_stock - #{commodityNumber}
   where sku_id=#{skuId} and allocate_stock >= #{commodityNumber}
</update>
```

##### 锁查询

```sql
select * from information_schema.innodb_locks;
show engine innodb status;
SELECT * FROM information_schema.innodb_lock_waits;

SHOW VARIABLES LIKE 'optimizer_trace';
SET optimizer_trace="enabled=on";
SELECT * FROM student WHERE name = 'fly';
SELECT * FROM information_schema.OPTIMIZER_TRACE;

--MySQL监控
-- 开启标准监控
CREATE TABLE innodb_monitor (a INT) ENGINE=INNODB;
-- 关闭标准监控
DROP TABLE innodb_monitor;
-- 开启锁监控
CREATE TABLE innodb_lock_monitor (a INT) ENGINE=INNODB; 
-- 关闭锁监控
DROP TABLE innodb_lock_monitor;

--在 MySQL 5.6.16 之后，可以通过设置系统参数来开启锁监控
-- 开启标准监控
set GLOBAL innodb_status_output=ON;
-- 关闭标准监控
set GLOBAL innodb_status_output=OFF;
-- 开启锁监控
set GLOBAL innodb_status_output_locks=ON;
-- 关闭锁监控
set GLOBAL innodb_status_output_locks=OFF;
--MySQL 提供了一个系统参数 innodb_print_all_deadlocks 专门用于记录死锁日志，当发生死锁时，死锁日志会记录到 MySQL 的错误日志文件中
set GLOBAL innodb_print_all_deadlocks=ON;
```

##### 死锁

<img src="E:\work\study\相关截图\死锁截图.png" alt="image-20200119142507106"  />

```sql
--事务A
mysql> begin;
Query OK, 0 rows affected

--执行顺序 1
mysql> update student set age=31
 where name = 'fly';
Query OK, 1 row affected
Rows matched: 1  Changed: 1  Warnings: 0

--执行顺序 3
mysql> update student set age=32 where name = 'alan';
Query OK, 1 row affected
Rows matched: 1  Changed: 1  Warnings: 0


--事务B
mysql> begin;
Query OK, 0 rows affected (0.00 sec)

--执行顺序 2
mysql> update student set age=31 where name = 'alan';
Query OK, 1 row affected (0.00 sec)
Rows matched: 1  Changed: 1  Warnings: 0

--执行顺序 4
mysql> update student set age=21 where name = 'fly';
ERROR 1213 (40001): Deadlock found when trying to get lock; try restarting transaction

```



#### 使用Mybatis操作

注意不要先查询再操作

##### 删除

```java
//推荐
@Test
public void testDelete() {
   Student student = new Student();
   student.setName("Hare");
   int delete = studentRepository.delete(student);
   log.info("删除{}条数据",delete);
}
//不推荐
public void testDelete() {
   Student student = studentRepositoryg.getByName("Hare");
   int delete = studentRepository.delete(student);
   log.info("删除{}条数据",delete);
}
```

##### 更新

```java
//更新
@Test
public void testUpdate() {
   Student student = new Student();
   student.setId("2073678416402304");
   student.setAge(26);
   studentRepository.update(student);
   log.info("test is success!!!");
}

//按条件更新
@Test
public void testUpdateByExample() {
   //修改内容
   Student student = new Student();
   student.setAge(27);
   //修改条件
   Example example = new Example(Student.class);
   Example.Criteria criteria = example.createCriteria();
   criteria.andIn("name", ImmutableList.of("fly","lvshen"));
   int i = studentRepository.updateByExample(student, example);
   log.info("test is success!!!{}",i);
}

//不推荐
@Test
public void testUpdate() {
   Student student = studentRepository.get("2073678416402304");
   studentRepository.update(student);
   log.info("test is success!!!");
}
```

#### linux命令

```shell
#内存信息
jmap -F -dump:format=b,file=test.bin 22829（pid）

#堆信息
jstat -gcutil 22829（pid）
```

#### Navicat快捷键

```shell
ctrl+r 运行当前查询窗口的所有sql语句
ctrl+shift+r 只运行选中的sql语句
ctrl+/ 注释sql语句
ctrl+shift +/ 解除注释**
ctrl+q 打开查询窗口
ctrl+n 打开一个新的查询窗口
ctrl+w 关闭当前查询窗口
ctrl+l 删除一行
Shift+Home 鼠标在当前一行末尾，按快捷选中当前一行
F6: 打开一个mysql命令行窗口
ctrl + l: 删除一行
##可以一用
F7: 运行从光标当前位置开始的一条完整sql语句  
```

mobaxterm

#### 代码规范

```
【推荐】尽量不要拿HashMap当返回结果。
【推荐】mybatis中能用#{}时不要用${}，#{}能防止SQL注入
【推荐】如果应用层能够保证一致性，外键关联尽量少用
【强制】in操作考虑集合数量，单个in控制在1000以内。

【推荐】如无特别需要，避免使用大字段（BLOB、CLOB）
【强制】文本字段类型mysql使用 varchar,oracle使用varchar2
【强制】单表索引不超过5个。
【强制】浮点数字段用decimal,不要用float。
【推荐】字段一律用utf-8,如果要存表情，使用utfmb4。
【推荐】小数字段对应entity使用BigDecimal。
【推荐】如果字段长度过大，另外独立一张表存储，主键关联，避免影响索引的效率。
【强制】主键pk_开头,唯一索引uk_开头，普通索引idx_开头,外键fk_开头。
【强制】查询接口如果数据量过多，需要分页返回。
【强制】接口提供方必须考虑幂等性，防止重复调用导致严重的业务灾难。
【推荐】Rest接口必须标明请求的content_type。比如content_type=applicaton/json
【强制】Rest接口返回值必须是Result对象，封装了错误码，错误描述，isSuccess,data信息。data放业务数据。业务错误码自己定义，从1开始，避免使用通用错误码，通用错误码参考BizExceptionEnum。

比如{code:”0”,msg:”操作成功”，data:{XX}}。
【强制】所有的枚举类型字段必须要有注释，说明每个数据项的用途。
【强制】严禁输出大量无效日志，比如在大循环中输出日志。
【推荐】异常日志必须包含堆栈信息和现场参数。
【推荐】避免直接抛RuntimeException,使用业务异常，比如DaoException,ServiceException。
【推荐】不允许捕获异常后不做任何处理，如果不想处理，抛出去。
【推荐】不允许对大段代码进行try-catch。
【强制】业务查询返回数据过多必须分页，比如不能超过5000条返回数据。
【强制】批量操作必须分组，比如批量插入一千条数据，分为500一组。

正例：List<List> groups = Lists.partition(list,500);
【推荐】类不能超过500行。
【强制】方法不能有副作用，比如查询类方法，不允许改变入参的属性值。

【推荐】一个方法只做一件事情，方法不能超过200行。
【推荐】参数和返回值不要用Map这种泛化参数。
【强制】方法的参数不允许超过5个。
【推荐】尽量少用else，使用卫语句。比如：if(condition) {return obj;} 其他逻辑; 如果实在if-else多，采用状态模式。
```

正例：超过 3 层的 if-else 的逻辑判断代码可以使用卫语句、策略模式、状态模式等来实现，

其中卫语句示例如下：

```java
    public void today() {
      if (isBusy()) {
       System.out.println(“change time.”);
       return; 
      }
      if (isFree()) {
       System.out.println(“go to travel.”);
       return;
      }
      return;
    }

```

【推荐】有返回值的函数尽量不要修改入参。
【强制】有并发修改同一个对象的场景，需要加锁，并发修改的概率大于20%，使用悲观锁，否则使用乐观锁, 乐观锁的重试次数不得小于 3 次。
【推荐】高并发时，考虑锁的性能，尽量用无锁数据结构，能锁区块就不要锁整个方法，能锁对象及不要锁整个类。
【强制】SimpleDateFormat线程不安全不要定义为static变量。

正例：注意线程安全，使用 DateUtils 。亦推荐如下处理：

```java
    private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
     @ Override
     protected DateFormat initialValue() {
      return new SimpleDateFormat("yyyy-MM-dd");
     }
    };

```

【强制】不能在foreach循环中删除集合元素，删除元素使用迭代器。

正例：

```java
    Iterator<String> iterator = list.iterator();
    while (iterator.hasNext()) {
     String item = iterator.next();
     if (删除元素的条件) {
      iterator.remove();
     }
    }

```

反例：

```java
    List<String> a = new ArrayList<String>();
    list.add("1");
    list.add("2");
    for (String item : list) {
     if ("1".equals(item)) {
      list.remove(item);
     }
    }
```

```
【推荐】类的方法的访问控制从严。类的方法只在内部使用必须是private,只对继承类开放，必须是protected，变量跟方法类似
【推荐】Redis等缓存产品只能用于缓存，不能当做持久化使用，当Redis挂掉后要保证数据能从其他持久化存储中恢复。
【推荐】按展示层，模型层（业务逻辑），持久层进行分层，也可以按领域驱动设计分：展示层，应用层，模型层，持久层
【推荐】Mybatis中多个条件的查询尽量不要用HashMap，HashMap可能会导致数据库不走索引,用到索引的必须用对象。
【强制】禁止在WHERE条件的属性上使用函数或者表达式。

正例：SELECT uid FROM t_user WHERE day>= to_date('2017-02-15 00:00:00')

反例：SELECT uid FROM t_user WHERE to_char(day)>='2017-02-15' 
【推荐】尽量不使用存储过程，触发器，函数，减少维护成本。
```


