package com.kap.service.dao.wb.wbe;

import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.wb.wbe.*;

import java.util.List;

public interface WBEBCarbonCompanyMapper {
    /**
     * 신청 조회
     */
    public List<WBEBCarbonCompanySearchDTO> selectCarbonCompanyList(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO);

    /**
     * 신청 전체 갯수
     */
    public int getCarbonCompanyListTotCnt(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO);

    /**
     * 연도 상세 조회
     */
    public List<String> selectYearDtl(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception;

    /**
     * 연도 상세 조회
     */
    public List<WBEBCarbonCompanySearchDTO> getYearSelect(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception;

    /**
     * 신청 상세 조회
     */
    public WBEBCarbonCompanyMstInsertDTO selectCarbonCompanyDtl(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception;

    /**
     * 회차 시퀀스 조회
     */
    public int selectEpisdSeq(WBEBCarbonCompanyMstInsertDTO EBCarbonCompanyMstInsertDTO) throws Exception;

    /**
     * 신청 마스터 등록
     */
    public int insertAppctnMst(WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO);

    /**
     * 신청 상세 등록
     */
    public int insertAppctnDtl(WBEBCarbonCompanyDtlDTO wBEBCarbonCompanyDtlDTO);

    /**
     * 신청자 정보 수정
     */
    public int updateAppctnMember(MPAUserDto mPAUserDto);

    /**
     * 부품사 정보 수정
     */
    public int updateAppctnCompany(WBEBCompanyDTO wBEBCompanyDTO);

    /**
     * 신청 마스터 수정
     */
    public int updateAppctnMst(WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO);

    /**
     * 신청 상세 수정
     */
    public int updateAppctnDtl(WBEBCarbonCompanyDtlDTO wBEBCarbonCompanyDtlDTO);

    /**
     * 신청환경 상세 수정
     */
    public int updateAppctnPbsnDtl(WBEBCarbonCompanyPbsnDtlDTO wBEBCarbonCompanyPbsnDtlDTO);


    /**
     * 신청환경 상세 등록
     */
    public int insertAppctnPbsnDtl(WBEBCarbonCompanyPbsnDtlDTO wBEBCarbonCompanyPbsnDtlDTO);


    /**
     * 신청자 조회
     */
    public List<MPAUserDto> selectCarbonCompanyMember(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception;

    /**
     * 신청 부품사 조회
     */
    public WBEBCompanyDTO selectCarbonCompany(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception;

    /**
     * 신청 부품사 SQ 조회
     */
    public List<WBEBCompanyDtlDTO> selectCarbonCompanySQ(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception;

    /**
     * 신청정보 조회
     */
    public List<WBEBCarbonCompanyDtlDTO> selectCarbonCompanyDtlDetail(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception;

    /**
     * 신청진행 상세
     */
    public List<WBEBCarbonCompanyPbsnDtlDTO> selectCarbonCompanyPbsn(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception;


    /**
     * 부품사 SQ 정보 삭제
     */
    public int deleteCarbonCompanySQ(WBEBCompanyDTO wBEBCompanyDTO);

    /**
     * 부품사 SQ 정보 입력
     */
    public int insertCarbonCompanySQ(WBEBCompanyDtlDTO wBEBCompanyDtlDTO);

    /**
     * 지원금액 상세 조회
     */
    public List<WBEBCarbonCompanySpprtDTO> selectAppctnSpprt(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception;

    /**
     * 지원금액 상세 입력
     */
    public int insertAppctnSpprt(WBEBCarbonCompanySpprtDTO wBEBCarbonCompanySpprtDTO);

    /**
     * 지원금액 상세 수정
     */
    public int updateAppctnSpprt(WBEBCarbonCompanySpprtDTO wBEBCarbonCompanySpprtDTO);


    /**
     * 참여이관 로그 리스트 삭제
     */
    public int carbonCompanyDeleteTrnsf(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO);

    /**
     * 참여이관 로그 추가
     */
    public int insertAppctnTrnsf(WBEBCarbonCompanyTrnsfDTO wBEBCarbonCompanyTrnsfDTO);

    /**
     * 상생신청지원금 리스트 삭제
     */
    public int carbonCompanyDeleteSpprt(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO);

    /**
     * 상생신청파일 리스트 삭제
     */
    public int carbonCompanyDeleteFileDtl(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO);

    /**
     * 상생신청 진행 상세 리스트 삭제
     */
    public int carbonCompanyDeletePbsn(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO);

    /**
     *상생신청 진행 파일 리스트 삭제
     */
    public int carbonCompanyDeleteRsumeFile(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO);

    /**
     * 상생신청 진행 리스트 삭제
     */
    public int carbonCompanyDeleteRsume(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO);

    /**
     * 신청 마스터 리스트 삭제
     */
    public int carbonCompanyDeleteMst(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO);

    /**
     * 진행순번 조회
     */
    public List<String> selectRsumeSeq(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception;

    /**
     * 참여 이관 로그 조회
     */
    public List<WBEBCarbonCompanyTrnsfDTO> getTrnsfList(WBEBCarbonCompanyTrnsfDTO wBEBCarbonCompanyTrnsfDTO);

    /**
     * 참여 이관 로그 갯수
     */
    public int getTrnsfListTotCnt(WBEBCarbonCompanyTrnsfDTO wBEBCarbonCompanyTrnsfDTO);

    /**
     * 신청 파일 등록
     */
    public int insertAppctnFileDtl(WBEBCarbonCompanyFileDtlDTO wBEBCarbonCompanyFileDtlDTO);

    /**
     * 신청 파일 조회
     */
    public List<WBEBCarbonCompanyFileDtlDTO> selectFileDtl(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO);

    /**
     * 엑셀 진행 상세
     */
    public List<WBEBCarbonCompanyPbsnDtlDTO> selectExcelPbsn(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception;

    /**
     * 지급차수 조회
     */
    public List getGiveOrdList(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception;

    /**
     * 신청 진행단계 확인
     */
    public int getRsumePbsnCnt(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception;

}
