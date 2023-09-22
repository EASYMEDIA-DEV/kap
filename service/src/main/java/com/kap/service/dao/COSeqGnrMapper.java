package com.kap.service.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface COSeqGnrMapper {

    // 해당 테이블 SEQ 값 조회
    public int getSeq(String seqCode) throws Exception;

    // 해당 테이블 SEQ 등록
    public int insertSeq(String seqCode) throws Exception;

    // 해당 테이블 SEQ 값 업데이트
    public int updateSeq(String seqCode) throws Exception;

}
