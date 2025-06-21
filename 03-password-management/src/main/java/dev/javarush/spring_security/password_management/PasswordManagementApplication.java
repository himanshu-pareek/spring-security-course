package dev.javarush.spring_security.password_management;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class PasswordManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(PasswordManagementApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			StringKeyGenerator keyGenerator = KeyGenerators.string();
			String salt = keyGenerator.generateKey();
			System.out.println("salt - " + salt);

			String password = "password";

			String text = "Hello, world";

			TextEncryptor encryptor = Encryptors.text(password, salt);
			String encrypted = encryptor.encrypt(text);
			String decrypted = encryptor.decrypt(encrypted);

			System.out.println("encrypted - " + encrypted);
			System.out.println("decrypted - " + decrypted);
		};
	}

	@Bean
	CommandLineRunner runner2() {
		return args -> {
			BytesKeyGenerator keyGenerator = KeyGenerators.shared(16);
			byte[] salt1 = keyGenerator.generateKey();
			int salt1Length = keyGenerator.getKeyLength();

			byte[] salt2 = keyGenerator.generateKey();
			int salt2Length = keyGenerator.getKeyLength();

			StringKeyGenerator keyGen = KeyGenerators.string();
			String salt = keyGen.generateKey();

			System.out.println("salt1 - " + Collections.singletonList(salt1));
			System.out.println("salt2 - " + Collections.singletonList(salt2));

			String password = "password";

			String text = "Hello, world";

			BytesEncryptor encryptor = Encryptors.standard(password, salt);
			byte[] encrypted = encryptor.encrypt(text.getBytes());
			byte[] decrypted = encryptor.decrypt(encrypted);

			System.out.println("encrypted - " + Collections.singletonList(encrypted));
			System.out.println("decrypted - " + Collections.singletonList(decrypted));
			System.out.println("decrypted str - " + new String(decrypted));

		};
	}

}
