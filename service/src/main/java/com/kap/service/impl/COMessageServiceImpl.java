package com.kap.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kap.core.dto.*;
import com.kap.service.COMessageService;
import com.kap.service.SMHSmsSendYnService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class COMessageServiceImpl implements COMessageService {
	//벨로시티
	private final VelocityEngine velocityEngine;
	/** SMS 발송 서비스 **/
	private final SMHSmsSendYnService smhSmsSendYnService;
	/* 발신자 이메일 */
	@Value("${app.mail.senderMail}")
	private String senderMail;
	/* 발신자 이메일 */
	@Value("${app.mail.senderHp}")
	private String senderHp;
	/* 발신자 이메일 */
	@Value("${app.mail.sender_name}")
	private String sender_name;
	/* directsend id */
	@Value("${app.mail.username}")
	private String username;
	/* directsend 발급 api key */
	@Value("${app.mail.key}")
	private String key;
	/* directsend 발급 Email api 요청 URL */
	@Value("${app.mail.mail_url}")
	private String apiMailUrl;
	/* directsend 발급 SMS api 요청 URL */
	@Value("${app.mail.sms_url}")
	private String apiSmslUrl;

	//홈페이지명
	@Value("${app.site.name}")
	private String siteName;
	//사용자 도메인
	@Value("${app.user-domain}")
	private String userDomain;
	//관리자 도메인
	@Value("${app.admin-domain}")
	private String adminDomain;
	//이메일 템플릿 경로
	@Value("${app.file.template-file-path}")
	private String tmplFilePath;

	/**
	 * 메일 발송 처리
	 */
	public HashMap<String, Object> sendMail(COMailDTO cOMailDTO, String templateFile) throws Exception
	{
		int toUserSize = 0;
		HashMap<String, Object>  responseMap = null;
		if(cOMailDTO.getReceiver().size() == 0){
			new Exception("NO RECEIVERS");
		}
		try
		{
			cOMailDTO.setSender(senderMail);
			cOMailDTO.setSender_name(sender_name);
			cOMailDTO.setUsername(username);
			cOMailDTO.setKey(key);
			cOMailDTO.setBody(getTemplate(cOMailDTO, templateFile));
			if(cOMailDTO.getFileList() != null && cOMailDTO.getFileList().size() > 0){
				String fileUrl = "";
				String fileName = "";
				if(userDomain.indexOf("127.0.0.1") != -1 && userDomain.indexOf("localhost") != -1) {
					for (COFileDTO cOFileDTO : cOMailDTO.getFileList()) {
						fileUrl += ("".equals(fileUrl) ? "" : "|") + (userDomain + cOFileDTO.getWebPath());
						fileName += ("".equals(fileName) ? "" : "|") + cOFileDTO.getOrgnFileNm();
					}
					cOMailDTO.setFile_url(fileUrl);
					cOMailDTO.setFile_name(fileName);
				}
			}
			URL obj = new URL(apiMailUrl);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestProperty("Cache-Control", "no-cache");
			con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
			con.setRequestProperty("Accept", "application/json");
			String urlParameters = new ObjectMapper().writeValueAsString(cOMailDTO);
			log.error("urlParameters : {}", urlParameters);
			System.setProperty("jsse.enableSNIExtension", "false");
			con.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter (con.getOutputStream(), "UTF-8");
			wr.write(urlParameters);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			log.error("responseCode : {}", responseCode);

			/*
			 * responseCode 가 200 이 아니면 내부에서 문제가 발생한 케이스입니다.
			 * directsend 관리자에게 문의해주시기 바랍니다.
			 */
			java.io.BufferedReader in = new java.io.BufferedReader(
					new java.io.InputStreamReader(con.getInputStream(), "UTF-8"));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			ObjectMapper objectMapper = new ObjectMapper();
			TypeReference<HashMap<String, Object>> typeReference = new TypeReference<HashMap<String,Object>>() {};
			responseMap = objectMapper.readValue(response.toString(), typeReference);
			log.error("response : {}", response.toString());
		}
		catch (MessagingException me)
		{
			log.debug(me.getMessage());
		}
		return responseMap;
	}

	/**
	 * SMS 발송 처리
	 */
	public HashMap<String, Object> sendSms(COSmsDTO cOSmsDTO, String templateFile) throws Exception
	{
		SMHSmsSendYnDTO sMHSmsSendYnDTO= new SMHSmsSendYnDTO();
		SMHSmsSendYnDTO rtnSMHSmsSendYnDTO = smhSmsSendYnService.selectSmsSendYnDtl(sMHSmsSendYnDTO);
		if("N".equals(rtnSMHSmsSendYnDTO.getSmsSendYn())){
			HashMap<String, Object> rtnMap = new HashMap<String, Object>();
			rtnMap.put("status", "101");
			rtnMap.put("msg", "SMS 발송을 할 수 없습니다.");
			return rtnMap;
		}
		int toUserSize = 0;
		HashMap<String, Object>  responseMap = null;
		if(cOSmsDTO.getReceiver().size() == 0){
			new Exception("NO RECEIVERS");
		}
		try
		{
			cOSmsDTO.setSender(senderHp);
			cOSmsDTO.setUsername(username);
			cOSmsDTO.setKey(key);
			cOSmsDTO.setMessage(getTemplate(cOSmsDTO, templateFile));
			URL obj = new URL(apiSmslUrl);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestProperty("Cache-Control", "no-cache");
			con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
			con.setRequestProperty("Accept", "application/json");
			String urlParameters = new ObjectMapper().writeValueAsString(cOSmsDTO);
			log.error("urlParameters : {}", urlParameters);
			System.setProperty("jsse.enableSNIExtension", "false");
			con.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter (con.getOutputStream(), "UTF-8");
			wr.write(urlParameters);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			log.error("responseCode : {}", responseCode);

			/*
			 * responseCode 가 200 이 아니면 내부에서 문제가 발생한 케이스입니다.
			 * directsend 관리자에게 문의해주시기 바랍니다.
			 */
			java.io.BufferedReader in = new java.io.BufferedReader(
					new java.io.InputStreamReader(con.getInputStream(), "UTF-8"));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			ObjectMapper objectMapper = new ObjectMapper();
			TypeReference<HashMap<String, Object>> typeReference = new TypeReference<HashMap<String,Object>>() {};
			responseMap = objectMapper.readValue(response.toString(), typeReference);
			log.error("response : {}", response.toString());
		}
		catch (MessagingException me)
		{
			log.debug(me.getMessage());
		}
		return responseMap;
	}

	/**
	 * 템플릿 문자열 파싱
	 */
	public String getTemplate(COMessageDTO cOMessageDTO, String templateFile) throws Exception
	{
		String body = "";
		if (!"".equals(templateFile))
		{
			try {
				//templateFile = mailTmplFilePath + templateFile;
				VelocityContext vc = new VelocityContext();
				vc.put("userDomain", userDomain);
				vc.put("adminDomain", adminDomain);
				vc.put("siteName", siteName);
				for (Field field : cOMessageDTO.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					Object value = field.get(cOMessageDTO);
					vc.put(field.getName(), value);
				}
				StringWriter sw = new StringWriter();
				sw.getBuffer().setLength(0);
				if(cOMessageDTO instanceof COMailDTO){
					velocityEngine.setProperty("file.resource.loader.path", tmplFilePath + "email" + File.pathSeparator);
				}
				else{
					velocityEngine.setProperty("file.resource.loader.path", tmplFilePath + "sms" + File.pathSeparator);
				}
				Template template = velocityEngine.getTemplate(templateFile.replace("/", File.separator), "UTF-8");
				template.merge(vc, sw);
				body = sw.toString();
			}catch (Exception e){
				System.out.println("@@@@ e = " + e);
			}
		}
		else
		{
			if(cOMessageDTO instanceof COMailDTO){
				body = ((COMailDTO)cOMessageDTO).getBody();
			}
			else{
				body = ((COSmsDTO)cOMessageDTO).getMessage();
			}
		}

		return body;
	}
}