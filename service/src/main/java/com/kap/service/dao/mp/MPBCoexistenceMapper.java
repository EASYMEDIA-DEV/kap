package com.kap.service.dao.mp;

import com.kap.core.dto.mp.mpb.MPBBnsSearchDTO;
import com.kap.core.dto.mp.mpb.MPBCompanyDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 마이페이지 상생 사업 신청내역 DAO
 * </pre>
 *
 * @ClassName		: MPBCoexistenceMapper.java
 * @Description		: 마이페이지 상생 사업 신청내역 DAO
 * @author 김태훈
 * @since 2024.01.08
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2024.01.08		김태훈				   최초 생성
 * </pre>
 */

@Mapper
public interface MPBCoexistenceMapper {

    /**
     *  List Page
     *  마이페이지 상생 사업 신청내역 List
     */
    public List<MPBBnsSearchDTO> selectApplyList(MPBBnsSearchDTO mpbBnsSearchDTO);

    /**
     *  List Page
     *  마이페이지 상생 사업 신청내역 Count
     */
    public int selectApplyCount(MPBBnsSearchDTO mpbBnsSearchDTO);

    /**
     *  마이페이지 공통 상생 사업여부
     */
    public String getBusinessYn(MPBBnsSearchDTO mpbBnsSearchDTO);

    /**
     *  마이페이지 상생 사업 상세
     */
    public MPBBnsSearchDTO getBsnDetail(MPBBnsSearchDTO mpbBnsSearchDTO);

    /**
     * 부품사 정보를 조회한다.
     */
    public MPBCompanyDTO getCompanyInfo(MPBBnsSearchDTO mpbBnsSearchDTO);

    /**
     * 부품사 SQ 정보를 조회한다.
     */
    public List<MPBCompanyDTO> selectSqList(MPBBnsSearchDTO mpbBnsSearchDTO);
}
