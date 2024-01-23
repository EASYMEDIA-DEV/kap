package com.kap.service.dao.wb.wbh;

import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wbb.*;
import com.kap.core.dto.wb.wbe.WBEBCarbonCompanySearchDTO;
import com.kap.core.dto.wb.wbh.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 신청업체관리 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: WBBBCompanyMapper.java
 * @Description		: 신청업체관리를 위한 DAO
 * @author 김태훈
 * @since 2023.12.11
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.11		김태훈				   최초 생성
 * </pre>
 */

@Mapper
public interface WBHACalibrationMapper {

    /**
     * 신청 조회
     */
    public List<WBHACalibrationSearchDTO> selectCalibrationList(WBHACalibrationSearchDTO wBHACalibrationSearchDTO);

    /**
     * 신청 전체 갯수
     */
    public int getCalibrationListTotCnt(WBHACalibrationSearchDTO wBHACalibrationSearchDTO);

    /**
     * 옵션마스터 조회
     */
    public WBHAValidDTO selectExamValid(WBHACalibrationSearchDTO wBHACalibrationSearchDTO);

    /**
     * 옵션 상세 조회
     */
    public List<WBHAValidDtlDTO> selectExamValidDtlList(WBHACalibrationSearchDTO wBHACalibrationSearchDTO);

    /**
     * 옵션 마스터 입력
     */
    public int examValidInsert(WBHAValidDTO wBGAValidDTO) throws Exception;

    /**
     * 옵션 마스터 수정
     */
    public int examValidUpdate(WBHAValidDTO wBHAValidDTO) throws Exception;

    /**
     * 옵션 상세 수정
     */
    public int examValidDtlInsert(WBHAValidDtlDTO wBHAValidDtlDTO) throws Exception;

    /**
     * 옵션 상세 삭제
     */
    public int deleteValidDtl(WBHAValidDTO wBHAValidDTO);

    /**
     * 연도 상세 조회
     */
    public List<String> selectYearDtl(WBHACalibrationSearchDTO wBHACalibrationSearchDTO) throws Exception;

    /**
     * 참여이관 로그 리스트 삭제
     */
    public int carbonCompanyDeleteTrnsf(WBHACalibrationSearchDTO wBHACalibrationSearchDTO);

    /**
     * 대상 장비 리스트 삭제
     */
    public int carbonCompanyDeleteTchlg(WBHACalibrationSearchDTO wBHACalibrationSearchDTO);

    /**
     * 진행순번 조회
     */
    public List<String> selectRsumeSeq(WBHACalibrationSearchDTO wBHACalibrationSearchDTO) throws Exception;

    /**
     * 신청진행계측 리스트 삭제
     */
    public int carbonCompanyDeleteRsumeTchlg(WBHACalibrationSearchDTO wBHACalibrationSearchDTO);

    /**
     *상생신청 진행 파일 리스트 삭제
     */
    public int carbonCompanyDeleteRsumeFile(WBHACalibrationSearchDTO wBHACalibrationSearchDTO);

    /**
     * 상생신청 진행 리스트 삭제
     */
    public int carbonCompanyDeleteRsume(WBHACalibrationSearchDTO wBHACalibrationSearchDTO);

    /**
     * 신청 마스터 리스트 삭제
     */
    public int carbonCompanyDeleteMst(WBHACalibrationSearchDTO wBHACalibrationSearchDTO);

    /**
     * 참여이관로그 조회
     */
    public List<WBHAConsultingDTO> getConsultingList(WBHAConsultingDTO wbhaConsultingDTO) throws Exception;

    /**
     * 참여이관로그 개수 조회
     */
    public int getConsultingCount(WBHAConsultingDTO wbhaConsultingDTO) throws Exception;

    /**
     * 해당년도 회차 개수 조회
     */
    public WBRoundMstSearchDTO getExisdDtl(WBHAApplyMstDTO wbhaApplyMstDTO) throws Exception;

    /**
     *  신청부품사 마스터 조회
     */
    public WBHAApplyMstDTO selectApplyMst(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO);

    /**
     * 신청업체관리 상세 조회
     */
    public List<WBHAApplyDtlDTO> selectApplyDtlList(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO);

    /**
     * 신청업체관리 대상장비 상세 조회
     */
    public List<WBHAEuipmentDTO> selectEuipemtDtlList(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO);

    /**
     * 신청업체관리 파일 조회
     */
    public List<WBHAApplyDtlDTO> selectFileList(WBHAApplyDtlDTO wbhaApplyDtlDTO);

