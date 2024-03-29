package com.kap.service.dao.im;

import com.kap.core.dto.im.ima.IMAQaDTO;
import com.kap.core.dto.im.ima.IMAQaPicDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 1:1 문의 Mapper
 * </pre>
 *
 * @ClassName		: IMAQaMapper.java
 * @Description		: 1:1 문의 Mapper
 * @author 장두석
 * @since 2023.11.01
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2023.11.01	  장두석	             최초 생성
 * </pre>
 */
@Mapper
public interface IMAQaMapper {
    /**
     * 1:1 문의 조회
   */
    public List<IMAQaDTO> selectQaList(IMAQaDTO iMAQaDTO) throws Exception;
    /**
     * 1:1 문의 전체 개수
     */
    public int getQaListTotCnt(IMAQaDTO iMAQaDTO) throws Exception;
    /**
     * 1:1 문의 담당자 목록 조회
     */
    public List<IMAQaPicDTO> selectQaPicList(IMAQaPicDTO pIMAQaPicDTO) throws Exception;
    /**
     * 1:1 문의 담당자 전체 개수
     */
    public int getQaPicListTotCnt(IMAQaPicDTO pIMAQaPicDTO) throws Exception;
    /**
     * 1:1 문의 등록
     */
    public int insertQaRply(IMAQaDTO iMAQaDTO) throws Exception;
    /**
     * 1:1 문의 진행상태 수정
     */
    public int updateQaRsume(IMAQaDTO iMAQaDTO) throws Exception;
    /**
     * 1:1 문의 상세
     */
    public IMAQaDTO selectQaDtl(IMAQaDTO iMAQaDTO) throws Exception;
    /**
     * 1:1 문의 담당자 등록
     */
    public int insertQaPic(IMAQaPicDTO pIMAQaPicDTO) throws Exception;
    /**
     * 1:1 문의 담당자 수정
     */
    public int updateQaPic(IMAQaPicDTO pIMAQaPicDTO) throws Exception;
    /**
     * 1:1 문의 담당자 삭제
     */
    public int deleteQaPic(IMAQaPicDTO pIMAQaPicDTO) throws Exception;
    /**
     * 1:1 문의 유형별 담당자 등록 개수 조회
     */
    public int getQaPicCnt(IMAQaPicDTO pIMAQaPicDTO) throws Exception;
    /**
     * 1:1 문의 담당자 상세
     */
    public IMAQaPicDTO selectQaPicDtl(IMAQaPicDTO pIMAQaPicDTO) throws Exception;


    /**
     * 사용자 1:1 문의 선택 항목 담당자 목록 조회
     */
    public List<IMAQaPicDTO> selectQaPicCtgryList(IMAQaDTO pIMAQaDTO) throws Exception;

    /**
     * 1:1 문의 등록
     */
    public int insertQa(IMAQaDTO pIMAQaDTO) throws Exception;

    /**
     * 1:1 문의 유형 등록
     */
    public int insertQaCtgry(IMAQaDTO pIMAQaDTO) throws Exception;

}
