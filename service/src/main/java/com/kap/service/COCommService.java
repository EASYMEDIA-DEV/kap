package com.kap.service;


import com.kap.core.dto.COFrontHeaderNtfyDTO;
import com.kap.core.dto.COUserCmpnDto;
import com.kap.core.dto.co.COCNiceMyResDto;
import com.kap.core.dto.co.COCNiceReqEncDto;
import com.kap.core.dto.co.COCNiceServiceDto;
import com.kap.core.dto.co.COCompApiResDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <pre> 
 * 공통 Service
 * </pre>
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.15		이옥정				   최초 생성
 * </pre>
 */
public interface COCommService {
    /**
     * 신청자, 부품사 정보 조회
     */
    public COUserCmpnDto getMemCmpnDtl(int memSeq) throws Exception;

    /**
     * 신청자, 부품사 정보 조회
     */
    public int setMemCmpnDtl(COUserCmpnDto cOUserCmpnDto, HttpServletRequest request) throws Exception;

    /**
     * 사용자 상단 공지사항
     */
    public List<COFrontHeaderNtfyDTO> getHeaderNtfyList() throws Exception;

    COCompApiResDto niceChk(String compNum) throws Exception;

    COCNiceServiceDto idnttvrfct(HttpServletRequest request , COCNiceReqEncDto cocNiceReqEncDto) throws Exception;

    COCNiceMyResDto idnttvrfctConfirm(String params , HttpServletRequest request) throws Exception;


}
