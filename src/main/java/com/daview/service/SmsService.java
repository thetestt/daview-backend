package com.daview.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Random;
import java.io.OutputStream;
import java.time.Instant;

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

    public String sendSms(String to) {
        try {
            String time = String.valueOf(Instant.now().toEpochMilli());
            String uri = "/sms/v2/services/" + serviceId + "/messages";
            String apiUrl = "https://sens.apigw.ntruss.com" + uri;
            String method = "POST";

            String signature = makeSignature(uri, time, method);

            String randomCode = createCode();
            log.info("인증번호: {}", randomCode);

            String json = "{"
                    + "\"type\":\"SMS\","
                    + "\"contentType\":\"COMM\","
                    + "\"countryCode\":\"82\","
                    + "\"from\":\"" + senderPhone + "\","
                    + "\"content\":\"[인증번호] " + randomCode + "\","
                    + "\"messages\":[{\"to\":\"" + to + "\"}]"
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
            log.info("네이버 응답 코드: {}", responseCode);
            
            String responseMessage = con.getResponseMessage();
            System.out.println("응답 상태 코드: " + responseCode);
            System.out.println("응답 메시지: " + responseMessage);
            
            return responseCode == 202 ? randomCode : null;

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
}
