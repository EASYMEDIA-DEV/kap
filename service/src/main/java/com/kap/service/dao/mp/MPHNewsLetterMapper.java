package com.kap.service.dao.mp;

import com.kap.core.dto.mp.mph.MPHNewsLetterDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 뉴스레터 신청자 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: MPHNewsLetterMapper.java
 * @Description		: 뉴스레터 신청자 관리를 위한 DAO
 * @author 구은희
 * @since 2023.11.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.20		구은희				   최초 생성
 *    2024.01.05        이옥정                  이메일 중복 검사 추가
 * </pre>
 */
@Mapper
public interface MPHNewsLetterMapper {

    /**
     * 목록을 조회
     */
    public List<MPHNewsLetterDTO> selectNewsLetterList(MPHNewsLetterDTO mphNewsLetterDTO) throws Exception;

    /**
     * 신청자 수를 조회
     */
    public int selectNewsLetterCnt(MPHNewsLetterDTO mphNewsLetterDTO) throws Exception;

    /**
     * 이메일 중복 검사
     * @param mphNewsLetterDTO
     * @return
     */
    public int selectDupEmail(MPHNewsLetterDTO mphNewsLetterDTO);

    /**
     * 뉴스레터 등록
     * @param mphNewsLetterDTO
     * @return
     */
    public int insertNewsletter(MPHNewsLetterDTO mphNewsLetterDTO);    
}
