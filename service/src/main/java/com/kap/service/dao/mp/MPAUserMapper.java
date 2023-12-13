package com.kap.service.dao.mp;

 import com.kap.core.dto.mp.mpa.MPAAttctnDto;
 import com.kap.core.dto.mp.mpa.MPAInqrDto;
 import com.kap.core.dto.mp.mpa.MPAUserDto;
 import com.kap.core.dto.MPPwdInitDto;
 import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
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
public interface MPAUserMapper {

    List<MPAUserDto> selectUserList(MPAUserDto mpaUserDto);

    int selectUserListCnt(MPAUserDto mpaUserDto);

    MPAUserDto selectUserDtl(MPAUserDto mpaUserDto);


    MPAUserDto selectUserDtlTab(MPAUserDto mpaUserDto);

    int selectDupEmail(MPAUserDto mpaUserDto);

    int selectDupId(MPAUserDto mpaUserDto);

    int updateUserDtl(MPAUserDto mpaUserDto);

    int insertUserDtlHistory(MPAUserDto mpaUserDto);



    List<MPAAttctnDto> selectAttcntList(MPAAttctnDto mpaAttctnDto);

    int selectAttcntListCnt(MPAAttctnDto mpaAttctnDto);

    List<MPAInqrDto> selectInqrList(MPAInqrDto mpaInqrDto);

    int selectInqrListCnt(MPAInqrDto mpaInqrDto);

    MPPwdInitDto selectMemDtl(MPPwdInitDto mpPwdInitDto);

    int updatePwdInit(MPPwdInitDto mpPwdInitDto);

    void insertUser(MPAUserDto mpaUserDto);

    int selectCiCnt(MPAUserDto mpaUserDto);

    int selectcmpnMst(MPEPartsCompanyDTO mpePartsCompanyDTO);

    void insertUserDtl(MPAUserDto mpaUserDto);

    void insertUserCmpnRel(MPAUserDto mpaUserDto);

    void updateUserDtlMod(MPAUserDto mpaUserDto);

    void updateUserDtlModS(MPAUserDto mpaUserDto);

}
