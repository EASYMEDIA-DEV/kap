package com.kap.service.dao.wb.wbb;

import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wba.WBAManagementOptnDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 상생사업 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: WBBARoundMapper.java
 * @Description		: 공통 회차관리 관리를 위한 DAO
 * @author 김태훈
 * @since 2023.11.24
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.24		김태훈				   최초 생성
 * </pre>
 */

@Mapper
public interface WBBARoundMapper {

    /**
     * 회차 조회
     */
    public List<WBRoundMstSearchDTO> selectRoundList(WBRoundMstSearchDTO wBRoundMstSearchDTO);

    /**
     * 회차 전체 갯수
     */
    public int getRoundListTotCnt(WBRoundMstSearchDTO wBRoundMstSearchDTO);

    /**
     * 회차 상세 조회
     */
    public WBRoundMstDTO selectRoundDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

    /**
     * 연도 상세 조회
     */
    public WBRoundMstDTO selectYearDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

    /**
     * 회차 등록
     */
    public int insertRound(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 회차 수정
     */
    public int updateRound(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 회차 삭제
     */
    public int deleteRound(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 최신 회차 상세 조회
     */
    public WBRoundMstSearchDTO getRoundDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

    /**
     * 회차 옵션파일 조회
     */
    public List<WBAManagementOptnDTO> selectOPtnList(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

    /**
     * 회차 신청여부 조회
     */
    public int getApplyCount(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

    /**
     * 동일 부품사 회차사업 신청여부 조회
     */
    public int getApplyPartsCount(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

    /**
     * 회차 삭제
     */
    public int getAppctnCnt(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 회차 리스트 삭제
     */
    public int deleteList(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 회차 중복 체크
     */
    public int episdChk(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 회차 신청 갯수
     */
    public int episdCnt(WBRoundMstDTO wBRoundMstDTO) throws Exception;
}
