package com.kap.service.dao;

import com.kap.core.dto.COUserCmpnDto;
import com.kap.core.dto.MPAAttctnDto;
import com.kap.core.dto.MPAInqrDto;
import com.kap.core.dto.MPAUserDto;
import com.kap.core.dto.eb.ebd.EBDSqCertiListDTO;
import com.kap.core.dto.eb.ebd.EBDSqCertiSearchDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 일반사용자 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: EBACouseMapper.java
 * @Description		: 일반사용자 관리를 위한 DAO
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
public interface COCommMapper {
    /**
     * 신청자, 부품사 정보 조회
     */
    public COUserCmpnDto getMemCmpnDtl(int memSeq);

    /**
     * 신청자 정보 변경
     */
    public int updateMem(COUserCmpnDto cOUserCmpnDto);
}
