package com.kap.service.impl.mp;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.mp.mpb.MPBBsnSearchDTO;
import com.kap.core.dto.mp.mpb.MPBCompanyDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.MPBCoexistenceService;
import com.kap.service.dao.mp.MPBCoexistenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 * 마이페이지 상생 사업 신청내역 ServiceImpl
 * </pre>
 *
 * @ClassName		: MPBCoexistenceServiceImpl.java
 * @Description		: 마이페이지 상생 사업 신청내역 ServiceImpl
 * @author 김태훈
 * @since 2024.01.08
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2024.01.08		김태훈				   최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MPBCoexistenceServiceImpl implements MPBCoexistenceService {

    private final MPBCoexistenceMapper mpbCoexistenceMapper;

    /**
     *   마이페이지 상생 사업 신청내역 List
     */
    public MPBBsnSearchDTO selectApplyList(MPBBsnSearchDTO mpbBnsSearchDTO, String type) throws Exception
    {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(mpbBnsSearchDTO.getPageIndex());
        page.setRecordCountPerPage(mpbBnsSearchDTO.getListRowSize());

        page.setPageSize(mpbBnsSearchDTO.getPageRowSize());

        if ("init".equals(type)) {
            mpbBnsSearchDTO.setFirstIndex(page.getFirstRecordIndex());
            mpbBnsSearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());
        }

        COUserDetailsDTO cOUserDetailsDTO = null;
        cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();

        mpbBnsSearchDTO.setMemSeq(cOUserDetailsDTO.getSeq());

        mpbBnsSearchDTO.setList(mpbCoexistenceMapper.selectApplyList(mpbBnsSearchDTO));
        mpbBnsSearchDTO.setTotalCount(mpbCoexistenceMapper.selectApplyCount(mpbBnsSearchDTO));

        return mpbBnsSearchDTO;
    }

    /**
     *   마이페이지 상생 사업 신청내역 count
     */
    public int selectApplyCount(MPBBsnSearchDTO mpbBnsSearchDTO) throws Exception
    {
        return mpbCoexistenceMapper.selectApplyCount(mpbBnsSearchDTO);
    }

    /**
     *   마이페이지 공통 상생 사업여부
     */
    public String getBusinessYn(MPBBsnSearchDTO mpbBnsSearchDTO) throws Exception
    {
        String businessYn = mpbCoexistenceMapper.getBusinessYn(mpbBnsSearchDTO);

        return businessYn;
    }

    /**
     *   마이페이지 상생 상세
     */
    public MPBBsnSearchDTO getBsnDetail(MPBBsnSearchDTO mpbBnsSearchDTO) throws Exception
    {
        return mpbCoexistenceMapper.getBsnDetail(mpbBnsSearchDTO);
    }

    /**
     * 부품사 회원 정보를 조회한다.
     * @return
     */
    public MPBCompanyDTO selectCompanyUserDtl(MPBBsnSearchDTO mpbBnsSearchDTO) throws Exception {

        MPBCompanyDTO mpbCompanyDTO = new MPBCompanyDTO();

        mpbCompanyDTO = mpbCoexistenceMapper.getCompanyInfo(mpbBnsSearchDTO);
        List<MPBCompanyDTO> sqList = mpbCoexistenceMapper.selectSqList(mpbBnsSearchDTO);
        mpbCompanyDTO.setSqInfoList(sqList);
        mpbCompanyDTO.setMemSeq(mpbBnsSearchDTO.getMemSeq());

        return mpbCompanyDTO;
    }

    /**
     *   마이페이지 상생 사업 사용자 신청취소
     */
    public int updateUserCancel(MPBBsnSearchDTO mpbBnsSearchDTO, String businessYn) throws Exception
    {
        int respCnt = 0;
        if ("Y".equals(businessYn)) {
            //공통사업
            mpbBnsSearchDTO.setAppctnSttsCd("PRO_TYPE04_2_5");
            mpbBnsSearchDTO.setMngSttsCd("PRO_TYPE04_1_4");
        } else {
            if("BSN03".equals(mpbBnsSearchDTO.getBsnCd()) || "BSN04".equals(mpbBnsSearchDTO.getBsnCd()) || "BSN05".equals(mpbBnsSearchDTO.getBsnCd())) {
                //보안환경구축, 안전설비구축, 탄소배출저감
                mpbBnsSearchDTO.setAppctnSttsCd("PRO_TYPE01001_01_006");
                mpbBnsSearchDTO.setMngSttsCd("PRO_TYPE01001_02_006");
            } else if ("BSN06".equals(mpbBnsSearchDTO.getBsnCd())) {
                //스마트공장구축
                mpbBnsSearchDTO.setAppctnSttsCd("PRO_TYPE02001_01_004");
                mpbBnsSearchDTO.setMngSttsCd("PRO_TYPE02001_02_003");
            } else if ("BSN07".equals(mpbBnsSearchDTO.getBsnCd()) || "BSN08".equals(mpbBnsSearchDTO.getBsnCd())) {
                //시험계측장비
                mpbBnsSearchDTO.setAppctnSttsCd("PRO_TYPE07001_01_004");
                mpbBnsSearchDTO.setMngSttsCd("PRO_TYPE07001_02_004");
            } else if ("BSN09".equals(mpbBnsSearchDTO.getBsnCd())) {
                //공급망안정화기금
                mpbBnsSearchDTO.setAppctnSttsCd("PRO_TYPE06001_01_004");
                mpbBnsSearchDTO.setMngSttsCd("PRO_TYPE06001_02_005");
            } else if ("BSN10".equals(mpbBnsSearchDTO.getBsnCd())) {
                //자동차부품산업대상
                mpbBnsSearchDTO.setAppctnSttsCd("PRO_TYPE05001_01_004");
                mpbBnsSearchDTO.setMngSttsCd("PRO_TYPE05001_02_004");
            } else if ("BSN11".equals(mpbBnsSearchDTO.getBsnCd())) {
                //미래차공모전전
                mpbBnsSearchDTO.setAppctnSttsCd("WBKB_REG_FRT004");
                mpbBnsSearchDTO.setMngSttsCd("WBKB_REG_LRT004");
            }
       }
        respCnt = mpbCoexistenceMapper.updateUserCancel(mpbBnsSearchDTO);
        return respCnt;
    }
}
