package com.kap.core.utility;

import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.Properties;


/**
 *  Class Name : EgovProperties.java
 *  Description : properties값들을 파일로부터 읽어와   Globals클래스의 정적변수로 로드시켜주는 클래스로
 *   문자열 정보 기준으로 사용할 전역변수를 시스템 재시작으로 반영할 수 있도록 한다.
 *  Modification Information
 *
 *   수정일              수정자          수정내용
 *   ----------  --------  ---------------------------
 *   2009.01.19  박지욱          최초 생성
 *	 2011.07.20    서준식 	   Globals파일의 상대경로를 읽은 메서드 추가
 *	 2014.10.13    이기하 	   Globals.properties 값이 null일 경우 오류처리
 *   2019.04.26    신용호 	   RELATIVE_PATH_PREFIX Path 적용 방식 개선
 *  @author 공통 서비스 개발팀 박지욱
 *  @since 2009. 01. 19
 *  @version 1.0
 *  @see
 *
 */

public class COApplicationProperties {

	//파일구분자
	final static  String FILE_SEPARATOR = System.getProperty("file.separator");


	/**
	 * 인자로 주어진 문자열을 Key값으로 하는 프로퍼티 값을 반환한다(Globals.java 전용)
	 * @param keyName String
	 * @return String
	 */
	public static String getProperty(String keyName) 
	{
		String value = "";
		
		FileInputStream fis = null;

		ClassPathResource resource = new ClassPathResource("application.yml");
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
			Properties props = new Properties();

			props.load(br);
			if (props.getProperty(keyName) == null)
			{
				return "";
			}
			value = props.getProperty(keyName).trim();
		} 
		catch (FileNotFoundException fne) 
		{
			throw new RuntimeException("Property file not found", fne);
		} 
		catch (IOException ioe) 
		{
			throw new RuntimeException("Property file IO exception", ioe);
		} 
		finally 
		{
			EgovResourceCloseHelper.close(fis);
		}
		
		return value;
	}
}