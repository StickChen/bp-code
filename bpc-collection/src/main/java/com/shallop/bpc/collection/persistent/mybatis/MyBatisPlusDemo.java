package com.shallop.bpc.collection.persistent.mybatis;

import com.baomidou.mybatisplus.mapper.Wrapper;
import org.junit.Test;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

/**
 * @author chenxuanlong
 * @date 2017/9/19
 */
public class MyBatisPlusDemo {
	/*
	 * 实体带查询使用方法  输出看结果
	 */
	@Test
	public void genericQuery(){


		EntityWrapper<User> ew = new EntityWrapper<User>();
		ew.setEntity(new User(1));
		ew.where("user_name={0}", "'zhangsan'").and("id=1")
				.orNew("user_status={0}", "0").or("status=1")
				.notLike("user_nickname", "notvalue")
				.and("new=xx").like("hhh", "ddd")
				.andNew("pwd=11").isNotNull("n1,n2").isNull("n3")
				.groupBy("x1").groupBy("x2,x3")
				.having("x1=11").having("x3=433")
				.orderBy("dd").orderBy("d1,d2");
		System.out.println(ew.getSqlSegment());

	}

	@Test
	public void genericQuery2(){
		Wrapper eq = Condition.create().setSqlSelect("sum(quantity)").isNull("order_id").eq("user_id", 1).eq("type", 1)
				.in("status", new Integer[] { 0, 1 }).eq("product_id", 1).between("created_time", "startDate", "currentDate")
				.eq("weal", 1);

		Condition.create().setSqlSelect("");

		System.out.println(eq.getSqlSegment());
	}
}
