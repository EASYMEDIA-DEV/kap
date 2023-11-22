package com.kap.service.dao.bd;

import com.kap.core.dto.bd.bdc.BDCFaqDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * FAQ Mapper
 * </pre>
 *
 * @ClassName		: BDCFaqMapper.java
 * @Description		: FAQ Mapper
 * @author 장두석
 * @since 2023.11.21
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2023.11.21	  장두석	             최초 생성
 * </pre>
 */
@Mapper
public interface BDCFaqMapper {
    /**
     * FAQ 조회
   */
    public List<BDCFaqDTO> selectFaqList(BDCFaqDTO pBDCFaqDTO);
    /**
     * FAQ 전체 개수
     */
    public int getFaqListTotCnt(BDCFaqDTO pBDCFaqDTO);
    /**
     * FAQ 상세
     */
    public BDCFaqDTO selectFaqDtl(BDCFaqDTO pBDCFaqDTO) throws Exception;
    /**
     * FAQ 등록
     */
    public int insertFaq(BDCFaqDTO pBDCFaqDTO) throws Exception;
    /**
     * FAQ 수정
     */
    public int updateFaq(BDCFaqDTO pBDCFaqDTO) throws Exception;
    /**
     * FAQ 삭제
     */
    public int deleteFaq(BDCFaqDTO pBDCFaqDTO) throws Exception;

}
