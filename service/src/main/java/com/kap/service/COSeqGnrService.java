package com.kap.service;

/**
 *
 * seq 값을 사용하는 공통 서비스를 정의하기 위한 서비스 인터페이스
 * @author 임서화
 * @since 2023.09.08
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *  2023.09.08  임서화          최초 생성
 *
 * </pre>
 */
public interface COSeqGnrService {
	/**
     * 시퀀스 값을 조회한다.
     */
    public int selectSeq(String tableNm) throws Exception;
}