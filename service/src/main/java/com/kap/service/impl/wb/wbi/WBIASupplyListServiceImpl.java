package com.kap.service.impl.wb.wbi;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.ex.exg.EXGExamQstnDtlDTO;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wbb.WBBACompanyDTO;
import com.kap.core.dto.wb.wbb.WBBACompanySearchDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBIASupplyListService;
import com.kap.service.dao.wb.wbb.WBBBCompanyMapper;
import com.kap.service.dao.wb.wbi.WBIASupplyListMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 공급망안정화기금 > 신청업체관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: WBIASupplyListServiceImpl.java
 * @Description		: 신청업체관리를 위한 ServiceImpl
 * @author 오병호
 * @since 2023.11.17
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.17		오병호				   최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WBIASupplyListServiceImpl implements WBIASupplyListService {


    //Mapper
    private final WBIASupplyListMapper wBIASupplyListMapper;
    private final WBBBCompanyMapper wbbbCompanyMapper;

    /* 회차관리 마스터 시퀀스 */
    private final EgovIdGnrService cxEpisdSeqIdgen;

    /**
     * 회차 목록
     */
    public WBRoundMstSearchDTO selectSupplyList(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();
        page.setCurrentPageNo(wBRoundMstSearchDTO.getPageIndex());
        page.setRecordCountPerPage(wBRoundMstSearchDTO.getListRowSize());
        page.setPageSize(wBRoundMstSearchDTO.getPageRowSize());

        if ("admin".equals(wBRoundMstSearchDTO.getSiteGubun())) {
            wBRoundMstSearchDTO.setFirstIndex(page.getFirstRecordIndex());
            wBRoundMstSearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());
        }
        wBRoundMstSearchDTO.setList(wBIASupplyListMapper.selectSupplyList(wBRoundMstSearchDTO));
        wBRoundMstSearchDTO.setTotalCount(wBIASupplyListMapper.getSupplyListTotCnt(wBRoundMstSearchDTO));

        return wBRoundMstSearchDTO;
    }

    /**
     * 회차 등록
     */
    public int insertSupply(WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        //신청시간 년도 구하기
        Date now = new Date();
        SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");

        wBRoundMstDTO.setYear(Integer.valueOf(yyyy.format(now)));

        int firstEpisdSeqIdgen = cxEpisdSeqIdgen.getNextIntegerId();
        wBRoundMstDTO.setEpisdSeq(firstEpisdSeqIdgen);
        wBRoundMstDTO.setBsnStrtDtm(wBRoundMstDTO.getYear()+"-01-01 00:00:00");
        wBRoundMstDTO.setBsnEndDtm(wBRoundMstDTO.getYear()+"-12-31 23:59:59");

        respCnt = wBIASupplyListMapper.insertSupply(wBRoundMstDTO);

        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 수정
     */
    public int updateSupply(WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        respCnt = wBIASupplyListMapper.updateSupply(wBRoundMstDTO);

        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 삭제
     */
    public int deleteSupply(WBRoundMstDTO wBRoundMstDTO) throws Exception {

        int respCnt = 0;

        respCnt = wBIASupplyListMapper.deleteSupply(wBRoundMstDTO);

        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 상세 조회
     */
    public WBRoundMstDTO selectSupplyDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {

        String detailsKey = wBRoundMstSearchDTO.getDetailsKey();

        WBRoundMstDTO wBRoundMstDTO = wBIASupplyListMapper.selectSupplyDtl(wBRoundMstSearchDTO);

        if(wBRoundMstDTO != null) {
            wBRoundMstDTO.setDetailsKey(detailsKey);
        }

        return wBRoundMstDTO;
    }

    /**
     * 연도 상세 조회
     */
    public WBRoundMstDTO selectYearDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {
        return wBIASupplyListMapper.selectYearDtl(wBRoundMstSearchDTO);
    }

    /**
     * 회차 삭제
     */
    public int SupplyDeleteList(WBRoundMstDTO wBRoundMstDTO) throws Exception {

        int respCnt = wBIASupplyListMapper.SupplyDeleteList(wBRoundMstDTO);

        return respCnt;
    }

    /**
     *  선택 연도 값에 따른 episdCnt 값 가져오기
     */
    public List<String> roundCnt(WBRoundMstDTO wBRoundMstDTO)  throws Exception
    {
        return wBIASupplyListMapper.roundCnt(wBRoundMstDTO);
    }

    /**
     *  해당년도 신청 갯수
     */
    public List<String> episdCnt(WBRoundMstDTO wBRoundMstDTO)  throws Exception
    {
        EXGExamQstnDtlDTO eXGExamQstnDtlDTO = new EXGExamQstnDtlDTO();
        eXGExamQstnDtlDTO.setDelValueList( wBRoundMstDTO.getDelValueList() );

        return wBIASupplyListMapper.episdCnt(wBRoundMstDTO);
    }

    /**
     * 최신 회차 상세 조회
     */
    public WBRoundMstSearchDTO getRoundDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {

        wBRoundMstSearchDTO = wBIASupplyListMapper.getRoundDtl(wBRoundMstSearchDTO);

        if (wBRoundMstSearchDTO != null) {
            //공통사업의 경우 신청단계의 옵션정보를 가져온다. 그외 사업의 경우 양식관리 파일정보를 가져와야함.
            wBRoundMstSearchDTO.setOptnList(wBIASupplyListMapper.selectOPtnList(wBRoundMstSearchDTO));
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
                    wBRoundMstSearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());
                    int cnt = wBIASupplyListMapper.getApplyCount(wBRoundMstSearchDTO);

                    if (cnt > 0) {
                        //신청여부 존재 코드 300
                        rtnCode = 300;
                    } else {
                        //신청가능 코드 200
                        rtnCode = 200;
                        RequestContextHolder.getRequestAttributes().setAttribute("contentAuth", wBRoundMstSearchDTO.getEpisdSeq(), RequestAttributes.SCOPE_SESSION);
                    }
                } else {
                    //부품사가 1차 2차가 아닐떄,
                    rtnCode = 190;
                }

                //같은 회차에 동일 부품사가 신청한 내역이 있는지
                int rtnCnt = wBIASupplyListMapper.getApplyPartsCount(wBRoundMstSearchDTO);
                if (rtnCnt > 0) {
                    rtnCode = 460;
                 }
            }
        }
        return rtnCode;
    }
}
