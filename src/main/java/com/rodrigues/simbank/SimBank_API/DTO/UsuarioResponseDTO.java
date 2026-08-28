package com.rodrigues.simbank.SimBank_API.DTO;

public class UsuarioResponseDTO {

    private Long rowid;
    private String nome;
    private String cpf;
    private String email;

    public UsuarioResponseDTO(){
    }

    public UsuarioResponseDTO(Long rowid, String nome, String cpf, String email) {
        this.rowid = rowid;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
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
}
