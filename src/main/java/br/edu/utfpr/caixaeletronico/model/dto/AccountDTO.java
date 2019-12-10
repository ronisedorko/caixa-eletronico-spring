package br.edu.utfpr.caixaeletronico.model.dto;

import javax.validation.constraints.NotEmpty;

import br.edu.utfpr.caixaeletronico.model.entity.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AccountDTO {
	
	private String account;

    @NotEmpty(message = "A agência não pode ser vazia")
    private String agency;

    @NotEmpty(message = "O usuário não pode ser vazio")
    private String user;
    
    public AccountDTO(String account, String agency, String user) {
    	this.account = account;
    	this.agency = agency;
    	this.user = user;
    }
    
    public AccountDTO(Account account) {
    	this.account = account.getAccount();
    	this.agency = account.getAgency();
    	this.user = account.getUser();
	}
}
