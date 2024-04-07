package io.hrushik09.authservice.config.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class GenerateCodeVerifierAndCodeChallenge {
    public static void main(String[] args) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] code = new byte[32];
        secureRandom.nextBytes(code);
        String codeVerifier = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(code);
        System.out.println("codeVerifier = " + codeVerifier);

        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA256");
            byte[] digest = sha256.digest(codeVerifier.getBytes());
            String codeChallenge = Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(digest);
            System.out.println("codeChallenge = " + codeChallenge);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
