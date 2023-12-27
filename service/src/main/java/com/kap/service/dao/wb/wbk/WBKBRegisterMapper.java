package com.kap.service.dao.wb.wbk;

import com.kap.core.dto.wb.WBCompanyDetailMstDTO;
import com.kap.core.dto.wb.WBRoundOptnMstDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterSearchDTO;
import com.kap.core.dto.wb.wbk.*;

import java.util.List;

/**
 * <pre>
 * 스마트공장 > 신청부품사 관리 DAO
 * </pre>
 *
 * @ClassName		: WBFBRegisterCompanyMapper.java
 * @Description		: 스마트공장 > 신청부품사 관리 DAO
 * @author 김동현
 * @since 2023.11.16
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *	     since		  author	            description
 *    ==========    ==========    ==============================
 * 	  2023.11.16	  김동현	             최초 생성
 * </pre>
 */
public interface WBKBRegisterMapper {

    /**
     *  List Page
     *  신청부품사 목록 List Get
     */
    public List<WBKBRegisterSearchDTO> getRegisterList(WBKBRegisterSearchDTO wBKBRegisterSearchDTO);

    /**
     *  List Page
     *  신청팀 목록 Sub List Get
     */
    public WBKBRegisterSearchDTO getRegisterCompanySubList(WBKBRegisterSearchDTO wBKBRegisterSearchDTO);

    /**
     *  List Page
     *  신청팀 1차 심사 결과 목록 get
     */
    public WBKBRegisterSearchDTO getRegisterFirstResultList(WBKBRegisterSearchDTO wBKBRegisterSearchDTO);

    /**
     *  List Page
     *  신청팀 최종 심사 결과 목록 get
     */
    public WBKBRegisterSearchDTO getRegisterFinalResultList(WBKBRegisterSearchDTO wBKBRegisterSearchDTO);


    /**
     * 상세 조회
     */
    public WBKBRegisterDTO selectFutureCarRegDtl(WBKBRegisterSearchDTO wBKBRegisterSearchDTO);

    /**
     *
     * @param
     * @return
     */
    public List<WBKBRegPartDTO> selectFutureCarPartDtl(WBKBRegisterDTO wBKBRegisterDTO);

    /**
     *
     * @param
     * @return
     */
    public List<WBKBRsumeDTO> selectFutureCarRsumeDtl(WBKBRegisterDTO wBKBRegisterDTO);
 /**
     *
     * @param
     * @return
     */
    public List<WBKBFileDtlDTO> selectFutureCarFileDtl(WBKBRegisterDTO wBKBRegisterDTO);

    /**
     *  List Page
     *  신청부품사 목록 Count
     */
    public int getRegisterCompanyCount(WBKBRegisterSearchDTO wBKBRegisterSearchDTO);

    /**
     *  Write Page
     *  사업회차 연도 기준 회차 검색
     */
    public List<String> getOptEpisdList(WBKBRegisterSearchDTO wBKBRegisterSearchDTO);
    
    /**
     *  Write Page
     *  사업회차 연도 기준 회차 순번 검색
     */
    public List<String> getEpisdSeq(WBKBRegisterSearchDTO wBKBRegisterSearchDTO);



    /**
     *  Write Page
     *  사업회차 년도/회차 기준 옵션 검색
     */
    public List<WBRoundOptnMstDTO> getOptnList(WBFBRegisterSearchDTO wBFBRegisterSearchDTO);

    /**
     *  Write Page
     *  신청부품사 등록 - 신청 마스터
     */
    public int putAppctnMst(WBKBRegisterDTO wBKBRegisterDTO);

    /**
     *  Write Page
     *  미래차공보전 등록 - 신청 마스터 update
     */
    public int updAppctnMst(WBKBRegisterDTO wBKBRegisterDTO);

    /**
     *  Write Page
     *  참가 인원 seq
     */
    public List<String> getOptYearList(WBKBRegisterSearchDTO wBKBRegisterSearchDTO);

    /**
     *  Write Page
     *  신청부품사 등록 - 신청 파일 상세
     */
    public int putAppctnFileDtl(WBKBRegisterDTO wBKBRegisterDTO);

