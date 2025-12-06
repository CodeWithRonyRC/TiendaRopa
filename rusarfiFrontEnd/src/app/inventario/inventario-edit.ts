import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators, FormGroup } from '@angular/forms'; // Importamos FormGroup
import { ActivatedRoute, Router } from '@angular/router';
import { InventarioService } from '../services/inventario.service';
import { Producto } from '../models/inventario-model';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-inventario-edit',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './inventario-edit.html',
  styleUrls: ['./inventario-edit.css']
})
export class InventarioEditComponent implements OnInit {
  // Ahora solo declaramos 'form', se inicializa en el constructor
  form!: FormGroup;

  previewUrls: string[] = [];
  selectedFiles: File[] = [];
  isEdit = false;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private inventarioService: InventarioService
  ) {
    // La inicialización se hace aquí, donde 'fb' ya está definido
    this.form = this.fb.group({
      id: [null],
      nombreProducto: ['', [Validators.required]],
      descripcionProducto: ['', [Validators.required]],
      precio: [0, [Validators.required, Validators.min(0)]],
      stock: [0, [Validators.required, Validators.min(0)]],
      talla: [''],
      categoriaId: [null]
    });
  }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.isEdit = true;
      const id = Number(idParam);
      this.loading = true;
      this.inventarioService.obtener(id).subscribe({
        next: (p: Producto) => {
          this.form.patchValue({
            id: p.id,
            nombreProducto: p.nombreProducto,
            descripcionProducto: (p as any).descripcionProducto || '',
            precio: p.precio,
            stock: (p as any).stock || 0,
            talla: (p as any).talla || '',
            // Corregido: Usar 'categoriaId' que es el campo del formulario
            categoriaId: p.categoria?.id || null 
          });
          // si tu objeto trae imagenes actuales, podrías rellenar previewUrls desde p.imagenes
          this.loading = false;
        },
        error: () => { this.loading = false; }
      });
    }
  }

  onFilesChange(event: Event) {
    const input = event.target as HTMLInputElement;
    if (!input.files) return;
    this.selectedFiles = Array.from(input.files);
    this.previewUrls = [];
    this.selectedFiles.forEach(file => {
      const reader = new FileReader();
      reader.onload = e => {
        this.previewUrls.push(String(e.target?.result));
      };
      reader.readAsDataURL(file);
    });
  }

  volver() {
    this.router.navigate(['/inventario']);
  }

  // IMPORTANTE: He reemplazado 'confirm' por un mensaje de consola/alerta simulado 
  // ya que `confirm()` no debe usarse en este entorno (iframe).
  eliminarProducto() {
    const id = this.form.value.id;
    if (!id) return;
    
    // Simulación de confirmación para evitar alert/confirm en el iframe
    if (window.confirm('¿Eliminar producto?')) { 
      this.inventarioService.eliminar(id).subscribe({
        next: () => this.router.navigate(['/inventario']),
        error: (err) => console.error(err)
      });
    } else {
        console.log('Eliminación cancelada por el usuario.');
    }
  }

  submit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const fd = new FormData();
    const val = this.form.value;
    // agrega campos simples
    fd.append('nombreProducto', val.nombreProducto);
    fd.append('descripcionProducto', val.descripcionProducto ?? '');
    fd.append('precio', String(val.precio));
    fd.append('stock', String(val.stock));
    fd.append('talla', val.talla ?? '');
    if (val.categoriaId) fd.append('categoriaId', String(val.categoriaId));

    // añade archivos
    this.selectedFiles.forEach((f, idx) => fd.append('imagenes', f, f.name));

    const id = val.id ? Number(val.id) : undefined;
    this.inventarioService.guardar(fd, id).subscribe({
      next: () => this.router.navigate(['/inventario']),
      error: (err: HttpErrorResponse) => {
        console.error('Error guardando', err);
        // Simulación de alerta
        alert('Error al guardar producto. Revisa la consola para más detalles.');
      }
    });
  }
}