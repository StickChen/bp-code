package com.shallop.bpc.collection.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 扩展spring的BeanUtils，增加拷贝属性排除null值的功能，不处理空String
 */
@SuppressWarnings("rawtypes")
public class BeanUtils2 extends org.springframework.beans.BeanUtils{
	
	/**仿照Spring的BeanUtils，增加拷贝属性排除null值的功能，不处理空String
	 * @param source
	 * @param target
	 * @throws BeansException
	 */
	public static void copyNotNullProperties(Object source, Object target) throws BeansException {
		copyNotNullProperties(source, target, null, null);
	}
	
	public static void copyNotNullProperties(Object source, Object target, String[] ignoreProperties){
		copyNotNullProperties(source, target, null, ignoreProperties);
	}

	
	public static void copyNotNullProperties(Object source, Object target, Class<?> editable){
		copyNotNullProperties(source, target, editable, null);
	}
	
	/**仿照Spring的BeanUtils，增加拷贝属性排除null值的功能，不处理空String
	 * @param source
	 * @param target
	 * @param editable	限制需要拷贝属性的类或接口，目标类必须是其实例
	 * @param ignoreProperties	要忽略的属性集合
	 * @throws BeansException
	 */
	public static void copyNotNullProperties(Object source, Object target, Class<?> editable, String[] ignoreProperties){
		Assert.notNull(source,"Source must not be null");
		Assert.notNull(target,"Target must not be null");
		Class<?> actualEditable = target.getClass();
		if (editable != null) {
			if (!editable.isInstance(target)) {
				throw new IllegalArgumentException("Target class [" + target.getClass().getName() + "] not assignable to Editable class [" + editable.getName() + "]");
			}
			actualEditable = editable;
		}
		// 获取目标类的属性描述
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		List<String> ignoreList = (ignoreProperties !=null ) ? Arrays.asList(ignoreProperties):null;
		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null && (ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
				// 获取source类的属性描述
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Method writeMethod = targetPd.getWriteMethod();
						if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
							// 如果方法访问控制符不为public，则强制设为可写
							writeMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						// source的获取属性方法返回结果不为空
						if (value != null) {
							boolean isEmpty = false;
							/*
							if (value instanceof String) {
								// 不为空串
								String s = (String) value;
								if ("".equals(s.trim())) {
									isEmpty = true;
								}
							}
							*/
							if (value instanceof Set) {
								// 不为空Set
								Set s = (Set) value;
								if (s == null || s.isEmpty()) {
									isEmpty = true;
								}
							}else if (value instanceof Map) {
								// 不为空Map
								Map m = (Map) value;
								if (m == null || m.isEmpty()) {
									isEmpty = true;
								}
								Class<?>[] parameterTypes = writeMethod.getParameterTypes();
								Map<Object, Object> objects = new HashMap<>();
								Class<?> parameterType = parameterTypes[0];
								for (Object o : m.entrySet()) {
									Map.Entry o1 = (Map.Entry) o;

									Object instantiate = instantiate(parameterType);
									copyNotNullProperties(o1, instantiate);
									Object newO = instantiate(parameterTypes[1]);
									copyNotNullProperties(newO, o);
									objects.put(instantiate, newO);
								}
								value = objects;
							}else if (value instanceof List) {
								// 不为空list
								List l = (List) value;
								if (l == null || l.size() < 1) {
									isEmpty = true;
								}
								Class<?>[] parameterTypes = writeMethod.getParameterTypes();
								List<Object> objects = new ArrayList<>();
								Class<?> parameterType = parameterTypes[0];
								for (Object o : l) {
									Object newO = instantiate(parameterType);
									copyNotNullProperties(newO, o);
									objects.add(newO);
								}
								value = objects;
							}else if (value instanceof Collection) {
								// 不为空集合
								Collection c = (Collection) value;
								if (c == null || c.size() < 1 ) {
									isEmpty = true;
								}
							}
							// 不为空才进行复制
							if (!isEmpty) {

								// 复制属性
								writeMethod.invoke(target, value);
							}
						}
					} catch (Exception e) {
						throw new FatalBeanException("Could not copy properties from source to target", e);
					}
				}
			}
			
		}
	}
	
}
