package com.kap.service.dao.wb.wbi;

import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.core.dto.wb.*;
import com.kap.core.dto.wb.wbb.WBBAApplyDtlDTO;
import com.kap.core.dto.wb.wbb.WBBAApplyMstDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterSearchDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplyDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplyMstDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplySearchDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplyChangeDTO;
import com.kap.core.dto.wb.wbj.WBJAcomDTO;

import java.util.List;

/**
 * <pre>
 * 공급망안정화기금 > 신청부품사 관리 DAO
 * </pre>
 *
 * @ClassName		: WBFBRegisterCompanyMapper.java
 * @Description		: 공급망안정화기금 > 신청부품사 관리 DAO
 * @author 오병호
 * @since 2023.12.04
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *	     since		  author	            description
 *    ==========    ==========    ==============================
 * 	  2023.12.04	  오병호	             최초 생성
 * </pre>
 */
public interface WBIBSupplyCompanyMapper {

    /**
     *  List Page
     *  신청부품사 목록 List Get
     */
    public List<WBIBSupplySearchDTO> getRegisterCompanyList(WBIBSupplySearchDTO wBIBSupplySearchDTO);

    /**
     *  List Page
     *  신청부품사 목록 Count
     */
    public int getRegisterCompanyCount(WBIBSupplySearchDTO wBIBSupplySearchDTO);

    /**
     *  Write Page
     *  사업회차 연도 검색
     */
    public List<String> getOptYearList(WBIBSupplySearchDTO wBIBSupplySearchDTO);

    /**
     *  Write Page
     *  사업회차 연도 기준 회차 검색
     */
    public List<String> getOptEpisdList(WBIBSupplySearchDTO wBIBSupplySearchDTO);

    /**
     *  Write Page
     *  사업회차 연도 기준 회차 순번 검색
     */
    public Integer getEpisdSeq(WBIBSupplySearchDTO wBIBSupplySearchDTO);

    /**
     *  Write Page
     *  신청부품사 등록 - 신청 마스터
     */
    public int putAppctnMst(WBIBSupplyDTO wBIBSupplyDTO);
    
    /**
     *  Write Page
     *  신청부품사 등록 - 신청 파일 상세
     */
    public int putAppctnFileDtl(WBIBSupplyDTO wBIBSupplyDTO);

    /**
     *  Write Page
     *  신청부품사 등록 - 신청 파일 업데이트
     */
    public int delAppctnFileDtl(WBIBSupplyDTO wBIBSupplyDTO);

    /**
     *  Write Page
     *  신청부품사 등록 - 신청 상세
     */
    public int putAppctnRsumeDtl(WBIBSupplyDTO wBIBSupplyDTO);

    /**
     *  Write Page
     *  신청부품사 수정 - 신청 상세
     */
    public int updAppctnRsumeDtl(WBIBSupplyDTO wBIBSupplyDTO);

    /**
     *  Write Page
     *  회사 마스터 Update
     */
    public int updCoMemMst(WBIBSupplyDTO wBIBSupplyDTO);

    /**
     *  Write Page
     *  회사 마스터 Update
     */
    public int updCmpnCbsnMst(WBIBSupplyDTO wBFBRegisterDTO);

    /**
     *  Write Page
     *  회사업종 상세 Update
     */
    public int updCmpnCbsnDtl(WBIBSupplyDTO wBIBSupplyDTO);

    /**
     *  Write Page
     *  회사업종 상세 Insert
     */
    public int insCmpnCbsnDtl(WBIBSupplyDTO wBIBSupplyDTO);

    /**
     *  Write Page
     *  회사업종 상세 Insert
     */
    public int uptAppctnMst(WBIBSupplyDTO wBIBSupplyDTO);

    /**
     * 회차 상세 조회
     */
    public WBIBSupplyDTO selectSupplyDtl(WBIBSupplySearchDTO wBIBSupplySearchDTO) throws Exception;

    /**
     * 상생참여 마스터 삭제
     */
    public int deleteApplyMst(WBIBSupplySearchDTO wBIBSupplySearchDTO) throws Exception;

    /**
     * 상생참여자 상세 삭제
     */
    public int deleteApplyDtl(WBIBSupplySearchDTO wBIBSupplySearchDTO) throws Exception;

    /**
     * 상생참여자 이관정보 삭제
     */
    public int deleteTrans(WBIBSupplySearchDTO wBIBSupplySearchDTO) throws Exception;

    /**
     * 참여이관로그 조회
     */
    public List<WBIBSupplyChangeDTO> getChangeList(WBIBSupplyChangeDTO wBIBSupplyChangeDTO) throws Exception;

    /**
     * 참여이관로그 개수 조회
     */
    public int getChangeCount(WBIBSupplyChangeDTO wBIBSupplyChangeDTO) throws Exception;

    /**
     * 상생참여이관로그 등록
     */
    public int insertChangeLog(WBIBSupplyChangeDTO wBIBSupplyChangeDTO) throws Exception;

    /**
     *  관리자 미확인 갯수 조회
     */
    public int getCnt(WBIBSupplySearchDTO wBIBSupplySearchDTO);

    /**
     * 상생신청 마스터 생성
     */
    public int insertApply(WBIBSupplyDTO wBIBSupplyDTO) throws Exception;

    /**
     * 상생신청 상세 생성
     */
    public int insertApplyDtl(WBIBSupplyDTO wBIBSupplyDTO) throws Exception;

    /**
     * 회차 상세 조회
     */
    public WBIBSupplyDTO selectRecent(WBIBSupplySearchDTO wBIBSupplySearchDTO) throws Exception;

    /**
     * 회차 조회
     */
    public List<MPEPartsCompanyDTO> selectPartsCompanyList(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 회차 전체 갯수
     */
    public int selectPartsCompanyCnt(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 사업자번호 매핑 여부 확인
     */
    public int getBsnmNoCnt(WBIBSupplyMstDTO wBIBSupplyMstDTO) throws Exception;
}
