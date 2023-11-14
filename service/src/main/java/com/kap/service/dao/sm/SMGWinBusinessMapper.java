package com.kap.service.dao.sm;

import com.kap.core.dto.SMGWinBusinessDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 상생 사업 관리 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: SMGWinBusinessMapper.java
 * @Description		: 상생 사업 관리 관리를 위한 DAO
 * @author 임서화
 * @since 2023.11.14
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.14		임서화				   최초 생성
 * </pre>
 */
@Mapper
public interface SMGWinBusinessMapper {

    /**
     * 상생 사업 관리 목록 조회
     */
    public List<SMGWinBusinessDTO> selectWinBusinessList(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception;

    /**
     * 상생 사업 관리 상세 조회
     */
    public SMGWinBusinessDTO selectWinBusinessDtl(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception;

    /**
     * 상생 사업 관리 등록
     */
    public int insertWinBusiness(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception;

    /**
     * 상생 사업 관리 전체 카운트
     */
    public int selectWinBusinessTotCnt(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception;

    /**
     * 상생 사업 관리 수정
     */
    public int updateWinBusiness(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception;

    /**
     * 상생 사업 관리 삭제
     */
    public int deleteWinBusiness(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception;

    /**
     * 상생 사업 관리 정렬 수정
     */
    public void updateOrder(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception;

    /**
     * 정렬할 상생 사업 관리를 조회
     */
    public SMGWinBusinessDTO selectWinBusinessNewRow(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception;
}
