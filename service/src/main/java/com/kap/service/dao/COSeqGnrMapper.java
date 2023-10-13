package com.kap.service.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * <pre>
 * 로그인/로그아웃을 위한 DAO
 * </pre>
 *
 * @ClassName		: COSeqGnrMapper.java
 * @Description		: 로그인/로그아웃을 위한 DAO
 * @author 임서화
 * @since 2023.09.26
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				   description
 *   ===========    ==============    =============================
 *   2023.09.26			임서화			 		최초생성
 * </pre>
 */
@Mapper
public interface COSeqGnrMapper {

    // 해당 테이블 SEQ 값 조회
    public int selectSeq(String tableNm) throws Exception;

    // 해당 테이블 SEQ 등록
    public int insertSeq(String tableNm) throws Exception;

    // 해당 테이블 SEQ 값 업데이트
    public int updateSeq(String tableNm) throws Exception;

}
