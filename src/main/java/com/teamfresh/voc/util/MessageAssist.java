package com.teamfresh.voc.util;

import org.springframework.stereotype.Component;

@Component
public class MessageAssist {
    //테스트코드 및 서비스 메세지 작성 편의
    public String Success = "성공적으로 완료했습니다.";
    public String DupUsername = "중복된 아이디입니다.";
    public String NoUsername = "아이디는 필수 입력 값입니다.";
    public String NoPassword = "비밀번호는 필수 입력 값입니다.";
    public String WrongUsernameOrPassword = "아이디 또는 비밀번호가 올바르지 않습니다.";
    public String WrongContactNum = "올바르지 않은 전화번호 형식입니다.";
    public String WrongEmail = "이메일 형식이 올바르지 않습니다.";
    public String NoCompanyName = "회사명은 필수 입력 값입니다.";
    public String NoCompanyType = "회사 타입은 필수 입력 값입니다.";
    public String DupCompanyName = "이미 존재하는 회사이름입니다.";
    public String AlreadyHasCompany = "이미 회사에 등록되어있습니다.";
    public String WrongCompanyId = "존재하지 않는 회사 ID입니다.";
    public String WrongCompensationId = "존재하지 않는 배상 정보입니다.";
    public String NotDriver = "운송 기사로 등록된 유저만 가능합니다.";
    public String WrongPenaltyId = "존재하지 않는 패널티입니다.";
    public String NotPenaltyOwner = "자신의 패널티에만 이의를 제기할수있습니다.";
    public String AlreadyObjection = "이미 이의를 제기한 패널티입니다.";
}
