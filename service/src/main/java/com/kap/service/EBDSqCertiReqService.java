package com.kap.service;

import com.kap.core.dto.COUserCmpnDto;
import com.kap.core.dto.MPBEduDto;
import com.kap.core.dto.eb.ebd.EBDEdctnEdisdDTO;
import com.kap.core.dto.eb.ebd.EBDPrePrcsDTO;
import com.kap.core.dto.eb.ebd.EBDSqCertiListDTO;
import com.kap.core.dto.eb.ebd.EBDSqCertiSearchDTO;
import com.kap.core.dto.eb.ebg.EBGExamAppctnMstDTO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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

    /**
     * 만료일시 기준 몇개월전 알림 발송
     */
    public List<EBDSqCertiListDTO> getSqValidEndEmailList(int validMonth) throws Exception;

    /**
     * 사용자 MY-PAGE 참여한 교육중 자격증연계코드의 값이 LCNS_CNNCT02이고 수료 완료인 경우
     */
    public EBDSqCertiSearchDTO getEducationCompleteList(EBDSqCertiSearchDTO eBDSqCertiSearchDTO) throws Exception;
    /**
     * 사용자 MY-PAGE 참여한 교육중 자격증연계코드의 값이 LCNS_CNNCT02이고 수료 완료인 갯수
     */
    public int selectEducationCompleteListCnt(EBDSqCertiSearchDTO eBDSqCertiSearchDTO) throws Exception;

    /**
     * 사용자 MY-PAGE 참여한 교육중 자격증연계코드의 값이 LCNS_CNNCT02이고 보수 과목
     */
    public EBDSqCertiSearchDTO getEducationRepairList(EBDSqCertiSearchDTO eBDSqCertiSearchDTO) throws Exception;

    /**
     * SQ 평가원 자격증 신청 조건(자격증 연계를 수료하였고 평가원을 신청하지 않아야함)
     */
    public int getPosibleSqCertiCnt(EBDSqCertiSearchDTO eBDSqCertiSearchDTO) throws Exception;

    /**
     * SQ 평가원 자격증 신청
     */
    public int insert(MultipartHttpServletRequest multiRequest) throws Exception;
}
