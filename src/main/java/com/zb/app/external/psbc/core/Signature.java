/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.psbc.core;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;

import com.zb.app.external.psbc.exception.SignException;
import com.zb.app.external.psbc.exception.VerifyException;
import com.zb.app.external.psbc.utils.PSBCAssert;

/**
 * @author zxc Aug 8, 2014 1:36:51 PM
 */
public class Signature {

    private static final String MD5WITHRSA_ALGORITHM = "MD5withRSA";

    public Signature() {

    }

    private String byteToHex(byte byteArray[]) {
        if (byteArray == null) return "";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            String hexString = Integer.toHexString(byteArray[i] & 0xff);
            if (hexString.length() != 2) sb.append("0");
            sb.append(hexString);
        }

        return sb.toString();
    }

    private byte[] hexToByte(String hexString) {
        if (hexString == null || hexString.trim().length() == 0) return new byte[0];
        int unit = hexString.length() / 2;
        byte byteArray[] = new byte[unit];
        for (int i = 0; i < hexString.length() / 2; i++) {
            String hexChar = hexString.substring(i * 2, i * 2 + 2);
            byteArray[i] = (byte) Integer.parseInt(hexChar, 16);
        }

        return byteArray;
    }

    public String sign(String plain, String algorithm, PrivateKey privateKey) throws SignException,
                                                                             NoSuchAlgorithmException,
                                                                             InvalidKeyException, SignatureException {
        PSBCAssert.notNull(plain, "plain is null.");
        PSBCAssert.notNull(algorithm, "algorithm is null.");
        PSBCAssert.notNull(privateKey, "private key is null.");
        java.security.Signature sign;
        sign = java.security.Signature.getInstance(algorithm);
        sign.initSign(privateKey);
        sign.update(plain.getBytes());
        return byteToHex(sign.sign());
    }

    public String sign(String plain, PrivateKey privateKey) throws SignException, NoSuchAlgorithmException,
                                                           InvalidKeyException, SignatureException {
        return sign(plain, MD5WITHRSA_ALGORITHM, privateKey);
    }

    public boolean verify(String plain, String signature, String algorithm, PublicKey publicKey)
                                                                                                throws VerifyException,
                                                                                                NoSuchAlgorithmException,
                                                                                                InvalidKeyException,
                                                                                                SignatureException {
        PSBCAssert.notNull(plain, " plain is null.");
        PSBCAssert.notNull(signature, "signature is null.");
        PSBCAssert.notNull(algorithm, "algorithm is null.");
        PSBCAssert.notNull(publicKey, "public key is null.");
        java.security.Signature sign;
        byte signatureByteArray[];
        sign = java.security.Signature.getInstance(algorithm);
        sign.initVerify(publicKey);
        sign.update(plain.getBytes());
        signatureByteArray = hexToByte(signature);
        return sign.verify(signatureByteArray);
    }

    public boolean verify(String plain, String signature, PublicKey publicKey) throws VerifyException,
                                                                              NoSuchAlgorithmException,
                                                                              InvalidKeyException, SignatureException {
        return verify(plain, signature, MD5WITHRSA_ALGORITHM, publicKey);
    }
}
