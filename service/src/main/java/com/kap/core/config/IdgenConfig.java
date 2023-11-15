package com.kap.core.config;

import com.kap.service.IdGnrBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.impl.EgovSequenceIdGnrServiceImpl;
import org.egovframe.rte.fdl.idgnr.impl.EgovTableIdGnrServiceImpl;
import org.egovframe.rte.fdl.idgnr.impl.strategy.EgovIdGnrStrategyImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class IdgenConfig {

    @Autowired
    @Qualifier("dataSource")
    DataSource dataSource;

    /**
     * 첨부파일 ID Generation  Strategy Config
     * @return
     */
    private EgovIdGnrStrategyImpl fileStrategy() {
        EgovIdGnrStrategyImpl egovIdGnrStrategyImpl = new EgovIdGnrStrategyImpl();
        egovIdGnrStrategyImpl.setPrefix("FILE_");
        egovIdGnrStrategyImpl.setCipers(15);
        egovIdGnrStrategyImpl.setFillChar('0');
        return egovIdGnrStrategyImpl;
    }

    /**
     * 첨부파일 ID Generation  Config
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl egovFileIdGnrService() {
        EgovTableIdGnrServiceImpl egovTableIdGnrServiceImpl = new EgovTableIdGnrServiceImpl();
        egovTableIdGnrServiceImpl.setDataSource(dataSource);
        egovTableIdGnrServiceImpl.setStrategy(fileStrategy());
        egovTableIdGnrServiceImpl.setBlockSize(1);
        egovTableIdGnrServiceImpl.setTable("co_seq_mst");
        egovTableIdGnrServiceImpl.setTableName("FILE_ID");
        return egovTableIdGnrServiceImpl;
    }

    /** 샘플 테이블 시퀀스
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl sampleTableIdGnrService() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("SAMPLE_ID")
                .setCipers(13)
                .build();
    }

    /** 메뉴 테이블 시퀀스
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl menuTableIdGnrService() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("MENU_TEST_SEQ")
                .setCipers(13)
                .build();
    }

    /** 샘플 쿼리 시퀀스
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovSequenceIdGnrServiceImpl sampleQueryIdGnrService() {
        EgovSequenceIdGnrServiceImpl egovIdGnrStrategyImpl = new EgovSequenceIdGnrServiceImpl();
        egovIdGnrStrategyImpl.setDataSource(dataSource);
        egovIdGnrStrategyImpl.setQuery("SELECT SEQ_SAMPLE.NEXTVAL FROM DUAL");
        return egovIdGnrStrategyImpl;
    }


    /** 시스템 로그 시퀀스
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl serviceLogIdGnrService() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setPreFix("SYSLOG_")
                .setTableName("SYSLOG_ID")
                .setCipers(13)
                .build();
    }

    /** 관리자 계정 테이블 시퀀스
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl admIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("ADM_SEQ")
                .setCipers(13)
                .build();
    }

    /** 관리자 알림 시퀀스
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl admNtcIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("ADM_NTC_SEQ")
                .setCipers(13)
                .build();
    }

    /** 관리자 알림 시퀀스
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl egovSysLogIdGnrService() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setPreFix("SYSLOG_")
                .setTable("co_seq_mst")
                .setTableName("SYSLOG_ID")
                .setCipers(13)
                .build();
    }

    /** 메뉴
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl menuIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("MENU_SEQ")
                .setCipers(13)
                .build();
    }

    /** QNA
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl qstnIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("QSTN_SEQ")
                .setCipers(13)
                .build();
    }

    /** RPLY_SEQ
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl rplyIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("RPLY_SEQ")
                .setCipers(13)
                .build();
    }

    /** 교육과정관리 마스터
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl edctnMstIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("EDCTN_MST_SEQ")
                .setCipers(13)
                .build();
    }

    /** 교육차수관리 마스터
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl edctnEpisdIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("EDCTN_PISD_SEQ")
                .setCipers(13)
                .build();
    }

    /** 교육 시험 마스터
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl examMstIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("EXAM_MST_SEQ")
                .setCipers(13)
                .build();
    }

    /** 교육 시험 질문 상세
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl examQstnDtlIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("EXAM_QSTN_SEQ")
                .setCipers(13)
                .build();
    }

    /** 교육 시험 질문 보기 상세
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl examQstnExmplDtlIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("EXAM_EXMPL_SEQ")
                .setCipers(13)
                .build();
    }

    /** 관리자 인증로그 시퀀스
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl admCrtfnNodgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("ADM_CRTFN_SEQ")
                .setCipers(13)
                .build();
    }

}
