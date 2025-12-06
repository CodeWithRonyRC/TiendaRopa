// Definición de Tallas basada en el Enum de Java (tallas para niños y adultos)
export type Talla = 'T04' | 'T06' | 'T08' | 'T10' | 'T12' | 'T14' | 'T16' | 'S' | 'M' | 'L' | 'XL';

/**
 * Interface para los campos de control de auditoría heredados de BaseEntity (Java).
 * Contiene campos de metadatos de creación y modificación.
 */
interface BaseEntity {
  id: number;
  createdAt?: string; // Fecha y hora de creación
  lastModifiedAt?: string; // Fecha y hora de última modificación
  createdBy?: string; // Usuario que creó
  lastModifiedBy?: string; // Usuario que modificó
}

/**
 * Interface para representar las imágenes de un producto.
 * Mapea la entidad ImagenProducto del backend.
 */
export interface ImagenProducto extends BaseEntity {
  urlImagen: string; // URL pública de la imagen
  productoId?: number; // Referencia al producto
}

/**
 * Interface para la entidad Categoria, hereda de BaseEntity.
 */
export interface Categoria extends BaseEntity {
  nombre: string;
  descripcion?: string;
  // La lista de productos no se incluye en el frontend para evitar ciclos infinitos
}

/**
 * Interface para la entidad Producto, hereda de BaseEntity.
 * Representa la información detallada de un artículo del inventario.
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