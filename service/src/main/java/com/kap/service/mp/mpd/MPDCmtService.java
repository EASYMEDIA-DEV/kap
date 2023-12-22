package com.kap.service.mp.mpd;

import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.mp.mpd.MPDKenDto;
import com.kap.core.dto.mp.mpf.MPFFileDto;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <pre>
 * 위원 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: MPDCmtService.java
 * @Description		: 위원 관리를 위한 Service
 * @author 양현우
 * @since 2023.11.21
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.21		양현우				   최초 생성
 * </pre>
 */
public interface MPDCmtService {


     /**
      * 위원등록
      * @param mpaUserDto
      * @return
      * @throws Exception
      */
     int insertCmt(MPAUserDto mpaUserDto) throws Exception;

     /**
      * 위원수정
      * @param mpaUserDto
      * @return
      * @throws Exception
      */
     int updateCmt(MPAUserDto mpaUserDto) throws Exception;

     /**
      * 위원 리스트 조회
      * @param mpdKenDto
      * @return
      * @throws Exception
      */
     MPDKenDto selectKenList(MPDKenDto mpdKenDto) throws Exception;

     /**
      * 엑셀 다운
      * @param mpdKenDto
      * @param response
      * @throws Exception
      */
     void excelDownload(MPDKenDto mpdKenDto, HttpServletResponse response) throws Exception;

     /**
      * 근태 월 조회
      * @param mpdKenDto
      * @return
      * @throws Exception
      */
     MPDKenDto selectKenMonthList(MPDKenDto mpdKenDto) throws Exception;

     /**
      * 근태 월 테이블 조회
      * @param mpdKenDto
      * @return
      * @throws Exception
      */
     MPDKenDto selectKenMonthTableList(MPDKenDto mpdKenDto) throws Exception;

     /**
      * 월 근태 갯수 조회
      * @param mpdKenDto
      * @return
      * @throws Exception
      */
     MPDKenDto selectKenMonthCnt(MPDKenDto mpdKenDto) throws Exception;

     /**
      * 위원 지도부품사 조회
      * @param mpdKenDto
      * @return
      * @throws Exception
      */
     MPDKenDto selectKenCmpnList(MPDKenDto mpdKenDto) throws Exception;

     /**
      * 위원 근태 insert
      */
     void insertAtend(MPDKenDto mpdKenDto) throws Exception;

     /**
      * 위원 근태 조회
      */
     MPDKenDto selectKenCmpnDtl(MPDKenDto mpdKenDto) throws Exception;


     MPFFileDto selectKenCmpnKickImage(MPFFileDto mpfFileDto) throws Exception;

     MPFFileDto selectKenCmpnLvlImage(MPFFileDto mpfFileDto) throws Exception;


     void updateCnstgRsumeMst(MPFFileDto mpfFileDto) throws Exception;




}