package com.shallop.bpc.collection.tests;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Mockito测试使用实例
 *
 * @author badqiu
 * @email badqiu(a)gmail.com
 *
 */
public class MockitoListTestDemo extends Assert {

	@SuppressWarnings("unchecked")
	List<String> mockList = mock(List.class);

	@Test
	// 标准测试
	public void test_3A() {
		// arrange
		when(mockList.get(0)).thenReturn("one");	//
		when(mockList.get(1)).thenReturn("two");

		// action
		String result1 = mockList.get(0);

		// assert
		assertEquals(result1, "one");
		verify(mockList).get(0);
	}

	@Test
	// 返回默认值，无需录制动作
	public void test_default_value_return_by_mockito() {
		// action
		String result1 = mockList.get(0);

		// assert
		assertEquals(result1, null);
		verify(mockList).get(0);
	}

	@Test
	// 参数的精确匹配及模糊匹配any()
	public void test_arguments_matche() {
		// 参数匹配,使用的eq()等参数匹配，则必须全部使用匹配符,不使用则不用
		// eq() any() anyInt() anyObject() anyMap() anyCollection()
		when(mockList.get(anyInt())).thenReturn("return me by anyInt()");

		for (int i = 0; i < 100; i++) {
			assertEquals("return me by anyInt()", mockList.get(i));
		}
	}

	@Test
	// 调用次数不同，返回值也不同
	public void test_return_different_values_by_call_times() {
		when(mockList.get(0)).thenReturn("1").thenReturn("2");

		assertEquals(mockList.get(0), "1");
		assertEquals(mockList.get(0), "2");
	}

	@Test
	// 验证方法调用次数
	public void verify_how_many_times_a_method_is_called() {

		mockList.get(0);
		mockList.get(2);
		mockList.get(2);
		mockList.get(2);

		// 方法调用次数验证
		verify(mockList, atLeastOnce()).get(0);
		verify(mockList, never()).get(1);
		verify(mockList, times(3)).get(2);
	}

	// 没有返回值的方法 exception 抛出
	@Test(expected = IllegalStateException.class)
	public void test_throw_exception_with_not_return_value_method() {

		doThrow(new IllegalStateException("please not invocke clear() ")).when(mockList).clear();

		mockList.clear();
	}

	// 有返回值的方法 exception 抛出
	@Test(expected = IllegalStateException.class)
	public void test_throw_exception_with_has_return_value_method() {
		when(mockList.remove(1)).thenThrow(new IllegalStateException("I has return value"));

		mockList.remove(1);
	}

	@Test
	public void test_ArgumentCaptor() {
		ArgumentCaptor<List> argumentCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockList).addAll(argumentCaptor.capture());
		assertEquals("John", argumentCaptor.getValue().get(0));
	}

}
