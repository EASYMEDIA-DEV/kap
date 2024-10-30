package com.kap.service;

import com.kap.core.dto.bd.bda.BDANoticeDTO;
import com.kap.core.dto.bd.bdb.BDBCompanyNewsDTO;
import com.kap.core.dto.bd.bdc.BDCFaqDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceInsertDTO;
import com.kap.core.dto.eb.ebb.EBBEpisdDTO;
import com.kap.core.dto.mp.mpb.MPBBsnSearchDTO;
import com.kap.core.dto.sm.smb.SMBMainVslDTO;
import com.kap.core.dto.sm.smc.SMCMnPopDTO;
import com.kap.core.dto.sm.smg.SMGWinBusinessDTO;
import com.kap.core.dto.sm.smj.SMJFormDTO;
import com.kap.core.dto.sm.smk.SMKTrendDTO;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * COSampleService 서비스
 *
 * @author 박주석
 * @since 2022.01.10
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2022.01.10  박주석         최초 생성
 * </pre>
 */
public interface COMainService {

    /**
     * 메인에서 사용하는 쿼리 단체조회
    */
    public HashMap<String, Object> selectMainGroup(boolean isNomal
                                                                                , SMBMainVslDTO sMBMainVslDTO
                                                                                , SMCMnPopDTO sMCMnPopDTO
                                                                                , SMGWinBusinessDTO sMGWinBusinessDTO
                                                                                , BDANoticeDTO bDANoticeDTO
                                                                                , BDCFaqDTO bDCFaqDTO
                                                                                , BDBCompanyNewsDTO bDBCompanyNewsDTO
    ) throws Exception;

    /**
     * 인터셉터에서 호출
     */
    public HashMap<String, Object> selectMainGroup2(boolean selectYn, SMJFormDTO smjFormDTO, SMKTrendDTO sMKTrendDTO, EBBEpisdDTO ebbEpisdDTO, CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO, MPBBsnSearchDTO mpbBnsSearchDTO) throws Exception;;
}
