package com.kap.service.dao.sm;

import com.kap.core.dto.sm.smb.SMBMainVslDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
/**
 * <pre>
 * 메인 비주얼 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: SMBMnVslMapper.java
 * @Description		: 메인 비주얼 관리를 위한 DAO
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
public interface SMBMnVslMapper {

    /**
     * 메인 비주얼 목록 조회
     */
    public List<SMBMainVslDTO> selectMnVslList(SMBMainVslDTO pSMBMainVslDTO) throws Exception;

    /**
     * 메인 비주얼 상세 조회
     */
    public SMBMainVslDTO selectMnVslDtl(SMBMainVslDTO pSMBMainVslDTO) throws Exception;

    /**
     * 메인 비주얼 등록
     */
    public int insertMnVsl(SMBMainVslDTO pSMBMainVslDTO) throws Exception;

    /**
     * 메인 비주얼 전체 카운트
     */
    public int selectMnVsldTotCnt(SMBMainVslDTO pSMBMainVslDTO) throws Exception;

    /**
     * 메인 비주얼 수정
     */
    public int updateMnVsl(SMBMainVslDTO pSMBMainVslDTO) throws Exception;

    /**
     * 메인 비주얼 삭제
     */
    public int deleteMnVsl(SMBMainVslDTO pSMBMainVslDTO) throws Exception;

    /**
     * 메인 비주얼 정렬 수정
     */
    public void updateOrder(SMBMainVslDTO pSMBMainVslDTO) throws Exception;

    /**
     * 정렬할 메인 비주얼을 조회
     */
    public SMBMainVslDTO selectMnNewRow(SMBMainVslDTO pSMBMainVslDTO) throws Exception;
}
