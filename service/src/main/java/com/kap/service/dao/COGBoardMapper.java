package com.kap.service.dao;

import com.kap.core.dto.COGBoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 게시판 Mapper
 * </pre>
 *
 * @ClassName		: COGBoardMapper.java
 * @Description		: 게시판 Mapper
 * @author 임서화
 * @since 2023.09.18
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2023.09.18	  임서화	             최초 생성
 * </pre>
 */
@Mapper
public interface COGBoardMapper {
    /**
     * 게시판 조회
   */
    public List<COGBoardDTO> selectBoardList(COGBoardDTO cOGBoardDTO);
    /**
     * 게시판 전체 갯수
     */
    public int getBoardListTotCnt(COGBoardDTO cOGBoardDTO);
    /**
     * 게시판 등록
     */
    public int insertBoard(COGBoardDTO cOGBoardDTO) throws Exception;
    /**
     * 게시판 상세
     */
    public COGBoardDTO selectBoardDtl(COGBoardDTO cOGBoardDTO) throws Exception;
    /**
     * 게시판 수정
     */
    public int updateBoard(COGBoardDTO cOGBoardDTO) throws Exception;
    /**
     * 게시판 삭제
     */
    public int deleteBoard(COGBoardDTO cOGBoardDTO) throws Exception;

}
