// Definición de Tallas basada en el Enum de Java
export type Talla = 'T04' | 'T06' | 'T08' | 'T10' | 'T12' | 'T14' | 'T16' | 'S' | 'M' | 'L' | 'XL';

/**
 * Interface para los campos de control de auditoría heredados de BaseEntity.
 */
interface BaseEntity {
  id: number;
  createdAt?: string; // Usamos string para representar LocalDateTime
  lastModifiedAt?: string;
  createdBy?: string;
  lastModifiedBy?: string;
}

/**
 * Interface para representar las imágenes de un producto.
 * Nota: El backend tiene una entidad ImagenProducto.
 */
export interface ImagenProducto {
  id: number;
  urlImagen: string; // URL o base64 de la imagen
  productoId?: number; // Referencia al producto
}

/**
 * Interface para la entidad Categoria, hereda de BaseEntity.
 */
export interface Categoria extends BaseEntity {
  nombre: string;
  descripcion?: string;
  // La lista de productos no es necesaria en el frontend
}

/**
 * Interface para la entidad Producto, hereda de BaseEntity.
 */
export interface Producto extends BaseEntity {
  nombreProducto: string;
  descripcionProducto: string;
  precio: number;
  stock: number;
  talla: Talla;
  codigoModelo?: string;

  // Relaciones
  categoria: Categoria;
  imagenProductos: ImagenProducto[]; // Array de objetos ImagenProducto
}

/**
 * Interface para la estructura de filtros en el catálogo.
 */
export interface ProductoFiltros {
  categoria?: number | string | null;
  precioMax?: number | null;
  enStock?: boolean | null;
}