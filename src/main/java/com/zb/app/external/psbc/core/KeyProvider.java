/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.psbc.core;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;

import com.zb.app.external.psbc.exception.DataAccessException;
import com.zb.app.external.psbc.utils.PSBCAssert;

/**
 * @author zxc Aug 8, 2014 1:38:11 PM
 */
public class KeyProvider {

    private static final String DEFAULT_KEYSTORE = "/lib/security/cacerts";
    private static final String JAVA_HOME        = "java.home";
    private KeyStore            keystore;

    public KeyProvider(KeyStore keystore) {
        this.keystore = keystore;
    }

    public static KeyStore getInstance(String keystoreFilePath, String keystorePassword) {
        InputStream is;
        if (keystoreFilePath == null) {
            String javahomePath = System.getProperty(JAVA_HOME);
            keystoreFilePath = javahomePath + DEFAULT_KEYSTORE;
        }
        char pwd[] = keystorePassword != null ? keystorePassword.toCharArray() : null;
        is = null;
        KeyStore keystore1;
        try {
            is = new BufferedInputStream(new FileInputStream(keystoreFilePath));
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(is, pwd);
            keystore1 = keystore;
        } catch (Exception e) {
            throw new DataAccessException("密钥容器加载异常!", e);
        } finally {

        }
        if (is != null) try {
            is.close();
        } catch (IOException e) {
            throw new DataAccessException(e);
        }
        return keystore1;
    }

    public Certificate getCertificate(String alias) throws DataAccessException, KeyStoreException {
        PSBCAssert.notNull(keystore, "keystore is null.");
        PSBCAssert.notNull(alias, "certificate alias is null.");
        return keystore.getCertificate(alias);
    }

    public Certificate[] getCertificateChain(String alias) throws DataAccessException, KeyStoreException {
        PSBCAssert.notNull(keystore, "keystore is null.");
        PSBCAssert.notNull(alias, "certificate chain alias is null.");
        return keystore.getCertificateChain(alias);
    }

    public PrivateKey getPrivateKey(String alias, String privatekeyPassword) throws DataAccessException,
                                                                            UnrecoverableKeyException,
                                                                            KeyStoreException, NoSuchAlgorithmException {
        PSBCAssert.notNull(keystore, "keystore is null.");
        PSBCAssert.notNull(alias, "private key alias is null.");
        return (PrivateKey) keystore.getKey(alias, privatekeyPassword != null ? privatekeyPassword.toCharArray() : null);
    }
}
