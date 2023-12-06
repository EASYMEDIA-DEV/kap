package com.kap.service.dao.cm;

import com.kap.core.dto.MPPwdInitDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * <pre>
 * 공통 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: CommonMapper.java
 * @Description		: 공통 관리를 위한 DAO
 * @author 양현우
 * @since 2023.11.09
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.09		양현우				   최초 생성
 * </pre>
 */


@Mapper
public interface CommonMapper {


    String selectSeqNum(String tableNm);

    void updateSeq(String tableNm);

    int updatePwdInit(MPPwdInitDto mpPwdInitDto);

    MPPwdInitDto selectMemDtl(MPPwdInitDto mpPwdInitDto);


}
