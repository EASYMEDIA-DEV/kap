package com.kap.service.dao.bd;

import com.kap.core.dto.bd.bdb.BDBCompanyNewsDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 재단소식 Mapper
 * </pre>
 *
 * @ClassName		: BDBCompanyNewsMapper.java
 * @Description		: 재단소식 Mapper
 * @author 장두석
 * @since 2023.11.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2023.11.20	  장두석	             최초 생성
 * </pre>
 */
@Mapper
public interface BDBCompanyNewsMapper {
    /**
     * 재단소식 조회
   */
    public List<BDBCompanyNewsDTO> selectCompanyNewsList(BDBCompanyNewsDTO pBDBCompanyNewsDTO);
    /**
     * 재단소식 전체 개수
     */
    public int getCompanyNewsListTotCnt(BDBCompanyNewsDTO pBDBCompanyNewsDTO);
    /**
     * 재단소식 상세
     */
    public BDBCompanyNewsDTO selectCompanyNewsDtl(BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception;
    /**
     * 재단소식 등록
     */
    public int insertCompanyNews(BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception;
    /**
     * 재단소식 수정
     */
    public int updateCompanyNews(BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception;
    /**
     * 재단소식 삭제
     */
    public int deleteCompanyNews(BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception;
    /**
     * 재단소식 첨부파일 목록 조회
     */
    public List<BDBCompanyNewsDTO> selectCompanyNewsFileList(BDBCompanyNewsDTO pBDBCompanyNewsDTO);
    /**
     * 재단소식 조회수 증가
     */
    public int updateCompanyNewsReadCnt(BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception;
    /**
     * 재단소식 이전, 다음 글 SEQ 조회
     */
    public BDBCompanyNewsDTO selectNextAndPrevSeqVal(BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception;

}
