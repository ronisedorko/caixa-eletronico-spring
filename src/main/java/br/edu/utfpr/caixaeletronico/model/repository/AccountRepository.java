package br.edu.utfpr.caixaeletronico.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.edu.utfpr.caixaeletronico.model.entity.Account;

public interface AccountRepository extends JpaRepository<Account, String> {

	@Transactional(readOnly = true)
	Account findByAccount(String account);
	
}
