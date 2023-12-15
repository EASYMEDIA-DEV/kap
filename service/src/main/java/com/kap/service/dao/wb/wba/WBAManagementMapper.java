package com.kap.service.dao.wb.wba;

import com.kap.core.dto.COEgovSeqDTO;
import com.kap.core.dto.wb.wba.WBAManageInsertDTO;
import com.kap.core.dto.wb.wba.WBAManageSearchDTO;
import com.kap.core.dto.wb.wba.WBAManagementDtlDTO;
import com.kap.core.dto.wb.wba.WBAManagementOptnDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 상생사업 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: WBAManagementMapper.java
 * @Description		: 상생사업 관리를 위한 DAO
 * @author 김태훈
 * @since 2023.11.10
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.10		김태훈				   최초 생성
 * </pre>
 */

@Mapper
public interface WBAManagementMapper {
    /**
     * 상생사업 목록을 조회
     */
    public List<WBAManageSearchDTO> selectManagementList(WBAManageSearchDTO wbaManageSearchDTO) throws Exception;

    /**
     * 상생사업 개수를 조회
     */
    public int selectManagementCnt(WBAManageSearchDTO wbaManageSearchDTO) throws Exception;

    /**
     * 상생사업 마스터 상세를 조회
     */
    public WBAManageInsertDTO selectManagementMst(WBAManageSearchDTO wbaManageSearchDTO) throws Exception;

    /**
     * 상생사업 단계 조회
     */
    public List<WBAManagementDtlDTO> selectStepDtlList(WBAManageSearchDTO wbaManageSearchDTO) throws Exception;

    /**
     * 상생사업 옵션 조회
     */
    public List<WBAManagementOptnDTO> selectOptnDtlList(WBAManagementDtlDTO wbaManagementDtlDTO) throws Exception;

    /**
     * 상생사업 마스터 등록
     */
    public int insertManagementMst(WBAManageInsertDTO wbaManageInsertDTO);

    /**
     * 상생사업 단계 등록
     */
    public int insertManagementDtl(WBAManagementDtlDTO wbaManagementDtlDTO);

    /**
     * 상생사업 옵션 등록
     */
    public int insertManagementOptn(WBAManagementOptnDTO wbaManagementOptnDTO);

    /**
     * 상생사업 마스터 수정
     */
    public int updateManagementMst(WBAManageInsertDTO wbaManageInsertDTO);

    /**
     * 상생사업 단계 수정
     */
    public int updateManagementDtl(WBAManagementDtlDTO wbaManagementDtlDTO);

    /**
     * 상생사업 옵션 수정
     */
    public int updateManagementOptn(WBAManagementOptnDTO wbaManagementOptnDTO);

    /**
     * 상생사업 마스터 삭제
     */
    public int deleteManagementMst(WBAManageInsertDTO wbaManageInsertDTO);

    /**
     * 상생사업 단계 삭제
     */
    public int deleteManagementDtl(WBAManageInsertDTO wbaManageInsertDTO);

    /**
     * 상생사업 옵션 삭제
     */
    public int deleteManagementOptn(WBAManageInsertDTO wbaManageInsertDTO);

    /**
     * 상생사업 회차 삭제
     */
    public int deleteRound(WBAManageInsertDTO wbaManageInsertDTO);

    /**
     * 첨부파일 업데이트
     */
    public int updateFileInfo(WBAManageInsertDTO wbaManageInsertDTO);

    /**
     * 상생사업 단계 조회
     */
    public WBAManagementDtlDTO getStepDtl(WBAManageSearchDTO wbaManageSearchDTO) throws Exception;

    /**
     * 사업 참여자 카운트
     */
    public int getApplyUserCount(WBAManageInsertDTO WBAManageInsertDTO) throws Exception;
}
