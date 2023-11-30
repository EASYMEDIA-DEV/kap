package com.kap.service.impl.mp;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.mp.mpc.MPCLecturerDTO;
import com.kap.service.MPCLecturerService;
import com.kap.service.dao.mp.MPCLecturerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * 강사 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: MPEPartsCompanyServiceImpl.java
 * @Description		: 강사 관리를 위한 ServiceImpl
 * @author 구은희
 * @since 2023.11.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.20		구은희				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class MPCLecturerServiceImpl implements MPCLecturerService {

    // DAO
    private final MPCLecturerMapper mpcLecturerMapper;
    
    /* 시퀀스 */
    private final EgovIdGnrService mpcLecturerDtlIdgen;

    /**
     * 강사 목록을 조회한다.
     */
    public MPCLecturerDTO selectLecturerList(MPCLecturerDTO mpcLecturerDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(mpcLecturerDTO.getPageIndex());
        page.setRecordCountPerPage(mpcLecturerDTO.getListRowSize());

        page.setPageSize(mpcLecturerDTO.getPageRowSize());

        mpcLecturerDTO.setFirstIndex(page.getFirstRecordIndex());
        mpcLecturerDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        mpcLecturerDTO.setList(mpcLecturerMapper.selectLecturerList(mpcLecturerDTO));
        mpcLecturerDTO.setTotalCount(mpcLecturerMapper.selectLecturerCnt(mpcLecturerDTO));

        return mpcLecturerDTO;
    }

    /**
     * 강사 개수를 조회한다.
     */
    public int selectLecturerCnt(MPCLecturerDTO mpcLecturerDTO) throws Exception {
        return mpcLecturerMapper.selectLecturerCnt(mpcLecturerDTO);
    }

    /**
     * 상세를 조회한다.
     */
    public MPCLecturerDTO selectLecturerDtl(MPCLecturerDTO mpcLecturerDTO) throws Exception {
        return mpcLecturerMapper.selectLecturerDtl(mpcLecturerDTO);
    }

    /**
     * 이메일 중복 체크를 한다.
     */
    public int selectDupEmail(MPCLecturerDTO mpcLecturerDTO) throws Exception {
        return mpcLecturerMapper.selectDupEmail(mpcLecturerDTO);
    }

    /**
     * 강사를 등록한다.
     */
    public int insertLecturer(MPCLecturerDTO mpcLecturerDTO) throws Exception {
        mpcLecturerDTO.setIsttrSeq(mpcLecturerDtlIdgen.getNextIntegerId());
       return mpcLecturerMapper.insertLecturer(mpcLecturerDTO);
    }

    /**
     * 강사를 수정한다.
     */
    public int updateLecturer(MPCLecturerDTO mpcLecturerDTO) throws Exception {
        return mpcLecturerMapper.updateLecturer(mpcLecturerDTO);
    }

    /**
     * 강사를 삭제한다.
     */
    public int deleteLecturer(MPCLecturerDTO mpcLecturerDTO) throws Exception {
        return mpcLecturerMapper.deleteLecturer(mpcLecturerDTO);
    }
}
