// src/app/inventario/inventario.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule  } from '@angular/router';
import { InventarioService } from '../services/inventario.service';
import { Producto } from '../models/inventario-model';

@Component({
  selector: 'app-inventario',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './inventario.html',
  styleUrls: ['./inventario.css']
})
export class InventarioComponent implements OnInit {
  productos: Producto[] = [];
  loading = true;

  constructor(private inventarioService: InventarioService, private router: Router) {}

  ngOnInit(): void {
    this.cargar();
  }

  cargar() {
    this.loading = true;
    this.inventarioService.listar().subscribe({
      next: (p) => {
        this.productos = p;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar inventario', err);
        this.productos = [];
        this.loading = false;
      }
    });
  }

  editar(id: number) {
    this.router.navigate(['/inventario/editar', id]);
  }

  borrar(id: number) {
    if (!confirm('¿Borrar producto?')) return;
    this.inventarioService.eliminar(id).subscribe({
      next: () => this.cargar(),
      error: (err) => console.error('Error borrando', err)
    });
  }
}



