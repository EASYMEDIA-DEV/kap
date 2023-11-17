package com.kap.service;

import com.kap.core.dto.COUserCmpnDto;
import com.kap.core.dto.eb.ebd.EBDEdctnEdisdDTO;
import com.kap.core.dto.eb.ebd.EBDPrePrcsDTO;
import com.kap.core.dto.eb.ebd.EBDSqCertiSearchDTO;
import com.kap.core.dto.eb.ebg.EBGExamAppctnMstDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * SQ평가원 자격증 신청관리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.09  김학규         최초 생성
 * </pre>
 */
public interface EBDSqCertiReqService {
    /**
     * 리스트 조회
    */
    public EBDSqCertiSearchDTO selectList(EBDSqCertiSearchDTO eBDSqCertiSearchDTO) throws Exception;

    /**
     * 리스트 조회
     */
    public EBDEdctnEdisdDTO selectView(EBDSqCertiSearchDTO eBDSqCertiSearchDTO) throws Exception;

    /**
     * 자격증 상세
     */
    public EBGExamAppctnMstDTO selectExamAppctnMst(EBDSqCertiSearchDTO eBDSqCertiSearchDTO) throws Exception;

    /**
     * 선수과목 수료 목록 조회
     */
    public List<EBDPrePrcsDTO> getPrePrcsList(EBDSqCertiSearchDTO eBDSqCertiSearchDTO) throws Exception;

    /**
     * 수정
     */
    public int update(COUserCmpnDto cOUserCmpnDto, EBDEdctnEdisdDTO eBDEdctnEdisdDTO, HttpServletRequest request) throws Exception;

    /**
     * 수정
     */
    public int updateConfirmInfo(COUserCmpnDto cOUserCmpnDto, EBGExamAppctnMstDTO eBGExamAppctnMstDTO, HttpServletRequest request) throws Exception;
}
