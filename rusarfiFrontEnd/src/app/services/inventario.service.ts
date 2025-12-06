import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Producto, Categoria, ProductoFiltros } from '../models/inventario-model'; 

@Injectable({ providedIn: 'root' })
export class InventarioService {
  private baseUrl = '/api/productos'; // URL base para productos
  private categoriaUrl = '/api/categorias'; // URL base para categorías

  constructor(private http: HttpClient) {}

  // ------------------------------------------------
  // MÉTODOS CRUD (PARA INVENTARIO Y EDICIÓN)
  // ------------------------------------------------

  /** Lista todos los productos (generalmente para la vista CRUD de Inventario). */
  listar(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.baseUrl);
  }

  /** Obtiene un producto específico por ID (para Detalle y Edición). */
  obtener(id: number): Observable<Producto> {
    return this.http.get<Producto>(`${this.baseUrl}/${id}`);
  }

  /** Elimina un producto por ID. */
  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  /**
   * Guarda o actualiza un producto usando FormData (manejo de imágenes).
   * @param formData Contiene los datos del producto y archivos de imagen.
   * @param id ID del producto si es una actualización, o undefined si es nuevo.
   */
  guardar(formData: FormData, id?: number): Observable<Producto> {
    if (id) {
      // PUT para editar
      return this.http.put<Producto>(`${this.baseUrl}/${id}`, formData);
    } else {
      // POST para crear
      return this.http.post<Producto>(this.baseUrl, formData);
    }
  }

  // ------------------------------------------------
  // MÉTODOS DE CONSULTA (PARA CATÁLOGO)
  // ------------------------------------------------

  /**
   * Obtiene la lista de productos aplicando filtros para el catálogo.
   * Construye parámetros de consulta HTTP (query params) a partir del objeto de filtros.
   * @param filtros Objeto con los criterios de filtrado (categoría, precioMax, enStock).
   */
  listarProductos(filtros: ProductoFiltros): Observable<Producto[]> {
    let params = new HttpParams();

    // Agregar filtros solo si están definidos
    if (filtros.categoria) {
      params = params.set('categoriaId', filtros.categoria.toString());
    }
    if (filtros.precioMax) {
      params = params.set('precioMax', filtros.precioMax.toString());
    }
    if (filtros.enStock) {
      params = params.set('enStock', filtros.enStock.toString());
    }

    return this.http.get<Producto[]>(this.baseUrl, { params });
  }
  
  /**
   * Obtiene todas las categorías disponibles.
   */
  listarCategorias(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(this.categoriaUrl);
  }
}