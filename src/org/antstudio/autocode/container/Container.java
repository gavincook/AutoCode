package org.antstudio.autocode.container;

import java.util.HashMap;
import java.util.Map;

/**
 * ���������������ʵ���������
 * @author Gavin
 * @date 2013-10-3 ����10:12:33
 */
public class Container {

	private static Map<String,Object> container = new HashMap<String, Object>();
	
	public static void register(String name,Object object){
		container.put(name, object);
	}
	
	public static Object get(String name){
		return container.get(name);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T get(String name,Class<T> className){
		return (T) container.get(name);
	}
}
