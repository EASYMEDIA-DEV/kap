package com.kap.service;

import com.kap.core.dto.EmfMap;
import com.kap.core.dto.ex.exg.EXGExamMstSearchDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wba.WBAManageInsertDTO;
import com.kap.core.dto.wb.wba.WBAManageSearchDTO;
import com.kap.core.dto.wb.wba.WBAManagementOptnDTO;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 상생사업 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: WBAManagementService.java
 * @Description		: 상생사업 관리를 위한 Service
 * @author 김태훈
 * @since 2023.11.10
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.10		김태훈				   최초 생성
 * </pre>
 */
public interface WBAManagementService {

    /**
     * 상생사업 리스트 목록을 조회한다.
     */
    public WBAManageSearchDTO selectManagementList(WBAManageSearchDTO wbaManageSearchDTO) throws Exception;

    /**
     * 상생사업 개수를 조회한다.
     */
    public int selectManagementCnt(WBAManageSearchDTO wbaManageSearchDTO) throws Exception;

    /**
     * 상생사업 상세 목록을 조회한다.
     */
    public WBAManageInsertDTO selectManagementDtl(WBAManageSearchDTO wbaManageSearchDTO) throws Exception;

    /**
     * 상생사업 등록
     */
    public WBAManageInsertDTO insertManagement(WBAManageInsertDTO WBAManageInsertDTO, HttpServletRequest request) throws Exception;

    /**
     * 상생사업 수정
     */
    public WBAManageInsertDTO updateManagement(WBAManageInsertDTO WBAManageInsertDTO, HttpServletRequest request) throws Exception;

    /**
     * 상생사업 삭제
     */
    public int deleteManagement(WBAManageInsertDTO wbaManageInsertDTO) throws Exception;

    /**
     * 첨부파일 업데이트
     */
    public int updateFileInfo(WBAManageInsertDTO WBAManageInsertDTO) throws Exception;

    /**
     * 첨부파일 업데이트
     */
    public List<WBAManagementOptnDTO> selectOptnList(WBAManageSearchDTO wbaManageSearchDTO) throws Exception;

}
