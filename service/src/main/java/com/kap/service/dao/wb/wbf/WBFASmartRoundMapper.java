package com.kap.service.dao.wb.wbf;

import com.kap.core.dto.wb.WBOrderMstDto;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.WBRoundOptnMstDTO;

import java.util.List;

/**
 * <pre>
 * 스마트공장 > 회차 관리 DAO
 * </pre>
 *
 * @ClassName		: WBFASmartRoundMapper.java
 * @Description		: 스마트공장 > 회차 관리 DAO
 * @author 김동현
 * @since 2023.11.17
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *	     since		  author	            description
 *    ==========    ==========    ==============================
 * 	  2023.11.17	  김동현	             최초 생성
 * </pre>
 */
public interface WBFASmartRoundMapper {

    /**
     * 회차 관리 Select List
     * */
    public List<WBRoundMstSearchDTO> selectSmartRoundList(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

    /**
     * 회차 관리 Select List Total Count
     * */
    public int selectSmartRoundListTotCnt(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

    /**
     * 회차 관리 글 상세 Sel
     * */
    public WBRoundMstDTO selRoundDetail(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

    /**
     * 지급차수 조회
     */
    public List<WBOrderMstDto> selectGiveList(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 사업유형 조회
     */
    public List<WBRoundOptnMstDTO> selectAssignmentList(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 과제명 조회
     */
    public List<WBRoundOptnMstDTO> selectBusinessList(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 등록된 회차 확인
     */
    public int selectRoundCheck(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 회차 등록 insert
     */
    public int insertRound(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 회차 별 지급 차수 insert
     */
    public int insertGiveList(WBOrderMstDto wBOrderMstDto) throws Exception;

    /**
     * 회차 별 사업 유형 / 과제명 insert
     */
    public int insertBsnOptnList(WBRoundOptnMstDTO wBRoundOptnMstDTO) throws Exception;

    /**
     * 회사 수정 전 확인
     */
    public int updateRoundChk(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 회사 수정
     */
    public int updateRound(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 회사 수정
     */
    public int updateGiveList(WBOrderMstDto wBOrderMstDto) throws Exception;

    /**
     * 회차 삭제
     */
    public int deleteRound(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 회차 별 지급차수 삭제
     */
    public int deleteGiveList(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 회차 별 사업유형 삭제
     */
    public int deleteBsinList(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     *  회차 별 과제명 삭제
     */
    public int deleteAsigtList(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 회차 등록 페이지 Year 옵션
     * */
    public WBRoundMstDTO selectYearDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;
}
