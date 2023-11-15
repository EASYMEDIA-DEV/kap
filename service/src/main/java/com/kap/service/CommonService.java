package com.kap.service;

import com.kap.core.dto.MPPwdInitDto;
import com.kap.core.dto.TaxInfoResDto;

public interface CommonService {



    String selectSeq(String tableNm) throws Exception;

    int updatePwdInit(MPPwdInitDto mpPwdInitDto) throws Exception;

}