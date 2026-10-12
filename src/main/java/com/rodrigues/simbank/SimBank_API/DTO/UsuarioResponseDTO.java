package com.rodrigues.simbank.SimBank_API.DTO;

import com.rodrigues.simbank.SimBank_API.Utilidades.TipoRole;

public class UsuarioResponseDTO {

    private Long rowid;
    private String nome;
    private String cpf;
    private String email;
    private TipoRole role;

    public UsuarioResponseDTO(){
    }

    public UsuarioResponseDTO(Long rowid, String nome, String cpf, String email, TipoRole role) {
        this.rowid = rowid;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.role = role;
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

    public TipoRole getRole() {
        return role;
    }

    public void setRole(TipoRole role) {
        this.role = role;
    }
}
