package org.antstudio.autocode.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ���ڱ�ʾ�ֶ��Ƿ���Ҫ�־û�
 * @author Gavin
 * @date 2013-10-20 ����5:07:30
 */
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {

	public String name();
	
	public String dbType() default "";
}
