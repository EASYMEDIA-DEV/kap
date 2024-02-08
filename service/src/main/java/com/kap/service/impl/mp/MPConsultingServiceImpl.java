package com.kap.service.impl.mp;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COSystemLogDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceInsertDTO;
import com.kap.core.dto.eb.ebb.EBBEpisdDTO;
import com.kap.core.dto.mp.mpc.MPCLecturerDTO;
import com.kap.service.COSystemLogService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.MPCLecturerService;
import com.kap.service.MPConsultingService;
import com.kap.service.dao.cb.cba.CBATechGuidanceMapper;
import com.kap.service.dao.mp.MPCLecturerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

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
public class MPConsultingServiceImpl implements MPConsultingService {

    // DAO
    private final CBATechGuidanceMapper cBATechGuidanceMapper;
    /* 시퀀스 */

    //로그인 상태값 시스템 등록
    private final COSystemLogService cOSystemLogService;



    /**
     * 강사 목록을 조회한다.
     */
    public CBATechGuidanceInsertDTO selectConsultingList(CBATechGuidanceInsertDTO cBATechGuidanceInsertDTO) throws Exception {
        System.out.println("@@@ cBATechGuidanceInsertDTO = " +cBATechGuidanceInsertDTO.getPageIndex());


        cBATechGuidanceInsertDTO.setPageRowSize(10);
        cBATechGuidanceInsertDTO.setTotalCount(cBATechGuidanceMapper.selectMemSeqAppctnMstCnt(cBATechGuidanceInsertDTO));
        int recordCountPerPage = (cBATechGuidanceInsertDTO.getPageIndex() * cBATechGuidanceInsertDTO.getPageRowSize() >= cBATechGuidanceInsertDTO.getTotalCount()) ? cBATechGuidanceInsertDTO.getTotalCount() : cBATechGuidanceInsertDTO.getPageIndex() * cBATechGuidanceInsertDTO.getPageRowSize();

        cBATechGuidanceInsertDTO.setFirstIndex(0);
        cBATechGuidanceInsertDTO.setRecordCountPerPage(recordCountPerPage);

        cBATechGuidanceInsertDTO.setList(cBATechGuidanceMapper.selectMemSeqAppctnMst(cBATechGuidanceInsertDTO));


        return cBATechGuidanceInsertDTO;
    }



}
