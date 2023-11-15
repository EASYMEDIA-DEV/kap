package com.kap.service.impl.sm;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.SMDAPsnIfDTO;
import com.kap.service.SMDAPsnIfService;
import com.kap.service.dao.sm.SMDAPsnIfMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 * 개인정보처리방침 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: SMDAPsnIfServiceImpl.java
 * @Description		: 개인정보처리방침 관리를 위한 ServiceImpl
 * @author 구은희
 * @since 2023.09.26
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.09.26		구은희				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class SMDAPsnIfServiceImpl implements SMDAPsnIfService {

    // DAO
    private final SMDAPsnIfMapper smdPsnIfMapper;
    /* 시퀀스 */
    private final EgovIdGnrService prsnDtlIdgen;

    /**
     * 목록을 조회한다.
     */
    public SMDAPsnIfDTO selectPsnIfList(SMDAPsnIfDTO smdPsnIfDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(smdPsnIfDTO.getPageIndex());
        page.setRecordCountPerPage(smdPsnIfDTO.getListRowSize());

        page.setPageSize(smdPsnIfDTO.getPageRowSize());

        smdPsnIfDTO.setFirstIndex( page.getFirstRecordIndex() );
        smdPsnIfDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

        smdPsnIfDTO.setList(smdPsnIfMapper.selectPsnIfList(smdPsnIfDTO));
        smdPsnIfDTO.setTotalCount(smdPsnIfMapper.selectPsnIfCnt(smdPsnIfDTO));
        smdPsnIfDTO.setList(smdPsnIfMapper.selectPsnIfList(smdPsnIfDTO));
        return smdPsnIfDTO;
    }
    /**
     * 목록의 개수를 조회한다.
     */
    public int selectPsnIfCnt(SMDAPsnIfDTO smdPsnIfDTO) throws Exception {
        return smdPsnIfMapper.selectPsnIfCnt(smdPsnIfDTO);
    }
    /**
     * 상세를 조회한다.
     */
    public SMDAPsnIfDTO selectPsnIfDtl(SMDAPsnIfDTO smdPsnIfDTO) throws Exception {
        return smdPsnIfMapper.selectPsnIfDtl(smdPsnIfDTO);
    }
    /**
     * 게시물을 등록한다.
     */
    public int insertPsnIf(SMDAPsnIfDTO smdPsnIfDTO) throws Exception {

        smdPsnIfDTO.setPsnifSeq(prsnDtlIdgen.getNextIntegerId());
        return smdPsnIfMapper.insertPsnIf(smdPsnIfDTO);
    }
    /**
     * 게시물을 수정한다.
     */
    public int updatePsnIf(SMDAPsnIfDTO smdPsnIfDTO) throws Exception {
        return smdPsnIfMapper.updatePsnIf(smdPsnIfDTO);
    }
    /**
     * 게시물을 삭제한다.
     */
    @Transactional
    public int deletePsnIf(SMDAPsnIfDTO smdPsnIfDTO) throws Exception {
        return smdPsnIfMapper.deletePsnIf(smdPsnIfDTO);
    }
}
