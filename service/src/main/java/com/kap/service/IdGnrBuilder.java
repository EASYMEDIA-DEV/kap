package com.kap.service;

import org.apache.commons.lang3.StringUtils;
import org.egovframe.rte.fdl.idgnr.impl.EgovTableIdGnrServiceImpl;
import org.egovframe.rte.fdl.idgnr.impl.strategy.EgovIdGnrStrategyImpl;

import javax.sql.DataSource;

public class IdGnrBuilder {
    // TODO : 기본값 설정, 예외처리 필요

    private DataSource dataSource;
    private EgovIdGnrStrategyImpl egovIdGnrStrategyImpl;

    private String preFix;
    private int cipers;
    private char fillChar;

    private int blockSize;
    private String table;
    private String tableName;

    public IdGnrBuilder setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    public IdGnrBuilder setEgovIdGnrStrategyImpl(EgovIdGnrStrategyImpl egovIdGnrStrategyImpl) {
        this.egovIdGnrStrategyImpl = egovIdGnrStrategyImpl;
        return this;
    }

    public IdGnrBuilder setPreFix(String preFix) {
        this.preFix = preFix;
        return this;
    }
    public IdGnrBuilder setCipers(int cipers) {
        this.cipers = cipers;
        return this;
    }
    public IdGnrBuilder setFillChar(char fillChar) {
        this.fillChar = fillChar;
        return this;
    }
    public IdGnrBuilder setBlockSize(int blockSize) {
        this.blockSize = blockSize;
        return this;
    }
    public IdGnrBuilder setTable(String table) {
        this.table = table;
        return this;
    }
    public IdGnrBuilder setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public EgovTableIdGnrServiceImpl build() {

        EgovTableIdGnrServiceImpl egovTableIdGnrServiceImpl = new EgovTableIdGnrServiceImpl();
        egovTableIdGnrServiceImpl.setDataSource(dataSource);

        if(egovIdGnrStrategyImpl != null) {
            egovIdGnrStrategyImpl = new EgovIdGnrStrategyImpl();
            if(StringUtils.isNotEmpty(preFix)) {
                egovIdGnrStrategyImpl.setPrefix(preFix);
            }
            egovIdGnrStrategyImpl.setCipers(cipers);
            //egovIdGnrStrategyImpl.setFillChar(fillChar);

            egovTableIdGnrServiceImpl.setStrategy(egovIdGnrStrategyImpl);
        }
        egovTableIdGnrServiceImpl.setBlockSize(blockSize);
        egovTableIdGnrServiceImpl.setTable(table);
        egovTableIdGnrServiceImpl.setTableName(tableName);

        return egovTableIdGnrServiceImpl;
    }
}
