package com.kap.service.impl;

import com.kap.common.utility.COStringUtil;
import com.kap.core.dto.COMenuDTO;
import com.kap.service.COBUserMenuService;
import com.kap.service.dao.COBUserMenuMapper;
import com.nhncorp.lucy.security.xss.XssPreventer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 * 메뉴 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: CODMenuServiceImpl.java
 * @Description		: 메뉴 관리를 위한 ServiceImpl
 * @author 허진영
 * @since 2020.10.19
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *	     since		  author	            description
 *    ==========    ==========    ==============================
 * 	  2020.10.19	  허진영	             최초 생성
 * 	  2023.10.13	  임서화		    cms url을 관리자와 사용자 분리
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class COBUserMenuServiceImpl implements COBUserMenuService {

	//DAO
	private final COBUserMenuMapper cOBUserMenuMapper;

	/**
	 * 메뉴 목록을 조회한다.
	 */
	public List<COMenuDTO> getMenuList(COMenuDTO cOMenuDTO) throws Exception
	{
		if(!"Y".equals(cOMenuDTO.getIsMenu())){
			cOMenuDTO.setUserMenuList( cOBUserMenuMapper.getUserMenuList(cOMenuDTO) );
		}

		if (cOMenuDTO.getSubSeq() != null) {
			cOMenuDTO.setMenuSeq(cOMenuDTO.getSubSeq());
		} else {
			cOMenuDTO.setMenuSeq(cOBUserMenuMapper.selectUserTopNode(cOMenuDTO));
		}
		return cOBUserMenuMapper.getMenuList(cOMenuDTO);
	}

	/**
	 * 메뉴의 상세정보를 조회한다.
	 */
	public COMenuDTO selectMenuDtl(COMenuDTO cOMenuDTO) throws Exception
	{
		return cOBUserMenuMapper.selectMenuDtl(cOMenuDTO);
	}

	/**
	 * 메뉴를 등록한다.
	 */
	public int insertMenu(COMenuDTO cOMenuDTO) throws Exception
	{
		cOMenuDTO.setRhtVal(cOBUserMenuMapper.getRhtVal(cOMenuDTO));
		cOMenuDTO.setDpth(cOBUserMenuMapper.getDpth(cOMenuDTO));
		cOBUserMenuMapper.setLftVal(cOMenuDTO);
		cOBUserMenuMapper.setRhtVal(cOMenuDTO);
		// 신규 메뉴 등록 시 관리자 메뉴 권한 관리 테이블에서 신규메뉴의 부모 seq 삭제
		cOBUserMenuMapper.deleteAdmMenu(cOMenuDTO);

		return cOBUserMenuMapper.insertMenu(cOMenuDTO);
	}

	/**
	 * 메뉴명을 수정한다.
	 */
	public int updateMenuNm(COMenuDTO cOMenuDTO) throws Exception
	{
		//메뉴명에 특수기호 변경
		cOMenuDTO.setMenuNm( cOMenuDTO.getMenuNm().replaceAll("&amp;", "&") );
		return cOBUserMenuMapper.updateMenuNm(cOMenuDTO);
	}

	/**
	 * 메뉴 정보를 수정한다.
	 */
	public int updateMenuInf(COMenuDTO cOMenuDTO) throws Exception
	{
		String menuType = cOMenuDTO.getMenuType();

		String userUrl = cOMenuDTO.getUserUrl();

		if(userUrl != null && !userUrl.isEmpty()) {
			/*int trgtLen = cOMenuDTO.getUserUrl().length() - 1;

			if (userUrl.lastIndexOf("/") == trgtLen) {
				userUrl = userUrl.substring(0, trgtLen);
			}*/

			if (menuType.equals("cms")) {
//				cOMenuDTO.setUserUrl(COStringUtil.nullConvert(userUrl + "/" + cOMenuDTO.getMenuSeq() + "/content"));
				cOMenuDTO.setUserUrl(COStringUtil.nullConvert(userUrl + "/content"));
			} else {
				cOMenuDTO.setUserUrl(COStringUtil.nullConvert(cOMenuDTO.getAdmUrl()).replace("/mngwserc", ""));
			}
		}
		return cOBUserMenuMapper.updateMenuInf(cOMenuDTO);
	}

	/**
	 * 메뉴를 이동한다.
	 */
	public int updateMenuPstn(COMenuDTO cOMenuDTO) throws Exception
	{
		boolean sqlFlag1 = false, sqlFlag2 = false, sqlFlag3 = false, sqlFlag4 = false, sqlFlag5 = false, sqlFlag6 = false;
		
		String node_ids = "";

		int node_parntSeq = 0;
		int node_pstn = 0;
		int node_lftVal = 0;
		int node_rhtVal = 0;
		int node_dpth = 0;

		int refNode_parntSeq = 0;
		int refNode_pstn = 0;
		int refNode_lftVal = 0;
		int refNode_rhtVal = 0;
		int refNode_dpth = 0;

		int ndif = 2;
		int ref_ind = 0;
		int self = 0;
		int ldif = 0;
		int idif = 0;

		int menuSeq = cOMenuDTO.getMenuSeq();
		int pstn 	= cOMenuDTO.getPstn();
		int refSeq 	= cOMenuDTO.getRefSeq();
		int isCopy 	= cOMenuDTO.getIsCopy();
		int actCnt 	= 0;

		// 현재 트리
		COMenuDTO currentMenuDto = cOBUserMenuMapper.selectMenuDtl(cOMenuDTO);

		// 임시 이동할 트리
		COMenuDTO tmpMenuDto = new COMenuDTO();
		tmpMenuDto.setMenuSeq( refSeq );

		//참조 트리
		COMenuDTO refMenuDto = cOBUserMenuMapper.selectMenuDtl(tmpMenuDto);

		node_parntSeq 		= currentMenuDto.getParntSeq();
		node_pstn 			= currentMenuDto.getPstn();
		node_lftVal 		= currentMenuDto.getLftVal();
		node_rhtVal 		= currentMenuDto.getRhtVal();
		node_dpth 			= currentMenuDto.getDpth();

		refNode_rhtVal 		= refMenuDto.getRhtVal();
		refNode_dpth 		= refMenuDto.getDpth();

		if (node_lftVal > 0)
		{
			COMenuDTO exitMenuDto = new COMenuDTO();
			exitMenuDto.setLftVal( node_lftVal );
			exitMenuDto.setRhtVal( node_rhtVal );
			exitMenuDto.setRefSeq( refSeq );

			Integer exitMenuSeq = cOBUserMenuMapper.getMoveExits(exitMenuDto);

			if (exitMenuSeq != null)
			{
				return 0;
			}

			COMenuDTO nodeIdsMenuDto = new COMenuDTO();
			nodeIdsMenuDto.setLftVal( node_lftVal );
			nodeIdsMenuDto.setRhtVal( node_rhtVal );

			List<COMenuDTO> moveNodeIds = cOBUserMenuMapper.getMoveNodeIds(nodeIdsMenuDto);

			for (int q = 0; q < moveNodeIds.size(); q++)
			{
				COMenuDTO moveNodeIdMenuDto = moveNodeIds.get(q);

				if ("".equals(node_ids))
				{
					node_ids = COStringUtil.nullConvert(moveNodeIdMenuDto.getMenuSeq());
				}
				else
				{
					node_ids = node_ids + "," + COStringUtil.nullConvert(moveNodeIdMenuDto.getMenuSeq());
				}
			}

			ndif = node_rhtVal - node_lftVal + 1;
		}

		if ("".equals(node_ids))
		{
			node_ids = "0";
		}

		COMenuDTO maxMenuDto = new COMenuDTO();
		maxMenuDto.setRefSeq( refSeq );

		int maxPosition = cOBUserMenuMapper.getMaxPosition(maxMenuDto);

		if (pstn >= maxPosition)
		{
			pstn = maxPosition;
		}

		COMenuDTO sqlMenuDto1 = null;
		if (node_lftVal > 0 && isCopy == 0)
		{
			sqlMenuDto1 = new COMenuDTO();
			sqlMenuDto1.setNodeParntSeq( node_parntSeq );
			sqlMenuDto1.setNodePstn( node_pstn );
			sqlMenuDto1.setNdif( ndif );
			sqlMenuDto1.setNodeLftVal( node_lftVal );
			sqlMenuDto1.setNodeRhtVal( node_rhtVal );
			sqlMenuDto1.setNodeIds( node_ids.split(",") );

			sqlFlag1 = true;
		}

		COMenuDTO sqlMenuDto2 = new COMenuDTO();
		sqlMenuDto2.setRefSeq( refSeq );
		sqlMenuDto2.setPstn( pstn );

		if (isCopy == 0)
		{
			sqlMenuDto2.setNodeIds( node_ids.split(",") );
			sqlFlag2 = true;
		}
		else
		{
			sqlFlag3 = true;
		}

		if (refSeq != 0)
		{
			ref_ind = refNode_rhtVal;
		}

		if (ref_ind < 1)
		{
			ref_ind = 1;
		}

		if (node_lftVal > 0 && isCopy == 0 && node_parntSeq == refSeq && pstn > node_pstn)
		{
			self = 1;
		}

		COMenuDTO moveExitsMenuDto = new COMenuDTO();
		moveExitsMenuDto.setSelf( self );
		moveExitsMenuDto.setPstn( pstn );
		moveExitsMenuDto.setRefSeq( refSeq );

		Integer moveExits2 = cOBUserMenuMapper.getMoveExits2(moveExitsMenuDto);

		if (moveExits2 != 0)
		{
			ref_ind = cOBUserMenuMapper.getRefInd(moveExitsMenuDto);
		}

		if (node_lftVal > 0 && isCopy == 0 && node_lftVal < ref_ind)
		{
			ref_ind = ref_ind - ndif;
		}

		COMenuDTO sqlMenuDto3 = new COMenuDTO();
		sqlMenuDto3.setNdif( ndif );
		sqlMenuDto3.setRefInd( ref_ind );

		if (isCopy == 0)
		{
			sqlMenuDto3.setNodeIds( node_ids.split(",") );
			sqlFlag4 = true;
		}
		else
		{
			sqlFlag5 = true;
		}

		if (refSeq != 0)
		{
			ldif = refNode_dpth + 1;
		}

		idif = ref_ind;

		COMenuDTO sqlMenuDto4 = null;

		if (node_lftVal > 0)
		{
			ldif = node_dpth - (refNode_dpth + 1);
			idif = node_lftVal - ref_ind;

			if (isCopy == 0)
			{
				sqlMenuDto4 = new COMenuDTO();
				sqlMenuDto4.setRefSeq( refSeq );
				sqlMenuDto4.setPstn( pstn );
				sqlMenuDto4.setMenuSeq( menuSeq );
				sqlMenuDto4.setIdif( idif );
				sqlMenuDto4.setLdif( ldif );
				sqlMenuDto4.setNodeIds( node_ids.split(",") );
				
				sqlFlag6 = true;
			}
		}
		
		if (sqlFlag1)
		{
			actCnt += cOBUserMenuMapper.setMenuMove1(sqlMenuDto1);
			actCnt += cOBUserMenuMapper.setMenuMove2(sqlMenuDto1);
			actCnt += cOBUserMenuMapper.setMenuMove3(sqlMenuDto1);
		}
		
		if (sqlFlag2)
		{
			actCnt += cOBUserMenuMapper.setMenuMove4(sqlMenuDto2);
		}
		
		if (sqlFlag3)
		{
			actCnt += cOBUserMenuMapper.setMenuMove5(sqlMenuDto2);
		}
		
		if (sqlFlag4)
		{
			actCnt += cOBUserMenuMapper.setMenuMove6(sqlMenuDto3);
			actCnt += cOBUserMenuMapper.setMenuMove7(sqlMenuDto3);
		}
		
		if (sqlFlag5)
		{
			actCnt += cOBUserMenuMapper.setMenuMove8(sqlMenuDto3);
			actCnt += cOBUserMenuMapper.setMenuMove9(sqlMenuDto3);
		}
		
		if (sqlFlag6)
		{
			actCnt += cOBUserMenuMapper.setMenuMove10(sqlMenuDto4);
			actCnt += cOBUserMenuMapper.setMenuMove11(sqlMenuDto4);
		}

		return actCnt;
	}

	/**
	 * 메뉴를 삭제한다.
	 */
	public int deleteMenu(COMenuDTO cOMenuDTO) throws Exception
	{
		COMenuDTO infoDto = cOBUserMenuMapper.selectMenuDtl(cOMenuDTO);
		
		int actCnt = 0;

		if (infoDto != null)
		{
			actCnt = cOBUserMenuMapper.deleteMenu(infoDto);
			
			cOBUserMenuMapper.setDeleteUpdateLftVal(infoDto);
			cOBUserMenuMapper.setDeleteUpdateRhtVal(infoDto);
			cOBUserMenuMapper.setDeleteUpdatePstn(infoDto);
		}

		return actCnt;
	}

	/**
	 * 상위부모를 다 가져온다.
	 */
	public List<COMenuDTO> getParntData(COMenuDTO cOMenuDTO) throws Exception
	{
		return cOBUserMenuMapper.getParntData(cOMenuDTO);
	}

	/**
	 * 하위노드를 다 가져온다.
	 */
	public List<COMenuDTO> getChildData(COMenuDTO cOMenuDTO) throws Exception
	{
		return cOBUserMenuMapper.getChildData(cOMenuDTO);
	}

	/**
	 * 메뉴 목록 일괄처리를 위한 재귀함수
	 */
	public JSONArray getJsonData(List<COMenuDTO> jsonList, int startNum, int paramSeq) throws Exception
	{
		JSONArray tmpArray = new JSONArray();
		COMenuDTO menuDto = null;
		int i = 0, seq = 0, parntSeq = 0;

		String checktype = "", checkrole = "";
		String menuNm = "";
		for (i = startNum; i < jsonList.size(); i++)
		{
			menuDto 	= jsonList.get(i);
			seq 		= menuDto.getMenuSeq();
			parntSeq 	= menuDto.getParntSeq();


			if (paramSeq == parntSeq)
			{
				JSONObject tmpObject = new JSONObject();

				menuNm =  menuDto.getMenuNm();
				//역치환해서 보여준다.
				tmpObject.put("data", XssPreventer.unescape(menuNm));
				tmpObject.put("i", i);

//				if (menuDto.getChildcnt() > 0  && !"menu".equals(menuDto.getMenuType()))
				if (menuDto.getChildcnt() > 0)
				{
					tmpObject.put("state", "open");

					i = i + 1;

					JSONArray rtnArray = getJsonData(jsonList, i, seq);

					tmpObject.put("children", rtnArray);

					if (rtnArray.size() > 0)
					{
						i = Integer.parseInt(((JSONObject) rtnArray.get(rtnArray.size() - 1)).get("i").toString());
					}
					rtnArray = null;
				}
				JSONObject jsonAttr = new JSONObject();
		        jsonAttr.put("id", 				"node_" + menuDto.getMenuSeq());
		        jsonAttr.put("rel", 			menuDto.getMenuType());
		        jsonAttr.put("treeid", 			menuDto.getMenuSeq());
		        jsonAttr.put("parent_treeid", 	menuDto.getParntSeq());
		        jsonAttr.put("dpth", 			menuDto.getDpth());
		        jsonAttr.put("link", 			menuDto.getUserUrl());
		        jsonAttr.put("gnbYn", 			menuDto.getGnbYn());
		        jsonAttr.put("status", 			menuDto.getUseYn());

		        if (!"".equals(COStringUtil.nullConvert(menuDto.getChecktype())))
		        {
		        	checktype = menuDto.getChecktype();
		        	jsonAttr.put("checktype", checktype);
		        	if ("".equals(checktype))
	                {
	                	jsonAttr.put("class", "jstree-unchecked");
	                }
	                else
	                {
	                	jsonAttr.put("class", "jstree-checked");
	                }
		        }
		        tmpObject.put("attr", jsonAttr);
				tmpArray.add(tmpObject);
		        tmpObject = null;
		        jsonAttr = null;
			}
		}

		return tmpArray;
	}

	/**
	 * 사용자 메뉴 목록을 가져온다.
	 */
	public List<COMenuDTO> getClientMenuList(COMenuDTO cOMenuDTO) throws Exception{
		return cOBUserMenuMapper.getClientMenuList(cOMenuDTO);
	}

}