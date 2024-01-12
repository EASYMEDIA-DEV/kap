package com.kap.service.impl.wb.wbf;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.wb.WBOrderMstDto;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.WBRoundOptnMstDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBFASmartRoundService;
import com.kap.service.dao.wb.wbf.WBFASmartRoundMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * 스마트공장구축 > 신청업체관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: WBFBRegisterCompanyServiceImpl.java
 * @Description		: 신청업체관리를 위한 ServiceImpl
 * @author 김동현
 * @since 2023.11.17
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.17		김동현				   최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WBFASmartRoundServiceImpl implements WBFASmartRoundService {

    /* Mapper */
    private final WBFASmartRoundMapper wBFASmartRoundMapper;

    /* 회차관리 마스터 시퀀스 */
    private final EgovIdGnrService cxEpisdSeqIdgen;

    /* 회차 사업옵션 마스터 시퀀스 */
    private final EgovIdGnrService cxEpisdBsnOptnSeqIdgen;

    /* 상생사업관리 지급 시퀀스 */
    private final EgovIdGnrService cxEpisdGiveSeqIdgen;

    /**
     *   신청부품사 목록 List Get
     */
    public WBRoundMstSearchDTO selRoundList(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {
        wBRoundMstSearchDTO.setBsnCd("BSN06");

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wBRoundMstSearchDTO.getPageIndex());
        page.setRecordCountPerPage(wBRoundMstSearchDTO.getListRowSize());

        page.setPageSize(wBRoundMstSearchDTO.getPageRowSize());

        if ("admin".equals(wBRoundMstSearchDTO.getSiteGubun())) {
            wBRoundMstSearchDTO.setFirstIndex(page.getFirstRecordIndex());
            wBRoundMstSearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());
        }

        wBRoundMstSearchDTO.setList(wBFASmartRoundMapper.selectSmartRoundList(wBRoundMstSearchDTO));
        wBRoundMstSearchDTO.setTotalCount(wBFASmartRoundMapper.selectSmartRoundListTotCnt(wBRoundMstSearchDTO));

        return wBRoundMstSearchDTO;
    }

    /**
     * 회차 관리 글 상세 Select
     */
    public WBRoundMstDTO selRoundDetail(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {
        String detailsKey = wBRoundMstSearchDTO.getDetailsKey();

        WBRoundMstDTO wBRoundMstDTO = wBFASmartRoundMapper.selRoundDetail(wBRoundMstSearchDTO);

        wBRoundMstDTO.setDetailsKey(detailsKey);
        wBRoundMstDTO.setGiveList(wBFASmartRoundMapper.selectGiveList(wBRoundMstDTO));
        wBRoundMstDTO.setOptnCd("BGN_REG_INF01001"); /* 사업유형 구분 코드 값 */
        wBRoundMstDTO.setBsinList(wBFASmartRoundMapper.selectBusinessList(wBRoundMstDTO));
        wBRoundMstDTO.setOptnCd("BGN_REG_INF01002"); /* 과제명 구분 코드 값 */
        wBRoundMstDTO.setAsigtList(wBFASmartRoundMapper.selectAssignmentList(wBRoundMstDTO));

        return wBRoundMstDTO;
    }

    /**
     * 연도 상세 조회
     */
    public WBRoundMstDTO selectYearDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {
        return wBFASmartRoundMapper.selectYearDtl(wBRoundMstSearchDTO);
    }

    /**
     * 회차 등록
     */
    @Transactional
    public WBRoundMstDTO insertRound(WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception {
        int respCnt = 0;

        int firstEpisdGiveSeqIdgen = 0;
        int firstEpisdBsnOptnSeqIdgen = 0;
        int firstEpisdSeqIdgen = cxEpisdSeqIdgen.getNextIntegerId();
        wBRoundMstDTO.setEpisdSeq(firstEpisdSeqIdgen);

        respCnt = wBFASmartRoundMapper.insertRound(wBRoundMstDTO);

        for(int i = 0; i < wBRoundMstDTO.getGiveList().size(); i++){

            firstEpisdGiveSeqIdgen = cxEpisdGiveSeqIdgen.getNextIntegerId();

            WBOrderMstDto wBOrderMstDto= wBRoundMstDTO.getGiveList().get(i);

            wBOrderMstDto.setEpisdSeq(firstEpisdSeqIdgen);
            wBOrderMstDto.setGiveSeq(firstEpisdGiveSeqIdgen);

            wBFASmartRoundMapper.insertGiveList(wBOrderMstDto);
        }

        /* 사업 옵션 - 사업 유형 */
        for(WBRoundOptnMstDTO list : wBRoundMstDTO.getBsinList()){
            firstEpisdBsnOptnSeqIdgen = cxEpisdBsnOptnSeqIdgen.getNextIntegerId();
            list.setOptnCd("BGN_REG_INF01001");
            list.setEpisdSeq(firstEpisdSeqIdgen);
            list.setBsnOptnSeq(firstEpisdBsnOptnSeqIdgen);
            wBFASmartRoundMapper.insertBsnOptnList(list);
        }

        /* 사업 옵션 - 과제명 */
        for(WBRoundOptnMstDTO list : wBRoundMstDTO.getAsigtList()){
            firstEpisdBsnOptnSeqIdgen = cxEpisdBsnOptnSeqIdgen.getNextIntegerId();
            list.setOptnCd("BGN_REG_INF01002");
            list.setEpisdSeq(firstEpisdSeqIdgen);
            list.setBsnOptnSeq(firstEpisdBsnOptnSeqIdgen);
            wBFASmartRoundMapper.insertBsnOptnList(list);
        }

        wBRoundMstDTO.setRespCnt(respCnt);

        return wBRoundMstDTO;
    }

    /**
     * 회차 수정
     */
    @Transactional
    public WBRoundMstDTO updateRound(WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception {
        int respCnt = 0;

        String detailsKey = wBRoundMstDTO.getDetailsKey();

        int firstEpisdGiveSeqIdgen = 0;
        int firstEpisdBsnOptnSeqIdgen = 0;

        wBFASmartRoundMapper.deleteGiveList(wBRoundMstDTO);

        wBRoundMstDTO.setOptnCd("BGN_REG_INF01001");
        wBFASmartRoundMapper.deleteBsnOptnList(wBRoundMstDTO);
        wBRoundMstDTO.setOptnCd("BGN_REG_INF01002");
        wBFASmartRoundMapper.deleteBsnOptnList(wBRoundMstDTO);

        for(int i = 0; i < wBRoundMstDTO.getGiveList().size(); i++) {
            firstEpisdGiveSeqIdgen = cxEpisdGiveSeqIdgen.getNextIntegerId();

            WBOrderMstDto wBOrderMstDto = wBRoundMstDTO.getGiveList().get(i);

            wBOrderMstDto.setGiveSeq(firstEpisdGiveSeqIdgen);
            wBOrderMstDto.setDetailsKey(detailsKey);

            wBFASmartRoundMapper.updateGiveList(wBOrderMstDto);
        }

        /* 사업 옵션 - 사업 유형 */
        for (WBRoundOptnMstDTO list : wBRoundMstDTO.getBsinList()) {
            firstEpisdBsnOptnSeqIdgen = cxEpisdBsnOptnSeqIdgen.getNextIntegerId();
            list.setOptnCd("BGN_REG_INF01001");
            list.setEpisdSeq(Integer.parseInt(detailsKey));
            list.setBsnOptnSeq(firstEpisdBsnOptnSeqIdgen);
            wBFASmartRoundMapper.insertBsnOptnList(list);
        }

        /* 사업 옵션 - 과제명 */
        for (WBRoundOptnMstDTO list : wBRoundMstDTO.getAsigtList()) {
            firstEpisdBsnOptnSeqIdgen = cxEpisdBsnOptnSeqIdgen.getNextIntegerId();
            list.setOptnCd("BGN_REG_INF01002");
            list.setEpisdSeq(Integer.parseInt(detailsKey));
            list.setBsnOptnSeq(firstEpisdBsnOptnSeqIdgen);
            wBFASmartRoundMapper.insertBsnOptnList(list);
        }

        respCnt = wBFASmartRoundMapper.updateRound(wBRoundMstDTO);

        wBRoundMstDTO.setRespCnt(respCnt);

        return wBRoundMstDTO;
    }

    /**
     * 회차 관리 수정 - 노출여부 수정
     */
    public int updateExpsYn(WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception {
        int respCnt = 0;

        respCnt = wBFASmartRoundMapper.updateExpsYn(wBRoundMstDTO);

        return respCnt;
    }

    /**
     * 회차 삭제
     */
    @Transactional
    public int deleteRound(WBRoundMstDTO wBRoundMstDTO) throws Exception {
        int respCnt = 0;

        respCnt = wBFASmartRoundMapper.deleteRound(wBRoundMstDTO);
        wBFASmartRoundMapper.deleteGiveList(wBRoundMstDTO);

        wBRoundMstDTO.setOptnCd("BGN_REG_INF01001");
        wBFASmartRoundMapper.deleteBsnOptnList(wBRoundMstDTO);
        wBRoundMstDTO.setOptnCd("BGN_REG_INF01002");
        wBFASmartRoundMapper.deleteBsnOptnList(wBRoundMstDTO);

        return respCnt;
    }

    /**
     * 회차 관리 등록 - 등록된 회차 확인
     */
    public int getRoundChk(WBRoundMstDTO wBRoundMstDTO) throws Exception {
        int respCnt = 0;

        respCnt = wBFASmartRoundMapper.getRoundChk(wBRoundMstDTO);

        return respCnt;
    }

    /**
     * 회차 관리 수정 - 등록된 신청부품사
     */
    public int getRegisterChk(WBRoundMstDTO wBRoundMstDTO) throws Exception {
        int respCnt = 0;

        respCnt = wBFASmartRoundMapper.getRegisterChk(wBRoundMstDTO);

        return respCnt;
    }

    /**
     * 최신 회차 상세 조회
     */
    public WBRoundMstSearchDTO getRecentRoundDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {

        wBRoundMstSearchDTO = wBFASmartRoundMapper.getRecentRoundDtl(wBRoundMstSearchDTO);

        return wBRoundMstSearchDTO;
    }

    /**
     * 신청 여부 조회
     */
    public int getApplyChecked(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {

        int rtnCode = 0;

        COUserDetailsDTO cOUserDetailsDTO = null;

        if (!COUserDetailsHelperService.isAuthenticated())
        {
            //비로그인 코드 100
            rtnCode = 999;
        }
        else
        {
            cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();

            if (!"CP".equals(cOUserDetailsDTO.getAuthCd())) {
                if ("CS".equals(cOUserDetailsDTO.getAuthCd())) {
                    //위원인 경우 150
                    rtnCode = 150;
                } else {
                    //부품사회원이 아닌경우 100
                    rtnCode = 100;
                }
            } else if ("CP".equals(cOUserDetailsDTO.getAuthCd())) {
                wBRoundMstSearchDTO.setMemSeq(cOUserDetailsDTO.getSeq());
                int cnt = wBFASmartRoundMapper.getApplyCount(wBRoundMstSearchDTO);

                if (cnt > 0) {
                    //신청여부 존재 코드 300
                    rtnCode = 300;
                } else {
                    //신청가능 코드 200
                    rtnCode = 200;
                }
            }
        }

        return rtnCode;
    }

}
