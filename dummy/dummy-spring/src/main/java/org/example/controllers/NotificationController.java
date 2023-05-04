package org.example.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.List;
import java.util.Map;


/**
 * openssl ecparam -name prime256v1 -genkey -noout -out private.ec.key
 * openssl ec -in private.ec.key -pubout -out public.pem
 * -- the non encrypted can be used from Postman, see example in the collection
 * openssl pkcs8 -topk8 -in private.ec.key -out p8file.pem
 * openssl pkcs8 -topk8 -nocrypt -in private.ec.key -out p8file.pem
 */
@RestController
@RequestMapping("webhooks")
public class NotificationController {


    @PostMapping("{aspspIntegrationId}")
    public void webHook(
            @PathVariable("aspspIntegrationId") Long id,
            @RequestHeader Map<String, String> httpHeaders,
            @RequestBody JsonNode httpBody
    ) throws ParseException, JOSEException, IOException {

        // the bearer consist of 3 parts: algorithm.jwtpayload.signature
        String bearer = httpHeaders.get("authorization").substring(7);
        SignedJWT signedJWT = SignedJWT.parse(bearer);

        String validPublicKey = """
               -----BEGIN PUBLIC KEY-----
               MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEk+bTgN2JbbBxMXv7777YI7w4uZjg
               upErnTm0l/G1HnzUkP2y3QgOvgp9FDe8JJXyFc5aiSMbvAV4jDo6tL3B3g==
               -----END PUBLIC KEY-----
               """;

        String invalidPublicKey = """
                -----BEGIN PUBLIC KEY-----
                MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE4M605eioTQmXP26oNxSH2eCdquXD
                ZneBGquUy7UZUBNgxTuoqCJEZRA1Q78NXPpOQrEIVSWnyQDxeRdG2nA9jQ==
                -----END PUBLIC KEY-----
                """;

        String string = signedJWT.getPayload().toString(); // the jwt payload

        List<JWK> keys = JWKSet.load(URI.create("https://api.yapily.com/.well-known/jwks.json").toURL())
                .getKeys();

        JWK validJwk = JWK.parseFromPEMEncodedObjects(validPublicKey);
        JWK invalidJwk2 = JWK.parseFromPEMEncodedObjects(invalidPublicKey);
        if(validJwk instanceof ECKey eckey){
            JWSVerifier verifier = new ECDSAVerifier(eckey);
            boolean verify = signedJWT.verify(verifier);
            System.out.println(verify);
        }

        if(invalidJwk2 instanceof ECKey eckey){
            JWSVerifier verifier = new ECDSAVerifier(eckey);
            boolean verify = signedJWT.verify(verifier);
            System.out.println(verify);
        }
    }
}