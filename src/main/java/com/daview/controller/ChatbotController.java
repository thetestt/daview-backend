package com.daview.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.cdimascio.dotenv.Dotenv;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

	  private static final Dotenv dotenv = Dotenv.configure()
		        .directory("./") 
		        .ignoreIfMissing()
		        .load();

		    private static final String OLLAMA_PATH = dotenv.get("OLLAMA_PATH");
		    private static final String MODEL_NAME = dotenv.get("OLLAMA_MODEL");
		    private static final long TIMEOUT_SECONDS = Long.parseLong(dotenv.get("OLLAMA_TIMEOUT", "60"));

    private String cleanAnsi(String line) {
        return line
            .replaceAll("\u001B\\[[;\\d]*[ -/]*[@-~]", "")
            .replaceAll("\u001B\\[\\?25[hl]", "")
            .replaceAll("\u001B\\[\\?[0-9]{1,4}[a-zA-Z]", "")
        	.replaceAll("[\\u2800-\\u28FF]", "");
    }


    @PostMapping("/message")
    public ResponseEntity<String> chatWithOllama(@RequestBody Map<String, String> payload) {
        String userMessage = payload.get("message");

        if (userMessage == null || userMessage.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("message 값이 비어 있습니다.");
        }

        try {
            ProcessBuilder pb = new ProcessBuilder(OLLAMA_PATH, "run", MODEL_NAME);
            pb.redirectErrorStream(true);
            Process process = pb.start();

            process.getOutputStream().write((userMessage + "\n").getBytes(StandardCharsets.UTF_8));
            process.getOutputStream().flush();
            process.getOutputStream().close();

            BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8)
            );
            StringBuilder output = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(cleanAnsi(line)).append("\n");
            }

            boolean finished = process.waitFor(TIMEOUT_SECONDS, TimeUnit.SECONDS);
            if (!finished) {
                process.destroyForcibly();
                return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
                        .body("Ollama 응답 시간이 초과되었습니다.");
            }

            return ResponseEntity.ok(output.toString().trim());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("내부 오류: " + e.getMessage());
        }
    }
}