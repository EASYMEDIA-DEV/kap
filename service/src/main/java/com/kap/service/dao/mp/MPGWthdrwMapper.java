package com.kap.service.dao.mp;

import com.kap.core.dto.mp.mpg.MPGWthdrwDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 탈퇴사용자 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: EBACouseMapper.java
 * @Description		: 탈퇴사용자 관리를 위한 DAO
 * @author 양현우
 * @since 2023.11.28
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.28		양현우				   최초 생성
 * </pre>
 */


@Mapper
public interface MPGWthdrwMapper {

    List<MPGWthdrwDto> selectWthdrwList(MPGWthdrwDto mpgWthdrwDto);

    int selectWthdrwListCnt(MPGWthdrwDto mpgWthdrwDto);



}
