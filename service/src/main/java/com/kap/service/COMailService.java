package com.kap.service;

import com.kap.core.dto.COMailDTO;

public interface COMailService {
	
	/**
     * 메일 발송 처리
     */
    public int sendMail(COMailDTO cOMailDTO, String templateFile) throws Exception;
}