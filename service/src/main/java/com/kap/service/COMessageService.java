package com.kap.service;

import com.kap.core.dto.COMailDTO;
import com.kap.core.dto.COSmsDTO;

import java.util.HashMap;

public interface COMessageService {
	
	/**
     * 메일 발송 처리
     */
    public HashMap<String, Object> sendMail(COMailDTO cOMailDTO, String templateFile) throws Exception;

    /**
     * SMS 발송 처리
     */
    public HashMap<String, Object> sendSms(COSmsDTO cOSmsDTO, String templateFile) throws Exception;
}