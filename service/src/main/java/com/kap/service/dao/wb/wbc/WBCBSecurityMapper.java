package com.kap.service.dao.wb.wbc;

import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.wbc.*;

import java.util.List;

public interface WBCBSecurityMapper {
    /**
     * 신청 조회
     */
    public List<WBCBSecuritySearchDTO> selectCarbonCompanyList(WBCBSecuritySearchDTO wBCBSecuritySearchDTO);

    /**
     * 신청 전체 갯수
     */
    public int getCarbonCompanyListTotCnt(WBCBSecuritySearchDTO wBCBSecuritySearchDTO);

    /**
     * 연도 상세 조회
     */
    public List<String> selectYearDtl(WBCBSecuritySearchDTO wBCBSecuritySearchDTO) throws Exception;

    /**
     * 연도 상세 조회
     */
    public List<WBCBSecuritySearchDTO> getYearSelect(WBCBSecuritySearchDTO wBCBSecuritySearchDTO) throws Exception;

    /**
     * 신청 상세 조회
     */
    public WBCBSecurityMstInsertDTO selectCarbonCompanyDtl(WBCBSecuritySearchDTO wBCBSecuritySearchDTO) throws Exception;

    /**
     * 회차 시퀀스 조회
     */
    public int selectEpisdSeq(WBCBSecurityMstInsertDTO EBCarbonCompanyMstInsertDTO) throws Exception;

    /**
     * 신청 마스터 등록
     */
    public int insertAppctnMst(WBCBSecurityMstInsertDTO wBEBCarbonCompanyMstInsertDTO);

    /**
     * 신청 상세 등록
     */
    public int insertAppctnDtl(WBCBSecurityDtlDTO wBCBSecurityDtlDTO);

    /**
     * 신청자 정보 수정
     */
    public int updateAppctnMember(MPAUserDto mPAUserDto);

    /**
     * 부품사 정보 수정
     */
    public int updateAppctnCompany(WBCBCompanyDTO wBCBCompanyDTO);

    /**
     * 신청 마스터 수정
     */
    public int updateAppctnMst(WBCBSecurityMstInsertDTO wBCBSecurityMstInsertDTO);

    /**
     * 신청 상세 수정
     */
    public int updateAppctnDtl(WBCBSecurityDtlDTO wBCBSecurityDtlDTO);

    /**
     * 신청환경 상세 수정
     */
    public int updateAppctnPbsnDtl(WBCBSecurityPbsnDtlDTO wBCBSecurityPbsnDtlDTO);


    /**
     * 신청환경 상세 등록
     */
    public int insertAppctnPbsnDtl(WBCBSecurityPbsnDtlDTO wBCBSecurityPbsnDtlDTO);


    /**
     * 신청자 조회
     */
    public List<MPAUserDto> selectCarbonCompanyMember(WBCBSecuritySearchDTO wBCBSecuritySearchDTO) throws Exception;

    /**
     * 신청 부품사 조회
     */
    public WBCBCompanyDTO selectCarbonCompany(WBCBSecuritySearchDTO wBCBSecuritySearchDTO) throws Exception;

    /**
     * 신청 부품사 SQ 조회
     */
    public List<WBCBCompanyDtlDTO> selectCarbonCompanySQ(WBCBSecuritySearchDTO wBCBSecuritySearchDTO) throws Exception;

    /**
     * 신청정보 조회
     */
    public List<WBCBSecurityDtlDTO> selectCarbonCompanyDtlDetail(WBCBSecuritySearchDTO wBCBSecuritySearchDTO) throws Exception;

    /**
     * 신청진행 상세
     */
    public List<WBCBSecurityPbsnDtlDTO> selectCarbonCompanyPbsn(WBCBSecuritySearchDTO wBCBSecuritySearchDTO) throws Exception;


    /**
     * 부품사 SQ 정보 삭제
     */
    public int deleteCarbonCompanySQ(WBCBCompanyDTO wBCBCompanyDTO);

    /**
     * 부품사 SQ 정보 입력
     */
    public int insertCarbonCompanySQ(WBCBCompanyDtlDTO wBCBCompanyDtlDTO);

