package com.kap.service.dao.mp;

import com.kap.core.dto.cb.cba.CBATechGuidanceInsertDTO;
import com.kap.core.dto.cb.cbb.CBBManageConsultListDTO;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.core.dto.wb.wbg.WBGAExamSearchDTO;
import com.kap.core.dto.wb.wbj.WBJAcomSearchDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 부품사 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: MPEPartsCompanyMapper.java
 * @Description		: 부품사 관리를 위한 DAO
 * @author 구은희
 * @since 2023.11.09
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.09		구은희				   최초 생성
 * </pre>
 */
@Mapper
public interface MPEPartsCompanyMapper {

    /**
     * 부품사 목록을 조회
     */
    public List<MPEPartsCompanyDTO> selectPartsCompanyList(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 부품사 개수를 조회
     */
    public int selectPartsCompanyCnt(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 부품사 상세를 조회
     */
    public List<MPEPartsCompanyDTO> selectPartsCompanyDtl(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 부품사 SQ정보 목록을 조회
     */
    public List<MPEPartsCompanyDTO> selectPartsComSQInfo(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;


    /**
     * 부품사 사업자등록번호 등록여부를 확인
     */
    public MPEPartsCompanyDTO checkBsnmNo(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 부품사 등록
     */
    public int insertPartsCompany(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 부품사 SQ 정보 등록
     */
    public int insertPartsComSQInfo(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 부품사 수정
     */
    public int updatePartsCompany(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 부품사 SQ 정보를 수정
     */
    public int updatePartsComSQInfo(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 부품사 삭제
     */
    public int deletePartsCompany(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 교육사업 개수 조회
     */
    public int selectEduCnt(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 컨설팅사업 개수 조회
     */
    public int selectConsultingCnt(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 상생사업 개수 조회
     */
    public int selectWinBusinessCnt(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * SQ 정보 삭제
     */
    public int deletePartsComSQInfo(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 교육사업 연도별 통계 목록을 조회
     */
    public List<MPEPartsCompanyDTO> selectEduStatisticsCntList(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 컨설팅 목록(기술지도)을 조회
     */
    public List<CBATechGuidanceInsertDTO> selectTechGuidanceList(CBATechGuidanceInsertDTO cbaTechGuidanceInsertDTO) throws Exception;

    /**
     * 컨설팅 목록(경영컨설팅)을 조회
     */
    public List<CBBManageConsultListDTO> selectManageConsultList(CBBManageConsultListDTO cbbManageConsultListDTO) throws Exception;

    /**
     * 컨설팅 목록 개수 조회
     */
    public int selectConsultingListCnt(CBATechGuidanceInsertDTO cbaTechGuidanceInsertDTO) throws Exception;

    /**
     * 자금지원 목록을 조회
     */
    public List<WBGAExamSearchDTO> selectFundingList(WBGAExamSearchDTO wbgaExamSearchDTO) throws Exception;

    /**
     * 자금지원 목록 개수 조회
     */
    public int selectFundingCnt(WBGAExamSearchDTO wbgaExamSearchDTO) throws Exception;


    /**
     * 자동차부품산업대상 목록을 조회
     */
    public List<WBJAcomSearchDTO> selectCarTargetList(WBJAcomSearchDTO wbjAcomSearchDTO) throws Exception;

    /**
     * 등록된 부품사 회원이 있는지 체크
     */
    public int selectMemberPartsSocietyExistsCheck(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;
}
