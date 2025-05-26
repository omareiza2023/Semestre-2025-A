import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CategoriasService } from '../../../services/categorias.service';

@Component({
  selector: 'app-crear-categoria',
  templateUrl: './crear-categoria.component.html',
  styleUrls: ['./crear-categoria.component.scss'],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule]
})
export class CrearCategoriaComponent {

  categoriaForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private categoriasService: CategoriasService,
    private router: Router
  ) {
    this.categoriaForm = this.fb.group({
      nombre: ['', Validators.required],
      descripcion: ['', [Validators.required]]
    });
  }

  guardar(): void {
    if (this.categoriaForm.valid) {
      const nuevaCategoria = {
        nombre: this.categoriaForm.value.nombre,
        descripcion: this.categoriaForm.value.descripcion
      };

      console.log('Enviando categoría:', nuevaCategoria);

      const username = 'admin';
      const password = 'admin123';

      this.categoriasService.createCategoria(nuevaCategoria, username, password).subscribe({
        next: (response) => {
          console.log('Respuesta exitosa:', response);
          this.router.navigate(['/categoria']);
        },
        error: (error) => {
          console.error('Error detallado:', error);
          if (error.status === 401) {
            console.error('Error de autenticación. Verifica las credenciales.');
          } else if (error.status === 404) {
            console.error('Ruta no encontrada. Verifica la URL del endpoint.');
          }
          // Aquí podrías mostrar un mensaje al usuario
        }
      });
    } else {
      console.log('Formulario inválido:', this.categoriaForm.errors);
    }
  }
}