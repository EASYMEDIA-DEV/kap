package com.kap.service.dao;

import com.kap.core.dto.SMBMainVslDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SMBMnVslMapper {

    /**
     * 메인 비주얼 목록 조회
     */
    public List<SMBMainVslDTO> selectMnVslList(SMBMainVslDTO pSMBMainVslDTO) throws Exception;

    /**
     * 메인 비주얼 상세 조회
     */
    public SMBMainVslDTO selectMnVslDtl(SMBMainVslDTO pSMBMainVslDTO) throws Exception;

    /**
     * 메인 비주얼 등록
     */
    public int insertMnVsl(SMBMainVslDTO pSMBMainVslDTO) throws Exception;

    /**
     * 메인 비주얼 수정
     */
    public int updateMnVsl(SMBMainVslDTO pSMBMainVslDTO) throws Exception;

    /**
     * 메인 비주얼 삭제
     */
    public int deleteMnVsl(SMBMainVslDTO pSMBMainVslDTO) throws Exception;

    /**
     * 메인 비주얼 사용 여부 수정
     */
    public int updateUseYn(SMBMainVslDTO pSMBMainVslDTO) throws Exception;

    /**
     * 메인 비주얼 정렬 수정
     */
    public int updateOrder(SMBMainVslDTO pSMBMainVslDTO) throws Exception;


    /**
     * 노출=Y 메인 비주얼 개수
     */
    public int selectMnVslCnt(SMBMainVslDTO pSMBMainVslDTO) throws Exception;

    /**
     * 기존에 노출 중인 메인 비주얼
     */
    public SMBMainVslDTO selectNowMnVsl(SMBMainVslDTO pSMBMainVslDTO) throws Exception;

}
