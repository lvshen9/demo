package com.lvshen.demo.guava.study.strings;

import com.google.common.base.CaseFormat;
import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Description:字符替换
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/6/20 17:47
 * @since JDK 1.8
 */
public class StringDemo {

	@Test
	public void testJoiner() {
		Joiner joiner = Joiner.on(",").skipNulls();
		String join = joiner.join("Harry", null, "Ron");
		System.out.println("join:" + join);

		Joiner joiner1 = Joiner.on(",").useForNull("");
		String content1 = joiner1.join("Harry", null, "Ron", "Hermione");
		System.out.println("content1:" + content1);

		System.out.println(Joiner.on("-").join("a", "b", "c", "d"));
		System.out.println(Joiner.on("-").join(Lists.newArrayList("a", "b", "c", "d")));
	}

	@Test
	public void testMapJoiner() {
		LinkedHashMap<String, String> map = Maps.newLinkedHashMap();
		map.put("name", "doctor");
		map.put("sex", "man");

		Joiner.MapJoiner mapJoiner = Joiner.on("&").withKeyValueSeparator("~");
		String join = mapJoiner.join(map);
		System.out.println(join);
	}

	@Test
	public void testSplitter() {
		String content = ",a,b,,,c,";
		//String content = "a,b";
		// java内置的会忽略[尾部]空内容
		String[] split = content.split(",");
		System.out.println(Arrays.toString(split));

		// guava默认不会忽略空内容
		List<String> splitToList = Splitter.on(",").splitToList(content);
		System.out.println(splitToList.toString());

		// 可以自由选择的忽略，像java一样，忽略空字符串
		List<String> splitToList2 = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(content);
		System.out.println(splitToList2.toString());
	}

	@Test
	public void testCharMatcher() {
		// 1.获取字符串中数字
		String str1 = "1mahesh123ff87f";
		String retainFrom = CharMatcher.digit().retainFrom(str1);
		System.out.println("1:" +retainFrom);

		// 2.把多个空格替换,并去掉首位的空格
		String str2 = "     Mahesh     Parashar  ";
		String collapseFrom = CharMatcher.whitespace().trimAndCollapseFrom(str2, ' ');
		System.out.println("2:" +collapseFrom);

		// 3.去掉转义字符(\t,\n,\b...)
		String str3 = " ab\tcd\nef\bg	a";
		String removeFrom = CharMatcher.javaIsoControl().removeFrom(str3);
		String collapseFrom1 = CharMatcher.whitespace().trimAndCollapseFrom(removeFrom, ' ');
		System.out.println("3:" +collapseFrom1);

		// ★4.把所有的数字用"*"代替
		String str4 = "124abc85dds";
		String replaceFrom = CharMatcher.javaDigit().replaceFrom(str4, "*");
		System.out.println("4:" +replaceFrom);

		// 5.获取所有的数字和小写字母
		String str5 = "124abc85ddsAF1HNsd";
		String retainFrom1 = CharMatcher.javaDigit().or(CharMatcher.javaLowerCase()).retainFrom(str5);
		System.out.println("5:" +retainFrom1);

		// 7.获取所有单字节长度的符号
		String str7 = ",dg,123AH中国";
		String retainFrom2 = CharMatcher.singleWidth().retainFrom(str7);
		System.out.println("7:" +retainFrom2);

		// 8.获取字母
		String str8 = "FirstName LastName +1 123 456 789 !@#$%^&*()_+|}{:\"?><";
		String retainFrom3 = CharMatcher.javaLetterOrDigit().retainFrom(str8);
		System.out.println("8:" +retainFrom3);

		// 9.获取字母和数字
		String retainFrom4 = CharMatcher.javaLetterOrDigit().retainFrom(str8);
		System.out.println("9:" +retainFrom4);

		// 10.出现次数统计
		int count = CharMatcher.any().countIn(str8);
		System.out.println("10:" +count);

		// 11.数字出现次数
		int count1 = CharMatcher.digit().countIn(str8);
		System.out.println("11:" +count1);

		// 12.获得除小写字母外其他所有字符
		String retainFrom5 = CharMatcher.javaLowerCase().negate().retainFrom(str8);
		System.out.println("12:" +retainFrom5);


	}

	@Test
	public void testCaseFormat(){
		//与关于驼峰命名的格式化

		System.out.println(CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, "test-data"));
		System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "test_data"));
		System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, "test_data"));


		System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "TestData"));
		System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, "testData"));
	}
}
