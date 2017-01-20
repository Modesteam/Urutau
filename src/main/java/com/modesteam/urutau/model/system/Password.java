package com.modesteam.urutau.model.system;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import org.postgresql.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Password {
	/*
	 * Ordinary length of salt, it could be smaller
	 */
	private static final int SALT_LENGHT = 17;
	private static final Logger logger = LoggerFactory.getLogger(Password.class);

	private String userPasswordPassed;
	private byte[] salt;
	private byte[] hashPassword;

	/**
	 * Generate a encrypt password hash based on content
	 * 
	 * @return The SHA-512 hash with salt
	 */
	public byte[] encrypt(byte[] salt, String content) {
		MessageDigest digest = null;

		try {
			digest = MessageDigest.getInstance("SHA-512");
			digest.reset();
			digest.update(salt);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return digest.digest(Base64.encodeBytes(content.getBytes()).getBytes());
	}

	/**
	 * Generate an encrypted password
	 */
	public void generateHash() {
		logger.info("Encrypting password");

		setSalt(generateSalt());
		byte[] newHash = encrypt(salt, this.userPasswordPassed);

		setHashPassword(newHash);
	}

	/**
	 * Verifies if password is authenticated
	 */
	public boolean authenticated() {
		byte[] tryHash = encrypt(this.salt, this.userPasswordPassed);

		return Arrays.equals(tryHash, this.hashPassword);
	}

	public String getUserPasswordPassed() {
		return userPasswordPassed;
	}

	public void setUserPasswordPassed(String userPasswordPassed) {
		this.userPasswordPassed = userPasswordPassed;
	}

	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] bs) {
		this.salt = bs;
	}

	public byte[] getHashPassword() {
		return hashPassword;
	}

	public void setHashPassword(byte[] hashPassword) {
		this.hashPassword = hashPassword;
	}

	/**
	 * Generate a {@link SecureRandom} salt with a lenght of SALT_LENGTH
	 * 
	 * @return byte array
	 */
	private byte[] generateSalt() {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[SALT_LENGHT];
		random.nextBytes(bytes);
		return bytes;
	}
}
