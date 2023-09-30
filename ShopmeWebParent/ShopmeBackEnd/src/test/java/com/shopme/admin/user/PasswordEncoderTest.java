package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
	@Test
	public void testEncodePassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String rawPassword = "nam2020";
		String encodedPassword = passwordEncoder.encode(rawPassword);
		
		//$2a$10$DbFUV3l/lACgIruejbG/yu3h.YWbpdj0vjF7nbehsRa/LOpMZS9py
		//$2a$10$18MPxEMxEG3rhuxcSCSeJ.JaAw0iiCRZrhfLGHyacsUrsDlG1qh4u

		boolean encode = passwordEncoder.matches(rawPassword, "$2a$10$18MPxEMxEG3rhuxcSCSeJ.JaAw0iiCRZrhfLGHyacsUrsDlG1qh4u");
		assertThat(encode).isTrue();
	}
}
