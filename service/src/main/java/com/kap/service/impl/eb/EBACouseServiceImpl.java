package com.kap.service.impl.eb;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.eb.eba.EBACouseDTO;
import com.kap.core.dto.eb.ebb.EBBEpisdDTO;
import com.kap.service.COCodeService;
import com.kap.service.COFileService;
import com.kap.service.COSeqGnrService;
import com.kap.service.EBACouseService;
import com.kap.service.dao.COFileMapper;
import com.kap.service.dao.eb.EBACouseMapper;
import com.kap.service.dao.eb.EBBEpisdMapper;
import com.kap.service.dao.eb.EBBFrontEpisdMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
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
 *    2023.11.01		김학규				   최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EBACouseServiceImpl implements EBACouseService {

	//DAO
	private final EBACouseMapper eBACouseMapper;

	private final EBBEpisdMapper eBBEpisdMapper;

	private final EBBFrontEpisdMapper eBBFrontEpisdMapper;

	private final COSeqGnrService cOSeqGnrService;

	//파일 서비스
	private final COFileService cOFileService;
	// DAO
	private final COFileMapper cOFileMapper;

	public final COCodeService cOCodeService;

	//파일 업로드 위치
	@Value("${app.file.upload-path}")
	private String fileUploadPath;

	/* 교육과정마스터 시퀀스 */
	private final EgovIdGnrService edctnMstIdgen;


	/**
	 * 교육과정 목록을 조회한다
	 */
	public EBACouseDTO selectCouseList(EBACouseDTO eBACouseDTO) throws Exception
	{

		COPaginationUtil page = new COPaginationUtil();

		page.setCurrentPageNo(eBACouseDTO.getPageIndex());
		page.setRecordCountPerPage(eBACouseDTO.getListRowSize());

		page.setPageSize(eBACouseDTO.getPageRowSize());

		eBACouseDTO.setFirstIndex( page.getFirstRecordIndex() );
		eBACouseDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

		eBACouseDTO.setList( eBACouseMapper.selectCouseList(eBACouseDTO) );
		eBACouseDTO.setTotalCount( eBACouseMapper.selectCouseListCnt(eBACouseDTO) );

		return eBACouseDTO;
	}

	/**
	 * 교육과정 상세를 조회한다.
	 */
	public HashMap<String, Object> selectCouseDtl(EBACouseDTO eBACouseDTO) throws Exception
	{
		HashMap<String, Object> map = new HashMap();

		EBACouseDTO ebaDto = eBACouseMapper.selectCouseDtl(eBACouseDTO);

		map.put("rtnData", ebaDto);

		//학습대상목록을 호출한다. 관리자는 모든 공통코드가 출력되야하고, 사용자는 선택한것만 나와야해서 출력해오는 데이터가 상이함
		if(eBACouseDTO.getSiteGubun().equals("admin")){
			map.put("rtnTrgtData", eBACouseMapper.selectCouseTrgtList(eBACouseDTO));
		}else{
			map.put("rtnTrgtData", eBBFrontEpisdMapper.selectCouseTrgtList(eBACouseDTO));
		}




		return map;
	}

	/**
	 * 교육과정연계 상세를 조회한다.
	 */
	public List<EBACouseDTO> selectEdctnRelList(EBACouseDTO eBACouseDTO) throws Exception
	{

		List<EBACouseDTO> ebaList = null;

		ebaList = eBACouseMapper.selectEdctnRelList(eBACouseDTO);

		return ebaList;
	}




	/**
	 * 교육과정을 등록한다.
	 */
	public int insertCouse(EBACouseDTO eBACouseDTO) throws Exception
	{

		int respCnt = 0;

		//파일 처리
		HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(eBACouseDTO.getFileList());

		eBACouseDTO.setThnlFileSeq(fileSeqMap.get("thnlFileSeq"));


		int firstEdctnMstIdgen = edctnMstIdgen.getNextIntegerId();

		eBACouseDTO.setEdctnSeq(firstEdctnMstIdgen);


		//교육과정 등록
		respCnt = eBACouseMapper.insertCouse(eBACouseDTO);

		//교육과정대상 상세 등록 시작
		String temp = eBACouseDTO.getTargetCd();

		List<EBACouseDTO> tempList = new ArrayList<>();

		if(temp != null &  temp != ""){
			String[] tempArray =temp.split(",");
			for(String a : tempArray){
				EBACouseDTO targetDto = new EBACouseDTO();
				targetDto.setEdctnSeq(eBACouseDTO.getEdctnSeq());
				targetDto.setTargetCd(a);
				targetDto.setEtcNm(null);
				tempList.add(targetDto);
			}
		}

		//기타부분 입력
		if(eBACouseDTO.getEtcNm() !=""){
			EBACouseDTO targetDto = new EBACouseDTO();
			targetDto.setEdctnSeq(eBACouseDTO.getEdctnSeq());
			targetDto.setTargetCd("ED_TARGET05001");
			targetDto.setEtcNm(eBACouseDTO.getEtcNm());
			tempList.add(targetDto);
		}

		//교육과정대상 등록
		eBACouseDTO.setTrgtDtlList(tempList);
		eBACouseMapper.insertCouseTrgt(eBACouseDTO);
		//교육과정대상 상세 등록 끝




		//연계학습 등록
		setEdctnRel(eBACouseDTO);



		return respCnt;
	}

	/**
	 * 교육과정를 수정한다.
	 */
	public int updateCouse(EBACouseDTO eBACouseDTO) throws Exception
	{
		int respCnt = 0;

		//파일 처리
		HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(eBACouseDTO.getFileList());

		eBACouseDTO.setThnlFileSeq(fileSeqMap.get("thnlFileSeq"));

		respCnt = eBACouseMapper.updateCouse(eBACouseDTO);

		eBACouseMapper.deleteCouseTrgt(eBACouseDTO);



		//교육과정대상 상세 등록 시작
		String temp = eBACouseDTO.getTargetCd();

		List<EBACouseDTO> tempList = new ArrayList<>();

		if(temp != null &  temp != ""){
			String[] tempArray =temp.split(",");
			for(String a : tempArray){
				EBACouseDTO targetDto = new EBACouseDTO();
				targetDto.setEdctnSeq(eBACouseDTO.getEdctnSeq());
				targetDto.setTargetCd(a);
				targetDto.setEtcNm(null);
				tempList.add(targetDto);
			}
		}

		//기타부분 입력
		if(eBACouseDTO.getEtcNm() !=""){
			EBACouseDTO targetDto = new EBACouseDTO();
			targetDto.setEdctnSeq(eBACouseDTO.getEdctnSeq());
			targetDto.setTargetCd("ED_TARGET05001");
			targetDto.setEtcNm(eBACouseDTO.getEtcNm());
			tempList.add(targetDto);
		}

		eBACouseDTO.setTrgtDtlList(tempList);
		eBACouseMapper.insertCouseTrgt(eBACouseDTO);
		//교육과정대상 상세 등록 끝

		//연계학습 등록
		setEdctnRel(eBACouseDTO);


		return respCnt;
	}

	/**
	 *  교육과정연계 상세를 세팅한다.
	 */
	private void setEdctnRel(EBACouseDTO eBACouseDTO){

		try{
			//삭제후 등록
			eBACouseMapper.deleteEdctnRel(eBACouseDTO);

			ArrayList<String> cdDtlList = new ArrayList<String>();

			cdDtlList.add("EDCTN_REL");//교욱과정 연계(선수, 후속, 필수)
			HashMap<String ,List<COCodeDTO>> tempObj  = cOCodeService.getCmmCodeBindAll(cdDtlList, "2");

			List<COCodeDTO> tmpList = tempObj.get("EDCTN_REL");

			//선수
			if(eBACouseDTO.getEdctnRel1() != null && eBACouseDTO.getEdctnRel1().size()>0){
				for(int edCtnRel : eBACouseDTO.getEdctnRel1()){
					eBACouseDTO.setCnnctCd(tmpList.get(0).getCd());
					eBACouseDTO.setCnnctEdctnSeq(edCtnRel);
					eBACouseMapper.insertEdctnRel(eBACouseDTO);
				}
			}

			//후속
			if(eBACouseDTO.getEdctnRel2() != null && eBACouseDTO.getEdctnRel2().size()>0){
				for(int edCtnRel : eBACouseDTO.getEdctnRel2()){

					eBACouseDTO.setCnnctCd(tmpList.get(1).getCd());
					eBACouseDTO.setCnnctEdctnSeq(edCtnRel);
					eBACouseMapper.insertEdctnRel(eBACouseDTO);
				}
			}

			//필수
			if(eBACouseDTO.getEdctnRel3() != null && eBACouseDTO.getEdctnRel3().size()>0){
				for(int edCtnRel : eBACouseDTO.getEdctnRel3()){
					eBACouseDTO.setCnnctCd(tmpList.get(2).getCd());
					eBACouseDTO.setCnnctEdctnSeq(edCtnRel);
					eBACouseMapper.insertEdctnRel(eBACouseDTO);
				}
			}
		}catch (Exception e){

		}


	}

	/**
	 * 현재 등록된 교육과정에 종속된 교육차수 체크
	 */
	@Transactional
	public int selectEpisdListChk(EBACouseDTO eBACouseDTO) throws Exception
	{
		int actCnt = eBACouseMapper.selectEpisdListChk(eBACouseDTO);

		return actCnt;
	}


	/**
	 * 교육과정을 삭제한다.
	 */
	@Transactional
	public int deleteCouse(EBACouseDTO eBACouseDTO) throws Exception
	{
		int actCnt = 0;


		eBACouseMapper.deleteCouseTrgt(eBACouseDTO);

		actCnt = eBACouseMapper.deleteCouseDtl(eBACouseDTO);

		return actCnt;
	}















	/**
	 * 전달받은 문자열과 리스트 형태의 문자열중 일치하는게 있으면 반환
	 *
	 * @return String
	 */
	public static String forEachChk(String str, List<EBACouseDTO> codeList) {
		String rtnStr = "N";

        if (codeList != null && codeList.size() > 0) {
            for (EBACouseDTO a : codeList) {

                if (str.contains(a.getTargetCd())) {
                    rtnStr = "Y";
                    break;
                } else {
                    rtnStr = "N";
                }
            }
        }

		return rtnStr;
	}


}