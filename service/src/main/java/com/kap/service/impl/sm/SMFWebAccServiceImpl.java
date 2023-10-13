package com.kap.service.impl.sm;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.SMFWebAccDTO;
import com.kap.service.SMFWebAccService;
import com.kap.service.dao.sm.SMFWebAccMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 * 웹접근성 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: SMFWebAccServiceImpl.java
 * @Description		: 웹접근성 관리를 위한 ServiceImpl
 * @author 구은희
 * @since 2023.10.05
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.10.05		구은희				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class SMFWebAccServiceImpl implements SMFWebAccService {

    // DAO
    private final SMFWebAccMapper smfWebAccMapper;
    String tableNm = "WEB_ACC_SEQ";
    /**
     * 목록을 조회한다.
     */
    public SMFWebAccDTO selectWebAccList(SMFWebAccDTO smfWebAccDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(smfWebAccDTO.getPageIndex());
        page.setRecordCountPerPage(smfWebAccDTO.getListRowSize());

        page.setPageSize(smfWebAccDTO.getPageRowSize());

        smfWebAccDTO.setFirstIndex( page.getFirstRecordIndex() );
        smfWebAccDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

        smfWebAccDTO.setList(smfWebAccMapper.selectWebAccList(smfWebAccDTO));
        smfWebAccDTO.setTotalCount(smfWebAccMapper.selectWebAccCnt(smfWebAccDTO));
        smfWebAccDTO.setList(smfWebAccMapper.selectWebAccList(smfWebAccDTO));
        return smfWebAccDTO;
    }
    /**
     * 목록의 개수를 조회한다.
     */
    public int selectWebAccCnt(SMFWebAccDTO smfWebAccDTO) throws Exception {
        return smfWebAccMapper.selectWebAccCnt(smfWebAccDTO);
    }
    /**
     * 상세를 조회한다.
     */
    public SMFWebAccDTO selectWebAccDtl(SMFWebAccDTO smfWebAccDTO) throws Exception {
        return smfWebAccMapper.selectWebAccDtl(smfWebAccDTO);
    }
    /**
     * 게시물을 등록한다.
     */
    public int insertWebAcc(SMFWebAccDTO smfWebAccDTO) throws Exception {

        smfWebAccDTO.setTableNm(tableNm);
        String detailsKey = smfWebAccMapper.selectSeqNum(smfWebAccDTO.getTableNm());
        smfWebAccDTO.setDetailsKey(detailsKey);
        smfWebAccMapper.updateWebAccSeq(tableNm);

        return smfWebAccMapper.insertWebAcc(smfWebAccDTO);
    }
    /**
     * 게시물을 수정한다.
     */
    public int updateWebAcc(SMFWebAccDTO smfWebAccDTO) throws Exception {
        return smfWebAccMapper.updateWebAcc(smfWebAccDTO);
    }
    /**
     * 게시물을 삭제한다.
     */
    @Transactional
    public int deleteWebAcc(SMFWebAccDTO smfWebAccDTO) throws Exception {
        return smfWebAccMapper.deleteWebAcc(smfWebAccDTO);
    }
}
