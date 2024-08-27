package com.kap.service.dao.mp;

 import com.kap.core.dto.mp.mpa.MPAAttctnDto;
 import com.kap.core.dto.mp.mpa.MPAInqrDto;
 import com.kap.core.dto.mp.mpa.MPAPartDto;
 import com.kap.core.dto.mp.mpa.MPAUserDto;
 import com.kap.core.dto.MPPwdInitDto;
 import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
 import com.kap.core.dto.mp.mpi.MPIWthdrwDto;
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

    /**
     * 사용자 조회
     * @param mpaUserDto
     * @return
     */
    List<MPAUserDto> selectUserList(MPAUserDto mpaUserDto);

    /**
     * 사용자 조회 개수
     * @param mpaUserDto
     * @return
     */
    int selectUserListCnt(MPAUserDto mpaUserDto);

    /**
     * 사용자 상세 조회
     * @param mpaUserDto
     * @return
     */
    MPAUserDto selectUserDtl(MPAUserDto mpaUserDto);

    /**
     * 사용자 탭 상세 조회
     * @param mpaUserDto
     * @return
     */
    MPAUserDto selectUserDtlTab(MPAUserDto mpaUserDto);

    MPAUserDto selectCiUser(MPAUserDto mpaUserDto);

    /**
     * 이메일 중복 체크
     * @param mpaUserDto
     * @return
     */
    int selectDupEmail(MPAUserDto mpaUserDto);

    /**
     * id 중복 체크
     * @param mpaUserDto
     * @return
     */
    int selectDupId(MPAUserDto mpaUserDto);

    /**
     * 사용자 정보 upd
     * @param mpaUserDto
     * @return
     */
    int updateUserDtl(MPAUserDto mpaUserDto);

    /**
     * 사용자 상세 이력 ins
     * @param mpaUserDto
     * @return
     */
    int insertUserDtlHistory(MPAUserDto mpaUserDto);

    /**
     * 미래차공모전 리스트 조회
     * @param mpaAttctnDto
     * @return
     */
    List<MPAAttctnDto> selectAttcntList(MPAAttctnDto mpaAttctnDto);

    /**
     * 미래차 공모전 리스트 개수
     * @param mpaAttctnDto
     * @return
     */
    int selectAttcntListCnt(MPAAttctnDto mpaAttctnDto);

    /**
     * 문의 리스트 조회
     * @param mpaInqrDto
     * @return
     */
    List<MPAInqrDto> selectInqrList(MPAInqrDto mpaInqrDto);

    /**
     * 문의 리스트 개수
     * @param mpaInqrDto
     * @return
     */
    int selectInqrListCnt(MPAInqrDto mpaInqrDto);

    /**
     * 사용자 정보 조회
     * @param mpPwdInitDto
     * @return
     */
    MPPwdInitDto selectMemDtl(MPPwdInitDto mpPwdInitDto);

    /**
     * 비밀번호 수정
     * @param mpPwdInitDto
     * @return
     */
    int updatePwdInit(MPPwdInitDto mpPwdInitDto);

    /**
     * 사용자 저장
     * @param mpaUserDto
     */
    void insertUser(MPAUserDto mpaUserDto);

    /**
     * ci 값 조회
     * @param mpaUserDto
     * @return
     */
    int selectCiCnt(MPAUserDto mpaUserDto);

    /**
     * 부품사 갯수 조회
     * @param mpePartsCompanyDTO
     * @return
     */
    int selectcmpnMst(MPEPartsCompanyDTO mpePartsCompanyDTO);

    /**
     * 사용자 저장
     * @param mpaUserDto
     */
    void insertUserDtl(MPAUserDto mpaUserDto);

    /**
     * 사용자 부품사 저장
     * @param mpaUserDto
     */
    void insertUserCmpnRel(MPAUserDto mpaUserDto);

    /**
     * 사용자 수정
     * @param mpaUserDto
     */

    void updateUserDtlMod(MPAUserDto mpaUserDto);

    /**
     * 사용자 마케팅 수정
     * @param mpaUserDto
     */
    void updateUserDtlModS(MPAUserDto mpaUserDto);

    /**
     * 부품사 수정
     * @param mpaUserDto
     */
    void updateUserDtlModBsnm(MPAUserDto mpaUserDto);

    /**
     * 부품사 수정시 사업 조회
     * @param mpaUserDto
     * @return
     */
    List<MPAPartDto> selectMPHPartList(MPAUserDto mpaUserDto);

    /**
     * 컨설팅 개수 조회
     * @param mpaUserDto
     * @return
     */
    int selectConListCnt(MPAUserDto mpaUserDto);

    /**
     * 탈퇴여부 N 변경
     * @param mpiWthdrwDto
     */
    void updateUserWthdrw(MPIWthdrwDto mpiWthdrwDto);

    /**
     * CI값 삭제
     * @param mpiWthdrwDto
     */
    void updateUserCiDel(MPIWthdrwDto mpiWthdrwDto);

    /**
     * 회원 탈퇴상세(이력) 등록
     * @param mpiWthdrwDto
     */
    void insertUserWthdrw(MPIWthdrwDto mpiWthdrwDto);

    /**
     * 사용자 id 조회
     * @param mpaUserDto
     * @return
     */
    MPAUserDto selectUserDtlId(MPAUserDto mpaUserDto);


    /**
     * 사용자 회사 관계 카운팅
     * @param mpaUserDto
     * @return
     */
    int selectCmpnRelCount(MPAUserDto mpaUserDto);

    /**
     * 사용자 회사 관계 수정
     * @param mpaUserDto
     * @return
     */
    void updateCmpnRel(MPAUserDto mpaUserDto);

}
