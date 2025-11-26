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
@Table(name="tbl_imagen_producto")
public class ImagenProducto extends BaseEntity {
    @Column(name = "url_imagen", nullable = false)
    private String urlImagen;
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
}
