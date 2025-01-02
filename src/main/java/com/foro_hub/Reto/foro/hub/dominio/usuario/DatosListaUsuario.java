package com.foro_hub.Reto.foro.hub.dominio.usuario;


import com.foro_hub.Reto.foro.hub.dominio.direccion.DatosDireccion;

public class DatosListaUsuario {


    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;
    private DatosDireccion direccion;
    private String perfil;
    private String login;
    private String clave;
    private String fotoUrl;

    public DatosListaUsuario(Usuario usuario) {
        this.id = usuario.getId();
        this.nombre = usuario.getNombre();
        this.email = usuario.getEmail();
        this.telefono = usuario.getTelefono();
        this.documento = usuario.getDocumento();
        this.direccion = new DatosDireccion(
                usuario.getDireccion().getCalle(),
                usuario.getDireccion().getDistrito(),
                usuario.getDireccion().getCiudad(),
                usuario.getDireccion().getNumero(),
                usuario.getDireccion().getComplemento()
        );
        this.perfil = usuario.getPerfil();
        this.login = usuario.getLogin();
        this.clave = usuario.getClave();
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public DatosDireccion getDireccion() {
        return direccion;
    }

    public void setDireccion(DatosDireccion direccion) {
        this.direccion = direccion;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}
