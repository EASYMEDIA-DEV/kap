package com.kap.service;

import com.kap.core.dto.eb.ebf.EBFEduRoomDetailDTO;
import com.kap.core.dto.eb.ebf.EBFEduRoomSearchDTO;
import com.kap.core.dto.eb.ebf.EBFEduRoomWriteDTO;

/**
 * 교육장 service
 *
 * @author 장두석
 * @since 2023.11.15
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.15  장두석         최초 생성
 * </pre>
 */
public interface EBFEduRoomService {

    /**
     * 교육장 목록 조회
    */
    public EBFEduRoomSearchDTO selectEduRoomList(EBFEduRoomSearchDTO pEBFEduRoomSearchDTO) throws Exception;

    /**
     * 교육장 상세
     */
    public EBFEduRoomDetailDTO selectEduRoomDtl(EBFEduRoomDetailDTO pEBFEduRoomDetailDTO) throws Exception;

    /**
     * 교육장 등록
     */
    public int insertEduRoom(EBFEduRoomWriteDTO pEBFEduRoomWriteDTO) throws Exception;

    /**
     * 교육장 수정
     */
    public int updateEduRoom(EBFEduRoomWriteDTO pEBFEduRoomWriteDTO) throws Exception;

    /**
     * 교육장 삭제
     */
    public int deleteEduRoom(EBFEduRoomSearchDTO pEBFEduRoomSearchDTO) throws Exception;

}