    /**
     * 지원금액 상세 조회
     */
    public List<WBCBSecuritySpprtDTO> selectAppctnSpprt(WBCBSecuritySearchDTO wBCBSecuritySearchDTO) throws Exception;

    /**
     * 지원금액 상세 입력
     */
    public int insertAppctnSpprt(WBCBSecuritySpprtDTO wBCBSecuritySpprtDTO);

    /**
     * 지원금액 상세 수정
     */
    public int updateAppctnSpprt(WBCBSecuritySpprtDTO wBCBSecuritySpprtDTO);


    /**
     * 참여이관 로그 리스트 삭제
     */
    public int carbonCompanyDeleteTrnsf(WBCBSecuritySearchDTO wBCBSecuritySearchDTO);

    /**
     * 참여이관 로그 추가
     */
    public int insertAppctnTrnsf(WBCBSecurityTrnsfDTO wBCBSecurityTrnsfDTO);

    /**
     * 상생신청지원금 리스트 삭제
     */
    public int carbonCompanyDeleteSpprt(WBCBSecuritySearchDTO wBCBSecuritySearchDTO);

    /**
     * 상생신청파일 리스트 삭제
     */
    public int carbonCompanyDeleteFileDtl(WBCBSecuritySearchDTO wBCBSecuritySearchDTO);

    /**
     * 상생신청 진행 상세 리스트 삭제
     */
    public int carbonCompanyDeletePbsn(WBCBSecuritySearchDTO wBCBSecuritySearchDTO);

    /**
     *상생신청 진행 파일 리스트 삭제
     */
    public int carbonCompanyDeleteRsumeFile(WBCBSecuritySearchDTO wBCBSecuritySearchDTO);

    /**
     * 상생신청 진행 리스트 삭제
     */
    public int carbonCompanyDeleteRsume(WBCBSecuritySearchDTO wBCBSecuritySearchDTO);

    /**
     * 신청 마스터 리스트 삭제
     */
    public int carbonCompanyDeleteMst(WBCBSecuritySearchDTO wBCBSecuritySearchDTO);

    /**
     * 진행순번 조회
     */
    public List<String> selectRsumeSeq(WBCBSecuritySearchDTO wBCBSecuritySearchDTO) throws Exception;

    /**
     * 참여 이관 로그 조회
     */
    public List<WBCBSecurityTrnsfDTO> getTrnsfList(WBCBSecurityTrnsfDTO wBCBSecurityTrnsfDTO);

    /**
     * 참여 이관 로그 갯수
     */
    public int getTrnsfListTotCnt(WBCBSecurityTrnsfDTO wBCBSecurityTrnsfDTO);

    /**
     * 신청 파일 등록
     */
    public int insertAppctnFileDtl(WBCBSecurityFileDtlDTO wBCBSecurityFileDtlDTO);

    /**
     * 신청 파일 조회
     */
    public List<WBCBSecurityFileDtlDTO> selectFileDtl(WBCBSecuritySearchDTO wBCBSecuritySearchDTO);

    /**
     * 엑셀 진행 상세
     */
    public List<WBCBSecurityPbsnDtlDTO> selectExcelPbsn(WBCBSecuritySearchDTO wBCBSecuritySearchDTO) throws Exception;

    /**
     * 지급차수 조회
     */
    public List getGiveOrdList(WBCBSecuritySearchDTO wBCBSecuritySearchDTO) throws Exception;

    /**
     * 신청 진행단계 확인
     */
    public int getRsumePbsnCnt(WBCBSecuritySearchDTO wBCBSecuritySearchDTO) throws Exception;

    /**
     * 부품사 정보를 조회한다.
     */
    public WBCBCompanyDTO getCompanyInfo(WBCBSecuritySearchDTO wBCBSecuritySearchDTO);

    /**
     * 부품사 SQ 정보를 조회한다.
     */
    public List<WBCBCompanyDtlDTO> selectSqList(WBCBSecuritySearchDTO wBCBSecuritySearchDTO);

    /**
     * 사업자번호 매핑 여부 확인
     */
    public int getBsnmNoCnt(WBCBSecurityMstInsertDTO wBCBSecurityMstInsertDTO) throws Exception;

}
