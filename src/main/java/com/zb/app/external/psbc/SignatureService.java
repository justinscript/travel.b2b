/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.psbc;

import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.util.ResourceBundle;

import com.sun.net.ssl.internal.ssl.Provider;
import com.zb.app.external.psbc.core.KeyProvider;
import com.zb.app.external.psbc.core.Signature;
import com.zb.app.external.psbc.exception.DataAccessException;
import com.zb.app.external.psbc.utils.PSBCAssert;

/**
 * @author zxc Aug 8, 2014 1:36:22 PM
 */
@SuppressWarnings("restriction")
public class SignatureService {

    private static KeyStore       keystore;
    private static ResourceBundle resourceBundle;

    static {
        Security.addProvider(new Provider());
        resourceBundle = ResourceBundle.getBundle("psbc_merchant");
        String keystoreFilePath = resourceBundle.getString("psbc.jks.file");
        String keystorePassword = resourceBundle.getString("psbc.jks.password");
        keystore = KeyProvider.getInstance(keystoreFilePath, keystorePassword);
    }

    public SignatureService() {

    }

    public static String sign(String plain) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException,
                                           UnrecoverableKeyException, DataAccessException, KeyStoreException {
        Signature signature = new Signature();
        KeyProvider keyProvider = new KeyProvider(keystore);
        String alias = resourceBundle.getString("psbc.merchant.key.alias");
        String privatekeyPassword = resourceBundle.getString("psbc.merchant.key.password");
        java.security.PrivateKey privateKey = keyProvider.getPrivateKey(alias, privatekeyPassword);
        return signature.sign(plain, privateKey);
    }

    public static boolean verify(String plain, String signature) throws NoSuchAlgorithmException, InvalidKeyException,
                                                                SignatureException, DataAccessException,
                                                                KeyStoreException {
        Signature signatureObj = new Signature();
        KeyProvider keyProvider = new KeyProvider(keystore);
        String alias = resourceBundle.getString("psbc.paygate.key.alias");
        Certificate certificate = keyProvider.getCertificate(alias);
        PSBCAssert.notNull(certificate, "can't get the certificate of paygate.");
        return signatureObj.verify(plain, signature, certificate.getPublicKey());
    }
}
