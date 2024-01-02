package com.kap.service;

import com.kap.core.dto.wb.wbb.WBBAApplyMstDTO;
import com.kap.core.dto.wb.wbb.WBBACompanyDTO;
import com.kap.core.dto.wb.wbb.WBBACompanySearchDTO;
import com.kap.core.dto.wb.wbb.WBBATransDTO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <pre>
 * 공통 부품사관리 Service
 * </pre>
 *
 * @ClassName		: WBBBCompanyService.java
 * @Description		: 공통 회차관리 Service
 * @author 김태훈
 * @since 2023.11.27
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.27		김태훈				   최초 생성
 * </pre>
 */

public interface WBBBCompanyService {
    /**
     *   신청부품사 목록 List Get
     */
    public WBBACompanySearchDTO selectCompanyList(WBBACompanySearchDTO wbbCompanySearchDTO) throws Exception;

    /**
     * 진행상태 목록을 조회한다.
     * @return
     */
    public List<WBBACompanySearchDTO> selectProgressList(WBBACompanySearchDTO wbbCompanySearchDTO) throws Exception;

    /**
     * 부품사관리 상세 조회한다.
     * @return
     */
    public WBBAApplyMstDTO selectCompanyDtl(WBBACompanySearchDTO wbbCompanySearchDTO) throws Exception;

    /**
     * 회원 부품사정보 상세 조회한다.
     * @return
     */
    public WBBACompanyDTO selectCompanyUserDtl(WBBACompanySearchDTO wbbCompanySearchDTO) throws Exception;

    /**
     * 연도 상세 조회한다.
     * @return
     */
    public List<WBBACompanySearchDTO> selectYearDtl(WBBACompanySearchDTO wbbCompanySearchDTO) throws Exception;

    /**
     * 회차 상세 조회한다.
     * @return
     */
    public List<WBBACompanySearchDTO> getEplisdsList(WBBACompanySearchDTO wbbCompanySearchDTO) throws Exception;

    /**
     * 신청자를 등록한다.
     * @return
     */
    public int insert(WBBACompanyDTO wbbCompanyDTO, WBBAApplyMstDTO wbbApplyMstDTO, HttpServletRequest request) throws Exception;

    /**
     * 신청자를 수정한다.
     * @return
     */
    public int update(WBBACompanyDTO wbbCompanyDTO, WBBAApplyMstDTO wbbApplyMstDTO, HttpServletRequest request) throws Exception;

    /**
     * 신창자를 삭제한다.
     */
    public int delete(WBBACompanySearchDTO wbbCompanySearchDTO) throws Exception;

    /**
     * 참여 이관 로그를 가져온다.
     */
    public WBBATransDTO getTrnsfList(WBBATransDTO wbbTransDTO) throws Exception;

    /**
     * 참여 이관 로그를 가져온다.
     */
    public void excelDownload(WBBACompanySearchDTO wbbCompanySearchDTO, HttpServletResponse response) throws Exception;

    /**
     * 신청자를 등록한다.
     * @return
     */
    public int insertApply(WBBAApplyMstDTO wbbApplyMstDTO, MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception;

    /**
     * 단계 파일여부를 조회한다.
     * @return
     */
    public String getFileYn(WBBACompanySearchDTO wbbCompanySearchDTO) throws Exception;

    /**
     *   신청자 정보 조회
     */
    public WBBACompanySearchDTO getApplyDtl(WBBACompanySearchDTO wbbCompanySearchDTO) throws Exception;
}
