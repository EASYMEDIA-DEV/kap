package com.kap.service.dao.cb.cbb;

import com.kap.core.dto.COEgovSeqDTO;
import com.kap.core.dto.cb.cbb.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 경영컨설팅 Mapper
 * </pre>
 *
 * @ClassName		: CBBManageConsultMapper.java
 * @Description		: 경영컨설팅 Mapper
 * @author 이옥정
 * @since 2023.11.24
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2023.11.24	  이옥정	             최초 생성
 * </pre>
 */

@Mapper
public interface CBBManageConsultMapper {
    /**
     * 리스트 조회
     */
    public List<CBBManageConsultListDTO> selectManageConsultList(CBBManageConsultSearchDTO cBBManageConsultSearchDTO);

    /**
     * 리스트 갯수 조회
     */
    public int getManageConsultListCnt(CBBManageConsultSearchDTO cBBManageConsultSearchDTO);

    /**
     * 만족도 종합결과 리스트 조회
     */
    public List<CBBConsultSuveyRsltListDTO> selectConsultSuveyRsltList(CBBConsultSuveyRsltListDTO cBBConsultSuveyRsltListDTO);

    /**
     * 만족도 종합결과 리스트 갯수 조회
     */
    public int getConsultSuveyRsltCnt(CBBConsultSuveyRsltListDTO cBBConsultSuveyRsltListDTO);

    /**
     * 만족도 종합결과 엑셀 상세 조회
     */
    public List<CBBConsultSuveyRsltListDTO> selectConsultSuveyRsltDtlExcel(CBBConsultSuveyRsltListDTO cBBConsultSuveyRsltListDTO);

    /**
     * 만족도 종합결과 엑셀 갯수 조회
     */
    public int getConsultSuveyRsltDtlCnt(CBBConsultSuveyRsltListDTO cBBConsultSuveyRsltListDTO);

}
