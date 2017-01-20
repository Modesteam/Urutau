package com.modesteam.urutau.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.modesteam.urutau.model.system.Password;

public class PasswordTest {

	@Test
	public void testEncryptGeneratingPassword() {
		Password p = new Password();
		p.setUserPasswordPassed("HelloWorld");
		p.generateHash();
		assertNotNull(p.getHashPassword());
	}

	@Test
	public void testAuthenticateWhenShouldBeTrue() {
		Password p = new Password();
		String realPassword = "HelloWorld";
		p.setUserPasswordPassed(realPassword);
		p.generateHash();
		assertTrue(p.authenticated());
	}

	@Test
	public void testAuthenticateWhenShouldBeFalse() {
		Password p = new Password();
		String realPassword = "HelloWorld";
		p.setUserPasswordPassed(realPassword);
		p.generateHash();
		p.setUserPasswordPassed("Hello");
		assertFalse(p.authenticated());
	}

}
