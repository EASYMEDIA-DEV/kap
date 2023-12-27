package com.kap.service.dao;

import com.kap.core.dto.COGCntsDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 컨텐츠 관리 위한 DAO
 * </pre>
 *
 * @ClassName		: COGCntsMapper.java
 * @Description		: 컨텐츠 관리 위한 DAO
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
	 * 컨텐츠 목록 개수 조회
	 */
	public int getCntsListCnt(COGCntsDTO pCOGCntsDTO) throws Exception;

	/**
	 * 컨텐츠 목록을 조회
	 */
	public List<COGCntsDTO> selectCntsList(COGCntsDTO pCOGCntsDTO) throws Exception;

	 /**
	 * 컨텐츠 배포여부 수정
	 */
	/*public int updateUseCnts(COGCntsDTO pCOGCntsDTO) throws Exception;*/

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
	 * 컨텐츠 만료
	 */
	public int updateCntsExpr(COGCntsDTO pCOGCntsDTO) throws Exception;
	/**
	 * 컨텐츠 배포
	 */
	public int updateCntsAprvl(COGCntsDTO pCOGCntsDTO) throws Exception;
	/**
	 * 컨텐츠 복사
	 */
	public int insertCntsCopy(COGCntsDTO pCOGCntsDTO) throws Exception;
	/**
	 * 컨텐츠 최신 버전 값 조회
	 */
	public int selectNewVer(COGCntsDTO pCOGCntsDTO) throws Exception;

	/**
	 * 컨텐츠 배포 내용조회
	 */
	public COGCntsDTO getCmsDtl(COGCntsDTO pCOGCntsDTO) throws Exception;


}