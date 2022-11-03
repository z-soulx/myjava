package com.example.javabase;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2022-09-01 10:23
 **/
public class T {

	public static void main(String[] args) throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");

		KeyPair keys = keyPairGenerator.generateKeyPair();

		PublicKey publicKey = keys.getPublic();




		System.out.printf("pub:"+ Base64.getEncoder().encodeToString(publicKey.getEncoded()));

		PrivateKey privateKey = keys.getPrivate();




		System.out.println("\n");

		System.out.println("priv:"+Base64.getEncoder().encodeToString(privateKey.getEncoded()));




		Signature sig = Signature.getInstance("SHA256withDSA", "SUN");

		sig.initSign(privateKey);

		String clearText = "SQT1-AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +

				"SQT1-AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +

				"SQT1-AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n";

		sig.update(clearText.getBytes(StandardCharsets.UTF_8));

		byte[] signature = sig.sign();

		System.out.println("sign:"+Base64.getEncoder().encodeToString(signature));




		sig.initVerify(publicKey);

		sig.update(clearText.getBytes(StandardCharsets.UTF_8));

		boolean verify = sig.verify(signature);

		System.out.println("verify:"+verify);
	}
}
