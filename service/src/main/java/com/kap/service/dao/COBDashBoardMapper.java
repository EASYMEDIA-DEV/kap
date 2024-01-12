package com.kap.service.dao;

import com.kap.core.dto.COBDashBoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 대쉬보드 DAO
 * </pre>
 *
 * @ClassName		: COBDashBoardMapper.java
 * @Description		: 대쉬보드 DAO
 * @author 박준희
 * @since 2024.01.12
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2024.01.12		박준희				   최초 생성
 * </pre>
 */
@Mapper
public interface COBDashBoardMapper {

    /**
     * 교육사업
     */
    public COBDashBoardDTO getEduCnt();

    /**
     * 컨설팅
     */
    public COBDashBoardDTO getConCnt();

    /**
     * 공지사항
     */
    public List<COBDashBoardDTO> getNoticeList();

    /**
     * 1:1문의
     */
    public COBDashBoardDTO getInquiryCnt();

    /**
     * 회원
     */
    public COBDashBoardDTO getMemberCnt();

}
