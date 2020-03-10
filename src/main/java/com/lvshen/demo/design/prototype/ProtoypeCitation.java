package com.lvshen.demo.design.prototype;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Description:原型模式，对象拷贝
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/30 13:55
 * @since JDK 1.8
 */
@Slf4j
public class ProtoypeCitation {

	@Test
	public void test() throws CloneNotSupportedException {
		Citation obj1 = new Citation("张三", "同学：在2016学年第一学期中表现优秀，被评为三好学生。", "韶关学院");
		obj1.display();
		Citation obj2 = (Citation) obj1.clone();
		obj2.setName("李四");
		obj2.display();
	}

	@Data
	class Citation implements Cloneable {
		String name;
		String info;
		String college;

		public Citation(String name, String info, String college) {
			this.name = name;
			this.info = info;
			this.college = college;
			log.info("Citation创建成功");
		}

		@Override
		public Object clone() throws CloneNotSupportedException {
			log.info("拷贝成功");
			return super.clone();
		}

		void display() {
			log.info(name + info + college);
		}
	}
}
