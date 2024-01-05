package com.kap.service.dao.wb.wbg;

import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wbb.WBBATransDTO;
import com.kap.core.dto.wb.wbe.WBEBCarbonCompanySearchDTO;
import com.kap.core.dto.wb.wbg.*;
import com.kap.core.dto.wb.wbh.WBHACalibrationSearchDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 신청업체관리 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: WBGAExamMapper.java
 * @Description		: 신청업체관리를 위한 DAO
 * @author 김대성
 * @since 2023.12.11
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.11		김대성				   최초 생성
 * </pre>
 */

@Mapper
public interface WBGAExamMapper {

    /**
     * 신청 조회
     */
    public List<WBGAExamSearchDTO> selectCalibrationList(WBGAExamSearchDTO wBGAExamSearchDTO);

    /**
     * 신청 전체 갯수
     */
    public int getCalibrationListTotCnt(WBGAExamSearchDTO wBGAExamSearchDTO);

    /**
     * 옵션마스터 조회
     */
    public WBGAValidDTO selectExamValid(WBGAExamSearchDTO wBGAExamSearchDTO);

    /**
     * 옵션 상세 조회
     */
    public List<WBGAValidDtlDTO> selectExamValidDtlList(WBGAExamSearchDTO wBGAExamSearchDTO);

    /**
     * 옵션 마스터 입력
     */
    public int examValidInsert(WBGAValidDTO wBGAValidDTO) throws Exception;

    /**
     * 옵션 마스터 수정
     */
    public int examValidUpdate(WBGAValidDTO wBGAValidDTO) throws Exception;

    /**
     * 옵션 상세 수정
     */
    public int examValidDtlInsert(WBGAValidDtlDTO wBGAValidDtlDTO) throws Exception;

    /**
     * 옵션 상세 삭제
     */
    public int deleteValidDtl(WBGAValidDTO wBGAValidDTO);

    /**
     * 연도 상세 조회
     */
    public List<String> selectYearDtl(WBGAExamSearchDTO wBGAExamSearchDTO) throws Exception;

    /**
     * 참여이관 로그 리스트 삭제
     */
    public int carbonCompanyDeleteTrnsf(WBGAExamSearchDTO wBGAExamSearchDTO);

    /**
     * 대상 장비 리스트 삭제
     */
    public int carbonCompanyDeleteTchlg(WBGAExamSearchDTO wBGAExamSearchDTO);

    /**
     * 진행순번 조회
     */
    public List<String> selectRsumeSeq(WBGAExamSearchDTO wBGAExamSearchDTO) throws Exception;

    /**
     * 신청진행계측 리스트 삭제
     */
    public int carbonCompanyDeleteRsumeTchlg(WBGAExamSearchDTO wBGAExamSearchDTO);

    /**
     *상생신청 진행 파일 리스트 삭제
     */
    public int carbonCompanyDeleteRsumeFile(WBGAExamSearchDTO wBGAExamSearchDTO);

    /**
     * 상생신청 진행 리스트 삭제
     */
    public int carbonCompanyDeleteRsume(WBGAExamSearchDTO wBGAExamSearchDTO);

    /**
     * 신청 마스터 리스트 삭제
     */
    public int carbonCompanyDeleteMst(WBGAExamSearchDTO wBGAExamSearchDTO);

    /**
     * 참여이관로그 조회
     */
    public List<WBGAConsultingDTO> getConsultingList(WBGAConsultingDTO wBGAConsultingDTO) throws Exception;

    /**
     * 참여이관로그 개수 조회
     */
    public int getConsultingCount(WBGAConsultingDTO wBGAConsultingDTO) throws Exception;

    /**
     * 해당년도 회차 개수 조회
     */
    public WBRoundMstSearchDTO getExisdDtl(WBGAApplyMstDTO wBGAApplyMstDTO) throws Exception;

    /**
     *  신청부품사 마스터 조회
     */
    public WBGAApplyMstDTO selectApplyMst(WBGAExamSearchDTO wBGAExamSearchDTO);

    /**
     * 신청업체관리 상세 조회
     */
    public List<WBGAApplyDtlDTO> selectApplyDtlList(WBGAExamSearchDTO wBGAExamSearchDTO);

    /**
     * 신청업체관리 대상장비 상세 조회
     */
    public List<WBGAEuipmentDTO> selectEuipemtDtlList(WBGAExamSearchDTO wBGAExamSearchDTO);

