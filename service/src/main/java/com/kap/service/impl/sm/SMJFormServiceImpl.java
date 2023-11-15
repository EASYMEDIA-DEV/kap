package com.kap.service.impl.sm;

import com.kap.core.dto.SMJFormDTO;
import com.kap.service.COFileService;
import com.kap.service.SMJFormService;
import com.kap.service.dao.sm.SMJFormMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * 양식 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: SMJFormServiceImpl.java
 * @Description		: 양식 관리를 위한 ServiceImpl
 * @author 구은희
 * @since 2023.11.15
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.15		구은희				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class SMJFormServiceImpl implements SMJFormService {

    // DAO
    private final SMJFormMapper smjFormMapper;

    //파일 서비스
    private final COFileService cOFileService;

    /**
     * 상세를 조회한다.
     */
    public SMJFormDTO selectFormDtl(SMJFormDTO smjFormDTO) throws Exception {
        return smjFormMapper.selectFormDtl(smjFormDTO);
    }

    /**
     * 양식 관리를 수정한다.
     */
    public int updateFormDtl(SMJFormDTO smjFormDTO) throws Exception {

        if(smjFormDTO.getTypeCd().equals("BUSINESS01")) {
            smjFormDTO.setTchgdFileSeq(smjFormDTO.getTchgdFileSeq());
            smjFormDTO.setMngmntFileSeq(smjFormDTO.getMngmntFileSeq());

        } else if(smjFormDTO.getTypeCd().equals("BUSINESS02")) {
            smjFormDTO.setScrtyEnvrnmtFileSeq(smjFormDTO.getScrtyEnvrnmtFileSeq());
            smjFormDTO.setSftyFcltyFileSeq(smjFormDTO.getSftyFcltyFileSeq());
            smjFormDTO.setCrbnEmsnsFileSeq(smjFormDTO.getCrbnEmsnsFileSeq());
            smjFormDTO.setSmrtFctryAppctnFileSeq(smjFormDTO.getSmrtFctryAppctnFileSeq());
            smjFormDTO.setSmrtFctryScrtyFileSeq(smjFormDTO.getSmrtFctryScrtyFileSeq());
            smjFormDTO.setExamMsremntFileSeq(smjFormDTO.getExamMsremntFileSeq());
            smjFormDTO.setClbtnFileSeq(smjFormDTO.getClbtnFileSeq());
            smjFormDTO.setSplychnStblztnFileSeq(smjFormDTO.getSplychnStblztnFileSeq());
        }
        return smjFormMapper.updateFormDtl(smjFormDTO);
    }
}
