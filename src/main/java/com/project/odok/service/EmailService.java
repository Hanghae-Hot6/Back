package com.project.odok.service;

import com.project.odok.dto.ResponseDto;
import com.project.odok.dto.requestDto.member.FindIdRequestDto;
import com.project.odok.entity.Member;
import com.project.odok.repository.MemberRepository;
import com.project.odok.service.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

import static com.project.odok.service.util.EmailUtil.createKey;

@Slf4j
@Service
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;
    private final MemberRepository memberRepository;
    private String auth; //인증번호 생성

    @Value("${spring.mail.username}")
    private String id;

    public MimeMessage createMessage(String email) throws MessagingException, UnsupportedEncodingException {

        auth = createKey();
        log.info("인증 번호 : " + auth);
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, email); // to 보내는 대상
        message.setSubject("Odok 회원가입 인증 코드: "); //메일 제목

        // 메일 내용 메일의 subtype을 html로 지정하여 html문법 사용 가능
        String msg = "";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 확인 코드를 회원가입 화면에서 입력해주세요.</p>";
        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
        msg += auth;
        msg += "</td></tr></tbody></table></div>";

        message.setText(msg, "utf-8", "html"); //내용, charset타입, subtype
        message.setFrom(new InternetAddress(id, "prac_Admin")); //보내는 사람의 메일 주소, 보내는 사람 이름

        return message;
    }

    public MimeMessage findId(FindIdRequestDto findIdRequestDto) throws MessagingException, UnsupportedEncodingException {
        String email = findIdRequestDto.getEmail();
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("등록되지 않은 email입니다.")
        );

        if (!(member.getEmail().equals(email) && member.getUsername().equals(findIdRequestDto.getUsername()))) {
            new IllegalArgumentException("유저 정보가 틀립니다.");
        }
        auth = member.getMemberId();
        log.info("찾는 ID : " + auth);
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, email); // to 보내는 대상
        message.setSubject("Odok 이메일 인증 코드: "); //메일 제목

        // 메일 내용 메일의 subtype을 html로 지정하여 html문법 사용 가능
        String msg = "";
        msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">ID 확인</h1>";
        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
        msg += auth;
        msg += "</td></tr></tbody></table></div>";

        message.setText(msg, "utf-8", "html"); //내용, charset타입, subtype
        message.setFrom(new InternetAddress(id, "prac_Admin")); //보내는 사람의 메일 주소, 보내는 사람 이름

        return message;
    }

    /*
        메일 발송
        sendSimpleMessage의 매개변수로 들어온 to는 인증번호를 받을 메일주소
        MimeMessage 객체 안에 내가 전송할 메일의 내용을 담아준다.
        bean으로 등록해둔 javaMailSender 객체를 사용하여 이메일 send
     */
    public ResponseDto<?> sendSimpleMessage(String email)throws Exception {
        if (memberRepository.existsByEmail(email)) {
            return ResponseDto.fail("이미 존재하는 email입니다.");
        }
        MimeMessage message = createMessage(email);
        try{
            redisUtil.setDataExpire(auth, email, 60 * 5L); // 유효시간 5분
            javaMailSender.send(message); // 메일 발송
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        log.info("인증코드 : " + auth);
        return ResponseDto.success(auth); // 메일로 보냈던 인증 코드를 서버로 리턴
    }

    public ResponseDto<?> sendEmailMessage(FindIdRequestDto findIdRequestDto)throws Exception {
        MimeMessage message = findId(findIdRequestDto);
        try{
            javaMailSender.send(message); // 메일 발송
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        log.info("찾는 이메일 : " + auth);
        return ResponseDto.success("이메일을 메일로 보냈습니다."); // 메일로 보냈던 인증 코드를 서버로 리턴
    }

    public ResponseDto<?> verifyEmail(String key) {
        String code = redisUtil.getData(key);
        if (code == null) {
            return ResponseDto.fail("인증번호가 일치하지 않습니다.");
        }
        redisUtil.deleteData(key);
        log.info("인증번호가 삭제되었습니다.");
        return ResponseDto.success("인증되었습니다.");
    }
}