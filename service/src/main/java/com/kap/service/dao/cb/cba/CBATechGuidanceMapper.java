package com.kap.service.dao.cb.cba;

import com.kap.core.dto.cb.cba.CBAConsultSuveyRsltListDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceInsertDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceUpdateDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
/**
 * <pre>
 * 컨설팅 기술 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: CBATechGuidanceMapper.java
 * @Description		: 컨설팅 기술 관리를 위한 DAO
 * @author 임서화
 * @since 2023.10.16
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.10.16		임서화				   최초 생성
 *  2024.01.23      양현우             컨설팅 내역 조회(사용자 취소 제외)
 * </pre>
 */
@Mapper
public interface CBATechGuidanceMapper {

    /**
     * 컨설팅 기술 지도 목록 조회
     */
    public List<CBATechGuidanceInsertDTO> selectTechGuidanceList(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 컨설팅 기술 지도 상세 조회
     */
    public CBATechGuidanceInsertDTO selectTechGuidanceDtl(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 고객사 비율 상세 조회
     */
    public List<CBATechGuidanceInsertDTO> selectCnstgDlvryInfo(Integer cnstgSeq) throws Exception;
    /**
     * 완성차 의존 비율 상세 조회
     */
    public List<CBATechGuidanceInsertDTO> selectCnstgDpndnInfo(Integer cnstgSeq) throws Exception;

    /**
     * 컨설팅 기술 지도 카운트
     */
    public int selectTechGuidanceTotCnt(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 컨설팅 기술 지도 등록
     */
    public int insertTechGuidance(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 회사 업종 상세 카운트
     */
    public int selectCmpnCbsnInfoCnt(Integer cbsnSeq) throws Exception;

    /**
     * 컨설팅 기술 지도 수정
     */
    public int updateTechGuidance(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 컨설팅 진행 마스터 등록
     */
    public int insertTechGuidanceRsume(CBATechGuidanceUpdateDTO pCBATechGuidanceupUpdateDTO) throws Exception;
    /**
     * 컨설팅 진행 마스터 수정
     */
    public int updateTechGuidanceRsume(CBATechGuidanceUpdateDTO pCBATechGuidanceupUpdateDTO) throws Exception;

    /**
     * 컨설팅 진행 사항
     */
    public List<String> selectTechGuidanceRsume(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 컨설팅 기술 지도 삭제
     */
    public int deleteTechGuidance(CBATechGuidanceDTO pCBATechGuidanceDTO) throws Exception;

    /**
     * 부품사 업종 상세 수정
     */
    public int updateCbsnDtl(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO);

    /**
     *  부품사 업종 수정
     */
    public int updateCbsnDtl(HashMap cbsnCdMap);

    /**
     *  부품사 업종 카운트
     */
    public int selectCnstgDpndnInfoCnt(HashMap cbsnCdMap);

    /**
     *  고객사 비율 수정
     */
    public int updateCnstgDlvryInfo(HashMap cnstgDlyvMap);

    /**
     * 완성차 의존 수정
     */
    public int updateCnstgDpndnInfo(HashMap dpndnMap);

    /**
     * 부품사 업종 상세 등록
     */
    public int insertCbsnDtl(HashMap cbstgCdMap);

    /**
     * 고객사 비율 상세 등록
     */
    public int insertCnstgDlvryInfo(HashMap dpndnMap);

    /**
     * 완성차 의존 매출 등록
     */
    public int insertCnstgDpndnInfo(HashMap dpndnMap);

    /**
     * 신청 분야 상세 등록
     */
    public int insertCnstgAppctnType(HashMap appctnTypeMap);

    /**
     * 신청 분야 상세 삭제
     */
    public int deleteCnstgAppctnType(HashMap appctnTypeMap);

    /**
     * 신청 분야 상세
     */
    public List<CBATechGuidanceInsertDTO> selectCnstgAppctnType(Integer cnstgSeq);
    
    /**
     * 만족도 상세
     */
    public List<CBATechGuidanceInsertDTO> selectTechGuidanceSurvey(Integer cnstgSeq);

    /**
     * 신청 부품사 카운트
     */
    public int selectRsumeTotCnt(Integer cnstgSeq) throws Exception;

    /**
     * 컨설팅 이관 내역 카운트
     */
    public int selectTrsfGuidanceCnt(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 컨설팅 이관 내역 등록
     */
    public int insertTrsfGuidanceList(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 만족도 종합결과 리스트 조회
     */
    public List<CBAConsultSuveyRsltListDTO> selectConsultSuveyRsltList(CBAConsultSuveyRsltListDTO cBAConsultSuveyRsltListDTO);

    /**
     * 만족도 종합결과 리스트 갯수 조회
     */
    public int getConsultSuveyRsltCnt(CBAConsultSuveyRsltListDTO cBAConsultSuveyRsltListDTO);

    /**
     * 컨설팅 이관 목록 조회
     */
    public  List<CBATechGuidanceInsertDTO> selectTrsfGuidanceList(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;


    /**
     * 컨설팅사업 신청마스터 테이블 삭제
     */
    public int deleteManageConsult(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 컨설팅사업 진행마스터 테이블 삭제
     */
    public int deleteConsultRsumeMst(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 컨설팅사업 담당임원 테이블 삭제
     */
    public int deleteConsultPicDtl(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 컨설팅사업 부품사 업종 상세 테이블 삭제
     */
    public int deleteConsultCbsnDtl(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 컨설팅사업 고객사비율 상세 테이블 삭제
     */
    public int deleteConsultDlvryDtl(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 컨설팅사업 완성차의존율 상세 테이블 삭제
     */
    public int deleteConsultDpndnDtl(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 컨설팅사업 신청분야 상세 테이블 삭제
     */
    public int deleteConsultAppctnTypeDtl(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 컨설팅사업 만족도 결과 상세 테이블 삭제
     */
    public int deleteConsultSrvRsltDtl(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 컨설팅사업 참여이관로그 테이블 삭제
     */
    public int deleteConsultAppctnTrnsfDtl(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 컨설팅 기술 지도 목록 조회 카운트
     */
    public int selectMemSeqAppctnMstCnt(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 컨설팅 기술 지도 목록 조회
     */
    public List<CBATechGuidanceInsertDTO> selectMemSeqAppctnMst(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 컨설팅 설문 유효성 확인
     */
    public CBATechGuidanceInsertDTO selectTechGuidanceDtlCheck(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 최근 1년 간 신청한 컨설팅 내역 조회
     */
    public int selectYearConsultingCount(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 최근 1년 간 신청한 컨설팅 내역 조회(사용자 취소 제외)
     */
    public int selectYearCancelNotConsultingCount(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;


    /**
     * 컨설팅 신청 완료 정보
     */
    public CBATechGuidanceInsertDTO selectCompleteInfo(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 작성자 seq 값 조회
     */
    public int selectWriteMemSeq(Integer cnstgSeq) throws Exception;
    /**
     * 컨설팅 개선활동 추진계획서 다운로드
     */
    public CBATechGuidanceInsertDTO selectConsultingFilePath(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 메모장 저장
     */
    public int updAdmMemo(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 기술지도 - 설문초기화
     */
    public int deleteSurveyRspn(CBATechGuidanceUpdateDTO pCBATechGuidanceUpdateDTO) throws Exception;

    /**
     * 기술지도 - 설문 응답 개수
     */
    public int checkSurveyCnt(CBATechGuidanceUpdateDTO pCBATechGuidanceUpdateDTO) throws Exception;

}
