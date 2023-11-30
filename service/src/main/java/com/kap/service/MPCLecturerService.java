package com.kap.service;

import com.kap.core.dto.mp.mpc.MPCLecturerDTO;

/**
 * <pre>
 * 강사 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: MPCLecturerService.java
 * @Description		: 강사 관리를 위한 Service
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
public interface MPCLecturerService {

    /**
     * 강사 목록을 조회한다.
     */
    public MPCLecturerDTO selectLecturerList(MPCLecturerDTO mpcLecturerDTO) throws Exception;

    /**
     * 강사 수를 조회한다.
     */
    public int selectLecturerCnt(MPCLecturerDTO mpcLecturerDTO) throws Exception;

    /**
     * 강사 상세를 조회한다.
     */
    public MPCLecturerDTO selectLecturerDtl(MPCLecturerDTO mpcLecturerDTO) throws Exception;

    /**
     * 강사 이메일 중복 체크를 한다.
     */
    public int selectDupEmail(MPCLecturerDTO mpcLecturerDTO) throws Exception;

    /**
     * 강사를 등록한다.
     */
    public int insertLecturer(MPCLecturerDTO mpcLecturerDTO) throws Exception;

    /**
     * 강사를 수정한다.
     */
    public int updateLecturer(MPCLecturerDTO mpcLecturerDTO) throws Exception;

    /**
     * 강사를 삭제한다.
     */
    public int deleteLecturer(MPCLecturerDTO mpcLecturerDTO) throws Exception;

}
