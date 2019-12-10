package br.edu.utfpr.caixaeletronico.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.caixaeletronico.model.dto.AccountDTO;
import br.edu.utfpr.caixaeletronico.model.entity.Account;
import br.edu.utfpr.caixaeletronico.model.service.AccountService;
import br.edu.utfpr.caixaeletronico.util.Response;

@RestController
@RequestMapping("/conta")
@CrossOrigin(origins = "*")
public class AccountController {
	public static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    AccountService accountService;

    @Value("${pagina.quantidade}")
    private int paginationAmount;
    
    @GetMapping(value = "/inicializa")
    public void init() {
    	accountService.init();
    }
    
    @RequestMapping(method = GET)
    public List<Account> findAll() {
    	return accountService.findAll();
    }
    
    @GetMapping(value = "/paginacao-dto")
    public ResponseEntity<Response<List<AccountDTO>>> findAllPagination(
    		@RequestParam(value = "pag", defaultValue = "0") int page,
    		@RequestParam(value = "ord", defaultValue = "account") String order,
    		@RequestParam(value = "dir", defaultValue = "ASC")  String direction) {
    	
    	log.info("Buscando contas ordenadas por {}, página {}", order, page);

        Response<List<AccountDTO>> response = new Response<>();
        PageRequest pageRequest = PageRequest.of(page, this.paginationAmount, Sort.Direction.valueOf(direction), order);
    	
        Page<Account> accounts = this.accountService.findAll(pageRequest);
        Page<AccountDTO> accountsDTOs = accounts.map(a -> new AccountDTO(a));
        response.setData(accountsDTOs.getContent());
        return ResponseEntity.ok(response);
    }
    
    @GetMapping(value = "/paginacao-page")
    public ResponseEntity<Response<Page<AccountDTO>>> findAllPaginationWithPage(
    		@RequestParam(value = "pag", defaultValue = "0") int page,
    		@RequestParam(value = "ord", defaultValue = "account") String order,
    		@RequestParam(value = "dir", defaultValue = "ASC")  String direction) {

    	log.info("Buscando contas ordenadas por {}, página {}", order, page);

    	Response<Page<AccountDTO>> response = new Response<>();
    	PageRequest pageRequest = PageRequest.of(page, this.paginationAmount, Sort.Direction.valueOf(direction), order);
    	
    	Page<Account> accounts = this.accountService.findAll(pageRequest);
    	Page<AccountDTO> accountsDTOs = accounts.map(a -> new AccountDTO(a));
        response.setData(accountsDTOs);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping(value = "/paginacao-pageable")
    public ResponseEntity<Response<Page<AccountDTO>>> findAllPaginationWithPageable(
    		@PageableDefault(page=0, size=3, direction = Direction.ASC) Pageable pageable) {
    
    	Response<Page<AccountDTO>> response = new Response<>();
    	
    	Page<Account> accounts = this.accountService.findAll(pageable);
    	Page<AccountDTO> accountsDTOS = accounts.map(a -> new AccountDTO(a));
    	response.setData(accountsDTOS);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/conta/{account}")
    public ResponseEntity<Response<AccountDTO>> findByAccount(@PathVariable String account) {
    	Response<AccountDTO> response = new Response<>();
    	
    	Account a = accountService.findByAccount(account);
    	
    	if (a == null) {
            throw new EntityNotFoundException();
    	}
    	
    	AccountDTO dto = new AccountDTO(a);
    	response.setData(dto);
        log.info("usuário por conta {}", a);
        
        return ResponseEntity.ok(response);
    }
    
    @RequestMapping(value = "/{account}", method = GET)
    public ResponseEntity<Response<AccountDTO>> get(@PathVariable String account) {
    	Response<AccountDTO> response = new Response<>();
    	
    	Account accounts = accountService.getOne(account);
    	if (account == null) {
            response.addError("A conta " + account + " não foi encontrada");
            return ResponseEntity.badRequest().body(response);
    	}
    	
    	AccountDTO accountDTO = new AccountDTO(accounts);
    	response.setData(accountDTO);
    	return ResponseEntity.ok(response);
    }
    
    @PutMapping(value = "/{account}")
    public ResponseEntity<?> put(@PathVariable String account, @Valid @RequestBody AccountDTO dto, BindingResult result) {
    	
    	Response<AccountDTO> response = new Response<>();
    	if (result.hasErrors()) {
    		response.setErrors(result);
    		return ResponseEntity.badRequest().body(response);
    	}
    	
    	Optional<Account> o = accountService.findById(account);
    	if (!o.isPresent()) {
            response.addError("Conta não encontrada");
            return ResponseEntity.badRequest().body(response);
    	}
    	
    	Account a = o.get();
    	a.update(dto);
    	a = accountService.save(a);
    	dto = new AccountDTO(a);
    	response.setData(dto);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping
    public ResponseEntity<Response<AccountDTO>> post(@Valid @RequestBody AccountDTO dto, BindingResult result) {
    	
    	Response<AccountDTO> response = new Response<>();
    	if (result.hasErrors()) {
    		response.setErrors(result);
            return ResponseEntity.badRequest().body(response);
    	}
    	
    	if (dto.getAccount() != null) {
    		Optional<Account> o = accountService.findById(dto.getAccount());
    		if (o.isPresent()) {
    			response.addError("Conta já cadastrada");
                return ResponseEntity.badRequest().body(response);			
    		}
    	}
    	
    	Account a = new Account(dto);
    	a = accountService.save(a);
    	dto = new AccountDTO(a);
        response.setData(dto);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping(value = "/{account}")
    public ResponseEntity<Response<String>> delete(@PathVariable String account) {
        log.info("Removendo a conta {}", account);
        
        Response<String> response = new Response<>();
        Optional<Account> o = accountService.findById(account);
        
        if (!o.isPresent()) {
            log.info("Erro ao remover");
            response.addError("Erro ao remover, registro não encontrado para a conta " + account);
            return ResponseEntity.badRequest().body(response);
        }
        
        this.accountService.deleteById(account);
        return ResponseEntity.ok(response);
    }

}
