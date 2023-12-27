package com.kap.service;

import com.kap.core.dto.COGCntsDTO;

/**
 * <pre>
 * 컨텐츠 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: COGCntsService.java
 * @Description		: 컨텐츠 관리를 위한 Service
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
public interface COGCntsService {

	/**
	 * 컨텐츠 목록 조회
	 */
	public COGCntsDTO selectCntsList(COGCntsDTO pCOGCntsDTO) throws Exception;

	/**
	 * 컨텐츠 상세 조회
	 */
	public COGCntsDTO selectCntsDtl(COGCntsDTO pCOGCntsDTO) throws Exception;

	/**
	 * 컨텐츠 등록
	 */
	public int insertCnts(COGCntsDTO pCOGCntsDTO) throws Exception;

	/**
	 * 컨텐츠 수정
	 */
	public int updateCnts(COGCntsDTO pCOGCntsDTO) throws Exception;

	/**
	 * 컨텐츠 삭제
	 */
	public int deleteCnts(COGCntsDTO pCOGCntsDTO) throws Exception;

	/**
	 * 컨텐츠 배포
	 */
	public void updateCntsAprvl(COGCntsDTO pCOGCntsDTO) throws Exception;

	/**
	 * 컨텐츠 복사
	 */
	public void insertCntsCopy(COGCntsDTO pCOGCntsDTO) throws Exception;

	/**
	 * 컨텐츠 최신 버전 값 조회
	 */
	public COGCntsDTO selectNewVer(COGCntsDTO pCOGCntsDTO) throws Exception;

	/**
	 * 컨텐츠 상세조회
	 */
	public COGCntsDTO getCmsDtl(COGCntsDTO pCOGCntsDTO,String bsnCd) throws Exception;
}