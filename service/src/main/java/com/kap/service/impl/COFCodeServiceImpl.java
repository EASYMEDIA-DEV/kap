package com.kap.service.impl;

import com.kap.common.utility.COStringUtil;
import com.kap.core.dto.COCodeDTO;
import com.kap.service.COFCodeService;
import com.kap.service.dao.COFCodeMapper;
import com.nhncorp.lucy.security.xss.XssPreventer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 * 코드 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: COECodeServiceImpl.java
 * @Description		: 코드 관리를 위한 ServiceImpl
 * @author 허진영
 * @since 2021.04.26
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author					description
 *   ===========    ==============    =============================
 *    2021.04.26		허진영					최초생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class COFCodeServiceImpl  implements COFCodeService {

	private final COFCodeMapper cOFCodeMapper;

	/**
	 * 코드 목록을 조회한다.
	 */
	public List<COCodeDTO> getCodeList(COCodeDTO cOCodeDTO) throws Exception
	{
		return cOFCodeMapper.getCodeList(cOCodeDTO);
	}

	/**
	 * 코드 상세정보를 조회한다.
	 */
	public COCodeDTO selectCodeDtl(COCodeDTO cOCodeDTO) throws Exception
	{
		return cOFCodeMapper.selectCodeDtl(cOCodeDTO);
	}

	/**
	 * 코드를 등록한다.
	 */
	public int insertCode(COCodeDTO cOCodeDTO) throws Exception
	{
		cOCodeDTO.setRhtVal( cOFCodeMapper.getRhtVal(cOCodeDTO) );
		cOCodeDTO.setDpth( cOFCodeMapper.getDpth(cOCodeDTO) );

		cOFCodeMapper.setLftVal(cOCodeDTO);
		cOFCodeMapper.setRhtVal(cOCodeDTO);

		return cOFCodeMapper.insertCode(cOCodeDTO);
	}

	/**
	 * 코드명을 수정한다.
	 */
	public int updateCodeNm(COCodeDTO cOCodeDTO) throws Exception
	{
		return cOFCodeMapper.updateCodeNm(cOCodeDTO);
	}

	/**
	 * 코드 정보를 수정한다.
	 */
	public int updateCodeInf(COCodeDTO cOCodeDTO) throws Exception
	{
		int actCnt = cOFCodeMapper.updateCodeInf(cOCodeDTO);
		if (actCnt > 0 && 1 == cOCodeDTO.getDpth())
		{
			cOFCodeMapper.updateCdId(cOCodeDTO);
		}
		return actCnt;
	}

	/**
	 * 코드를 이동한다.
	 */
	public int updateCodePstn(COCodeDTO cOCodeDTO) throws Exception
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

		int seq 	= cOCodeDTO.getCdSeq();
		int pstn 	= cOCodeDTO.getPstn();
		int refSeq 	= cOCodeDTO.getRefSeq();
		int isCopy 	= cOCodeDTO.getIsCopy();

		//현재 트리
		COCodeDTO currCodeDto = cOFCodeMapper.selectCodeDtl(cOCodeDTO);

		//이동할 트리
		COCodeDTO tmpDto = new COCodeDTO();
		tmpDto.setCdSeq( refSeq );

		COCodeDTO refDto = cOFCodeMapper.selectCodeDtl(tmpDto);

		node_parntSeq 	= currCodeDto.getParntSeq();
		node_pstn 		= currCodeDto.getPstn();
		node_lftVal 	= currCodeDto.getLftVal();
		node_rhtVal 	= currCodeDto.getRhtVal();
		node_dpth 		= currCodeDto.getDpth();

		refNode_rhtVal 		= refDto.getRhtVal();
		refNode_dpth 		= refDto.getDpth();

		if (node_lftVal > 0)
		{
			COCodeDTO exitDto = new COCodeDTO();
			exitDto.setLftVal( node_lftVal );
			exitDto.setRhtVal( node_rhtVal );
			exitDto.setRefSeq( refSeq );
			Integer exitCdSeq = cOFCodeMapper.getMoveExits(exitDto);

			if (exitCdSeq != null)
			{
				return 0;
			}

			COCodeDTO nodeIdsDto = new COCodeDTO();
			nodeIdsDto.setLftVal(node_lftVal);
			nodeIdsDto.setRhtVal(node_rhtVal);

			List<COCodeDTO> moveNodeIds = cOFCodeMapper.getMoveNodeIds(nodeIdsDto);

			for (int q = 0; q < moveNodeIds.size(); q++)
			{
				COCodeDTO moveNodeIdDto = moveNodeIds.get(q);

				if ("".equals(node_ids))
				{
					node_ids = COStringUtil.nullConvert(moveNodeIdDto.getCdSeq());
				}
				else
				{
					node_ids = node_ids + "," + COStringUtil.nullConvert(moveNodeIdDto.getCdSeq());
				}
			}

			ndif = node_rhtVal - node_lftVal + 1;
		}

		if ("".equals(node_ids))
		{
			node_ids = "0";
		}

		COCodeDTO maxDto = new COCodeDTO();
		maxDto.setRefSeq( refSeq );
		int maxPosition = cOFCodeMapper.getMaxPosition(maxDto);

		if (pstn >= maxPosition)
		{
			pstn = maxPosition;
		}

		COCodeDTO sqlDto1 = null;
		if (node_lftVal > 0 && isCopy == 0)
		{
			sqlDto1 = new COCodeDTO();
			sqlDto1.setNodeParntSeq( node_parntSeq );
			sqlDto1.setNodePstn( node_pstn );
			sqlDto1.setNdif( ndif);
			sqlDto1.setNodeRhtVal( node_rhtVal );
			sqlDto1.setNodeLftVal( node_lftVal );
			sqlDto1.setNodeIds( node_ids.split(",") );
			sqlFlag1 = true;
		}
		
		COCodeDTO sqlDto2 = new COCodeDTO();
		sqlDto2.setRefSeq( refSeq );
		sqlDto2.setPstn( pstn );
		if (isCopy == 0)
		{
			sqlDto2.setNodeIds( node_ids.split(",") );
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

		COCodeDTO moveExitsDto = new COCodeDTO();
		moveExitsDto.setSelf( self );
		moveExitsDto.setPstn( pstn );
		moveExitsDto.setRefSeq( refSeq );

		Integer moveExits2 = cOFCodeMapper.getMoveExits2(moveExitsDto);

		if (moveExits2 != 0)
		{
			ref_ind = cOFCodeMapper.getRefInd(moveExitsDto);
		}

		if (node_lftVal > 0 && isCopy == 0 && node_lftVal < ref_ind)
		{
			ref_ind = ref_ind - ndif;
		}
		
		COCodeDTO sqlDto3 = new COCodeDTO();
		sqlDto3.setNdif( ndif );
		sqlDto3.setRefInd( ref_ind );

		if (isCopy == 0)
		{
			sqlDto3.setNodeIds( node_ids.split(",") );

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

		COCodeDTO sqlDto4 = null;
		
		if (node_lftVal > 0)
		{
			ldif = node_dpth - (refNode_dpth + 1);
			idif = node_lftVal - ref_ind;

			if (isCopy == 0)
			{
				sqlDto4 = new COCodeDTO();
				sqlDto4.setRefSeq( refSeq );
				sqlDto4.setPstn( pstn );
				sqlDto4.setCdSeq( seq );
				sqlDto4.setIdif( idif );
				sqlDto4.setLdif( ldif );
				sqlDto4.setNodeIds( node_ids.split(",") );
				sqlFlag6 = true;
			}
		}
		
		int actCnt = 0;
		
		if (sqlFlag1)
		{
			actCnt += cOFCodeMapper.setCodeMove1(sqlDto1);
			actCnt += cOFCodeMapper.setCodeMove2(sqlDto1);
			actCnt += cOFCodeMapper.setCodeMove3(sqlDto1);
		}
		
		if (sqlFlag2)
		{
			actCnt += cOFCodeMapper.setCodeMove4(sqlDto2);
		}
		
		if (sqlFlag3)
		{
			actCnt += cOFCodeMapper.setCodeMove5(sqlDto2);
		}
		
		if (sqlFlag4)
		{
			actCnt += cOFCodeMapper.setCodeMove6(sqlDto3);
			actCnt += cOFCodeMapper.setCodeMove7(sqlDto3);
		}
		
		if (sqlFlag5)
		{
			actCnt += cOFCodeMapper.setCodeMove8(sqlDto3);
			actCnt += cOFCodeMapper.setCodeMove9(sqlDto3);
		}
		
		if (sqlFlag6)
		{
			actCnt += cOFCodeMapper.setCodeMove10(sqlDto4);
			actCnt += cOFCodeMapper.setCodeMove11(sqlDto4);
		}

		return actCnt;
	}

	/**
	 * 코드를 삭제한다.
	 */
	public int deleteCode(COCodeDTO cOCodeDTO) throws Exception
	{
		COCodeDTO info = cOFCodeMapper.selectCodeDtl(cOCodeDTO);
		
		int actCnt = 0;

		if (info != null)
		{
			actCnt = cOFCodeMapper.deleteCode(info);
			
			cOFCodeMapper.setDeleteUpdateLftVal(info);
			cOFCodeMapper.setDeleteUpdateRhtVal(info);
			cOFCodeMapper.setDeleteUpdatePstn(info);
		}

		return actCnt;
	}

	/**
	 * 코드 목록 일괄처리를 위한 재귀함수
	 */
	public JSONArray getJsonData(List<COCodeDTO> jsonList, int startNum, int paramSeq) throws Exception
	{
		JSONArray tmpArray = new JSONArray();
		COCodeDTO cOCodeDTO = null;
		int i = 0, seq = 0, parntSeq = 0;
		for (i = startNum; i < jsonList.size(); i++)
		{
			cOCodeDTO 	= jsonList.get(i);
			seq 		= cOCodeDTO.getCdSeq();
			parntSeq 	= cOCodeDTO.getParntSeq();
			if (paramSeq == parntSeq)
			{
				JSONObject tmpObject = new JSONObject();
				tmpObject.put("data", XssPreventer.unescape(cOCodeDTO.getCdNm()));
				tmpObject.put("i", i);

				if (cOCodeDTO.getChildcnt() > 0)
				{
					tmpObject.put("state", "open");
					i = i + 1;
					JSONArray rtnArray = getJsonData(jsonList, i, seq);
					tmpObject.put("children", rtnArray);
					if (rtnArray.length() > 0)
					{
						i = Integer.parseInt(((JSONObject) rtnArray.get(rtnArray.length() - 1)).get("i").toString());
					}
					rtnArray = null;
				}

				JSONObject jsonAttr = new JSONObject();

		        jsonAttr.put("id", "node_" + cOCodeDTO.getCdSeq());
		        
		        int level = cOCodeDTO.getDpth();
		        
		        if (1 == level)
		        {
		        	jsonAttr.put("rel", "drive");
		        }
		        else
		        {
		        	jsonAttr.put("rel", "folder");
		        }
		        
		        jsonAttr.put("treeid", cOCodeDTO.getCdSeq());
		        jsonAttr.put("parent_treeid", cOCodeDTO.getParntSeq());
		        jsonAttr.put("level", level);
		        jsonAttr.put("status", cOCodeDTO.getUseYn());

		        tmpObject.put("attr", jsonAttr);
		        tmpArray.put(tmpObject);

		        tmpObject = null;
		        jsonAttr = null;
			}
		}

		return tmpArray;
	}
}