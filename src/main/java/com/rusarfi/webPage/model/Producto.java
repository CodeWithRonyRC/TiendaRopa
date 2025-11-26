package com.rusarfi.webPage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "tbl_producto")
public class Producto extends BaseEntity {

    public enum Talla { T04,T06,T08,T10,T12,T14,T16, S, M, L, XL }

    @Column(name = "nom_producto", length = 50, nullable = false)
    private String nombreProducto;

    @Column(name = "descrip_producto", length = 150, nullable = false)
    private String descripcionProducto;

    @Column(name = "precio_producto", nullable = false)
    private Double precio;

    @Column(name = "stock_producto", nullable = false)
    private Integer stock;

    @Enumerated(EnumType.STRING)
    @Column(name = "talla")
    private Talla talla;

    @ManyToOne
    @JoinColumn(name = "inventario_id")
    private Inventario inventario;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    // 🔹 Carga las imágenes solo cuando se necesitan (lazy) y evita nullpointer
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ImagenProducto> imagenProductos = new ArrayList<>();

    @Column(name = "codigo_modelo", length = 50)
    private String codigoModelo;
}
