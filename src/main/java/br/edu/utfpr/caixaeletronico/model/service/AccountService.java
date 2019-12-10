package br.edu.utfpr.caixaeletronico.model.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.edu.utfpr.caixaeletronico.model.entity.Account;
import br.edu.utfpr.caixaeletronico.model.repository.AccountRepository;

@Service
public class AccountService {
	
	public static final Logger log = LoggerFactory.getLogger(AccountService.class);

	@Autowired
	AccountRepository accountRepository;

    public void init() {
    	Account a1 = new Account("56556-5", "1234-0", "Paulão");
    	Account a2 = new Account("03254-5", "3565-0", "João");
    	Account a3 = new Account("67845-2", "6753-2", "Felipe");
    	Account a4 = new Account("26856-4", "8965-8", "Maria");
    	Account a5 = new Account("45665-8", "8963-5", "Joana");
    	Account a6 = new Account("78236-2", "6783-6", "Luciana");
    	
    	List<Account> accounts = Arrays.asList(a1, a2, a3, a4, a5, a6);
    	log.info("Inicializando o BD com {} objetos da classe {}", accounts.size(), Account.class.toString());
        accountRepository.saveAll(accounts);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Account findByAccount(String account) {
        log.info("Buscando pela conta {}", account);
        return accountRepository.findByAccount(account);
    }
    
    public List<Account> findAll() {
    	return accountRepository.findAll();
    }
    
    public Page<Account> findAll(Pageable page) {
    	return accountRepository.findAll(page);
    }
    
    public Optional<Account> findById(String id) {
    	return accountRepository.findById(id);
    }
    
    public Account getOne(String id) {
    	return accountRepository.getOne(id);
    }
    
    public Account updateAccount(String account, String agency, String user) {
        Account accounts = accountRepository.findById(account).get();
        accounts.setAgency("4666-5");
        accounts.setUser("Roni");
        return accounts;
    }
    
    public void deleteById(String id) {
    	accountRepository.deleteById(id);
    }
    
    public Account save(Account account) throws DataIntegrityViolationException {
        log.info("Buscando a conta: {}", account);
        return accountRepository.save(account);
    }
	
}
