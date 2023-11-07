package com.kap.service.impl;

import com.kap.core.dto.COCodeDTO;
import com.kap.service.COCodeService;
import com.kap.service.dao.COCodeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Class Name : COCodeServiceImpl.java
 * @Description : 공통코드등 전체 업무에서 공용해서 사용해야 하는 서비스를 정의하기위한 서비스 구현 클래스
 * @Modification Information
 *
 *    수정일       수정자         수정내용
 *    -------        -------     -------------------
 *    2009. 3. 11.     이삼섭
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 11.
 * @version
 * @see
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class COCodeServiceImpl implements COCodeService {

	//DAO
	private final COCodeMapper cOCodeMapper;

    /**
     * 공통코드를 조회한다.
     */
    public HashMap<String, List<COCodeDTO>> getCmmCodeBindAll(ArrayList<String> cdIdList) throws Exception
    {
    	List<COCodeDTO> codeList = cOCodeMapper.getCdIdAllList(cdIdList);
		COCodeDTO COCodeDTOdtl = null;
		HashMap<String, List<COCodeDTO>> rtnMap = new HashMap<String, List<COCodeDTO>>();
    	if (codeList != null && codeList.size() > 0)
    	{
    		for (int i = 0, size = codeList.size(); i < size; i++)
        	{
				COCodeDTOdtl = (COCodeDTO) codeList.get(i);
        		if (rtnMap.get(COCodeDTOdtl.getCdId()) == null)
    			{
					rtnMap.put(COCodeDTOdtl.getCdId(), new ArrayList<COCodeDTO>());
    			}
				rtnMap.get(COCodeDTOdtl.getCdId()).add(COCodeDTOdtl);
    		}
    	}
    	return rtnMap;
    }

	/**
	 * 공통코드를 조회한다.(뎁스선택)
	 */
	public HashMap<String, List<COCodeDTO>> getCmmCodeBindAll(ArrayList<String> cdIdList , String depth) throws Exception
	{
		HashMap codeMap = new HashMap();
		codeMap.put("cdIdList",cdIdList);
		codeMap.put("depth",depth);
		List<COCodeDTO> codeList = cOCodeMapper.getCdIdOneList(codeMap);
		COCodeDTO COCodeDTOdtl = null;
		HashMap<String, List<COCodeDTO>> rtnMap = new HashMap<String, List<COCodeDTO>>();
		if (codeList != null && codeList.size() > 0)
		{
			for (int i = 0, size = codeList.size(); i < size; i++)
			{
				COCodeDTOdtl = (COCodeDTO) codeList.get(i);
				if (rtnMap.get(COCodeDTOdtl.getCdId()) == null)
				{
					rtnMap.put(COCodeDTOdtl.getCdId(), new ArrayList<COCodeDTO>());
				}
				rtnMap.get(COCodeDTOdtl.getCdId()).add(COCodeDTOdtl);
			}
		}
		return rtnMap;
	}


	/**
	 * 코드 목록(선택한 코드부터 자식코드까지)
	 */
	public List<COCodeDTO> getCdIdList(COCodeDTO cOCodeDTO) throws Exception
	{

		return cOCodeMapper.getCdIdList(cOCodeDTO);
	}


	/**
	 * 코드 목록(선택한 코드부터 부모까지 목록)
	 */
	public List<COCodeDTO> getCdIdPrntList(COCodeDTO cOCodeDTO) throws Exception
	{
		return cOCodeMapper.getCdIdPrntList(cOCodeDTO);
	}
}