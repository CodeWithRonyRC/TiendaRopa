import { Component, OnInit } from '@angular/core';
import { CommonModule, DecimalPipe } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { InventarioService } from '../services/inventario.service';
import { Producto, Categoria, ProductoFiltros } from '../models/inventario-model';
import { catchError, finalize, of, startWith, tap } from 'rxjs';

@Component({
  selector: 'app-catalogo',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule, DecimalPipe],
  templateUrl: './catalogo.html',
  styleUrls: ['./catalogo.css']
})
export class CatalogoComponent implements OnInit {
  pageTitle = 'Catálogo de Productos';
  productos: Producto[] = [];
  categorias: Categoria[] = [];
  filterForm: FormGroup;
  loading = false;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private inventarioService: InventarioService
  ) {
    // Inicialización del formulario de filtros
    this.filterForm = this.fb.group({
      categoria: [''],
      precioMax: [null],
      enStock: [false]
    });
  }

  ngOnInit(): void {
    this.loadCategorias();
    // Carga los productos iniciales sin filtros al inicio
    this.loadProductos(this.filterForm.value as ProductoFiltros);
  }
  
  loadCategorias(): void {
    this.inventarioService.listarCategorias().pipe(
      tap(cats => this.categorias = cats),
      catchError(err => {
        console.error('Error al cargar categorías:', err);
        return of([]);
      })
    ).subscribe();
  }

  loadProductos(filtros: ProductoFiltros): void {
    this.loading = true;
    this.errorMessage = null;

    this.inventarioService.listarProductos(filtros).pipe(
      tap(prods => this.productos = prods),
      catchError(err => {
        console.error('Error al cargar productos:', err);
        this.errorMessage = 'No se pudo conectar al servicio para cargar los productos.';
        this.productos = []; // Limpiar lista en caso de error
        return of([]);
      }),
      finalize(() => this.loading = false)
    ).subscribe();
  }

  applyFilters(): void {
    const filtros: ProductoFiltros = this.filterForm.value;
    this.loadProductos(filtros);
  }
}