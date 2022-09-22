/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.tuyendev.auth.common.utils;

import com.nimbusds.jose.jwk.RSAKey;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.util.ResourceUtils;

import java.io.FileReader;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.UUID;

/**
 * @author Joe Grandja
 * @since 0.1.0
 */
public final class JwksUtil {

    private JwksUtil() {
    }

    public static RSAKey buildDefaultRsaKey() {
        // @formatter:off
        return new RSAKey
                .Builder(readPublicKey())
                .privateKey(readPrivateKey())
                .keyID(UUID.randomUUID().toString())
                .build();
        // @formatter:on
    }

    public static RSAPublicKey readPublicKey() {

        try (FileReader keyReader = new FileReader(ResourceUtils.getFile("classpath:keys/public.key"));
             PemReader pemReader = new PemReader(keyReader)) {

            KeyFactory factory = KeyFactory.getInstance("RSA");
            PemObject pemObject = pemReader.readPemObject();
            byte[] content = pemObject.getContent();
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(content);
            return (RSAPublicKey) factory.generatePublic(pubKeySpec);
        } catch (Exception e) {
            throw new RuntimeException("Cannot read public key", e);
        }
    }

    public static RSAPrivateKey readPrivateKey() {
        try (FileReader keyReader = new FileReader(ResourceUtils.getFile("classpath:keys/private.key"));
             PemReader pemReader = new PemReader(keyReader)) {
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PemObject pemObject = pemReader.readPemObject();
            byte[] content = pemObject.getContent();
            PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(content);
            return (RSAPrivateKey) factory.generatePrivate(privKeySpec);
        } catch (Exception e) {
            throw new RuntimeException("Cannot read private key", e);
        }
    }
}
