package com.kap.service.dao;

import com.kap.core.dto.COGCntsDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * CMS 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: COGCntsMapper.java
 * @Description		: CMS 관리를 위한 DAO
 * @author 임서화
 * @since 2023.09.07
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.09.07		임서화				   최초 생성
 * </pre>
 */
@Mapper
public interface COGCntsMapper {
	/**
	 * CMS 목록 갯수 조회
	 */
	public int getCntsListCnt(COGCntsDTO pCOGCntsDTO) throws Exception;

	/**
	 * CMS 목록을 조회한다.
	 */
	public List<COGCntsDTO> selectCntsList(COGCntsDTO pCOGCntsDTO) throws Exception;

	/**
	 * 현재 카테고리의 CMS 시퀀스 값을 가져온다.
	 */
	public String selectSeqNum(String tableNm) throws Exception;

	/**
	 * CMS 시퀀스 값을 상승시킨다.
	 */
	public int updateCntsSeq(String tableNm) throws Exception;

	 /**
	 * CMS를 수정한다.
	 */
	public int updateUseCnts(COGCntsDTO pCOGCntsDTO) throws Exception;

	/**
	 * CMS 상세를 조회한다.
	 */
	public COGCntsDTO selectCntsDtl(COGCntsDTO pCOGCntsDTO) throws Exception;
	/**
	 * CMS를 등록한다.
	 */
	public int insertCnts(COGCntsDTO pCOGCntsDTO) throws Exception;
	/**
	 * CMS를 수정한다.
	 */
	public int updateCnts(COGCntsDTO pCOGCntsDTO) throws Exception;
	/**
	 * CMS를 삭제한다.
	 */
	public int deleteCnts(COGCntsDTO pCOGCntsDTO) throws Exception;

}