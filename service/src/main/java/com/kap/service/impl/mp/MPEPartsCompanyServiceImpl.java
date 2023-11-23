package com.kap.service.impl.mp;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.MPEPartsCompanyDTO;
import com.kap.service.MPEPartsCompanyService;
import com.kap.service.dao.mp.MPEPartsCompanyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 부품사 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: MPEPartsCompanyServiceImpl.java
 * @Description		: 부품사 관리를 위한 ServiceImpl
 * @author 구은희
 * @since 2023.11.09
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.09		구은희				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class MPEPartsCompanyServiceImpl implements MPEPartsCompanyService {

    // DAO
    private final MPEPartsCompanyMapper mpePartsCompanyMapper;
    
    /* 시퀀스 */
    private final EgovIdGnrService mpePartsCompanyDtlIdgen;

    /**
     * 부품사 목록을 조회한다.
     */
    public MPEPartsCompanyDTO selectPartsCompanyList(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(mpePartsCompanyDTO.getPageIndex());
        page.setRecordCountPerPage(mpePartsCompanyDTO.getListRowSize());

        page.setPageSize(mpePartsCompanyDTO.getPageRowSize());

        mpePartsCompanyDTO.setFirstIndex(page.getFirstRecordIndex());
        mpePartsCompanyDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        mpePartsCompanyDTO.setList(mpePartsCompanyMapper.selectPartsCompanyList(mpePartsCompanyDTO));
        mpePartsCompanyDTO.setTotalCount(mpePartsCompanyMapper.selectPartsCompanyCnt(mpePartsCompanyDTO));

        return mpePartsCompanyDTO;
    }

    /**
     * 부품사 개수를 조회한다.
     */
    @Override
    public int selectPartsCompanyCnt(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {
        return mpePartsCompanyMapper.selectPartsCompanyCnt(mpePartsCompanyDTO);
    }

    /**
     * 상세를 조회한다.
     */
    public MPEPartsCompanyDTO selectPartsCompanyDtl(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {
        mpePartsCompanyDTO.setList(mpePartsCompanyMapper.selectPartsCompanyDtl(mpePartsCompanyDTO));

        return mpePartsCompanyDTO;
    }

    /**
     * 부품사 사업자번호 등록여부를 확인한다.
     */
    public MPEPartsCompanyDTO checkBsnmNo(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {
        return mpePartsCompanyMapper.checkBsnmNo(mpePartsCompanyDTO);
    }

    /**
     * 부품사를 등록한다.
     */
    public int insertPartsCompany(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {

        if (mpePartsCompanyDTO.getCtgryCd().equals("COMPANY01002")) {
            List<String> sqList1 = mpePartsCompanyDTO.getSqInfoList1();
            List<String> sqList2 = mpePartsCompanyDTO.getSqInfoList2();
            List<String> sqList3 = mpePartsCompanyDTO.getSqInfoList3();

            List<List<String>> allSqLists = new ArrayList<>();
            allSqLists.add(sqList1);
            allSqLists.add(sqList2);
            allSqLists.add(sqList3);

            String nm = "";
            int score = 0;
            int year = 0;
            String crtfnCmnNm = "";
            int index = 1;

            sqLoop:
            for(List<String> sqList : allSqLists) {
                for (int i = 1; i < sqList.size(); i++) {
                    String info = sqList.get(i);
                    if(info == null || info.isEmpty()) {
                        continue sqLoop;
                    }
                }
                nm = sqList.get(1);
                year = Integer.parseInt(sqList.get(3));
                score = Integer.parseInt(sqList.get(2));
                crtfnCmnNm = sqList.get(4);

                mpePartsCompanyDTO.setNm(nm);
                mpePartsCompanyDTO.setYear(year);
                mpePartsCompanyDTO.setScore(score);
                mpePartsCompanyDTO.setCrtfnCmpnNm(crtfnCmnNm);
                mpePartsCompanyDTO.setCbsnSeq(mpePartsCompanyDtlIdgen.getNextIntegerId());

                mpePartsCompanyMapper.insertPartsComSQInfo(mpePartsCompanyDTO);
                index += 1;
            }
        }
        return mpePartsCompanyMapper.insertPartsCompany(mpePartsCompanyDTO);
    }

    /**
     * 부품사를 수정한다.
     */
    public int updatePartsCompany(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {

        if (mpePartsCompanyDTO.getCtgryCd().equals("COMPANY01002")) {
            List<String> sqList1 = mpePartsCompanyDTO.getSqInfoList1();
            List<String> sqList2 = mpePartsCompanyDTO.getSqInfoList2();
            List<String> sqList3 = mpePartsCompanyDTO.getSqInfoList3();

            List<List<String>> allSqLists = new ArrayList<>();
            allSqLists.add(sqList1);
            allSqLists.add(sqList2);
            allSqLists.add(sqList3);

            String seq = "";
            String nm = "";
            String score = "";
            String year = "";
            String crtfnCmnNm = "";
            int index = 1;

            sqLoop:
            for(List<String> sqList : allSqLists) {
                if (sqList == null) {
                    continue sqLoop;
                }

                seq = sqList.get(0);
                nm = sqList.get(1);
                year = sqList.get(3);
                score = sqList.get(2);
                crtfnCmnNm = sqList.get(4);


                if(year.equals("")|| year.isEmpty()) {
                    mpePartsCompanyDTO.setYear(null);
                } else {
                    mpePartsCompanyDTO.setYear(Integer.valueOf(year));
                }

                if(score.equals("")|| score.isEmpty()) {
                    mpePartsCompanyDTO.setScore(null);
                } else {
                    mpePartsCompanyDTO.setScore(Integer.valueOf(score));
                }
                mpePartsCompanyDTO.setNm(nm);
                mpePartsCompanyDTO.setCrtfnCmpnNm(crtfnCmnNm);
                if (!seq.isEmpty()) {
                    mpePartsCompanyDTO.setCbsnSeq(Integer.valueOf(seq));
                    mpePartsCompanyMapper.updatePartsComSQInfo(mpePartsCompanyDTO);
                } else {
                    mpePartsCompanyDTO.setCbsnSeq(mpePartsCompanyDtlIdgen.getNextIntegerId());
                    mpePartsCompanyMapper.insertPartsComSQInfo(mpePartsCompanyDTO);
                }
                index += 1;
            }
        }
        return mpePartsCompanyMapper.updatePartsCompany(mpePartsCompanyDTO);
    }

    /**
     * 부품사를 삭제한다.
     */
    @Transactional
    public int deletePartsCompany(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {
        return mpePartsCompanyMapper.deletePartsCompany(mpePartsCompanyDTO);
    }

    /**
     * 교육사업 개수를 조회한다.
     */
    public int selectEduCnt(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {
        return mpePartsCompanyMapper.selectEduCnt(mpePartsCompanyDTO);
    }

    /**
     * 교육사업 개수를 조회한다.
     */
    public int selectConsultingCnt(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {
        return mpePartsCompanyMapper.selectConsultingCnt(mpePartsCompanyDTO);
    }

    /**
     * 상생사업 개수를 조회한다.
     */
    public int selectWinBusinessCnt(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {
        return mpePartsCompanyMapper.selectWinBusinessCnt(mpePartsCompanyDTO);
    }
}
