package com.kap.service;

import com.kap.core.dto.wb.WBAppctnTrnsfDtlDTO;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterSearchDTO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 스마트공장구축 > 신청업체관리 Service
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.16  김동현         최초 생성
 * </pre>
 */
public interface WBFBRegisterCompanyService {

    /**
     *  사업회차 연도 검색
     */
    public List<String> getOptYearList(WBFBRegisterSearchDTO wBFBRegisterSearchDTO) throws Exception;

    /**
     * 선택 연도 값에 따른
     * 회차 SELECT Ajax
     */
    public List<String> getOptEpisdList(WBFBRegisterSearchDTO wBFBRegisterSearchDTO) throws Exception;

    /**
     선택 연도/회차 값에 따른
     * 과제명/사업유형 SELECT Ajax
     */
    public WBRoundMstDTO getOptnList(WBFBRegisterSearchDTO wBFBRegisterSearchDTO) throws Exception;


    /**
     *   신청부품사 목록 List Get
     */
    public WBFBRegisterSearchDTO getRegisterCompanyList(WBFBRegisterSearchDTO wBFBRegisterSearchDTO) throws Exception;

    /**
     *   신청 부품사 등록 Insert
     */
    public int putRegisterCompany(WBFBRegisterDTO wBFBRegisterDTO) throws Exception;

    /**
     *   신청 부품사 Get Detail
     */
    public WBFBRegisterSearchDTO getRegisterDtl(WBFBRegisterSearchDTO wBFBRegisterSearchDTO) throws Exception;

    /**
     *   신청 부품사 연관 RsumDtl 테이블 Get Detail
     */
    public WBFBRegisterSearchDTO getEditInfo(WBFBRegisterSearchDTO wBFBRegisterSearchDTO) throws Exception;

    /**
     *  신청자 변경 이력 Get
     */
    public WBFBRegisterSearchDTO getAppctnTrnsfDtl(WBFBRegisterSearchDTO wBFBRegisterSearchDTO) throws Exception;

    /**
     *  사업자등록번호 Check
     */
    public WBFBRegisterSearchDTO getBsnmNoCheck(WBFBRegisterDTO wBFBRegisterDTO) throws Exception;

    /**
     *   신청 부품사 연관 RsumDtl 테이블 Get Detail
     */
    public int updRegisterCompany(WBFBRegisterDTO wBFBRegisterDTO) throws Exception;

    /**
     *  신청 부품사 삭제
     */
    public int deleteRegister(WBFBRegisterDTO wBFBRegisterDTO) throws Exception;

    /**
     *  신청 부품사 삭제전 - 사용자 / 관리자 상태 확인
     */
    public int confDeleteRegister(WBFBRegisterDTO wBFBRegisterDTO) throws Exception;

    /**
     *  신청 부품사 등록 - 사용자
     */
    public int insertApply(WBFBRegisterDTO wBFBRegisterDTO, MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception;

    /**
     *  부품사 회원 정보 조회 - 사용자
     */
    public WBFBRegisterSearchDTO getCompanyUserDtl(WBFBRegisterSearchDTO wBFBRegisterSearchDTO) throws Exception;

    /**
     *  신청 정보 조회 - 사용자
     */
    public WBFBRegisterSearchDTO getApplyDtl(WBFBRegisterSearchDTO wBFBRegisterSearchDTO) throws Exception;

    /**
     *  엑셀 Download
     */
    public void excelDownload(WBFBRegisterSearchDTO wBFBRegisterSearchDTO, HttpServletResponse response) throws Exception;

}