    /**
     * 신청업체관리 계측장비 조회
     */
    public List<WBHAMsEuipmentDTO> selectMsEuipmentList(WBHAApplyDtlDTO wbhaApplyDtlDTO);

    /**
     * 부품사 정보를 조회한다.
     */
    public WBHACompanyDTO getCompanyInfo(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO);

    /**
     * 부품사 SQ 정보를 조회한다.
     */
    public List<WBHACompanyDTO> selectSqList(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO);

    /**
     * 검교정 회차 등록
     */
    public int insetRound(WBRoundMstSearchDTO wbRoundMstSearchDTO) throws Exception;

    /**
     * 상생신청 마스터 생성
     */
    public int insertApply(WBHAApplyMstDTO wbhaApplyMstDTO) throws Exception;

    /**
     * 상생신청 상세 생성
     */
    public int insertApplyDtl(WBHAApplyDtlDTO wbhaApplyDtlDTO) throws Exception;

    /**
     * 상생신청 다음단계 생성
     */
    public int insertApplyStep(WBHAApplyDtlDTO wbhaApplyDtlDTO) throws Exception;

    /**
     * 대상장비 상세 생성
     */
    public int insertEuipment(WBHAEuipmentDTO wbhaEuipmentDTO) throws Exception;

    /**
     * 상생신청파일 등록
     */
    public int insertFileInfo(WBHAApplyDtlDTO wbhaApplyDtlDTO) throws Exception;

    /**
     * 부품사 회원 정보를 수정
     */
    public int updatePartUser(WBHACompanyDTO wbhaCompanyDTO) throws Exception;

    /**
     * 부품사 수정
     */
    public int updatePartsCompany(WBHACompanyDTO wbhaCompanyDTO) throws Exception;

    /**
     * 부품사 SQ 정보 등록
     */
    public int insertPartsComSQInfo(WBHACompanyDTO wbhaCompanyDTO) throws Exception;

    /**
     * 부품사 SQ 정보를 수정
     */
    public int updatePartsComSQInfo(WBHACompanyDTO wbhaCompanyDTO) throws Exception;

    /**
     * 부품사 SQ 정보를 삭제
     */
    public int deletePartsComSQInfo(WBHACompanyDTO wbhaCompanyDTO) throws Exception;
    
    /**
     * 신청업체관리 마스터 수정
     */
    public int updateApply(WBHAApplyMstDTO wbhaApplyMstDTO) throws Exception;

    /**
     * 신청업체관리 마스터 수정
     */
    public int updatePicCmssrSeq(WBHAApplyMstDTO wbhaApplyMstDTO) throws Exception;

    /**
     * 신청업체관리 파일 삭제
     */
    public int deleteFileInfo(WBHAApplyDtlDTO wbhaApplyDtlDTO) throws Exception;

    /**
     * 단계결과 수정
     */
    public int updateApplyStatus(WBHAApplyDtlDTO wbhaApplyDtlDTO) throws Exception;

    /**
     * 상생참여이관로그 등록
     */
    public int insertTransUserLog(WBBATransDTO wbbTransDTO) throws Exception;

    /**
     * 대상장비 삭제
     */
    public int deleteEuipment(WBHAApplyDtlDTO wbhaApplyDtlDTO) throws Exception;

    /**
     * 계측장비정보 삭제
     */
    public int deleteMsEuipment(WBHAMsEuipmentDTO wbhaMsEuipmentDTO) throws Exception;

    /**
     * 계측장비정보 등록
     */
    public int insertMsEuipment(WBHAMsEuipmentDTO wbhaMsEuipmentDTO) throws Exception;

    /**
     * 참여이관로그 조회
     */
    public List<WBBATransDTO> getTrnsfList(WBBATransDTO wbbTransDTO) throws Exception;

    /**
     * 참여이관로그 개수 조회
     */
    public int getTrnsfCount(WBBATransDTO wbbTransDTO) throws Exception;

    /**
     * 신청 진행단계 확인
     */
    public int getRsumePbsnCnt(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO) throws Exception;

    /**
     * 최신 회차 상세 조회
     */
    public WBHACalibrationSearchDTO getRoundDtl(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO) throws Exception;

    /**
     * 회차 신청여부 조회
     */
    public int getApplyCount(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO) throws Exception;

    /**
     * 컨설팅사업 신청여부 조회
     */
    public int getApplyCompanyCnt(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO) throws Exception;

    /**
     * 신청자 정보조회
     */
    public WBHACalibrationSearchDTO getApplyDtl(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO) throws Exception;

    /**
     * 계측장비정보 신청여부 조회
     */
    public int updateMsEuipment(WBHAMsEuipmentDTO wbhaMsEuipmentDTO) throws Exception;
}
