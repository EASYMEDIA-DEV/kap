package com.kap.core.utility;

import com.kap.service.dao.COSeqGnrMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component("cOSeqGnrUtil")
public class COSeqGnrUtil {

    // DAO
    private COSeqGnrMapper cOSeqGnrMapper;

    public int getSeq(String seqCode) throws Exception {
        int seq = 0;
        System.err.println("seqCode:::"+seqCode);
        try {
            if(!seqCode.isEmpty()) {
                seq = cOSeqGnrMapper.selectSeq(seqCode);
                if(seq > 0) {
                    cOSeqGnrMapper.updateSeq(seqCode);
                }
                else {
                    cOSeqGnrMapper.insertSeq(seqCode);
                    return seq;
                }
            }
        }catch (Exception e){
            System.err.println("e:::"+e);
        }

        return seq;
    }

}
