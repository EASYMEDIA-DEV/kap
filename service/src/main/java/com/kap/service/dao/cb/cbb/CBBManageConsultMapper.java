package com.kap.service.dao.cb.cbb;

import com.kap.core.dto.cb.cbb.CBBConsultSuveyRsltListDTO;
import com.kap.core.dto.cb.cbb.CBBManageConsultInsertDTO;
import com.kap.core.dto.cb.cbb.CBBManageConsultListDTO;
import com.kap.core.dto.cb.cbb.CBBManageConsultSearchDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
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
     *  상세 조회
     */
    public CBBManageConsultInsertDTO selectManageConsultList(CBBManageConsultInsertDTO pCBBManageConsultInsertDTO);

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


    /**
     * 컨설팅 기술 지도 등록
     */
    public int insertManageConsult(CBBManageConsultInsertDTO pCBBManageConsultInsertDTO) throws Exception;


    /**
     *  부품사 업종 카운트
     */
    public int selectCnstgDpndnInfoCnt(HashMap cbsnCdMap);

    /**
     *  고객사 비율 수정
     */
    public int updateCnstgDlvryInfo(HashMap cnstgDlyvMap);

    /**
     * 완성차 의존 수정
     */
    public int updateCnstgDpndnInfo(HashMap dpndnMap);

    /**
     * 신청 분야 상세
     */
    public List<CBBManageConsultInsertDTO> selectCnstgAppctnType(Integer cnstgSeq);

    /**
     * 컨설팅 진행 사항
     */
    public List<String> selectTechGuidanceRsume(CBBManageConsultInsertDTO pCBBManageConsultInsertDTO) throws Exception;

    /**
     * 회사 업종 상세 카운트
     */
    public int selectCmpnCbsnInfoCnt(Integer cbsnSeq) throws Exception;

    /**
     * 고객사 비율 상세 조회
     */
    public List<CBBManageConsultInsertDTO> selectCnstgDlvryInfo(Integer cnstgSeq) throws Exception;

    /**
     * 고객사 비율 상세 등록
     */
    public int insertCnstgDlvryInfo(HashMap dpndnMap);

    /**
     * 완성차 의존 매출 등록
     */
    public int insertCnstgDpndnInfo(HashMap dpndnMap);

    /**
     * 부품사 업종 상세 수정
     */
    public int updateCbsnDtl(CBBManageConsultInsertDTO pCBBManageConsultInsertDTO);

    /**
     *  부품사 업종 수정
     */
    public int updateCbsnDtl(HashMap cbsnCdMap);

    /**
     * 부품사 업종 상세 등록
     */
    public int insertCbsnDtl(HashMap cbstgCdMap);

    /**
     * 완성차 의존 비율 상세 조회
     */
    public List<CBBManageConsultInsertDTO> selectCnstgDpndnInfo(Integer cnstgSeq) throws Exception;

}
