package com.kap.service.dao;

import com.kap.core.dto.COCodeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 코드 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: COFCodeMapper.java
 * @Description		: 코드 관리를 위한 DAO
 * @author 허진영
 * @since 2020.10.19
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *	     since		  author	            description
 *    ==========    ==========    ==============================
 * 	  2020.10.19	  허진영	             최초 생성
 * </pre>
 */
@Mapper
public interface COFCodeMapper {

	/**
	 * 코드 목록을 조회한다.
	 */
	public List<COCodeDTO> getCodeList(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 코드 상세정보를 조회한다.
	 */
	public COCodeDTO selectCodeDtl(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 코드를 등록한다.
	 */
	public int insertCode(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 코드명을 수정한다.
	 */
	public int updateCodeNm(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 코드 정보를 수정한다.
	 */
	public int updateCodeInf(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 코드 그룹ID를 수정한다.
	 */
	public int updateCdId(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 해당 코드의 오른쪽 값을 가져온다.
	 */
	public Integer getRhtVal(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 해당 코드의 깊이를 가져온다.
	 */
	public Integer getDpth(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 해당 코드의 왼쪽 값을 지정한다.
	 */
	public void setLftVal(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 해당 코드의 오른쪽 값을 지정한다.
	 */
	public void setRhtVal(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 최상위 position을 가져온다.
	 */
	public Integer getMaxPosition(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 하위 트리들을 가져온다.
	 */
	public List<COCodeDTO> getMoveNodeIds(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 하위트리갯수를 가져온다.
	 */
	public Integer getRefInd(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 코드 자식의 갯수를 가져온다.
	 */
	public Integer getMoveExits(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 움직일 노드 갯수 확인한다.
	 */
	public Integer getMoveExits2(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 코드를 이동한다. (case 1)
	 */
	public int setCodeMove1(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 코드를 이동한다. (case 2)
	 */
	public int setCodeMove2(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 코드를 이동한다. (case 3)
	 */
	public int setCodeMove3(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 코드를 이동한다. (case 4)
	 */
	public int setCodeMove4(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 코드를 이동한다. (case 5)
	 */
	public int setCodeMove5(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 코드를 이동한다. (case 6)
	 */
	public int setCodeMove6(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 코드를 이동한다. (case 7)
	 */
	public int setCodeMove7(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 코드를 이동한다. (case 8)
	 */
	public int setCodeMove8(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 코드를 이동한다. (case 9)
	 */
	public int setCodeMove9(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 코드를 이동한다. (case 10)
	 */
	public int setCodeMove10(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 코드를 이동한다. (case 11)
	 */
	public int setCodeMove11(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 코드를 삭제한다.
	 */
	public int deleteCode(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 코드의 하위노드 왼쪽키값 변경
	 */
	public void setDeleteUpdateLftVal(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 코드의 하위노드 오른쪽키값 변경
	 */
	public void setDeleteUpdateRhtVal(COCodeDTO cOCodeDTO) throws Exception;

	/**
	 * 코드의 하위노드 위치 변경
	 */
	public void setDeleteUpdatePstn(COCodeDTO cOCodeDTO) throws Exception;
}