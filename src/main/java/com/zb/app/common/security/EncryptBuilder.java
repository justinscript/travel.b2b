/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.zb.app.common.core.SpringContextAware;

/**
 * 加密解密类 对于web层，一律使用common/下的EncryptBuilder
 * 
 * @author zxc Jun 18, 2014 11:16:09 AM
 */
@Component
public class EncryptBuilder {

    // 从配置文件zuobian.properties中获取
    @Value("${passwd.security.key}")
    private String        SECRET_KEY;

    private static Logger logger = LoggerFactory.getLogger(EncryptBuilder.class);

    public interface ICrypt {

        public void initCipher(SecretKey key, SecureRandom sr, Cipher cipher) throws InvalidKeyException;

        public byte[] getCryptedData(String secret);
    }

    public static EncryptBuilder getInstance() {
        return (EncryptBuilder) SpringContextAware.getBean("encryptBuilder");
    }

    // 解密
    public String decrypt(String secret) {
        return decrypt(secret, SECRET_KEY);
    }

    // 加密
    public String encrypt(String source) {
        return encrypt(source, SECRET_KEY);
    }

    // 解密
    public String decrypt(String secret, String secretKey) {
        if (StringUtils.isEmpty(secret) || StringUtils.isEmpty(secretKey)) {
            return null;
        }
        // 判断Key是否为16位
        if (secretKey.length() != 16) {
            logger.error("Key长度不是16位");
            return null;
        }
        try {

            return new String(doCrypt(secret, secretKey, new ICrypt() {

                @Override
                public void initCipher(SecretKey key, SecureRandom sr, Cipher cipher) throws InvalidKeyException {
                    cipher.init(Cipher.DECRYPT_MODE, key, sr);
                }

                @Override
                public byte[] getCryptedData(String secret) {
                    return Base64.decode(secret);
                }
            }));

        } catch (Exception e) {
            String info = secret + "\r\n" + ExceptionUtils.getFullStackTrace(e);
            logger.error(info);
        }
        return null;
    }

    // 加密
    public String encrypt(String source, String secretKey) {
        if (StringUtils.isEmpty(source) || StringUtils.isEmpty(secretKey)) {
            return null;
        }
        if (secretKey.length() != 16) {
            logger.error("Key长度不是16位");
            return null;
        }
        try {

            return Base64.encode(doCrypt(source, secretKey, new ICrypt() {

                @Override
                public void initCipher(SecretKey key, SecureRandom sr, Cipher cipher) throws InvalidKeyException {
                    cipher.init(Cipher.ENCRYPT_MODE, key, sr);
                }

                @Override
                public byte[] getCryptedData(String source) {
                    return source.getBytes();
                }
            }));

        } catch (Exception e) {
            String info = source + "\r\n" + ExceptionUtils.getFullStackTrace(e);
            logger.error(info);
        }
        return null;
    }

    @SuppressWarnings("restriction")
    private byte[] doCrypt(String source, String secretKey, ICrypt iCrypt) throws InvalidKeyException,
                                                                          NoSuchAlgorithmException,
                                                                          InvalidKeySpecException,
                                                                          NoSuchPaddingException,
                                                                          IllegalBlockSizeException,
                                                                          BadPaddingException {
        byte[] cryptedData = iCrypt.getCryptedData(source);
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        SecureRandom sr = new SecureRandom();
        byte[] rawKeyData = (new String(secretKey)).getBytes();

        DESKeySpec dks = new DESKeySpec(rawKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        iCrypt.initCipher(key, sr, cipher);

        return cipher.doFinal(cryptedData);
    }
}
