package com.kap.service.impl.sm;

import com.kap.core.dto.COFileDTO;
import com.kap.core.dto.sm.smj.SMJFormDTO;
import com.kap.service.COFileService;
import com.kap.service.SMJFormService;
import com.kap.service.dao.sm.SMJFormMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

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

        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(smjFormDTO.getFileList());

        if(smjFormDTO.getTypeCd().equals("BUSINESS01")) {
            for(COFileDTO fileDTO : smjFormDTO.getFileList()) {
                // dropzone에서 업로드한 파일 삭제 시 null 처리
                if(fileDTO.getFieldNm().equals("tchgdFileSeq")) {
                    if (fileDTO.getStatus().equals("delfile")) {
                        smjFormDTO.setTchgdFileSeq(null);
                    } else {
                        smjFormDTO.setTchgdFileSeq(fileSeqMap.get("tchgdFileSeq"));
                    }
                }

                if(fileDTO.getFieldNm().equals("mngmntFileSeq")) {
                    if (fileDTO.getStatus().equals("delfile")) {
                        smjFormDTO.setMngmntFileSeq(null);
                    } else {
                        smjFormDTO.setMngmntFileSeq(fileSeqMap.get("mngmntFileSeq"));
                    }
                }
            }

        } else if(smjFormDTO.getTypeCd().equals("BUSINESS02")) {

            for(COFileDTO fileDTO : smjFormDTO.getFileList()) {
                // dropzone에서 업로드한 파일 삭제 시 null 처리
                if(fileDTO.getFieldNm().equals("scrtyEnvrnmtFileSeq")) {
                    if (fileDTO.getStatus().equals("delfile")) {
                        smjFormDTO.setScrtyEnvrnmtFileSeq(null);
                    } else {
                        smjFormDTO.setScrtyEnvrnmtFileSeq(fileSeqMap.get("scrtyEnvrnmtFileSeq"));
                    }
                }

                if(fileDTO.getFieldNm().equals("sftyFcltyFileSeq")) {
                    if (fileDTO.getStatus().equals("delfile")) {
                        smjFormDTO.setSftyFcltyFileSeq(null);
                    } else {
                        smjFormDTO.setSftyFcltyFileSeq(fileSeqMap.get("sftyFcltyFileSeq"));
                    }
                }

                if(fileDTO.getFieldNm().equals("crbnEmsnsFileSeq")) {
                    if (fileDTO.getStatus().equals("delfile")) {
                        smjFormDTO.setCrbnEmsnsFileSeq(null);
                    } else {
                        smjFormDTO.setCrbnEmsnsFileSeq(fileSeqMap.get("crbnEmsnsFileSeq"));
                    }
                }

                if(fileDTO.getFieldNm().equals("smrtFctryAppctnFileSeq")) {
                    if (fileDTO.getStatus().equals("delfile")) {
                        smjFormDTO.setSmrtFctryAppctnFileSeq(null);
                    } else {
                        smjFormDTO.setSmrtFctryAppctnFileSeq(fileSeqMap.get("smrtFctryAppctnFileSeq"));
                    }
                }

                if(fileDTO.getFieldNm().equals("smrtFctryScrtyFileSeq")) {
                    if (fileDTO.getStatus().equals("delfile")) {
                        smjFormDTO.setSmrtFctryScrtyFileSeq(null);
                    } else {
                        smjFormDTO.setSmrtFctryScrtyFileSeq(fileSeqMap.get("smrtFctryScrtyFileSeq"));
                    }
                }


                if(fileDTO.getFieldNm().equals("examMsremntFileSeq")) {
                    if (fileDTO.getStatus().equals("delfile")) {
                        smjFormDTO.setExamMsremntFileSeq(null);
                    } else {
                        smjFormDTO.setExamMsremntFileSeq(fileSeqMap.get("examMsremntFileSeq"));
                    }
                }

                if(fileDTO.getFieldNm().equals("clbtnFileSeq")) {
                    if (fileDTO.getStatus().equals("delfile")) {
                        smjFormDTO.setClbtnFileSeq(null);
                    } else {
                        smjFormDTO.setClbtnFileSeq(fileSeqMap.get("clbtnFileSeq"));
                    }
                }

                if(fileDTO.getFieldNm().equals("splychnStblztnFileSeq")) {
                    if (fileDTO.getStatus().equals("delfile")) {
                        smjFormDTO.setSplychnStblztnFileSeq(null);
                    } else {
                        smjFormDTO.setSplychnStblztnFileSeq(fileSeqMap.get("splychnStblztnFileSeq"));
                    }
                }

                if(fileDTO.getFieldNm().equals("carPartAppctnFileSeq")) {
                    if (fileDTO.getStatus().equals("delfile")) {
                        smjFormDTO.setCarPartAppctnFileSeq(null);
                    } else {
                        smjFormDTO.setCarPartAppctnFileSeq(fileSeqMap.get("carPartAppctnFileSeq"));
                    }
                }

                if(fileDTO.getFieldNm().equals("ftreCarAppctnFileSeq")) {
                    if (fileDTO.getStatus().equals("delfile")) {
                        smjFormDTO.setFtreCarAppctnFileSeq(null);
                    } else {
                        smjFormDTO.setFtreCarAppctnFileSeq(fileSeqMap.get("ftreCarAppctnFileSeq"));
                    }
                }
            }

        } else if(smjFormDTO.getTypeCd().equals("BUSINESS03")) {
            for(COFileDTO fileDTO : smjFormDTO.getFileList()) {
                // dropzone에서 업로드한 파일 삭제 시 null 처리
                if(fileDTO.getFieldNm().equals("ttlEdctnFileSeq")) {
                    if (fileDTO.getStatus().equals("delfile")) {
                        smjFormDTO.setTtlEdctnFileSeq(null);
                    } else {
                        smjFormDTO.setTtlEdctnFileSeq(fileSeqMap.get("ttlEdctnFileSeq"));
                    }
                }
            }
        }
        return smjFormMapper.updateFormDtl(smjFormDTO);
    }
}