    /**
     * 신청업체관리 파일 조회
     */
    public List<WBGAApplyDtlDTO> selectFileList(WBGAApplyDtlDTO wBGAApplyDtlDTO);

    /**
     * 신청업체관리 계측장비 조회
     */
    public List<WBGAMsEuipmentDTO> selectMsEuipmentList(WBGAApplyDtlDTO wBGAApplyDtlDTO);

    /**
     * 부품사 정보를 조회한다.
     */
    public WBGACompanyDTO getCompanyInfo(WBGAExamSearchDTO wBGAExamSearchDTO);

    /**
     * 부품사 SQ 정보를 조회한다.
     */
    public List<WBGACompanyDTO> selectSqList(WBGAExamSearchDTO wBGAExamSearchDTO);

    /**
     * 검교정 회차 등록
     */
    public int insetRound(WBRoundMstSearchDTO wbRoundMstSearchDTO) throws Exception;

    /**
     * 상생신청 마스터 생성
     */
    public int insertApply(WBGAApplyMstDTO wBGAApplyMstDTO) throws Exception;

    /**
     * 상생신청 상세 생성
     */
    public int insertApplyDtl(WBGAApplyDtlDTO wBGAApplyDtlDTO) throws Exception;

    /**
     * 대상장비 상세 생성
     */
    public int insertEuipment(WBGAEuipmentDTO wBGAEuipmentDTO) throws Exception;

    /**
     * 상생신청파일 등록
     */
    public int insertFileInfo(WBGAApplyDtlDTO wBGAApplyDtlDTO) throws Exception;

    /**
     * 부품사 회원 정보를 수정
     */
    public int updatePartUser(WBGACompanyDTO wBGACompanyDTO) throws Exception;

    /**
     * 부품사 수정
     */
    public int updatePartsCompany(WBGACompanyDTO wBGACompanyDTO) throws Exception;

    /**
     * 부품사 SQ 정보 등록
     */
    public int insertPartsComSQInfo(WBGACompanyDTO wBGACompanyDTO) throws Exception;

    /**
     * 부품사 SQ 정보를 수정
     */
    public int updatePartsComSQInfo(WBGACompanyDTO wBGACompanyDTO) throws Exception;

    /**
     * 부품사 SQ 정보를 삭제
     */
    public int deletePartsComSQInfo(WBGACompanyDTO wBGACompanyDTO) throws Exception;
    
    /**
     * 신청업체관리 마스터 수정
     */
    public int updateApply(WBGAApplyMstDTO wBGAApplyMstDTO) throws Exception;

    /**
     * 신청업체관리 파일 삭제
     */
    public int deleteFileInfo(WBGAApplyDtlDTO wBGAApplyDtlDTO) throws Exception;

    /**
     * 단계결과 수정
     */
    public int updateApplyStatus(WBGAApplyDtlDTO wBGAApplyDtlDTO) throws Exception;

    /**
     * 상생참여이관로그 등록
     */
    public int insertTransUserLog(WBBATransDTO wbbTransDTO) throws Exception;

    /**
     * 대상장비 삭제
     */
    public int deleteEuipment(WBGAApplyDtlDTO wBGAApplyDtlDTO) throws Exception;

    /**
     * 계측장비정보 업데이트
     */
    public int deleteMsEuipment(WBGAMsEuipmentDTO wBGAMsEuipmentDTO) throws Exception;

    /**
     * 계측장비정보 업데이트
     */
    public int insertMsEuipment(WBGAMsEuipmentDTO wBGAMsEuipmentDTO) throws Exception;

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
    public int getRsumePbsnCnt(WBGAExamSearchDTO wBGAExamSearchDTO) throws Exception;

    /**
     * 최신 회차 상세 조회
     */
    public WBGAExamSearchDTO getRoundDtl(WBGAExamSearchDTO wbgaExamSearchDTO) throws Exception;

    /**
     * 회차 신청여부 조회
     */
    public int getApplyCount(WBGAExamSearchDTO wbgaExamSearchDTO) throws Exception;

    /**
     * 컨설팅사업 신청여부 조회
     */
    public int getApplyCompanyCnt(WBGAExamSearchDTO wbgaExamSearchDTO) throws Exception;

    /**
     * 신청자 정보조회
     */
    public WBGAExamSearchDTO getApplyDtl(WBGAExamSearchDTO wbgaExamSearchDTO) throws Exception;

}
