package com.kap.service;

import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplyChangeDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplyDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplyMstDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplySearchDTO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 공급망안정화기금 > 신청업체관리 Service
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.12.04  오병호         최초 생성
 * </pre>
 */
public interface WBIBSupplyCompanyService {

    /**
     *  사업회차 연도 검색
     */
    public List<String> getOptYearList(WBIBSupplySearchDTO wBIBSupplySearchDTO) throws Exception;

    /**
     * 선택 연도 값에 따른
     * 회차 SELECT Ajax
     */
    public List<String> getOptEpisdList(WBIBSupplySearchDTO wBIBSupplySearchDTO) throws Exception;

    /**
     * 선택 연도/회차 값에 따른
     * episdSeq 값 가져오기
     */
    public WBRoundMstDTO getEpisdSeq(WBIBSupplySearchDTO wBIBSupplySearchDTO) throws Exception;

    /**
     *   신청부품사 목록 List Get
     */
    public WBIBSupplySearchDTO getSupplyCompanyList(WBIBSupplySearchDTO wBIBSupplySearchDTO) throws Exception;

    /**
     *   신청 부품사 등록 Insert
     */
    public int putSupplyCompany(WBIBSupplyDTO wBIBSupplyDTO, WBIBSupplyMstDTO wBIBSupplyMstDTO, HttpServletRequest request) throws Exception;

    /**
     *   신청 부품사 등록 update
     */
    public int updateSupplyCompany(WBIBSupplyDTO wBIBSupplyDTO, WBIBSupplyMstDTO wBIBSupplyMstDTO, HttpServletRequest request) throws Exception;

    /**
     * 상세 조회한다.
     */
    public WBIBSupplyDTO selectSupplyDtl(WBIBSupplySearchDTO wBIBSupplySearchDTO) throws Exception;

    /**
     * 신창자를 삭제한다.
     */
    public int delete(WBIBSupplySearchDTO wBIBSupplySearchDTO) throws Exception;

    /**
     * 참여 이관 로그를 가져온다.
     */
    public WBIBSupplyChangeDTO getChangeList(WBIBSupplyChangeDTO wBIBSupplyChangeDTO) throws Exception;

    /**
     * 리스트 엑셀 다운로드
     */
    public void excelDownload(WBIBSupplySearchDTO wBIBSupplySearchDTO, HttpServletResponse response) throws Exception;

    /**
     * 신창자를 삭제한다.
     */
    public int getCnt(WBIBSupplySearchDTO wBIBSupplySearchDTO) throws Exception;

    /**
     * 신청자를 등록한다.
     * @return
     */
    public int insertApply(WBIBSupplyDTO wBIBSupplyDTO, WBIBSupplyMstDTO wBIBSupplyMstDTO, HttpServletRequest request, MultipartHttpServletRequest multiRequest) throws Exception;

    /**
     * 최신 게시물 조회
     */
    public WBIBSupplyDTO selectRecent(WBIBSupplySearchDTO wBIBSupplySearchDTO) throws Exception;

    /**
     * 상세 조회한다.
     */
    public MPEPartsCompanyDTO selectPartsCompanyList(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     *   신청자 업데이트
     */
    public int updateInfo(WBIBSupplyDTO wBIBSupplyDTO, MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception;

    /**
     * 사업자번호 매핑 여부 확인
     */
    public int getBsnmNoCnt(WBIBSupplyMstDTO wBIBSupplyMstDTO) throws Exception;

    /**
     *  Edit Page
     *  관리자 메모 수정
     */
    public int updAdmMemo(WBIBSupplyDTO wBIBSupplyDTO) throws Exception;
}
