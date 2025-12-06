// src/app/app.routes.ts
import { Routes } from '@angular/router';
import { HomeComponent } from './home/home';

export const routes: Routes = [
  { path: '', component: HomeComponent },

  {
    path: 'inventario',
    loadComponent: () => import('./inventario/inventario').then(m => m.InventarioComponent)
  },

  {
    path: 'inventario/nuevo',
    loadComponent: () => import('./inventario/inventario-edit').then(m => m.InventarioEditComponent)
  },

  {
    path: 'inventario/editar/:id',
    loadComponent: () => import('./inventario/inventario-edit').then(m => m.InventarioEditComponent)
  },

  {
  path: 'catalogo',
  loadComponent: () => import('./catalogo/catalogo').then(m => m.CatalogoComponent)
  },
  // Y si lo necesitas:
  {
    path: 'catalogo/:id', // Ruta para ver detalles de un producto
    // Reemplaza con el componente de detalles si existe
    loadComponent: () => import('./detalle-producto/detalle-producto').then(m => m.DetalleProductoComponent)
  },


  { path: '**', redirectTo: '' },
];
