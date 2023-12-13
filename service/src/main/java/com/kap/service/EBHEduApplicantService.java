package com.kap.service;

import com.kap.core.dto.eb.ebh.EBHEduApplicantMstDTO;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;

/**
 * 교육 신청자 관리 Service
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.12.01  장두석         최초 생성
 * </pre>
 */
public interface EBHEduApplicantService {
    /**
     * 리스트 조회
    */
    public EBHEduApplicantMstDTO selectList(EBHEduApplicantMstDTO pEBHEduApplicantMstDTO) throws Exception;

    /**
     * 상세 조회
     */
    public EBHEduApplicantMstDTO selectView(EBHEduApplicantMstDTO pEBHEduApplicantMstDTO) throws Exception;

    /**
     * 수정
     */
    public int update(EBHEduApplicantMstDTO pEBHEduApplicantMstDTO, MPEPartsCompanyDTO pMPEPartsCompanyDTO, MPAUserDto pMPAUserDto) throws Exception;

    /**
     * 선발 상태 수정
     */
    public int updateStts(EBHEduApplicantMstDTO pEBHEduApplicantMstDTO) throws Exception;
}
