import { Component, OnInit } from '@angular/core';
import { CommonModule, DecimalPipe } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { InventarioService } from '../services/inventario.service';
import { Producto } from '../models/inventario-model';
import { catchError, finalize, of, switchMap, tap } from 'rxjs';

@Component({
  selector: 'app-detalle-producto',
  standalone: true,
  imports: [CommonModule, RouterModule, DecimalPipe],
  templateUrl: './detalle-producto.html',
  styleUrls: ['./detalle-producto.css'] // Asumiendo que existe un archivo CSS o está vacío
})
export class DetalleProductoComponent implements OnInit {
  producto: Producto | null = null;
  loading = true;
  errorMessage: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private inventarioService: InventarioService
  ) {}

  ngOnInit(): void {
    this.route.params.pipe(
      // 1. Extrae el 'id' de la URL
      tap(() => {
        this.loading = true;
        this.errorMessage = null;
        this.producto = null;
      }),
      switchMap(params => {
        const id = +params['id']; // El '+' convierte la cadena a número
        if (isNaN(id)) {
          this.errorMessage = 'ID de producto inválido.';
          return of(null);
        }
        // 2. Llama al servicio para obtener el producto
        return this.inventarioService.obtener(id).pipe(
          catchError(err => {
            console.error('Error al cargar el producto:', err);
            this.errorMessage = 'El producto no fue encontrado o hubo un error de conexión.';
            return of(null);
          })
        );
      }),
      finalize(() => this.loading = false)
    ).subscribe(prod => {
      // 3. Asigna el producto si fue exitoso
      if (prod) {
        this.producto = prod;
      }
    });
  }
}