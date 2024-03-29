package com.kap.service.dao;

import com.kap.core.dto.*;
import com.kap.core.dto.eb.ebd.EBDSqCertiListDTO;
import com.kap.core.dto.eb.ebd.EBDSqCertiSearchDTO;
import com.kap.core.dto.sm.smk.SMKTrendDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 일반사용자 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: EBACouseMapper.java
 * @Description		: 일반사용자 관리를 위한 DAO
 * @author 양현우
 * @since 2023.11.09
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.09		양현우				   최초 생성
 *    2024.01.11        이옥정             사용자 퀵메뉴 트랜드 리스트 추가
 * </pre>
 */
@Mapper
public interface COCommMapper {
    /**
     * 신청자, 부품사 정보 조회
     */
    public COUserCmpnDto getMemCmpnDtl(int memSeq);

    /**
     * 신청자 정보 변경
     */
    public int updateUserDtl(COUserCmpnDto cOUserCmpnDto);

    /**
     * 신청자 회사 정보 변경
     */
    public int updatePartsCompany(COUserCmpnDto cOUserCmpnDto);

    /**
     * 사용자 상단 공지사항
     */
    public List<COFrontHeaderNtfyDTO> getHeaderNtfyList();

    /**
     * 사용자 퀵메뉴 트랜드 리스트
     */
    public List<SMKTrendDTO> quickTrendList(SMKTrendDTO sMKTrendDTO);
}
