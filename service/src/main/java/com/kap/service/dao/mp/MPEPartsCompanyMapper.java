package com.kap.service.dao.mp;

import com.kap.core.dto.MPEPartsCompanyDTO;
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
    public MPEPartsCompanyDTO selectPartsCompanyDtl(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

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
     * 현재 카테고리의 CMS 시퀀스 값을 가져온다.
     */
    public String selectSeqNum(String tableNm) throws Exception;

    /**
     * 시퀀스 값을 상승시킨다.
     */
    public int updatePartsCompanySeq(String tableNm) throws Exception;

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
}
