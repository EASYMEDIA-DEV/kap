package com.kap.service.dao.bd;

import com.kap.core.dto.bd.bda.BDANoticeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 공지사항 Mapper
 * </pre>
 *
 * @ClassName		: BDANoticeMapper.java
 * @Description		: 공지사항 Mapper
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
public interface BDANoticeMapper {
    /**
     * 공지사항 조회
   */
    public List<BDANoticeDTO> selectNoticeList(BDANoticeDTO pBDANoticeDTO);
    /**
     * 공지사항 전체 개수
     */
    public int getNoticeListTotCnt(BDANoticeDTO pBDANoticeDTO);
    /**
     * 공지사항 상세
     */
    public BDANoticeDTO selectNoticeDtl(BDANoticeDTO pBDANoticeDTO) throws Exception;
    /**
     * 공지사항 등록
     */
    public int insertNotice(BDANoticeDTO pBDANoticeDTO) throws Exception;
    /**
     * 공지사항 수정
     */
    public int updateNotice(BDANoticeDTO pBDANoticeDTO) throws Exception;
    /**
     * 공지사항 삭제
     */
    public int deleteNotice(BDANoticeDTO pBDANoticeDTO) throws Exception;
    /**
     * 공지사항 첨부파일 목록 조회
     */
    public List<BDANoticeDTO> selectNoticeFileList(BDANoticeDTO pBDANoticeDTO);
    /**
     * 공지사항 조회수 증가
     */
    public int updateNoticeReadCnt(BDANoticeDTO pBDANoticeDTO) throws Exception;
    /**
     * 공지사항 이전, 다음 글 SEQ 조회
     */
    public BDANoticeDTO selectNextAndPrevSeqVal(BDANoticeDTO pBDANoticeDTO) throws Exception;

    /**
     * 공지사항 중요공지 목록을 조회
     */
    public List<BDANoticeDTO> selectMainPostList(BDANoticeDTO pBDANoticeDTO) throws Exception;

}
