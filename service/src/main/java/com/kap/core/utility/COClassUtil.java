package com.kap.core.utility;

import com.kap.core.annotation.CheckOriginData;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;

/**
 *
 * Class 에 대한 Util 클래스
 * </pre>
 */
public class COClassUtil {

	/**
	 * 두 객체의 필드에 선언된 @CheckOriginData를 기준으로 같은지 여부 확인
	 */
	public static boolean isChangeOriginData(Object modObject, Object oriObject)
	{
		boolean check = false;
		HashMap<String, String> checkMap = new HashMap<String, String>();
		try{
			for(Field field : modObject.getClass().getDeclaredFields()){
				field.setAccessible(true);
				Annotation annotation  = field.getAnnotation(CheckOriginData.class);
				if(annotation != null){
					checkMap.put(field.getName(), String.valueOf(field.get(modObject)));
				}
			}
			for(Field field : oriObject.getClass().getDeclaredFields()){
				field.setAccessible(true);
				Annotation annotation  = field.getAnnotation(CheckOriginData.class);
				if(annotation != null){
					String value = String.valueOf(field.get(oriObject));
					if(!checkMap.get(field.getName()).equals(value)){
						check = true;
						continue;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();;
		}
		return check;
	}
}