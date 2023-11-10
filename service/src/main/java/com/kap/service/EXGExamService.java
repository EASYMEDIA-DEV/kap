package com.kap.service;

import com.kap.core.dto.ex.exg.EXGExamMstDTO;
import com.kap.core.dto.ex.exg.EXGExamMstInsertDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 교육 시험 서비스
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.09  김학규         최초 생성
 * </pre>
 */
public interface EXGExamService {
    /**
     * 리스트 조회
    */
    public EXGExamMstDTO selectExamList(EXGExamMstDTO eXGExamMstDTO) throws Exception;

    /**
     * 교육회차마스터 매핑 여부
     */
    public int getExamEdctnEpisdCnt(EXGExamMstDTO eXGExamMstDTO) throws Exception;

    /**
     * 리스트 삭제
     */
    public int deleteExamList(EXGExamMstDTO eXGExamMstDTO) throws Exception;

    /**
     * 리스트 등록
     */
    public int insertExamList(EXGExamMstInsertDTO eXGExamMstInsertDTO, HttpServletRequest request) throws Exception;
}
