package br.edu.utfpr.caixaeletronico.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.edu.utfpr.caixaeletronico.model.dto.AccountDTO;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "account")
@NoArgsConstructor
@Data
public class Account {

    @Id
    @Column(name = "account")
    private String account;

    @Column
    private String agency;
    
    @Column
    private String user;

    public Account(String account, String agency, String user) {
    	this.account = account;
    	this.agency = agency;
    	this.user = user;
    }
    
    public Account(AccountDTO dto) {
    	this.account = dto.getAccount();
    	this.agency = dto.getAgency();
    	this.user = dto.getUser();
    }
    
    public void update(AccountDTO dto) {
    	this.account = dto.getAccount();
    	this.agency = dto.getAgency();
    	this.user = dto.getUser();
    }
    
}
