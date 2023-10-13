package com.kap.service;

import com.kap.core.dto.COGBoardDTO;

/**
 * COSampleService 서비스
 *
 * @author 박주석
 * @since 2022.01.10
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2022.01.10  박주석         최초 생성
 * </pre>
 */
public interface COGBoardService {

    /**
     * 게시판 조회
    */
    public COGBoardDTO selectBoardList(COGBoardDTO pCOGBoardDTO) throws Exception;

    /**
     * 게시판 등록
     */
    public int insertBoard(COGBoardDTO pCOGBoardDTO) throws Exception;

    /**
     * 게시판 상세
     */
    public COGBoardDTO selectBoardDtl(COGBoardDTO pCOGBoardDTO) throws Exception;

    /**
     * 게시판 수정
     */
    public int updateBoard(COGBoardDTO pCOGBoardDTO) throws Exception;
    /**
     * 게시판 삭제
     */
    public int deleteBoard(COGBoardDTO pCOGBoardDTO) throws Exception;

}
