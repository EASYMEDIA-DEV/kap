package com.kap.service.dao.eb;

import com.kap.core.dto.eb.ebf.EBFEduRoomDetailDTO;
import com.kap.core.dto.eb.ebf.EBFEduRoomSearchDTO;
import com.kap.core.dto.eb.ebf.EBFEduRoomWriteDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 교육장 Mapper
 * </pre>
 *
 * @ClassName		: EBFEduRoomMapper.java
 * @Description		: 교육장 Mapper
 * @author 장두석
 * @since 2023.11.15
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2023.11.15	  장두석	             최초 생성
 * </pre>
 */
@Mapper
public interface EBFEduRoomMapper {
    /**
     * 교육장 조회
   */
    public List<EBFEduRoomSearchDTO> selectEduRoomList(EBFEduRoomSearchDTO pEBFEduRoomSearchDTO) throws Exception;

    /**
     * 교육장 전체 개수
     */
    public int getEduRoomListTotCnt(EBFEduRoomSearchDTO pEBFEduRoomSearchDTO) throws Exception;

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
