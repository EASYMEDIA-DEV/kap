package com.kap.service.dao.wb.wbf;

import com.kap.core.dto.wb.*;
import com.kap.core.dto.wb.wbf.WBFBRegisterDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterSearchDTO;
import com.kap.core.dto.wb.wbf.WBFBRsumeTaskDtlDTO;

import java.util.List;

/**
 * <pre>
 * 스마트공장 > 신청부품사 관리 DAO
 * </pre>
 *
 * @ClassName		: WBFBRegisterCompanyMapper.java
 * @Description		: 스마트공장 > 신청부품사 관리 DAO
 * @author 김동현
 * @since 2023.11.16
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *	     since		  author	            description
 *    ==========    ==========    ==============================
 * 	  2023.11.16	  김동현	             최초 생성
 * </pre>
 */
public interface WBFBRegisterCompanyMapper {

    /**
     *  List Page
     *  신청부품사 목록 List Get
     */
    public List<WBFBRegisterSearchDTO> getRegisterCompanyList(WBFBRegisterSearchDTO wbApplyMstSearchDTO);

    /**
     *  List Page
     *  신청부품사 목록 Count
     */
    public int getRegisterCompanyCount(WBFBRegisterSearchDTO wbApplyMstSearchDTO);

    /**
     *  Write Page
     *  사업회차 연도 검색
     */
    public List<String> getOptYearList(WBFBRegisterSearchDTO wBFBRegisterSearchDTO);

    /**
     *  Write Page
     *  사업회차 연도 기준 회차 검색
     */
    public List<String> getOptEpisdList(WBFBRegisterSearchDTO wBFBRegisterSearchDTO);

    /**
     *  Write Page
     *  사업회차 연도 기준 회차 순번 검색
     */
    public Integer getEpisdSeq(WBFBRegisterSearchDTO wBFBRegisterSearchDTO);

    /**
     *  Write Page
     *  사업회차 년도/회차 기준 옵션 검색
     */
    public List<WBRoundOptnMstDTO> getOptnList(WBFBRegisterSearchDTO wBFBRegisterSearchDTO);

    /**
     *  Write Page
     *  신청부품사 등록 - 신청 마스터
     */
    public int putAppctnMst(WBFBRegisterDTO wBFBRegisterDTO);

    /**
     *  Write Page
     *  신청부품사 등록 - 신청 파일 상세
     */
    public int putAppctnSpprtDtl(WBFBRegisterDTO wBFBRegisterDTO);

    /**
     *  Write Page
     *  신청부품사 등록 - 신청 파일 상세
     */
    public int putAppctnFileDtl(WBRsumeFileDtlDTO wBRsumeFileDtlDTO);

    /**
     *  Write Page
     *  신청부품사 - 첨부파일 삭제
     */
    public int delAppctnFileDtl(WBFBRsumeTaskDtlDTO wBFBRsumeTaskDtlDTO);

    /**
     *  Write Page
     *  신청부품사 등록 - 신청 상세
     */
    public int putAppctnRsumeDtl(WBFBRegisterDTO wBFBRegisterDTO);

    /**
     *  Write Page
     *  신청부품사 등록 - 스마트 상세
     */
    public int putAppctnRsumeTaskDtl(WBFBRegisterDTO wBFBRegisterDTO);

    /**
     *  Write Page
     *  회사 마스터 Update
     */
    public int updCoMemMst(WBFBRegisterDTO wBFBRegisterDTO);

    /**
     *  Write Page
     *  회사 마스터 Update
     */
    public int updCmpnCbsnMst(WBFBRegisterDTO wBFBRegisterDTO);

    /**
     *  Write Page
     *  회사업종 상세 Update
     */
    public int updCmpnCbsnDtl(WBCompanyDetailMstDTO wBCompanyDetailMstDTO);

    /**
     *  Write Page
     *  회사업종 상세 Insert
     */
    public int insCmpnCbsnDtl(WBCompanyDetailMstDTO wBCompanyDetailMstDTO);

    /**
     *  Edit Page
     *  등록 부품사 지급차수 Select
     */
    public List<WBOrderMstDto> getGiveList(WBFBRegisterSearchDTO wBFBRegisterSearchDTO);

    /**
     *  Edit Page
     *  등록 부품사 지급차수 Select
     */
    public List<WBFBRegisterSearchDTO> getAppctnTrnsfDtl(WBFBRegisterSearchDTO wBFBRegisterSearchDTO);

