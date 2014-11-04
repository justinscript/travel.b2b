/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.psbc;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.Security;
import java.util.Enumeration;
import java.util.Properties;

import com.sun.net.ssl.internal.ssl.Provider;
import com.zb.app.external.psbc.utils.PSBCAssert;

/**
 * @author zxc Aug 8, 2014 1:38:43 PM
 */
@SuppressWarnings("restriction")
public class ImportPFX {

    public static final String KEYSTORE_TYPE_PKCS12 = "pkcs12";
    public static final String KEYSTORE_TYPE_JKS    = "jks";

    public ImportPFX() {

    }

    public static void main(String args[]) throws Exception {
        ImportPFX instance = new ImportPFX();
        if (args == null || args.length == 0) instance.showUsage();
        Properties properties = instance.loadProperties(args[0]);
        String pfxFile = properties.getProperty("psbc.pfx.file");
        String pfxPwd = properties.getProperty("psbc.pfx.password");
        String jksFile = properties.getProperty("psbc.jks.file");
        String jksPwd = properties.getProperty("psbc.jks.password");
        String keyAlias = properties.getProperty("psbc.merchant.key.alias");
        PSBCAssert.notNullorEmpty(pfxFile, "value of 'psbc.pfx.file' is null or empty.");
        PSBCAssert.notNull(pfxPwd, "value of 'psbc.pfx.password' is null.");
        PSBCAssert.notNullorEmpty(jksFile, "value of 'psbc.jks.file' is null or empty.");
        PSBCAssert.notNullorEmpty(jksPwd, "value of 'psbc.jks.password' is null or empty.");
        PSBCAssert.notNullorEmpty(keyAlias, "value of 'psbc.merchant.key.alias' is null or empty.");
        Security.addProvider(new Provider());
        Security.removeProvider("IBMJCE");
        KeyStore pfx = instance.loadPFX(properties);
        KeyStore jks = instance.loadJKS(properties);
        instance.store(properties, pfx, jks);
    }

    private void showUsage() {
        System.err.println("Usage: import.bat <properties file>");
        System.err.println("Copyright 1999-2010 Client Service International, Inc. All rights reserved.");
        System.exit(0);
    }

    private Properties loadProperties(String file) throws Exception {
        FileInputStream input;
        Properties properties = new Properties();
        input = null;
        Properties properties1;
        try {
            input = new FileInputStream(file);
            properties.load(input);
            properties1 = properties;
        } catch (Exception ex) {
            System.err.println("ERROR: can't load the properties file.");
            ex.printStackTrace();
            throw ex;
        } finally {
        }
        if (input != null) try {
            input.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw ex;
        }
        return properties1;
    }

    private KeyStore loadPFX(Properties properties) throws Exception {
        FileInputStream pfxInput;
        pfxInput = null;
        KeyStore pfx = null;
        String pfxFile = properties.getProperty("psbc.pfx.file");
        String pfxPwd = properties.getProperty("psbc.pfx.password");
        KeyStore keystore;
        try {
            pfx = KeyStore.getInstance(KEYSTORE_TYPE_PKCS12, (new Provider()).getName());
            pfxInput = new FileInputStream(pfxFile);
            pfx.load(pfxInput, "".equals(pfxPwd) ? null : pfxPwd.toCharArray());
            keystore = pfx;
        } catch (Exception ex) {
            System.err.println("ERROR: can't load the pfx file.");
            ex.printStackTrace();
            throw ex;
        } finally {
        }
        if (pfxInput != null) try {
            pfxInput.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw ex;
        }
        return keystore;
    }

    private KeyStore loadJKS(Properties properties) throws Exception {
        KeyStore jks;
        String keyAlias;
        FileInputStream jksInput = null;
        jks = null;
        String jksFile = properties.getProperty("psbc.jks.file");
        String jksPwd = properties.getProperty("psbc.jks.password");
        keyAlias = properties.getProperty("psbc.merchant.key.alias");
        try {
            jks = KeyStore.getInstance(KEYSTORE_TYPE_JKS);
            jksInput = new FileInputStream(jksFile);
            jks.load(jksInput, "".equals(jksPwd) ? null : jksPwd.toCharArray());
        } catch (Exception ex) {
            System.err.println("ERROR: can't load the jks file.");
            ex.printStackTrace();
            throw ex;
        } finally {
            if (jksInput != null) try {
                jksInput.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                throw ex;
            }
        }
        if (jks.containsAlias(keyAlias)) {
            System.err.println("ERROR: jks already contains " + keyAlias);
            throw new Exception();
        } else {
            return jks;
        }
    }

    @SuppressWarnings("rawtypes")
    private void store(Properties properties, KeyStore pfx, KeyStore jks) throws Exception {
        FileOutputStream out = null;
        try {
            String jksFile = properties.getProperty("psbc.jks.file");
            String keyAlias = properties.getProperty("psbc.merchant.key.alias");
            String keyPwd = properties.getProperty("psbc.merchant.key.password");
            String jksPwd = properties.getProperty("psbc.jks.password");
            String pfxPwd = properties.getProperty("psbc.pfx.password");
            char pfxpwdChar[] = "".equals(pfxPwd) ? null : pfxPwd.toCharArray();
            char jksPwdChar[] = "".equals(jksPwd) ? null : jksPwd.toCharArray();
            char keyPwdChar[] = "".equals(keyPwd) ? null : keyPwd.toCharArray();
            for (Enumeration en = pfx.aliases(); en.hasMoreElements();) {
                String alias = (String) en.nextElement();
                if (pfx.isCertificateEntry(alias)) {
                    System.out.println("importing certificate " + alias);
                    jks.setCertificateEntry(alias, pfx.getCertificate(alias));
                }
                if (pfx.isKeyEntry(alias)) {
                    System.out.println("importing key " + alias + " as " + keyAlias);
                    jks.setKeyEntry(keyAlias, pfx.getKey(alias, pfxpwdChar), keyPwdChar, pfx.getCertificateChain(alias));
                }
            }

            out = new FileOutputStream(jksFile);
            jks.store(out, jksPwdChar);
            System.out.println("success.");
        } catch (Exception ex) {
            System.err.println("ERROR: can't import the pfx into jks.");
            ex.printStackTrace();
            throw ex;
        } finally {
            if (out != null) try {
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                throw ex;
            }
        }
        return;
    }
}
