/*
package com.lvshen.demo.date;

import com.google.common.collect.Lists;
import com.yjh.mushroom.common.dto.tuple.Pair;
import com.yjh.mushroom.utils.time.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

*/
/**
 * Description: 日期插入测试
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/6/10 22:03
 * @since JDK 1.8
 *//*

@Slf4j
public class DateTest {

	private static final String SUCCESS = "success";
	private static final String WRONG = "wrong";

	public static void main(String[] args) {

		List<DateEntity> dateEntities = Lists.newArrayList();

		DateEntity date1 = getDateEntity("2019-06-05 00:00:00", "2019-06-12 23:59:59");
		DateEntity date2 = getDateEntity("2019-06-18 00:00:00", "2019-06-20 23:59:59");
		DateEntity date3 = getDateEntity("2019-06-22 00:00:00", "2019-06-27 23:59:59");
		DateEntity date4 = getDateEntity("2019-06-01 00:00:00", "2019-06-02 23:59:59");

		dateEntities.add(date1);
		dateEntities.add(date2);
		dateEntities.add(date3);
		dateEntities.add(date4);

		String startDate = "2019-05-30 00:00:00";
		String endDate = "2019-05-29 23:59:59";

		DateEntity buildDate = getDateEntity(startDate, endDate);
		String status = createDate(buildDate, dateEntities);
		System.out.println(status);

	}

	private static Date stringToDate(String dateStr) {
		return DateTimeUtils.getFormatDate(dateStr);
	}

	private static DateEntity getDateEntity(String startDate, String enDate) {
		return new DateEntity(stringToDate(startDate), stringToDate(enDate));
	}

	private static String createDate(DateEntity dateEntity, List<DateEntity> dateEntities) {
		if (dateEntity == null || CollectionUtils.isEmpty(dateEntities)) {
			return "";
		}

		Date startDate = dateEntity.getStartDate();
		Date endDate = dateEntity.getEndDate();

		Optional<DateEntity> max = dateEntities.stream().max(Comparator.comparing(DateEntity::getEndDate));
		Optional<DateEntity> min = dateEntities.stream().min(Comparator.comparing(DateEntity::getStartDate));
		Date maxDate = max.get().getEndDate();
		Date minDate = min.get().getStartDate();

		if (DateTimeUtils.afterEqual(startDate, endDate)) {
			log.info("日期不可创建，开始时间不能大于结束时间");
			return WRONG;
		}

		if (DateTimeUtils.afterEqual(startDate, maxDate) || DateTimeUtils.beforeEqual(endDate, minDate)) {
			log.info("日期可创建！！！！，极值范围外");
			return SUCCESS;
		}

		for (int index = 0; index < dateEntities.size(); index++) {
			if (index == dateEntities.size() - 1) {
				break;
			}
			if (DateTimeUtils.afterEqual(startDate, dateEntities.get(index).getEndDate())
					&& DateTimeUtils.beforeEqual(endDate, dateEntities.get(index + 1).getStartDate())) {
				log.info("日期可创建！！！！，活动与活动之间");
				return String.valueOf(index);
			} else {
				log.info("无法插入");
				return WRONG;
			}
		}

		return WRONG;
	}

	@Test
	public void test1() {
		Date formatDate = DateTimeUtils.getFormatDate("2019-10-04 16:14:56");
		long diffMinutes = ChronoUnit.MINUTES.between(formatDate.toInstant(),Instant.now());
		System.out.println(diffMinutes);

	}

	@Test
	public void test2() {
		Date now = new Date();
		Date dateFirstTime = DateTimeUtils.getDateFirstTime(now);
		Date dateEndTime = DateTimeUtils.getDateEndTime(now);
		System.out.println(dateFirstTime);
		System.out.println(dateEndTime);
	}

	@Test
	public void test3() {
		Date now = new Date();
		String s = now.toString();
		System.out.println(s);
		Instant instant = Instant.now();
		String s1 = instant.toString();
		System.out.println(s1);

	}

	@Test
	public void testLocalDate() {
		LocalDate localDate = LocalDate.now();
		LocalTime localTime = LocalTime.of(12, 20);
		LocalDateTime localDateTime = LocalDateTime.now();
		OffsetDateTime offsetDateTime = OffsetDateTime.now();
		ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Europe/Paris"));
		System.out.println("program execute Successful!!!");
	}

	@Test
	public void testInstant() {
		Instant instant = Instant.now();
		Instant instant1 = instant.plus(Duration.ofMillis(5000));
		Instant instant2 = instant.minus(Duration.ofMillis(5000));
		Instant instant3 = instant.minusSeconds(10);
		System.out.println("program execute Successful!!!");
	}

	@Test
	public void testDuration() {
		Duration duration = Duration.ofMillis(5000);
		duration = Duration.ofSeconds(60);
		duration = Duration.ofMinutes(10);
		System.out.println("program execute Successful!!!");

	}

	@Test
	public void testPeriod() {
		Period period = Period.ofDays(6);
		period = Period.ofMonths(6);
		period = Period.between(LocalDate.now(), LocalDate.now().plusDays(60));
		System.out.println("program execute Successful!!!");
	}

	@Test
	public void testPair() {
		Pair<Integer, Integer> pair = getPair();
		log.info("元素：{}，{}",pair.getFirst(),pair.getSecond());
	}

	private Pair<Integer,Integer> getPair() {
		Pair<Integer,Integer> pair = new Pair<>();
		pair.setFirst(11);
		pair.setSecond(22);
		return pair;
	}

	@Test
	public void testNanoTime() {
		long nanoTime = System.nanoTime();
		log.info("testNanoTime:{}",nanoTime);
	}

	//有关活动校验算法，要求活动不能有重叠
	@Test
	public void testValidateActivity() {
		List<DateEntity> dateEntities = Lists.newArrayList();

		DateEntity date1 = getDateEntity("2019-06-05 00:00:00", "2019-06-12 23:59:59");
		DateEntity date2 = getDateEntity("2019-06-18 00:00:00", "2019-06-20 23:59:59");
		DateEntity date3 = getDateEntity("2019-06-22 00:00:00", "2019-06-27 23:59:59");
		DateEntity date4 = getDateEntity("2019-06-01 00:00:00", "2019-06-02 23:59:59");

		dateEntities.add(date1);
		dateEntities.add(date2);
		dateEntities.add(date3);
		dateEntities.add(date4);

		String startDateStr = "2019-06-26 00:00:00";
		String endDateStr = "2019-06-30 23:59:59";

		DateEntity buildDate = getDateEntity(startDateStr, endDateStr);
		Date startDate = buildDate.getStartDate();
		Date endDate = buildDate.getEndDate();

		validateActivity(startDate,endDate,dateEntities);
		log.info("test is over!!!");


	}

	private void validateActivity(Date startDate, Date endDate, List<DateEntity> enableActivityList) {
		if (DateTimeUtils.afterEqual(startDate, endDate)) {
			log.info("活动开始时间{}不能大于活动结束时间{}}", startDate, endDate);
			return;
		}

		if (CollectionUtils.isEmpty(enableActivityList)) {
			return;
		}

		//待编辑的活动在所有开启的活动范围之内，需要判断此活动开始日期是否大于某个活动结束日期，结束日期小于某个活动下一个活动的开始日期
		if (validateInnerActivityDate(startDate,endDate,enableActivityList)) {
			return;
		}

		//待编辑活动在所有已开启活动范围之外
		if (validateOuterActivityDate(startDate,endDate,enableActivityList)) {
			return;
		}

	}

	private boolean validateInnerActivityDate(Date startDate, Date endDate, List<DateEntity> enableActivityList) {

		Pair<Optional<DateEntity>, Optional<DateEntity>> optional = getOptional(enableActivityList);
		Optional<DateEntity> min = optional.getFirst();
		Optional<DateEntity> max = optional.getSecond();
		if (!max.isPresent() || !min.isPresent()) {
			return true;
		}
		Date maxDate = max.get().getEndDate();
		Date minDate = min.get().getStartDate();
		//待编辑的活动在所有开启的活动范围之内，需要判断此活动开始日期是否大于某个活动结束日期，结束日期小于某个活动下一个活动的开始日期
		if (DateTimeUtils.beforeEqual(endDate, maxDate) && DateTimeUtils.afterEqual(startDate, minDate)) {
			for (int index = 0; index < enableActivityList.size(); index++) {
				if (index == enableActivityList.size() - 1) {
					break;
				}
				if (DateTimeUtils.afterEqual(startDate, enableActivityList.get(index).getEndDate())
						&& DateTimeUtils.beforeEqual(endDate, enableActivityList.get(index + 1).getStartDate())) {
					return true;
				}
			}
			log.info("待编辑的活动在所有开启的活动范围之内--" +
					"【编辑的当前活动的结束时间不能大于下一个活动周期的开始时间】或【编辑的当前活动的开始时间不能小于上一个活动周期的结束时间】");
			return true;
		}
		return false;
	}

	private boolean validateOuterActivityDate(Date startDate, Date endDate,List<DateEntity> enableActivityList) {

		Pair<Optional<DateEntity>, Optional<DateEntity>> optional = getOptional(enableActivityList);
		Optional<DateEntity> min = optional.getFirst();
		Optional<DateEntity> max = optional.getSecond();
		if (!max.isPresent() || !min.isPresent()) {
			return true;
		}
		Date maxDate = max.get().getEndDate();
		Date minDate = min.get().getStartDate();
		if (DateTimeUtils.afterEqual(endDate, maxDate)
				&& DateTimeUtils.afterEqual(startDate, maxDate)) {
			return true;
		}
		if (DateTimeUtils.afterEqual(endDate, maxDate)
				&& DateTimeUtils.beforeEqual(startDate, maxDate)) {
			log.info("待编辑活动在所有已开启活动范围之外--【编辑的当前活动的开始时间不能小于最大活动的结束时间】");
			return true;
		}
		if (DateTimeUtils.beforeEqual(startDate, minDate)
				&& DateTimeUtils.beforeEqual(endDate, minDate)) {
			return true;
		}
		if (DateTimeUtils.beforeEqual(startDate, minDate)
				&& DateTimeUtils.afterEqual(endDate, minDate)) {
			log.info("待编辑活动在所有已开启活动范围之外--【编辑的当前活动的结束时间不能大于最早活动的开始时间】");
		}
		return false;
	}

	private Pair<Optional<DateEntity>,Optional<DateEntity>> getOptional(List<DateEntity> enableActivityList) {
		Pair<Optional<DateEntity>,Optional<DateEntity>> pair = new Pair<>();
		Optional<DateEntity> max = enableActivityList.stream().max(Comparator.comparing(DateEntity::getEndDate));
		Optional<DateEntity> min = enableActivityList.stream().min(Comparator.comparing(DateEntity::getStartDate));
		pair.setFirst(min);
		pair.setSecond(max);
		return pair;
	}

}
*/
