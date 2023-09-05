package com.kap.service.dao;

import com.kap.core.dto.COSampleDTO;
import com.kap.core.dto.EmfMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
/**
 * <pre>
 * 샘플 Mapper
 * </pre>
 *
 * @ClassName		: COSampleMapper.java
 * @Description		: 샘플 Mapper
 * @author 박주석
 * @since 2022.01.10
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2022.01.10	  박주석	             최초 생성
 * </pre>
 */
@Mapper
public interface  COSampleMapper {
    /**
     * 샘플 조회
   */
    public List<COSampleDTO> selectSmplList(COSampleDTO cOSampleDTO);
    /**
     * 샘플 전체 갯수
     */
    public int getSmplListTotCnt(COSampleDTO cOSampleDTO);
    /**
     * 샘플 등록
     */
    public int insertSmpl(COSampleDTO cOSampleDTO) throws Exception;
    /**
     * 샘플 상세
     */
    public COSampleDTO selectSmplDtl(COSampleDTO cOSampleDTO) throws Exception;
    /**
     * 샘플 수정
     */
    public int updateSmpl(COSampleDTO cOSampleDTO) throws Exception;
    /**
     * 샘플 삭제
     */
    public int deleteSmpl(COSampleDTO cOSampleDTO) throws Exception;

    /**
     * IR DATA 조회
     */
    public List<EmfMap> getIrList(COSampleDTO cOSampleDTO);

    /**
     * IR 삭제
     */
    public int deleteIr(COSampleDTO cOSampleDTO) throws Exception;
    /**
     * IR 등록
     */
    public int insertIr(EmfMap rtnMap) throws Exception;
    /**
     * IR 등록
     */
    public int insertTag(EmfMap rtnMap) throws Exception;
}
