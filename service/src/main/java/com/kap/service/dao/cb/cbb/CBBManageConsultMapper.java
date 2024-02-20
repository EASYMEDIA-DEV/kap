package com.kap.service.dao.cb.cbb;

import com.kap.core.dto.cb.cbb.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * 경영컨설팅 Mapper
 * </pre>
 *
 * @author 이옥정
 * @version 1.0
 * @ClassName : CBBManageConsultMapper.java
 * @Description : 경영컨설팅 Mapper
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2023.11.24	  이옥정	             최초 생성
 * </pre>
 * @see
 * @since 2023.11.24
 */

@Mapper
public interface CBBManageConsultMapper {
    /**
     * 리스트 조회
     */
    public List<CBBManageConsultListDTO> selectManageConsultList(CBBManageConsultSearchDTO cBBManageConsultSearchDTO);

    /**
     * 상세 조회
     */
    public CBBManageConsultInsertDTO selectManageConsultList(CBBManageConsultInsertDTO pCBBManageConsultInsertDTO);

    /**
     * 리스트 갯수 조회
     */
    public int getManageConsultListCnt(CBBManageConsultSearchDTO cBBManageConsultSearchDTO);

    /**
     * 만족도 종합결과 리스트 조회
     */
    public List<CBBConsultSuveyRsltListDTO> selectConsultSuveyRsltList(CBBConsultSuveyRsltListDTO cBBConsultSuveyRsltListDTO);

    /**
     * 만족도 종합결과 리스트 갯수 조회
     */
    public int getConsultSuveyRsltCnt(CBBConsultSuveyRsltListDTO cBBConsultSuveyRsltListDTO);

    /**
     * 만족도 종합결과 엑셀 상세 조회
     */
    public List<CBBConsultSuveyRsltListDTO> selectConsultSuveyRsltDtlExcel(CBBConsultSuveyRsltListDTO cBBConsultSuveyRsltListDTO);

    /**
     * 만족도 종합결과 엑셀 갯수 조회
     */
    public int getConsultSuveyRsltDtlCnt(CBBConsultSuveyRsltListDTO cBBConsultSuveyRsltListDTO);


    /**
     * 컨설팅 기술 지도 등록
     */
    public int insertManageConsult(CBBManageConsultInsertDTO pCBBManageConsultInsertDTO) throws Exception;

    /**
     * 컨설팅 기술 지도 수정
     */
    public int updateManageConsult(CBBManageConsultInsertDTO pCBBManageConsultInsertDTO) throws Exception;

    /**
     * 컨설팅 기술 지도 등록
     */
    public int updateManageConsultRsume(CBBManageConsultUpdateDTO pCBBManageConsultUpdateDTO) throws Exception;

    /**
     * 컨설팅 기술 지도 등록
     */
    public int insertManageConsultRsume(CBBManageConsultUpdateDTO pCBBManageConsultUpdateDTO) throws Exception;

    /**
     * 신청 부품사 카운트
     */
    public int selectRsumeTotCnt(Integer cnstgSeq) throws Exception;

    /**
     * 부품사 업종 카운트
     */
    public int selectCnstgDpndnInfoCnt(HashMap cbsnCdMap);

    /**
     * 고객사 비율 수정
     */
    public int updateCnstgDlvryInfo(HashMap cnstgDlyvMap);

    /**
     * 완성차 의존 수정
     */
    public int updateCnstgDpndnInfo(HashMap dpndnMap);

    /**
     * 신청 분야 상세
     */
    public List<CBBManageConsultInsertDTO> selectCnstgAppctnType(Integer cnstgSeq);

    /**
     * 컨설팅 진행 사항
     */
    public List<String> selectTechGuidanceRsume(CBBManageConsultInsertDTO pCBBManageConsultInsertDTO) throws Exception;

    /**
     * 회사 업종 상세 카운트
     */
    public int selectCmpnCbsnInfoCnt(Integer cbsnSeq) throws Exception;

    /**
     * 고객사 비율 상세 조회
     */
    public List<CBBManageConsultInsertDTO> selectCnstgDlvryInfo(Integer cnstgSeq) throws Exception;

    /**
     * 고객사 비율 상세 등록
     */
    public int insertCnstgDlvryInfo(HashMap dpndnMap);

    /**
     * 완성차 의존 매출 등록
     */
    public int insertCnstgDpndnInfo(HashMap dpndnMap);

    /**
     * 부품사 업종 상세 수정
     */
    public int updateCbsnDtl(CBBManageConsultInsertDTO pCBBManageConsultInsertDTO);

