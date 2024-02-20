package com.kap.service.dao.bd;

import com.kap.core.dto.bd.bdf.BDFOnlineDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * Online Mapper
 * </pre>
 *
 * @ClassName		: BDFOnlineMapper.java
 * @Description		: Online Mapper
 * @author 오병호
 * @since 2024.02.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2024.02.20	  오병호	             최초 생성
 * </pre>
 */
@Mapper
public interface BDFOnlineMapper {
    /**
     * Online 조회
   */
    public List<BDFOnlineDTO> selectOnlineList(BDFOnlineDTO bDFOnlineDTO);
    /**
     * Online 전체 개수
     */
    public int getOnlineListTotCnt(BDFOnlineDTO bDFOnlineDTO);
    /**
     * Online 상세
     */
    public BDFOnlineDTO selectOnlineDtl(BDFOnlineDTO bDFOnlineDTO) throws Exception;
    /**
     * Online 등록
     */
    public int insertOnline(BDFOnlineDTO bDFOnlineDTO) throws Exception;
    /**
     * Online 수정
     */
    public int updateOnline(BDFOnlineDTO bDFOnlineDTO) throws Exception;
    /**
     * Online 삭제
     */
    public int deleteOnline(BDFOnlineDTO bDFOnlineDTO) throws Exception;
    /**
     * Online 첨부파일 목록 조회
     */
    public List<BDFOnlineDTO> selectOnlineFileList(BDFOnlineDTO bDFOnlineDTO);

}
