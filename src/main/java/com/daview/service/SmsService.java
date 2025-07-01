package com.daview.service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.daview.dto.User;
import com.daview.mapper.UserMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SmsService {
	@Value("${naver.sens.access-key}")
    private String accessKey;

    @Value("${naver.sens.secret-key}")
    private String secretKey;

    @Value("${naver.sens.service-id}")
    private String serviceId;

    @Value("${naver.sens.sender-phone}")
    private String senderPhone;
    
    @Autowired
    private UserMapper userMapper;


    public String sendSms(String to, String code) {
        try {
            String time = String.valueOf(Instant.now().toEpochMilli());
            String uri = "/sms/v2/services/" + serviceId + "/messages";
            String apiUrl = "https://sens.apigw.ntruss.com" + uri;
            String method = "POST";

            String signature = makeSignature(uri, time, method);

            String sanitizedTo = to.replaceAll("-", "");
            String sanitizedFrom = senderPhone.replaceAll("-", "");

            String json = "{"
                    + "\"type\":\"SMS\","
                    + "\"contentType\":\"COMM\","
                    + "\"countryCode\":\"82\","
                    + "\"from\":\"" + sanitizedFrom + "\","
                    + "\"content\":\"[다뷰] 인증번호 [" + code + "]를 입력해주세요.\","
                    + "\"messages\":[{\"to\":\"" + sanitizedTo + "\"}]"
                    + "}";

            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(method);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("x-ncp-apigw-timestamp", time);
            con.setRequestProperty("x-ncp-iam-access-key", accessKey);
            con.setRequestProperty("x-ncp-apigw-signature-v2", signature);
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();

            int responseCode = con.getResponseCode();
            log.info("응답 코드: {}", responseCode);
            System.out.println("응답 메시지: " + con.getResponseMessage());
            
            return code;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private String createCode() {
        Random r = new Random();
        int num = r.nextInt(888888) + 111111;
        return String.valueOf(num);
    }

    private String makeSignature(String uri, String timestamp, String method) throws Exception {
        String message = method + " " + uri + "\n" + timestamp + "\n" + accessKey;
        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(rawHmac);
    }
    
    private final Map<String, String> signupVerificationCodes = new ConcurrentHashMap<>();

    public void sendSignupSmsCode(String phone) {
    	User user = userMapper.findActiveUserByPhone(phone);
        if (user != null) {
            throw new IllegalStateException("이미 사용 중인 전화번호입니다.");
        }
    	
        String cleanPhone = phone.replaceAll("-", "");
        String code = generateRandomCode();
        signupVerificationCodes.put(cleanPhone, code);

        System.out.println("[전송] 번호: " + cleanPhone + " / 코드: " + code);

        sendSms(phone, code); // 저장한 code 그대로 문자에 사용
    }


    public boolean verifySignupSmsCode(String phone, String code) {
    	String cleanPhone = phone.replaceAll("-", "");
        String saved = signupVerificationCodes.get(phone);
        System.out.println("입력된 번호: " + cleanPhone + ", 입력된 코드: " + code + ", 저장된 코드: " + saved);
        return saved != null && saved.equals(code);
    }

    private String generateRandomCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }



    //테스트
}
