package com.kap.service.impl.mp;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.MPEPartsCompanyDTO;
import com.kap.service.MPEPartsCompanyService;
import com.kap.service.dao.mp.MPEPartsCompanyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    String tableNm = "PARTS_COM_SQ_SEQ";

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

        return mpePartsCompanyMapper.selectPartsCompanyDtl(mpePartsCompanyDTO);
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

        mpePartsCompanyDTO.setTableNm(tableNm);
        String detailsKey = mpePartsCompanyMapper.selectSeqNum(mpePartsCompanyDTO.getTableNm());
        mpePartsCompanyDTO.setCbsnSeq(Integer.valueOf(detailsKey));
        mpePartsCompanyMapper.updatePartsCompanySeq(tableNm);
        mpePartsCompanyMapper.insertPartsComSQInfo(mpePartsCompanyDTO);

        return mpePartsCompanyMapper.insertPartsCompany(mpePartsCompanyDTO);
    }

    /**
     * 부품사를 수정한다.
     */
    public int updatePartsCompany(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {
       /* String detailsKey = mpePartsCompanyMapper.selectSeqNum(mpePartsCompanyDTO.getTableNm());
        mpePartsCompanyDTO.setCbsnSeq(Integer.valueOf(detailsKey));
        mpePartsCompanyMapper.updatePartsCompanySeq(tableNm);
        // 수정 페이지에서 SQ 정보를 추가하면 이 부분은 insert가 진행되어야 함.*/
        return mpePartsCompanyMapper.updatePartsCompany(mpePartsCompanyDTO);
    }

    /**
     * 부품사를 삭제한다.
     */
    @Transactional
    public int deletePartsCompany(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {
        return mpePartsCompanyMapper.deletePartsCompany(mpePartsCompanyDTO);
    }
}