    /**
     *  Edit Page
     *  등록 부품사 지급차수 Select
     */
    public int getAppctnTrnsfDtlCnt(WBFBRegisterSearchDTO wBFBRegisterSearchDTO);

    /**
     *  Edit Page
     *  등록 부품사 상세 Select
     */
    public WBFBRegisterDTO getRegisterDtl(WBFBRegisterSearchDTO wBFBRegisterSearchDTO);

    /**
     *  Edit Page
     *  사업자등록번호 Check
     */
    public WBFBRegisterSearchDTO getBsnmNoCheck(String offerBsnmNo);

    /**
     *  Edit Page
     *  등록 부품사 회사 SQ 정보 Select
     */
    public List<WBCompanyDetailMstDTO> getRegisterDtlSQ(WBFBRegisterSearchDTO wBFBRegisterSearchDTO);

    /**
     *  Edit Page
     *  등록 부품사 상세 Select Sub - 상생신청지원금액상세
     */
    public List<WBSpprtDtlDTO> getSpprtDtlList(WBFBRegisterSearchDTO wBFBRegisterSearchDTO);

    /**
     *  Edit Page
     *  등록 부품사 상세 Select Sub - 상생신청진행스마트상세
     */
    public List<WBFBRsumeTaskDtlDTO> getRsumeTaskDtlList(WBFBRegisterSearchDTO wBFBRegisterSearchDTO);

    /**
     *  Edit Page
     *  등록 부품사 상세 Select Sub - 상생신청진행스마트상세 파일정보
     */
    public List<WBRsumeFileDtlDTO> getDtlFileList(WBFBRsumeTaskDtlDTO wBFBRsumeTaskDtlDTO);

    /**
     *  Edit Page
     *  등록 부품사 수정 - 상생신청지원금액상세
     */
    public int updSpprtDtl(WBSpprtDtlDTO wBSpprtDtlDTO);

    /**
     *  Edit Page
     *  등록 부품사 수정 - 상생신청마스터
     */
    public int updAppctnMst(WBFBRegisterDTO wBFBRegisterDTO);

    /**
     *  Edit Page
     *  등록 부품사 수정 - 상생신청진행상세
     */
    public int updRsumeDtl(WBFBRsumeTaskDtlDTO wBFBRsumeTaskDtlDTO);

    /**
     *  Edit Page
     *  등록 부품사 수정 - 상생신청지원금액상세 별도
     */
    public int updRsumeTaskDtlSub(WBFBRegisterDTO wBFBRegisterDTO);

    /**
     *  Edit Page
     *  등록 부품사 수정 - 상생신청진행스마트상세
     */
    public int updRsumeTaskDtl(WBFBRsumeTaskDtlDTO wBFBRsumeTaskDtlDTO);

    /**
     *  Edit Page
     *  상생참여이관로그
     */
    public int insAppctnTrnsfDtl(WBAppctnTrnsfDtlDTO wBAppctnTrnsfDtlDTO);

    /**
     *  Delete 순서 1
     *  상생신청파일진행상세 Delete
     */
    public int delAppctnRsumeFileDtl(WBFBRegisterDTO wBFBRegisterDTO);

    /**
     *  Delete 순서 2
     *  상생신청진행스마트상세 Delete
     */
    public int delAppctnRsumeTaskDtl(WBFBRegisterDTO wBFBRegisterDTO);

    /**
     *  Delete 순서 3
     *  상생참여이관로그 Delete
     */
    public int delAppctnTrnsfDtl(WBFBRegisterDTO wBFBRegisterDTO);

    /**
     * Delete 순서 4
     *  상생신청지원금액상세 Delete
     */
    public int delAppctnSpprtDtl(WBFBRegisterDTO wBFBRegisterDTO);

    /**
     * Delete 순서 5
     *  상생신청진행상세 Delete
     */
    public int delAppctnRsumeDtl(WBFBRegisterDTO wBFBRegisterDTO);

    /**
     * Delete 순서 6
     *  상생신청마스터 Delete
     */
    public int delAppctnMst(WBFBRegisterDTO wBFBRegisterDTO);

    /**
     *  신청 부품사 삭제전 - 사용자 / 관리자 상태 확인
     */
    public int confDeleteRegister(WBFBRegisterDTO wBFBRegisterDTO);

}
