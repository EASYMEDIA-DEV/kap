package com.kap.service.dao.wb.wbj;

import com.kap.core.dto.wb.WBPartCompanyDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBCompanyDetailMstDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplyChangeDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplyDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplySearchDTO;
import com.kap.core.dto.wb.wbj.*;

import java.util.List;

public interface WBJBAcomListMapper {
    /**
     * 회차 조회
     */
    public List<WBRoundMstSearchDTO> selectAcomList(WBRoundMstSearchDTO wBRoundMstSearchDTO);

    /**
     * 회차 전체 갯수
     */
    public int getAcomListTotCnt(WBJAcomSearchDTO wBJAcomSearchDTO);

    /**
     * 연도 상세 조회
     */
    public WBRoundMstDTO selectYearDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

    /**
     *  Write Page
     *  신청부품사 등록 - 신청 마스터
     */
    public int putAppctnMst(WBJAcomDTO wBJAcomDTO);

    /**
     *  Write Page
     *  신청부품사 등록 - 신청 파일 상세
     */
    public int putAppctnFileDtl(WBJAcomDTO wBJAcomDTO);
    
    /**
     *  Write Page
     *  신청부품사 등록 - 신청 파일 업데이트
     */
    public int delAppctnFileDtl(WBJAcomDTO wBJAcomDTO);

    /**
     *  Write Page
     *  신청부품사 등록 - 신청 상세
     */
    public int putAppctnRsumeDtl(WBJAcomDTO wBJAcomDTO);

    /**
     *  Write Page
     *  신청부품사 등록 - 스마트 싱세
     */
    public int putAppctnRsumePizePartDtl(WBJAcomDTO wBJAcomDTO);

    /**
     *  Write Page
     *  회사 마스터 Update
     */
    public int updCoMemMst(WBJAcomDTO wBJAcomDTO);

    /**
     *  Write Page
     *  회사 마스터 Update
     */
    public int updCmpnCbsnMst(WBJAcomDTO wBJAcomDTO);

    /**
     *  Write Page
     *  회사업종 상세 Update
     */
    public int updCmpnCbsnDtl(WBJAcomDTO wBJAcomDTO);

    /**
     *  Write Page
     *  회사업종 상세 Insert
     */
    public int insCmpnCbsnDtl(WBJAcomDTO wBJAcomDTO);

    /**
     *  Write Page
     *  사업회차 연도 기준 회차 검색
     */
    public List<String> getOptEpisdList(WBJAcomSearchDTO wBJAcomSearchDTO);

    /**
     *  Write Page
     *  사업회차 연도 기준 회차 검색
     */
    public List<String> getOptPrizeList(WBJAcomSearchDTO wBJAcomSearchDTO);

    /**
     *  Write Page
     *  사업회차 포상 기준 훈격 검색
    */
    public List<String> getOptMrtsList(WBJAcomSearchDTO wBJAcomSearchDTO);

    /**
     *  List Page
     *  신청부품사 목록 List Get
     */
    public List<WBJAcomSearchDTO> getRegisterCompanyList(WBJAcomSearchDTO wBJAcomSearchDTO);

    /**
     *  Write Page
     *  사업회차 연도 검색
     */
    public List<String> getOptYearList(WBJAcomSearchDTO wBJAcomSearchDTO);

    /**
     * 회차 상세 조회
     */
    public WBJAcomDTO selectAcomDtl(WBJAcomSearchDTO wBJAcomSearchDTO) throws Exception;

    /**
     *  edit Page
     *  자동차 상세 update
     */
    public int updAppctnRsumePrizeDtl(WBJAcomDTO wBJAcomDTO);

    /**
     *  Write Page
     *  신청부품사 수정 - 신청 상세
     */
    public int updAppctnRsumeDtl(WBJAcomDTO wBJAcomDTO);

    /**
     *  Write Page
     *  신청부품사 수정 - 신청 상세
     */
    public int updAppctnMst(WBJAcomDTO wBJAcomDTO);

    /**
     * 단계결과 수정
     */
    public int updateApplyStatus(WBJBAcomMstDTO wBJBAcomMstDTO) throws Exception;

    /**
     * 최종단계결과 수정
     */
    public int updateFinalApplyStatus(WBJBAcomMstDTO wBJBAcomMstDTO) throws Exception;

    /**
     * 상생신청 상세 생성
     */
    public int insertApplyDtl(WBJBAcomDtlDTO wBJBAcomDtlDTO) throws Exception;

    /**
     * 신청부품사 상세 조회
     */
    public List<WBJBAcomDtlDTO> selectApplyDtlList(WBJAcomSearchDTO wBJAcomSearchDTO);

    /**
     * 신청부품사 상세 조회
     */
    public List<WBJBAcomDtlDTO> selectApplyFinalDtlList(WBJAcomSearchDTO wBJAcomSearchDTO);

    /**
     *  신청부품사 마스터 조회
     */
    public WBJBAcomMstDTO selectApplyMst(WBJAcomSearchDTO wBJAcomSearchDTO);

    /**
     * 상생참여 마스터 삭제
     */
    public int deleteApplyMst(WBJAcomSearchDTO wBJAcomSearchDTO) throws Exception;

    /**
     * 상생참여자 상세 삭제
     */
    public int deleteApplyDtl(WBJAcomSearchDTO wBJAcomSearchDTO) throws Exception;

    /**
     * 상생참여자 이관정보 삭제
     */
    public int deleteTrans(WBJAcomSearchDTO wBJAcomSearchDTO) throws Exception;

    /**
     * 상생참여자 이관정보 삭제
     */
    public int deleteOptnDtl(WBJAcomSearchDTO wBJAcomSearchDTO) throws Exception;

    /**
     * 참여이관로그 조회
     */
    public List<WBJBAcomChangeDTO> getChangeList(WBJBAcomChangeDTO wBJBAcomChangeDTO) throws Exception;

    /**
     * 참여이관로그 개수 조회
     */
    public int getChangeCount(WBJBAcomChangeDTO wBJBAcomChangeDTO) throws Exception;

    /**
     * 상생참여이관로그 등록
     */
    public int insertChangeLog(WBJBAcomChangeDTO wBJBAcomChangeDTO) throws Exception;

    /**
     * 관리자 미확인 갯수 조회
     */
    public int getCnt(WBJAcomSearchDTO wBJAcomSearchDTO) throws Exception;

}
