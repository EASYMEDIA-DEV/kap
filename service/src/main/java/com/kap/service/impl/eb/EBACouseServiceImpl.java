package com.kap.service.impl.eb;

import com.kap.common.utility.COPaginationUtil;
import com.kap.common.utility.seed.COSeedCipherUtil;
import com.kap.core.dto.*;
import com.kap.service.*;
import com.kap.service.dao.COAAdmMapper;
import com.kap.service.dao.COLgnMapper;
import com.kap.service.dao.eb.EBACouseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 관리자 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: COCAdmServiceImpl.java
 * @Description		: 관리자 관리를 위한 ServiceImpl
 * @author 신혜정
 * @since 2022.04.14
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2022.04.14		신혜정				   최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EBACouseServiceImpl implements EBACouseService {

	//DAO
	private final EBACouseMapper eBACouseMapper;

	/**
	 * 교육과정 목록을 조회한다
	 */
	public EBACouseDTO selectCouseList(EBACouseDTO eBACouseDTO) throws Exception
	{

		/*COPaginationUtil page = new COPaginationUtil();

		page.setCurrentPageNo(eBACouseDTO.getPageIndex());
		page.setRecordCountPerPage(eBACouseDTO.getListRowSize());

		page.setPageSize(eBACouseDTO.getPageRowSize());

		eBACouseDTO.setFirstIndex( page.getFirstRecordIndex() );
		eBACouseDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

		eBACouseDTO.setList( eBACouseMapper.selectCouseList(eBACouseDTO) );
		eBACouseDTO.setTotalCount( eBACouseMapper.selectCouseListCnt(eBACouseDTO) );*/

		return eBACouseDTO;
	}

	/**
	 * 교육과정 상세를 조회한다.
	 */
	public EBACouseDTO selectCouseDtl(EBACouseDTO eBACouseDTO) throws Exception
	{

		//EBACouseDTO tempDto = eBACouseMapper.selectCouseDtl(eBACouseDTO);


		return eBACouseDTO;
	}


	/**
	 * 교육과정을 등록한다.
	 */
	public int insertCouse(EBACouseDTO eBACouseDTO) throws Exception
	{

		int respCnt = 0;

		//respCnt = eBACouseMapper.insertCouse(eBACouseDTO);

		return respCnt;
	}

	/**
	 * 교육과정를 수정한다.
	 */
	public int updateCouse(EBACouseDTO eBACouseDTO) throws Exception
	{
		int respCnt = 0;

		//respCnt = eBACouseMapper.updateCouse(eBACouseDTO);

		return respCnt;
	}

	/**
	 * 교육과정을 삭제한다.
	 */
	@Transactional
	public int deleteCouse(EBACouseDTO eBACouseDTO) throws Exception
	{
		int actCnt = 0;
		//actCnt = eBACouseMapper.deleteCouse(eBACouseDTO);

		return actCnt;
	}

	/**
	 * 교육과정을 복사한다.
	 */
	public int copyCouse(EBACouseDTO eBACouseDTO){
		int actCnt = 0;

		try{


			//복사하려는 교육과정 조회후
			//EBACouseDTO tempDto = eBACouseMapper.selectCouseDtl(eBACouseDTO);



			//dto에 복사후

			//EBACouseDTO newDto = tempDto;

			//newDto.setCouseNm(tempDto.getCouseNm()+" - 복사본");

			//신규로 insert
			//eBACouseMapper.insertCouse(newDto);


		}catch (Exception e){

		}

		return actCnt;
	}


}