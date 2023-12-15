package com.kap.service.dao.wb.wbb;

import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
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
}
