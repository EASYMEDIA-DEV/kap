package com.kap.service.impl.wb.wbb;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.wb.WBOrderMstDto;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wba.WBAManagementOptnDTO;
import com.kap.core.dto.wb.wbb.WBBACompanyDTO;
import com.kap.core.dto.wb.wbb.WBBACompanySearchDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBBARoundService;
import com.kap.service.dao.wb.wbb.WBBARoundMapper;
import com.kap.service.dao.wb.wbb.WBBBCompanyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <pre>
 * 공통 회차관리 ServiceImpl
 * </pre>
 *
 * @ClassName		: WBBARoundServiceImpl.java
 * @Description		: 공통 회차관리 ServiceImpl
 * @author 김태훈
 * @since 2023.11.24
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.24		김태훈				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class WBBARoundServiceImpl implements WBBARoundService {

    //Mapper
    private final WBBARoundMapper wBBARoundMapper;
    private final WBBBCompanyMapper wbbbCompanyMapper;

    /* 회차관리 마스터 시퀀스 */
    private final EgovIdGnrService cxEpisdSeqIdgen;

    /**
     * 회차 목록
     */
    public WBRoundMstSearchDTO selectRoundList(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        WBRoundMstDTO wBRoundMstDTO = new WBRoundMstDTO();

        page.setCurrentPageNo(wBRoundMstSearchDTO.getPageIndex());
        page.setRecordCountPerPage(wBRoundMstSearchDTO.getListRowSize());

        page.setPageSize(wBRoundMstSearchDTO.getPageRowSize());

        if ("admin".equals(wBRoundMstSearchDTO.getSiteGubun())) {
            wBRoundMstSearchDTO.setFirstIndex(page.getFirstRecordIndex());
            wBRoundMstSearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());
        }

        wBRoundMstSearchDTO.setList(wBBARoundMapper.selectRoundList(wBRoundMstSearchDTO));
        wBRoundMstSearchDTO.setTotalCount(wBBARoundMapper.getRoundListTotCnt(wBRoundMstSearchDTO));

        return wBRoundMstSearchDTO;
    }

    /**
     * 회차 상세 조회
     */
    public WBRoundMstDTO selectRoundDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {

        String detailsKey = wBRoundMstSearchDTO.getDetailsKey();

        WBRoundMstDTO wBRoundMstDTO = wBBARoundMapper.selectRoundDtl(wBRoundMstSearchDTO);

        return wBRoundMstDTO;
    }

    /**
     * 연도 상세 조회
     */
    public WBRoundMstDTO selectYearDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {

        return wBBARoundMapper.selectYearDtl(wBRoundMstSearchDTO);
    }

    /**
     * 회차 등록
     */
    public int insertRound(WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        int firstEpisdGiveSeqIdgen = 0;
        int firstEpisdSeqIdgen = cxEpisdSeqIdgen.getNextIntegerId();
        wBRoundMstDTO.setEpisdSeq(firstEpisdSeqIdgen);

        respCnt = wBBARoundMapper.insertRound(wBRoundMstDTO);

        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 수정
     */
    public int updateRound(WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        respCnt = wBBARoundMapper.updateRound(wBRoundMstDTO);

        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 삭제
     */
    public int deleteRound(WBRoundMstDTO wBRoundMstDTO) throws Exception {

        int respCnt = 0;

        respCnt = wBBARoundMapper.deleteRound(wBRoundMstDTO);

        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 최신 회차 상세 조회
     */
    public WBRoundMstSearchDTO getRoundDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {

        wBRoundMstSearchDTO = wBBARoundMapper.getRoundDtl(wBRoundMstSearchDTO);

        if (wBRoundMstSearchDTO != null) {
            wBRoundMstSearchDTO.setStageOrd(1);
            //공통사업의 경우 신청단계의 옵션정보를 가져온다. 그외 사업의 경우 양식관리 파일정보를 가져와야함.
            List<WBAManagementOptnDTO> optionList = wBBARoundMapper.selectOPtnList(wBRoundMstSearchDTO);
            wBRoundMstSearchDTO.setOptnList(optionList);
        }

        return wBRoundMstSearchDTO;
    }

    /**
     * 최신 회차 상세 조회
     */
    public int getApplyChecked(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {

        int rtnCode = 0;

        COUserDetailsDTO cOUserDetailsDTO = null;

        if (!COUserDetailsHelperService.isAuthenticated())
        {
            //비로그인 코드 999
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

                WBBACompanySearchDTO wbbaCompanySearchDTO = new WBBACompanySearchDTO();
                wbbaCompanySearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());

                WBBACompanyDTO wbbaCompanyDTO = new WBBACompanyDTO();
                wbbaCompanyDTO = wbbbCompanyMapper.getCompanyInfo(wbbaCompanySearchDTO);

                if ("COMPANY01001".equals(wbbaCompanyDTO.getCtgryCd()) || "COMPANY01002".equals(wbbaCompanyDTO.getCtgryCd())) {
                    wBRoundMstSearchDTO.setMemSeq(cOUserDetailsDTO.getSeq());
                    int cnt = wBBARoundMapper.getApplyCount(wBRoundMstSearchDTO);

                    if (cnt > 0) {
                        //신청여부 존재 코드 300
                        rtnCode = 300;
                    } else {
                        //신청가능 코드 200
                        rtnCode = 200;
                    }
                } else {
                    //부품사가 1차 2차가 아닐떄,
                    rtnCode = 190;
                }

            }
        }

        return rtnCode;
    }

    /**
     * 회차 매핑 여부 확인
     */
    public int getAppctnCnt(WBRoundMstDTO wBRoundMstDTO) throws Exception {

        int respCnt = 0;

        respCnt = wBBARoundMapper.getAppctnCnt(wBRoundMstDTO);

        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 삭제
     */
    public int deleteList(WBRoundMstDTO wBRoundMstDTO) throws Exception {

        int respCnt = wBBARoundMapper.deleteList(wBRoundMstDTO);

        return respCnt;
    }

    /**
     * 회차 중복 체크
     */
    public int episdChk(WBRoundMstDTO wBRoundMstDTO) throws Exception {

        int respCnt = wBBARoundMapper.episdChk(wBRoundMstDTO);
        return respCnt;
    }
}
