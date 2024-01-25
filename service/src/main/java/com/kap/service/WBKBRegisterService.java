package com.kap.service;

import com.kap.core.dto.wb.wbb.WBBAApplyMstDTO;
import com.kap.core.dto.wb.wbb.WBBACompanySearchDTO;
import com.kap.core.dto.wb.wbj.WBJAcomDTO;
import com.kap.core.dto.wb.wbk.WBKBRegisterDTO;
import com.kap.core.dto.wb.wbk.WBKBRegisterSearchDTO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 미래차공모전 > 신청 Service
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.16  민윤기         최초 생성
 * </pre>
 */
public interface WBKBRegisterService {

    /**
     *  사업회차 연도 검색
     */
    public List<String> getOptYearList(WBKBRegisterSearchDTO wBKBRegisterSearchDTO) throws Exception;

    /**
     * 선택 연도 값에 따른
     * 회차 SELECT Ajax
     */
    public List<String> getOptEpisdList(WBKBRegisterSearchDTO wBKBRegisterSearchDTO) throws Exception;

    /**
     선택 연도/회차 값에 따른
     * 과제명/사업유형 SELECT Ajax
     */
    public List<String> getEpisdSeq(WBKBRegisterSearchDTO wBKBRegisterSearchDTO) throws Exception;


    /**
     *   신청부품사 목록 List Get
     */
    public WBKBRegisterSearchDTO getRegisterList(WBKBRegisterSearchDTO wBKBRegisterSearchDTO) throws Exception;

    /**
     *   미래차 공모전 신청 등록 Insert
     */
    public int putRegisterCompany(WBKBRegisterDTO wBKBRegisterDTO) throws Exception;

    /**
     *   미래차 공모전 신청 등록 update
     */
    public int updRegisterCompany(WBKBRegisterDTO wBKBRegisterDTO) throws Exception;

    /**
     *   미래차 공모전 진행상황 등록 update
     */
    public int updRsumeStep(WBKBRegisterDTO wBKBRegisterDTO) throws Exception;

    /**
     *   미래차 공모전 사용자 update
     */
    public int furecarUserUpd(WBKBRegisterDTO wBKBRegisterDTO, MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception;

    /**
     *   미래차 공모전 신청 삭제
     */
    public int deleteRegFutureCarContest(WBKBRegisterDTO wBKBRegisterDTO) throws Exception;

    /**
     *   미래차 공모전 엑셀 다운
     */
    void excelDownload(WBKBRegisterSearchDTO WBKBRegisterSearchDTO,  HttpServletResponse response) throws Exception;

    /**
     *  미래차 공모전 신청 상세조회
     */
    public WBKBRegisterDTO selectFutureCarConRegDtl(WBKBRegisterSearchDTO wBKBRegisterSearchDTO) throws Exception;

    /**
     * 신청자를 등록한다.
     * @return
     */
    public int insertApply(WBKBRegisterDTO wBKBRegisterDTO, MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception;

    /**
     *   신청자 정보 조회
     */
    public WBKBRegisterDTO getApplyDtl(WBKBRegisterDTO wBKBRegisterDTO) throws Exception;

    /**
     * 공모전 수상자
     */
    public WBKBRegisterDTO selectWinerList(WBKBRegisterDTO wBKBRegisterDTO) throws Exception;
}
