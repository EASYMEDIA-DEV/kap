package com.kap.service.impl.sm;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.SMELegNtcDTO;
import com.kap.service.SMELegNtcService;
import com.kap.service.dao.sm.SMELegNtcMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 * 법적고지 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: SMELegNtcServiceImpl.java
 * @Description		: 법적고지 관리를 위한 ServiceImpl
 * @author 구은희
 * @since 2023.10.04
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.10.04		구은희				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class SMELegNtcServiceImpl implements SMELegNtcService {

    // DAO
    private final SMELegNtcMapper smeLegNtcMapper;
    String tableNm = "LEG_NTC_SEQ";
    /**
     * 목록을 조회한다.
     */
    public SMELegNtcDTO selectLegNtcList(SMELegNtcDTO smeLegNtcDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(smeLegNtcDTO.getPageIndex());
        page.setRecordCountPerPage(smeLegNtcDTO.getListRowSize());

        page.setPageSize(smeLegNtcDTO.getPageRowSize());

        smeLegNtcDTO.setFirstIndex( page.getFirstRecordIndex() );
        smeLegNtcDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

        smeLegNtcDTO.setList(smeLegNtcMapper.selectLegNtcList(smeLegNtcDTO));
        smeLegNtcDTO.setTotalCount(smeLegNtcMapper.selectLegNtcCnt(smeLegNtcDTO));
        smeLegNtcDTO.setList(smeLegNtcMapper.selectLegNtcList(smeLegNtcDTO));
        return smeLegNtcDTO;
    }
    /**
     * 목록의 개수를 조회한다..
     */
    public int selectLegNtcCnt(SMELegNtcDTO smeLegNtcDTO) throws Exception {
        return smeLegNtcMapper.selectLegNtcCnt(smeLegNtcDTO);
    }
    /**
     * 상세를 조회한다.
     */
    public SMELegNtcDTO selectLegNtcDtl(SMELegNtcDTO smeLegNtcDTO) throws Exception {
        return smeLegNtcMapper.selectLegNtcDtl(smeLegNtcDTO);
    }
    /**
     * 게시물을 등록한다.
     */
    public int insertLegNtc(SMELegNtcDTO smeLegNtcDTO) throws Exception {

        smeLegNtcDTO.setTableNm(tableNm);
        String detailsKey = smeLegNtcMapper.selectSeqNum(smeLegNtcDTO.getTableNm());
        smeLegNtcDTO.setDetailsKey(detailsKey);
        smeLegNtcMapper.updateLegNtcSeq(tableNm);

        return smeLegNtcMapper.insertLegNtc(smeLegNtcDTO);
    }
    /**
     * 게시물을 수정한다.
     */
    public int updateLegNtc(SMELegNtcDTO smeLegNtcDTO) throws Exception {
        return smeLegNtcMapper.updateLegNtc(smeLegNtcDTO);
    }
    /**
     * 게시물을 삭제한다.
     */
    @Transactional
    public int deleteLegNtc(SMELegNtcDTO smeLegNtcDTO) throws Exception {
        return smeLegNtcMapper.deleteLegNtc(smeLegNtcDTO);
    }
}
