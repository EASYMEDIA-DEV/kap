package com.kap.service.dao.wb.wbi;

import com.kap.core.dto.wb.WBOrderMstDto;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wba.WBAManagementOptnDTO;

import java.util.List;

public interface WBIASupplyListMapper {
    /**
     * 회차 조회
     */
    public List<WBRoundMstSearchDTO> selectSupplyList(WBRoundMstSearchDTO wBRoundMstSearchDTO);

    /**
     * 회차 전체 갯수
     */
    public int getSupplyListTotCnt(WBRoundMstSearchDTO wBRoundMstSearchDTO);

    /**
     * 회차 등록
     */
    public int insertSupply(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 회차 수정
     */
    public int updateSupply(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 회차 삭제
     */
    public int deleteSupply(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 회차 상세 조회
     */
    public WBRoundMstDTO selectSupplyDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

    /**
     * 연도 상세 조회
     */
    public WBRoundMstDTO selectYearDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

    /**
     * 회차 리스트 삭제
     */
    public int SupplyDeleteList(WBRoundMstDTO wBRoundMstDTO) throws Exception;

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
     * 최신 회차 상세 조회
     */
    public WBRoundMstSearchDTO getRoundDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

    /**
     * 회차 옵션파일 조회
     */
    public List<WBAManagementOptnDTO> selectOPtnList(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

}
