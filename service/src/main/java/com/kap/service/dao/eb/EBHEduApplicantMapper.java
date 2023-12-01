package com.kap.service.dao.eb;

import com.kap.core.dto.eb.ebd.EBDEdctnEdisdDTO;
import com.kap.core.dto.eb.ebd.EBDPrePrcsDTO;
import com.kap.core.dto.eb.ebd.EBDSqCertiListDTO;
import com.kap.core.dto.eb.ebd.EBDSqCertiSearchDTO;
import com.kap.core.dto.eb.ebg.EBGExamAppctnMstDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 샘플 Mapper
 * </pre>
 *
 * @ClassName		: COSampleMapper.java
 * @Description		: 샘플 Mapper
 * @author 박주석
 * @since 2022.01.10
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2022.01.10	  박주석	             최초 생성
 * </pre>
 */
@Mapper
public interface EBHEduApplicantMapper {
    /**
     * 리스트 조회
   */
    public List<EBDSqCertiListDTO> selectList(EBDSqCertiSearchDTO eBDSqCertiSearchDTO);

    /**
     * 리스트 갯수 조회
     */
    public int getListCnt(EBDSqCertiSearchDTO eBDSqCertiSearchDTO);

    /**
     * 상세 조회
     */
    public EBDEdctnEdisdDTO selectView(EBDSqCertiSearchDTO eBDSqCertiSearchDTO);

    /**
     * 선수과목 수료 목록 조회
     */
    public List<EBDPrePrcsDTO> getPrePrcsList(EBDSqCertiSearchDTO eBDSqCertiSearchDTO);

    /**
     * 신청서 상태 변경
     */
    public int updateIssue(EBDEdctnEdisdDTO eBDEdctnEdisdDTO);

    /**
     * 자격증 상세
     */
    public EBGExamAppctnMstDTO selectExamAppctnMst(EBDSqCertiSearchDTO eBDEdctnEdisdDTO);

    /**
     * 자격증 정보 변경
     */
    public int updateConfirmInfo(EBGExamAppctnMstDTO eBGExamAppctnMstDTO);

    /**
     * 만료일시 기준 몇개월전 대상자 조회
     */
    public List<EBDSqCertiListDTO> getSqValidEndEmailList(int month);
}
