package com.kap.service.dao;

import com.kap.core.dto.COSystemLogDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 접속로그 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: COCAssLogMapper.java
 * @Description		: 접속로그 관리를 위한 DAO
 * @author 신혜정
 * @since 2022.04.08
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2022.04.08		신혜정				   최초 생성
 * </pre>
 */
@Mapper
public interface COCAssLogMapper {
    /**
     * 접속로그 목록 갯수 조회
     */
    public int getAssLogListCnt(COSystemLogDTO pCoSystemLogDTO) throws Exception;
    /**
     * 접속로그 목록을 조회한다.
     */
    public List<COSystemLogDTO> selectAssLogList(COSystemLogDTO pCoSystemLogDTO) throws Exception;

}
