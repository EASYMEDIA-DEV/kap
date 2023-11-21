package com.kap.service.dao.eb;

import com.kap.core.dto.EBACouseDTO;
import com.kap.core.dto.EBBEpisdDTO;
import com.kap.core.dto.EBBLctrDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 교육차수 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: EBBEpisdMapper.java
 * @Description		: 교육과정 관리를 위한 DAO
 * @author 김학규
 * @since 2023.11.02
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.02		김학규				   최초 생성
 * </pre>
 */
@Mapper
public interface EBBEpisdMapper {

	/**
	 * 교육차수 목록을 조회한다.
	 */
	public List<EBBEpisdDTO> selectEpisdList(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수 목록 갯수 조회
	 */
	public int selectEpisdListCnt(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수 상세를 조회한다.
	 */
	public EBBEpisdDTO selectEpisdDtl(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수를 등록한다.
	 */
	public int insertEpisd(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수 - 강사관계를 등록한다
	 */
	public int insertIsttrRel(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수 - 교육강의 상세를 등록한다.
	 */
	public int insertLctrDtl(EBBLctrDTO eBBLctrDTO) throws Exception;

	/**
	 * 교육차수를 삭제한다.
	 */
	public int deleteEpisdDtl(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수 중복체크
	 */
	public EBBEpisdDTO selectEpisdChk(EBBEpisdDTO eBBEpisdDTO) throws Exception;


}