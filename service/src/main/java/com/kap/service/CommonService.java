package com.kap.service;

import com.kap.core.dto.MPPwdInitDto;

public interface CommonService {



    String selectSeq(String tableNm) throws Exception;

    int updatePwdInit(MPPwdInitDto mpPwdInitDto) throws Exception;

}