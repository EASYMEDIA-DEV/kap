package com.kap.service.dao;

import com.kap.core.dto.COMenuDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 메뉴 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: CODMenuMapper.java
 * @Description		: 메뉴 관리를 위한 DAO
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
public interface COBMenuMapper {

	/**
	 * 메뉴 목록을 조회한다.
	 */
	public List<COMenuDTO> getMenuList(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 사용자 메뉴 TYPE을 조회한다.
	 */
	public List<COMenuDTO> getUserMenuList(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 메뉴의 상세정보를 조회한다.
	 */
	public COMenuDTO selectMenuDtl(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 메뉴를 등록한다.
	 */
	public int insertMenu(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 메뉴명을 수정한다.
	 */
	public int updateMenuNm(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 메뉴정보를 수정한다.
	 */
	public int updateMenuInf(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 해당 메뉴의 오른쪽 값을 가져온다.
	 */
	public int getRhtVal(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 해당 메뉴의 깊이를 가져온다.
	 */
	public int getDpth(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 해당 메뉴의 왼쪽 값을 지정한다.
	 */
	public void setLftVal(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 해당 메뉴의 오른쪽 값을 지정한다.
	 */
	public void setRhtVal(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 최상위 position을 가져온다.
	 */
	public Integer getMaxPosition(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 하위 트리들을 가져온다.
	 */
	public List<COMenuDTO> getMoveNodeIds(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 하위트리갯수를 가져온다.
	 */
	public Integer getRefInd(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 메뉴 자식의 갯수를 가져온다.
	 */
	public Integer getMoveExits(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 움직일 노드 갯수 확인한다.
	 */
	public Integer getMoveExits2(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 메뉴를 이동한다. (case 1)
	 */
	public int setMenuMove1(COMenuDTO cOMenuDTO) throws Exception;
	
	/**
	 * 메뉴를 이동한다. (case 2)
	 */
	public int setMenuMove2(COMenuDTO cOMenuDTO) throws Exception;
	
	/**
	 * 메뉴를 이동한다. (case 3)
	 */
	public int setMenuMove3(COMenuDTO cOMenuDTO) throws Exception;
	
	/**
	 * 메뉴를 이동한다. (case 4)
	 */
	public int setMenuMove4(COMenuDTO cOMenuDTO) throws Exception;
	
	/**
	 * 메뉴를 이동한다. (case 5)
	 */
	public int setMenuMove5(COMenuDTO cOMenuDTO) throws Exception;
	
	/**
	 * 메뉴를 이동한다. (case 6)
	 */
	public int setMenuMove6(COMenuDTO cOMenuDTO) throws Exception;
	
	/**
	 * 메뉴를 이동한다. (case 7)
	 */
	public int setMenuMove7(COMenuDTO cOMenuDTO) throws Exception;
	
	/**
	 * 메뉴를 이동한다. (case 8)
	 */
	public int setMenuMove8(COMenuDTO cOMenuDTO) throws Exception;
	
	/**
	 * 메뉴를 이동한다. (case 9)
	 */
	public int setMenuMove9(COMenuDTO cOMenuDTO) throws Exception;
	
	/**
	 * 메뉴를 이동한다. (case 10)
	 */
	public int setMenuMove10(COMenuDTO cOMenuDTO) throws Exception;
	
	/**
	 * 메뉴를 이동한다. (case 11)
	 */
	public int setMenuMove11(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 메뉴를 삭제한다.
	 */
	public int deleteMenu(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 메뉴의 하위노드 왼쪽키값 변경
	 */
	public void setDeleteUpdateLftVal(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 메뉴의 하위노드 오른쪽키값 변경
	 */
	public void setDeleteUpdateRhtVal(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 메뉴의 하위노드 위치 변경
	 */
	public void setDeleteUpdatePstn(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 상위부모를 다 가져온다.
	 */
	public List<COMenuDTO> getParntData(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 하위노드를 다 가져온다.
	 */
	public List<COMenuDTO> getChildData(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 부모키 기준으로 첫번째 하위노드를 조회한다.
	 */
	public List<COMenuDTO> getChildNodeData(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 신규 메뉴 등록 시 관리자 메뉴 권한 관리 테이블에서 신규메뉴의 부모 seq 삭제
	 */
	public void deleteAdmMenu(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 선택된 메뉴가 속한 최상위 드라이브 메뉴 정보
	 */
	public COMenuDTO getNodeDriveData(COMenuDTO cOMenuDTO) throws Exception;
}