package com.kap.service.dao.mp;

import com.kap.core.dto.mp.mpb.MPBBsnSearchDTO;
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
    public List<MPBBsnSearchDTO> selectApplyList(MPBBsnSearchDTO mpbBnsSearchDTO);

    /**
     *  List Page
     *  마이페이지 상생 사업 신청내역 Count
     */
    public int selectApplyCount(MPBBsnSearchDTO mpbBnsSearchDTO);

    /**
     *  마이페이지 공통 상생 사업여부
     */
    public String getBusinessYn(MPBBsnSearchDTO mpbBnsSearchDTO);

    /**
     *  마이페이지 상생 사업 상세
     */
    public MPBBsnSearchDTO getBsnDetail(MPBBsnSearchDTO mpbBnsSearchDTO);

    /**
     * 부품사 정보를 조회한다.
     */
    public MPBCompanyDTO getCompanyInfo(MPBBsnSearchDTO mpbBnsSearchDTO);

    /**
     * 부품사 SQ 정보를 조회한다.
     */
    public List<MPBCompanyDTO> selectSqList(MPBBsnSearchDTO mpbBnsSearchDTO);

    /**
     *  마이페이지 상생 사업 사용자 취소
     */
    public int updateUserCancel(MPBBsnSearchDTO mpbBnsSearchDTO);

    /**
     *  상생 사업 상세조회
     */
    public MPBBsnSearchDTO getBusinessInfo(MPBBsnSearchDTO mpbBnsSearchDTO);
}
