package com.rodrigues.simbank.SimBank_API.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rowid;

    private String nome;
    private String cpf;
    private String email;
    private String senha;

    public Usuario() {
    }

    public Usuario(Long rowid, String nome, String cpf, String email, String senha) {
        this.rowid = rowid;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha; // refatorar mais tarde
    }

    public Long getRowid() {
        return rowid;
    }

    public void setRowid(Long rowid) {
        this.rowid = rowid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
