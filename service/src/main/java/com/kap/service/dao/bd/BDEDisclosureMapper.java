package com.kap.service.dao.bd;

import com.kap.core.dto.bd.bde.BDEDisclosureDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 경영공시 Mapper
 * </pre>
 *
 * @ClassName		: BDEDisclosureMapper.java
 * @Description		: 경영공시 Mapper
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
public interface BDEDisclosureMapper {
    /**
     * 경영공시 조회
   */
    public List<BDEDisclosureDTO> selectDisclosureList(BDEDisclosureDTO pBDEDisclosureDTO);
    /**
     * 경영공시 전체 개수
     */
    public int getDisclosureListTotCnt(BDEDisclosureDTO pBDEDisclosureDTO);
    /**
     * 경영공시 상세
     */
    public BDEDisclosureDTO selectDisclosureDtl(BDEDisclosureDTO pBDEDisclosureDTO) throws Exception;
    /**
     * 경영공시 등록
     */
    public int insertDisclosure(BDEDisclosureDTO pBDEDisclosureDTO) throws Exception;
    /**
     * 경영공시 수정
     */
    public int updateDisclosure(BDEDisclosureDTO pBDEDisclosureDTO) throws Exception;
    /**
     * 경영공시 삭제
     */
    public int deleteDisclosure(BDEDisclosureDTO pBDEDisclosureDTO) throws Exception;

}
