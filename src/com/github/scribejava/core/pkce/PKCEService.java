package com.github.scribejava.core.pkce;

import com.github.scribejava.core.base64.Base64;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Used to implement Proof Key for Code Exchange by OAuth Public Clients https://tools.ietf.org/html/rfc7636
 *
 */
public class PKCEService {

    private static final SecureRandom RANDOM = new SecureRandom();
    /**
     * number of octets to randomly generate.
     */
    private final int numberOFOctets;

    public PKCEService(int numberOFOctets) {
        this.numberOFOctets = numberOFOctets;
    }

    /**
     * will create random generator with recommended params (32 octets) https://tools.ietf.org/html/rfc7636#section-4.1
     */
    public PKCEService() {
        this(32);
    }

    private static class DefaultInstanceHolder {

        private static final PKCEService INSTANCE = new PKCEService();
    }

    public static PKCEService defaultInstance() {
        return DefaultInstanceHolder.INSTANCE;
    }

    public PKCE generatePKCE() {
        final byte[] bytes = new byte[numberOFOctets];
        RANDOM.nextBytes(bytes);
        return generatePKCE(bytes);
    }

    public PKCE generatePKCE(byte[] randomBytes) {
        final String codeVerifier = Base64.encodeUrlWithoutPadding(randomBytes);

        final PKCE pkce = new PKCE();
        pkce.setCodeVerifier(codeVerifier);
        try {
            pkce.setCodeChallenge(pkce.getCodeChallengeMethod().transform2CodeChallenge(codeVerifier));
        } catch (NoSuchAlgorithmException nsaE) {
            pkce.setCodeChallengeMethod(PKCECodeChallengeMethod.PLAIN);
            try{
                pkce.setCodeChallenge(PKCECodeChallengeMethod.PLAIN.transform2CodeChallenge(codeVerifier));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return pkce;
    }
}