    /**
     * 부품사 업종 수정
     */
    public int updateCbsnDtl(HashMap cbsnCdMap);

    /**
     * 부품사 업종 상세 등록
     */
    public int insertCbsnDtl(HashMap cbstgCdMap);

    /**
     * 담당 임원 상세 등록
     */
    public int insertCnstgPicInfo(HashMap picInfoMap);

    /**
     * 고객사 비율 상세 조회
     */
    public List<CBBManageConsultInsertDTO> selectCnstgPicInfo(Integer cnstgSeq) throws Exception;

    /**
     * 담당 임원 상세 삭제
     */
    public int deleteCnstgPicInfo(HashMap picInfoMap);

    /**
     * 완성차 의존 비율 상세 조회
     */
    public List<CBBManageConsultInsertDTO> selectCnstgDpndnInfo(Integer cnstgSeq) throws Exception;

    /**
     * 컨설팅 경영 컨설팅 상세 조회
     */
    public CBBManageConsultInsertDTO selectManageConsultDtl(CBBManageConsultInsertDTO pCBBManageConsultInsertDTO) throws Exception;

    /**
     * 컨설팅 이관 내역 카운트
     */
    public int selectTrsfGuidanceCnt(CBBManageConsultInsertDTO pCBBManageConsultInsertDTO) throws Exception;

    /**
     * 컨설팅 이관 내역 등록
     */
    public int insertTrsfGuidanceList(CBBManageConsultInsertDTO pCBBManageConsultInsertDTO) throws Exception;

    /**
     * 컨설팅 이관 목록 조회
     */
    public List<CBBManageConsultInsertDTO> selectTrsfGuidanceList(CBBManageConsultInsertDTO pCBBManageConsultInsertDTO) throws Exception;

    /**
     * 만족도 상세
     */
    public List<CBBManageConsultInsertDTO> selectTechGuidanceSurvey(Integer cnstgSeq);

    /**
     * 컨설팅사업 신청마스터 테이블 삭제
     */
    public int deleteManageConsult(CBBManageConsultSearchDTO cBBManageConsultSearchDTO) throws Exception;

    /**
     * 컨설팅사업 진행마스터 테이블 삭제
     */
    public int deleteConsultRsumeMst(CBBManageConsultSearchDTO cBBManageConsultSearchDTO) throws Exception;

    /**
     * 컨설팅사업 담당임원 테이블 삭제
     */
    public int deleteConsultPicDtl(CBBManageConsultSearchDTO cBBManageConsultSearchDTO) throws Exception;

    /**
     * 컨설팅사업 부품사 업종 상세 테이블 삭제
     */
    public int deleteConsultCbsnDtl(CBBManageConsultSearchDTO cBBManageConsultSearchDTO) throws Exception;

    /**
     * 컨설팅사업 고객사비율 상세 테이블 삭제
     */
    public int deleteConsultDlvryDtl(CBBManageConsultSearchDTO cBBManageConsultSearchDTO) throws Exception;

    /**
     * 컨설팅사업 완성차의존율 상세 테이블 삭제
     */
    public int deleteConsultDpndnDtl(CBBManageConsultSearchDTO cBBManageConsultSearchDTO) throws Exception;

    /**
     * 컨설팅사업 신청분야 상세 테이블 삭제
     */
    public int deleteConsultAppctnTypeDtl(CBBManageConsultSearchDTO cBBManageConsultSearchDTO) throws Exception;

    /**
     * 컨설팅사업 만족도 결과 상세 테이블 삭제
     */
    public int deleteConsultSrvRsltDtl(CBBManageConsultSearchDTO cBBManageConsultSearchDTO) throws Exception;

    /**
     * 컨설팅사업 참여이관로그 테이블 삭제
     */
    public int deleteConsultAppctnTrnsfDtl(CBBManageConsultSearchDTO cBBManageConsultSearchDTO) throws Exception;

    /**
     *  마이페이지 컨설팅신청내역 담당 임원 정보 조회
     */
    public CBBManageConsultInsertDTO selectOneCnstgPicInfo(CBBManageConsultInsertDTO cbbManageConsultInsertDTO) throws Exception;

    /**
     * 기술지도 - 설문초기화
     */
    public int deleteSurveyRspn(CBBManageConsultUpdateDTO pCBBManageConsultUpdateDTO) throws Exception;

    /**
     * 기술지도 - 설문 응답 개수
     */
    public int checkSurveyCnt(CBBManageConsultUpdateDTO pCBBManageConsultUpdateDTO) throws Exception;
}
