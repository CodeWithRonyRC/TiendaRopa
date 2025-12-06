package com.rusarfi.webPage.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;
@EqualsAndHashCode(callSuper=true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name="tbl_inventario")
public class Inventario extends BaseEntity{

    private Integer cantidad;
    private LocalDate ultimaActualizacion;
    @OneToMany(mappedBy = "inventario")
    List<Producto> productos;
}
