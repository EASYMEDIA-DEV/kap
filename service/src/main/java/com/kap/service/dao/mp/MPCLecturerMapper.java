package com.kap.service.dao.mp;

import com.kap.core.dto.mp.mpc.MPCLecturerDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 강사 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: MPCLecturerMapper.java
 * @Description		: 강사 관리를 위한 DAO
 * @author 구은희
 * @since 2023.11.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.20		구은희				   최초 생성
 * </pre>
 */
@Mapper
public interface MPCLecturerMapper {

    /**
     * 강사 목록을 조회
     */
    public List<MPCLecturerDTO> selectLecturerList(MPCLecturerDTO mpcLecturerDTO) throws Exception;

    /**
     * 강사 수를 조회
     */
    public int selectLecturerCnt(MPCLecturerDTO mpcLecturerDTO) throws Exception;

    /**
     * 강사 상세를 조회
     */
    public MPCLecturerDTO selectLecturerDtl(MPCLecturerDTO mpcLecturerDTO) throws Exception;

    /**
     * 이메일 중복 체크
     */
    public int selectDupEmail(MPCLecturerDTO mpcLecturerDTO) throws Exception;

    /**
     * 강사 등록
     */
    public int insertLecturer(MPCLecturerDTO mpcLecturerDTO) throws Exception;

    /**
     * 강사 수정
     */
    public int updateLecturer(MPCLecturerDTO mpcLecturerDTO) throws Exception;

    /**
     * 강사 삭제
     */
    public int deleteLecturer(MPCLecturerDTO mpcLecturerDTO) throws Exception;

    /**
     * 상생 사업 현황 목록 조회
     */
    public List<MPCLecturerDTO> selectWinBusinessList(MPCLecturerDTO mpcLecturerDTO) throws Exception;

    /**
     * 상생 사업 현황 개수 조회
     */
    public int selectWinBusinessCnt(MPCLecturerDTO mpcLecturerDTO) throws Exception;
}
