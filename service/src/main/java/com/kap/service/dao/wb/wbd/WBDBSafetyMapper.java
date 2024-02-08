package com.kap.service.dao.wb.wbd;

import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.mp.mpc.MPCLecturerDTO;
import com.kap.core.dto.wb.wbd.*;
import com.kap.core.dto.wb.wbf.WBFBRegisterDTO;

import java.util.List;

public interface WBDBSafetyMapper {
    /**
     * 신청 조회
     */
    public List<WBDBSafetySearchDTO> selectCarbonCompanyList(WBDBSafetySearchDTO wBDBSafetySearchDTO);

    /**
     * 신청 전체 갯수
     */
    public int getCarbonCompanyListTotCnt(WBDBSafetySearchDTO wBDBSafetySearchDTO);

    /**
     * 연도 상세 조회
     */
    public List<String> selectYearDtl(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception;

    /**
     * 연도 상세 조회
     */
    public List<WBDBSafetySearchDTO> getYearSelect(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception;

    /**
     * 신청 상세 조회
     */
    public WBDBSafetyMstInsertDTO selectCarbonCompanyDtl(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception;

    /**
     * 회차 시퀀스 조회
     */
    public int selectEpisdSeq(WBDBSafetyMstInsertDTO EBCarbonCompanyMstInsertDTO) throws Exception;

    /**
     * 신청 마스터 등록
     */
    public int insertAppctnMst(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO);

    /**
     * 신청 상세 등록
     */
    public int insertAppctnDtl(WBDBSafetyDtlDTO wBDBSafetyDtlDTO);

    /**
     * 신청자 정보 수정
     */
    public int updateAppctnMember(MPAUserDto mPAUserDto);

    /**
     * 부품사 정보 수정
     */
    public int updateAppctnCompany(WBDBCompanyDTO wBDBCompanyDTO);

    /**
     * 신청 마스터 수정
     */
    public int updateAppctnMst(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO);

    /**
     * 신청 상세 수정
     */
    public int updateAppctnDtl(WBDBSafetyDtlDTO wBDBSafetyDtlDTO);

    /**
     * 신청환경 상세 수정
     */
    public int updateAppctnPbsnDtl(WBDBSafetyPbsnDtlDTO wBDBSafetyPbsnDtlDTO);


    /**
     * 신청환경 상세 등록
     */
    public int insertAppctnPbsnDtl(WBDBSafetyPbsnDtlDTO wBDBSafetyPbsnDtlDTO);


    /**
     * 신청자 조회
     */
    public List<MPAUserDto> selectCarbonCompanyMember(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception;

    /**
     * 강사 및 위탁위원 조회
     */
    public MPCLecturerDTO selectIsttrDtl(WBDBSafetyMstInsertDTO wBCBSecurityMstInsertDTO) throws Exception;

    /**
     * 신청 부품사 조회
     */
    public WBDBCompanyDTO selectCarbonCompany(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception;

    /**
     * 신청 부품사 SQ 조회
     */
    public List<WBDBCompanyDtlDTO> selectCarbonCompanySQ(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception;

    /**
     * 신청정보 조회
     */
    public List<WBDBSafetyDtlDTO> selectCarbonCompanyDtlDetail(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception;

    /**
     * 신청진행 상세
     */
    public List<WBDBSafetyPbsnDtlDTO> selectCarbonCompanyPbsn(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception;


    /**
     * 부품사 SQ 정보 삭제
     */
    public int deleteCarbonCompanySQ(WBDBCompanyDTO wBDBCompanyDTO);

    /**
     * 부품사 SQ 정보 입력
     */
    public int insertCarbonCompanySQ(WBDBCompanyDtlDTO wBDBCompanyDtlDTO);

    /**
     * 지원금액 상세 조회
     */
    public List<WBDBSafetySpprtDTO> selectAppctnSpprt(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception;

    /**
     * 지원금액 상세 입력
     */
    public int insertAppctnSpprt(WBDBSafetySpprtDTO wBDBSafetySpprtDTO);

    /**
     * 지원금액 상세 수정
     */
    public int updateAppctnSpprt(WBDBSafetySpprtDTO wBDBSafetySpprtDTO);


    /**
     * 참여이관 로그 리스트 삭제
     */
    public int carbonCompanyDeleteTrnsf(WBDBSafetySearchDTO wBDBSafetySearchDTO);

    /**
     * 참여이관 로그 추가
     */
    public int insertAppctnTrnsf(WBDBSafetyTrnsfDTO wBDBSafetyTrnsfDTO);

    /**
     * 상생신청지원금 리스트 삭제
     */
    public int carbonCompanyDeleteSpprt(WBDBSafetySearchDTO wBDBSafetySearchDTO);

    /**
     * 상생신청파일 리스트 삭제
     */
    public int carbonCompanyDeleteFileDtl(WBDBSafetySearchDTO wBDBSafetySearchDTO);

    /**
     * 상생신청 진행 상세 리스트 삭제
     */
    public int carbonCompanyDeletePbsn(WBDBSafetySearchDTO wBDBSafetySearchDTO);

    /**
     *상생신청 진행 파일 리스트 삭제
     */
    public int carbonCompanyDeleteRsumeFile(WBDBSafetySearchDTO wBDBSafetySearchDTO);

    /**
     * 상생신청 진행 리스트 삭제
     */
    public int carbonCompanyDeleteRsume(WBDBSafetySearchDTO wBDBSafetySearchDTO);

    /**
     * 신청 마스터 리스트 삭제
     */
    public int carbonCompanyDeleteMst(WBDBSafetySearchDTO wBDBSafetySearchDTO);

    /**
     * 진행순번 조회
     */
    public List<String> selectRsumeSeq(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception;

    /**
     * 참여 이관 로그 조회
     */
    public List<WBDBSafetyTrnsfDTO> getTrnsfList(WBDBSafetyTrnsfDTO wBDBSafetyTrnsfDTO);

    /**
     * 참여 이관 로그 갯수
     */
    public int getTrnsfListTotCnt(WBDBSafetyTrnsfDTO wBDBSafetyTrnsfDTO);

    /**
     * 신청 파일 등록
     */
    public int insertAppctnFileDtl(WBDBSafetyFileDtlDTO wBDBSafetyFileDtlDTO);

    /**
     * 신청 파일 조회
     */
    public List<WBDBSafetyFileDtlDTO> selectFileDtl(WBDBSafetySearchDTO wBDBSafetySearchDTO);

    /**
     * 엑셀 진행 상세
     */
    public List<WBDBSafetyPbsnDtlDTO> selectExcelPbsn(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception;

    /**
     * 지급차수 조회
     */
    public List getGiveOrdList(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception;

    /**
     * 신청 진행단계 확인
     */
    public int getRsumePbsnCnt(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception;

    /**
     * 부품사 정보를 조회한다.
     */
    public WBDBCompanyDTO getCompanyInfo(WBDBSafetySearchDTO wBDBSafetySearchDTO);

    /**
     * 부품사 SQ 정보를 조회한다.
     */
    public List<WBDBCompanyDtlDTO> selectSqList(WBDBSafetySearchDTO wBDBSafetySearchDTO);

    /**
     * 사업자번호 매핑 여부 확인
     */
    public int getBsnmNoCnt(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO) throws Exception;

    /**
     * 종된 사업자번호 매핑 여부 확인
     */
    public int getSbrdnBsnmNoCnt(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO) throws Exception;

    /**
     * 사업자번호 매핑 여부 확인
     */
    public int getBsnmNoSeqCnt(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO) throws Exception;

    /**
     * 종된 사업자번호 매핑 여부 확인
     */
    public int getSbrdnBsnmNoSeqCnt(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO) throws Exception;

    /**
     *  Edit Page
     *  등록 부품사 수정 - 관리자 메모 수정
     */
    public int updAdmMemo(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception;
}
