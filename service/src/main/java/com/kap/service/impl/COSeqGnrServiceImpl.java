package com.kap.service.impl;

import com.kap.service.COSeqGnrService;
import com.kap.service.dao.COSeqGnrMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Class Name : COSeqGnrServiceImpl.java
 * @Description : 공통코드등 전체 업무에서 공용해서 사용해야 하는 서비스를 정의하기위한 서비스 구현 클래스
 * @Modification Information
 *
 *    수정일       수정자           수정내용
 *    -------    -------     -------------------
 *  2023.09.26	 임서화		      최초 생성
 *
 * @author 임서화
 * @since 2023.09.26
 * @version
 * @see
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class COSeqGnrServiceImpl implements COSeqGnrService {

	//DAO
	private final COSeqGnrMapper cOSeqGnrMapper;

	@Override
	public Integer selectSeq(String tableNm) throws Exception {
		Integer seqCnt = cOSeqGnrMapper.selectCountSeq(tableNm);
		int seq;
			if(seqCnt > 0 ){
				cOSeqGnrMapper.updateSeq(tableNm);
				seq = cOSeqGnrMapper.selectSeq(tableNm);
			}else{
				cOSeqGnrMapper.insertSeq(tableNm);
				seq = cOSeqGnrMapper.selectSeq(tableNm);
			}
		return seq;
	}
}