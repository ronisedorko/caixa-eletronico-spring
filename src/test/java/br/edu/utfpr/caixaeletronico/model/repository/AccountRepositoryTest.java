package br.edu.utfpr.caixaeletronico.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.edu.utfpr.caixaeletronico.model.entity.Account;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountRepositoryTest {

	@Autowired
	AccountRepository accountRepository;
	
	public AccountRepositoryTest() {
		
	}
	
	@Test
    public void delete() {
		Account account = new Account("56556-5", "1234-0", "Roni");
		this.accountRepository.save(account);
		account = accountRepository.getOne(account.getAccount());
		String id = account.getAccount();
		this.accountRepository.delete(account);
		
		Assertions.assertThat(accountRepository.findById(id)).isEmpty();
	}
}
