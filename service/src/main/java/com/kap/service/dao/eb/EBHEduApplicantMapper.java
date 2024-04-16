package com.kap.service.dao.eb;

import com.kap.core.dto.eb.ebh.EBHEduApplicantMstDTO;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 교육 신청자 관리 Mapper
 * </pre>
 *
 * @ClassName		: EBHEduApplicantMapper.java
 * @Description		: 교육 신청자 관리 Mapper
 * @author 장두석
 * @since 2023.12.01
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2023.12.01	  장두석	             최초 생성
 * </pre>
 */
@Mapper
public interface EBHEduApplicantMapper {
    /**
     * 리스트 조회
   */
    public List<EBHEduApplicantMstDTO> selectList(EBHEduApplicantMstDTO pEBHEduApplicantMstDTO);

    /**
     * 리스트 개수 조회
     */
    public int getListCnt(EBHEduApplicantMstDTO pEBHEduApplicantMstDTO);

    /**
     * 상세 조회
     */
    public EBHEduApplicantMstDTO selectView(EBHEduApplicantMstDTO pEBHEduApplicantMstDTO);

    /**
     * 상세의 강사 목록 조회
     */
    public List<String> selectViewIns(EBHEduApplicantMstDTO pEBHEduApplicantMstDTO);

    /**
     * 상세의 SQ 정보 목록 조회
     */
    public List<MPEPartsCompanyDTO> selectViewSq(EBHEduApplicantMstDTO pEBHEduApplicantMstDTO);

    /**
     * 선발 상태 변경
     */
    public int updateStts(EBHEduApplicantMstDTO pEBHEduApplicantMstDTO);

    /**
     * 신청자 정보 변경
     */
    public int updateUser(MPAUserDto pMPAUserDto);

    /**
     * 부품사 정보 변경
     */
    public int updateCmpn(MPEPartsCompanyDTO pMPEPartsCompanyDTO);

    /**
     * 참여 정보 변경
     */
    public int updateApplicant(EBHEduApplicantMstDTO pEBHEduApplicantMstDTO);

    /**
     * 신청자 정원 체크
     */
    public EBHEduApplicantMstDTO selectFxnumChk(EBHEduApplicantMstDTO pEBHEduApplicantMstDTO) throws Exception;

}
