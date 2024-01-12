package com.kap.service.dao.wb.wbb;

import com.kap.core.dto.wb.wbb.*;
import com.kap.core.dto.wb.wbc.WBCBSecurityMstInsertDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 부품사 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: WBBBCompanyMapper.java
 * @Description		: 공통 부품사 관리를 위한 DAO
 * @author 김태훈
 * @since 2023.11.24
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.24		김태훈				   최초 생성
 * </pre>
 */

@Mapper
public interface WBBBCompanyMapper {

    /**
     *  List Page
     *  신청부품사 목록 List Get
     */
    public List<WBBACompanySearchDTO> getRegisterCompanyList(WBBACompanySearchDTO wbbCompanySearchDTO);

    /**
     *  List Page
     *  신청부품사 목록 Count
     */
    public int getRegisterCompanyCount(WBBACompanySearchDTO wbbCompanySearchDTO);

    /**
     *  신청부품사 마스터 조회
     */
    public WBBAApplyMstDTO selectApplyMst(WBBACompanySearchDTO wbbCompanySearchDTO);

    /**
     * 신청부품사 상세 조회
     */
    public List<WBBAApplyDtlDTO> selectApplyDtlList(WBBACompanySearchDTO wbbCompanySearchDTO);

    /**
     * 신청부품사 상세 옵션 조회
     */
    public List<WBBAApplyDtlDTO> selectApplyOptnList(WBBAApplyDtlDTO wbbaApplyDtlDTO);

    /**
     * 부품사 정보를 조회한다.
     */
    public WBBACompanyDTO getCompanyInfo(WBBACompanySearchDTO wbbCompanySearchDTO);

    /**
     * 부품사 SQ 정보를 조회한다.
     */
    public List<WBBACompanyDTO> selectSqList(WBBACompanySearchDTO wbbCompanySearchDTO);

    /**
     * 진행상태 목록 조회
     */
    public List<WBBACompanySearchDTO> selectProgressList(WBBACompanySearchDTO wbbCompanySearchDTO);

    /**
     * 연도 조회
     */
    public List<WBBACompanySearchDTO> selectYearDtl(WBBACompanySearchDTO wbbCompanySearchDTO);

    /**
     * 회차 조회
     */
    public List<WBBACompanySearchDTO> getEplisdsList(WBBACompanySearchDTO wbbCompanySearchDTO);

    /**
     * 상생신청 마스터 생성
     */
    public int insertApply(WBBAApplyMstDTO wbbApplyMstDTO) throws Exception;

    /**
     * 상생신청 상세 생성
     */
    public int insertApplyDtl(WBBAApplyDtlDTO wbbaApplyDtlDTO) throws Exception;

    /**
     * 상생신청 다음단계 생성
     */
    public int insertApplyStep(WBBAApplyDtlDTO wbbaApplyDtlDTO) throws Exception;

    /**
     * 신청 파일 생성
     */
    public int insertFileInfo(WBBAApplyDtlDTO wbbaApplyDtlDTO) throws Exception;

    /**
     * 상생신청 마스터 수정
     */
    public int updateApply(WBBAApplyMstDTO wbbApplyMstDTO) throws Exception;

    /**
     * 단계결과 수정
     */
    public int updateApplyStatus(WBBAApplyDtlDTO wbbaApplyDtlDTO) throws Exception;

    /**
     * 신청자 파일 삭제
     */
    public int deleteFileInfo(WBBAApplyDtlDTO wbbaApplyDtlDTO) throws Exception;

    /**
     * 부품사 회원 정보를 수정
     */
    public int updatePartUser(WBBACompanyDTO wbbCompanyDTO) throws Exception;
    
    /**
     * 부품사 수정
     */
    public int updatePartsCompany(WBBACompanyDTO wbbCompanyDTO) throws Exception;

    /**
     * 부품사 SQ 정보 등록
     */
    public int insertPartsComSQInfo(WBBACompanyDTO wbbCompanyDTO) throws Exception;

    /**
     * 부품사 SQ 정보를 수정
     */
    public int updatePartsComSQInfo(WBBACompanyDTO wbbCompanyDTO) throws Exception;

    /**
     * 부품사 SQ 정보를 삭제
     */
    public int deletePartsComSQInfo(WBBACompanyDTO wbbCompanyDTO) throws Exception;

    /**
     * 상생사업 단계 조회
     */
    public List<WBBAApplyDtlDTO> selectStepDtlList(WBBACompanySearchDTO wbbCompanySearchDTO) throws Exception;

    /**
     * 상생사업 옵션 조회
     */
    public List<WBBAApplyDtlDTO> selectOptnList(Integer stageSeq);

    /**
     * 참여이관로그 조회
     */
    public List<WBBATransDTO> getTrnsfList(WBBATransDTO wbbTransDTO) throws Exception;

    /**
     * 참여이관로그 개수 조회
     */
    public int getTrnsfCount(WBBATransDTO wbbTransDTO) throws Exception;

    /**
     * 상생참여이관로그 등록
     */
    public int insertTransUserLog(WBBATransDTO wbbTransDTO) throws Exception;

    /**
     * 상생참여 마스터 삭제
     */
    public int deleteApplyMst(WBBACompanySearchDTO wbbCompanySearchDTO) throws Exception;

    /**
     * 상생참여자 상세 삭제
     */
    public int deleteApplyDtl(WBBACompanySearchDTO wbbCompanySearchDTO) throws Exception;

    /**
     * 상생참여자 옵션 삭제
     */
    public int deleteOptnDtl(WBBACompanySearchDTO wbbCompanySearchDTO) throws Exception;

    /**
     * 상생참여자 이관정보 삭제
     */
    public int deleteTrans(WBBACompanySearchDTO wbbCompanySearchDTO) throws Exception;

    /**
     * 단계 파일여부를 조회한다.
     */
    public String getFileYn(WBBACompanySearchDTO wbbCompanySearchDTO) throws Exception;

    /**
     * 신청자 정보조회
     */
    public WBBACompanySearchDTO getApplyDtl(WBBACompanySearchDTO wbbCompanySearchDTO) throws Exception;

    /**
     * 사업자번호 매핑 여부 확인
     */
    public int getBsnmNoCnt(WBBAApplyMstDTO wBBAApplyMstDTO) throws Exception;

}
