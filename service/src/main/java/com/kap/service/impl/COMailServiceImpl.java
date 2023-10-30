package com.kap.service.impl;

import com.kap.core.dto.COMailDTO;
import com.kap.service.COMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.StringWriter;
import java.lang.reflect.Field;

@Slf4j
@Service
@RequiredArgsConstructor
public class COMailServiceImpl  implements COMailService {

	//메일 서비스
	private final JavaMailSender mailSender;

	//벨로시티
	private final VelocityEngine velocityEngine;

	//발신자 이메일
	@Value("${app.from-mail}")
	private String fromMail;

	//사용자 도메인
	@Value("${app.user-domain}")
	private String userDomain;
	//관리자 도메인
	@Value("${app.admin-domain}")
	private String adminDomain;

	//관리자 도메인
	@Value("${app.file.mail-tmpl-file-path}")
	private String mailTmplFilePath;


	/**
	 * 메일 발송 처리
	 */
	public int sendMail(COMailDTO cOMailDTO, String templateFile) throws Exception
	{

		MimeMessage message = mailSender.createMimeMessage();
		int toUserSize = 0;
		try
		{
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

			messageHelper.setSubject(cOMailDTO.getSubject());

			String toUsers = cOMailDTO.getEmails();
			if (!"".equals(toUsers) && toUsers != null)
			{
				messageHelper.setTo(toUsers);
				messageHelper.setFrom(fromMail);

				messageHelper.setText(getTemplate(cOMailDTO, templateFile), true);

				// 첨부파일
				String filePath = "", fileName = "", realFileNm = "";

				if (!"".equals(filePath) && !"".equals(fileName) && !"".equals(realFileNm))
				{
					messageHelper.addAttachment(MimeUtility.encodeText(realFileNm, "UTF-8", "B"), new FileDataSource(filePath + File.separator + fileName));
				}

				mailSender.send(message);
			}
			else
			{
				log.debug("toUser Size : 0");
			}
		}
		catch (MessagingException me)
		{
			log.debug(me.getMessage());
		}
		return toUserSize;
	}

	/**
	 * 템플릿 문자열 파싱
	 */
	public String getTemplate(COMailDTO cOMailDTO, String templateFile) throws Exception
	{
		String body = "";

		if (!"".equals(templateFile))
		{
			try {
				//templateFile = mailTmplFilePath + templateFile;
				VelocityContext vc = new VelocityContext();
				vc.put("userDomain", userDomain);
				vc.put("adminDomain", adminDomain);
				for (Field field : cOMailDTO.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					Object value = field.get(cOMailDTO);
					vc.put(field.getName(), value);
				}
				StringWriter sw = new StringWriter();
				sw.getBuffer().setLength(0);
				//templateFile = "/" + templateFile;
				//templateFile = "template/email/COAAdmPwdInit.html";

				velocityEngine.setProperty("file.resource.loader.path", mailTmplFilePath+"email/");

				Template template = velocityEngine.getTemplate(templateFile.replace("/", File.separator), "UTF-8");

				template.merge(vc, sw);
				body = sw.toString();
			}catch (Exception e){
				System.out.println("@@@@ e = " + e);
			}
		}
		else
		{
			body = cOMailDTO.getContent();
		}

		return body;
	}
}