    /**
     *  Write Page
     *  신청부품사 등록 - 신청 파일 삭제
     */
    public int delFile(WBKBRegisterDTO wBKBRegisterDTO);

    /**
     *  Write Page
     *  신청부품사 등록 - 신청 파일 상세
     */
    public int delDtlFile(WBKBRsumeDTO wBKBRsumeDTO);
/**
     *  Write Page
     *  신청부품사 등록 - 신청 파일 상세
     */
    public int putDtlFileDtl(WBKBRsumeDTO wBKBRsumeDTO);



    /**
     *  Write Page
     *  신청부품사 등록 - 신청 상세
     */
    public int putAppctnRsumeDtl(WBKBRegisterDTO wBKBRegisterDTO);

    /**
     *  Write Page
     *  신청 상세  등록 - 미래차 공모전
     */
    public int putAppctnRsumeTaskDtl(WBKBRegisterDTO wBKBRegisterDTO);

    /**
     *  Write Page
     *  신청 상세 업데이트 - 미래차 공모전
     */
    public int updAppctnRsumeTaskDtl(WBKBRegisterDTO wBKBRegisterDTO);

    /**
     *  Write Page
     *  참여 인원 등록 - 미래차 공모전
     */
    public int putPartDtl(WBKBRegPartDTO wBKBRegPartDTO);

    /**
     *  Write Page
     *  참여 인원 업데이트 - 미래차 공모전
     */
    public int updPartDtl(WBKBRegPartDTO wBKBRegPartDTO);

    /**
     *  Write Page
     *  참여 인원 업데이트 - 미래차 공모전
     */
    public int delPartDtl(WBKBRegisterDTO wBKBRegisterDTO);

    /**
     *  Write Page
     *  진행 상태 업데이트 - 미래차 공모전
     */
    public int updRsumeStep(WBKBRsumeDTO wBKBRsumeDTO);
    /**
     *  Write Page
     *  진행 상태 삭제 - 미래차 공모전
     */
    public int delRsumeNestStep(WBKBRsumeDTO wBKBRsumeDTO);
    /**
     *  Write Page
     *  진행 상태 스텝 추가 - 미래차 공모전
     */
    public int insertRsumeStep(WBKBRsumeDTO wBKBRsumeDTO);
    
    /**
     *  Write Page
     *  회사 마스터 Update
     */
    public int updCoMemMst(WBKBRegisterDTO wBKBRegisterDTO);

    /**
     *  Write Page
     *  회사 마스터 Update
     */
    public int updCmpnCbsnMst(WBFBRegisterDTO wBFBRegisterDTO);

    /**
     *  Write Page
     *  회사업종 상세 Update
     */
    public int updCmpnCbsnDtl(WBCompanyDetailMstDTO wBCompanyDetailMstDTO);

    /**
     *  Write Page
     *  회사업종 상세 Insert
     */
    public int insCmpnCbsnDtl(WBCompanyDetailMstDTO wBCompanyDetailMstDTO);


    /**
     *  Write Page
     *  신청 삭제 가능 확인
     */
    public int checkAppctnRsumeDtl(WBKBRegisterDTO wBKBRegisterDTO);

    /**
     *  Write Page
     *  신청 삭제 RsumeSeq 체크
     */
    public int checkAppctnRsumeSeq(WBKBRegisterDTO wBKBRegisterDTO);

    /**
     *  Write Page
     *  신청 삭제
     */
    public int deleteCarRegAppctnMst(WBKBRegisterDTO wBKBRegisterDTO);
    public int deleteCarRegAppctnRsumeDtl(WBKBRegisterDTO wBKBRegisterDTO);
    public int deleteCarRegAppctnFileDtl(WBKBRegisterDTO wBKBRegisterDTO);
    public int deleteCarRegAppctnDtl(WBKBRegisterDTO wBKBRegisterDTO);
    public int deleteCarRegAppctnTmmbrDtl(WBKBRegisterDTO wBKBRegisterDTO);

}
