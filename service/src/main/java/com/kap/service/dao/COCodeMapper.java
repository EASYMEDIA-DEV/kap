package com.kap.service.dao;

import com.kap.core.dto.COCodeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class Name : COCodeMapper.java
 * @Description : 공통코드등 전체 업무에서 공용해서 사용해야 하는 서비스를 정의하기위한 데이터 접근 클래스
 * @Modification Information
 *
 *    수정일       수정자         수정내용
 *    -------        -------     -------------------
 *    2009. 3. 11.     이삼섭
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 11.
 * @version
 * @see
 *
 */
@Mapper
public interface COCodeMapper {
    /**
     * 공통코드를 조회한다.
     */
    public List<COCodeDTO> getCdIdAllList(ArrayList<String> cdList) throws Exception;
}