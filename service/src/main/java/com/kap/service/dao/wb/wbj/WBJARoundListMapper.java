package com.kap.service.dao.wb.wbj;

import com.kap.core.dto.ex.exg.EXGExamMstSearchDTO;
import com.kap.core.dto.wb.WBOrderMstDto;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wba.WBAManagementOptnDTO;
import com.kap.core.dto.wb.wbj.WBJAcomSearchDTO;

import java.util.List;

public interface WBJARoundListMapper {
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
     * 연도 상세 조회
     */
    public WBRoundMstDTO selectYearDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

    /**
     * 포상상세 등록
     */
    public int insertPrizeList(WBOrderMstDto wBOrderMstDto) throws Exception;

    /**
     * 포상상세 조회
     */
    public List<WBOrderMstDto> selectPrizeList(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 장소상세 조회
     */
    public List<WBOrderMstDto> selectRoomList(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 포상 삭제
     */
    public int deletePrizeList(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 포상 수정
     */
    public int updatePrizeList(WBOrderMstDto wBOrderMstDto) throws Exception;

    /**
     *  선택 연도 값에 따른 episdCnt 값 가져오기
     *
     */
    public List<String> roundCnt(WBRoundMstDTO wBRoundMstDTO);
    
    /**
     *  해당 년도 회차 신청 갯수
     *
     */
    public List<String> episdCnt(WBRoundMstDTO wBRoundMstDTO);

    /**
     * 연도 상세 조회
     */
    public WBRoundMstDTO selectEpisdDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

    /**
     * 회차 옵션파일 조회
     */
    public List<WBAManagementOptnDTO> selectOPtnList(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;
}
