package com.kap.service.dao.cb;

import com.kap.core.dto.CBATechGuidanceDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
/**
 * <pre>
 * 컨설팅 기술 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: CBATechGuidanceMapper.java
 * @Description		: 컨설팅 기술 관리를 위한 DAO
 * @author 임서화
 * @since 2023.10.16
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.10.16		임서화				   최초 생성
 * </pre>
 */
@Mapper
public interface CBATechGuidanceMapper {

    /**
     * 컨설팅 기술 지도 목록 조회
     */
    public List<CBATechGuidanceDTO> selectTechGuidanceList(CBATechGuidanceDTO pCBATechGuidanceDTO) throws Exception;

    /**
     * 컨설팅 기술 지도 상세 조회
     */
    public CBATechGuidanceDTO selectTechGuidanceDtl(CBATechGuidanceDTO pCBATechGuidanceDTO) throws Exception;

    /**
     * 컨설팅 기술 지도 등록
     */
    public int selectTechGuidanceTopCnt(CBATechGuidanceDTO pCBATechGuidanceDTO) throws Exception;

    /**
     * 컨설팅 기술 지도 등록
     */
    public int insertTechGuidance(CBATechGuidanceDTO pCBATechGuidanceDTO) throws Exception;

    /**
     * 컨설팅 기술 지도 전체 카운트
     */
    public int selectMnVsldTotCnt(CBATechGuidanceDTO pCBATechGuidanceDTO) throws Exception;

    /**
     * 컨설팅 기술 지도 수정
     */
    public int updateTechGuidance(CBATechGuidanceDTO pCBATechGuidanceDTO) throws Exception;

    /**
     * 컨설팅 기술 지도 삭제
     */
    public int deleteTechGuidance(CBATechGuidanceDTO pCBATechGuidanceDTO) throws Exception;

}
