package com.kap.service.dao.bd;

import com.kap.core.dto.bd.bdd.BDDNewsletterDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 뉴스레터 Mapper
 * </pre>
 *
 * @ClassName		: BDDNewsletterMapper.java
 * @Description		: 뉴스레터 Mapper
 * @author 장두석
 * @since 2023.11.22
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2023.11.22	  장두석	             최초 생성
 * </pre>
 */
@Mapper
public interface BDDNewsletterMapper {
    /**
     * 뉴스레터 조회
   */
    public List<BDDNewsletterDTO> selectNewsletterList(BDDNewsletterDTO pBDDNewsletterDTO);
    /**
     * 뉴스레터 전체 개수
     */
    public int getNewsletterListTotCnt(BDDNewsletterDTO pBDDNewsletterDTO);
    /**
     * 뉴스레터 상세
     */
    public BDDNewsletterDTO selectNewsletterDtl(BDDNewsletterDTO pBDDNewsletterDTO) throws Exception;
    /**
     * 뉴스레터 등록
     */
    public int insertNewsletter(BDDNewsletterDTO pBDDNewsletterDTO) throws Exception;
    /**
     * 뉴스레터 수정
     */
    public int updateNewsletter(BDDNewsletterDTO pBDDNewsletterDTO) throws Exception;
    /**
     * 뉴스레터 삭제
     */
    public int deleteNewsletter(BDDNewsletterDTO pBDDNewsletterDTO) throws Exception;

}
