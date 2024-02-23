package com.kap.service.impl.mp;

import com.kap.core.dto.cb.cba.CBATechGuidanceInsertDTO;
import com.kap.service.MPConsultingService;
import com.kap.service.dao.cb.cba.CBATechGuidanceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * 마이페이지 > 컨설팅신청내역을 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: MPEPartsCompanyServiceImpl.java
 * @Description		: 마이페이지 > 컨설팅신청내역을 위한 ServiceImpl
 * @author 김학규
 * @since  2024.02.08
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2024.02.08		김학규				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class MPConsultingServiceImpl implements MPConsultingService {

    // DAO
    private final CBATechGuidanceMapper cBATechGuidanceMapper;
    /* 시퀀스 */


    /**
     * 컨설팅 리스트를 조회한다.
     */
    public CBATechGuidanceInsertDTO selectConsultingList(CBATechGuidanceInsertDTO cBATechGuidanceInsertDTO) throws Exception {

        cBATechGuidanceInsertDTO.setPageRowSize(10);
        cBATechGuidanceInsertDTO.setTotalCount(cBATechGuidanceMapper.selectMemSeqAppctnMstCnt(cBATechGuidanceInsertDTO));
        int recordCountPerPage = (cBATechGuidanceInsertDTO.getPageIndex() * cBATechGuidanceInsertDTO.getPageRowSize() >= cBATechGuidanceInsertDTO.getTotalCount()) ? cBATechGuidanceInsertDTO.getTotalCount() : cBATechGuidanceInsertDTO.getPageIndex() * cBATechGuidanceInsertDTO.getPageRowSize();

        cBATechGuidanceInsertDTO.setFirstIndex(0);
        cBATechGuidanceInsertDTO.setRecordCountPerPage(recordCountPerPage);

        cBATechGuidanceInsertDTO.setList(cBATechGuidanceMapper.selectMemSeqAppctnMst(cBATechGuidanceInsertDTO));

        return cBATechGuidanceInsertDTO;
    }
}
