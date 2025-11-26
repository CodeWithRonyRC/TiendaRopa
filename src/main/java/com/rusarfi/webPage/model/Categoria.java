package com.rusarfi.webPage.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.util.List;

@EqualsAndHashCode(callSuper=true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name="tbl_categoria")
public class Categoria extends BaseEntity {
    @Column(name="nombre_categoria")
    private String nombre;
    @Column(name="descripcion_categoria")
    private String descripcion;
    @OneToMany(mappedBy = "categoria")
    private List<Producto> productos;
}
