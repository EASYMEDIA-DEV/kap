package com.kap.service.dao.mp;

import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.mp.mpd.MPDKenDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 위원 관리를 위한 DAO
 * </pre>
 *
 * @author 양현우
 * @version 1.0
 * @ClassName        : MPDCmtMapper.java
 * @Description        : 위원 관리를 위한 mapper
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.21		양현우				   최초 생성
 * </pre>
 * @see
 * @since 2023.11.21
 */


@Mapper
public interface MPDCmtMapper {

    int insertCmt(MPAUserDto mpaUserDto);

    int updateCmt(MPAUserDto mpaUserDto);

    List<MPDKenDto> selectKenList(MPDKenDto mpdKenDto);

    int selectKenListCnt(MPDKenDto mpdKenDto);

    List<MPDKenDto> selectKenMonthList(MPDKenDto mpdKenDto);

    int selectKenMonthListCnt(MPDKenDto mpdKenDto);

    MPDKenDto selectKenMonthCnt(MPDKenDto mpdKenDto);

    List<MPDKenDto> selectKenCmpnList(MPDKenDto mpdKenDto);

    MPDKenDto selectKenCmpnDtl(MPDKenDto mpdKenDto);

    void insertAtend(MPDKenDto mpdKenDto);


}