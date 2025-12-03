package com.rusarfi.webPage.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
@EqualsAndHashCode(callSuper=true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name="tbl_usuario")

public class Usuario extends BaseEntity{
	
    public enum Rol{MANTENEDOR,ADMIN, CLIENTE}
    
    @Column(name="nom_usuario", length=50, nullable=false)
    private String nombreUsuario;
    
    @Column(name="ape_usuario", length=50, nullable=false)
    private String apellidoUsuario;
    
    @Column(name="direccion", length=200, nullable=false)
    private String direccion;
    
    @Column(name="email",length=100, nullable=false)
    private String emailUsuario;
    
    @Column(name="password",length=254, nullable=false)
    private String passwordUsuario;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Rol", nullable=false)
    private Rol rol;
}
