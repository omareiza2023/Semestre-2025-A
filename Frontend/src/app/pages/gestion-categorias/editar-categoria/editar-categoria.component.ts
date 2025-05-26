import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-editar-categoria',
  templateUrl: './editar-categoria.component.html',
  styles: [`
    .formulario-container {
      max-width: 500px;
      margin: 2rem auto;
      padding: 2rem;
      background-color: #ffffff;
      border-radius: 8px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }
    
    form {
      display: flex;
      flex-direction: column;
      gap: 1rem;
    }
    
    label {
      font-weight: bold;
      margin-bottom: 0.5rem;
    }
    
    input {
      padding: 0.5rem;
      border: 1px solid #ccc;
      border-radius: 4px;
      font-size: 1rem;
    }
    
    input:focus {
      outline: none;
      border-color: #007bff;
    }
    
    .btn-guardar {
      background-color: #007bff;
      color: white;
      padding: 0.75rem;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      font-size: 1rem;
    }
    
    .btn-guardar:hover {
      background-color: #0056b3;
    }
    
    .btn-guardar:disabled {
      background-color: #ccc;
      cursor: not-allowed;
    }
  `],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule]
})
export class EditarCategoriaComponent implements OnInit {
  categoriaForm: FormGroup;
  categoriaId: number = 0;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.categoriaForm = this.fb.group({
      nombre: ['', Validators.required],
      precio: ['', [Validators.required, Validators.min(0)]]
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.categoriaId = Number(id);
      this.cargarCategoria();
    } else {
      console.error('No se proporcionó ID de categoría');
      this.router.navigate(['/categoria']);
    }
  }

  cargarCategoria(): void {
    const username = 'admin';
    const password = 'admin123';
    const headers = {
      'Authorization': 'Basic ' + btoa(`${username}:${password}`),
      'ngrok-skip-browser-warning': 'true'
    };

    this.http.get(`https://pelican-alert-ram.ngrok-free.app/api/categoriaservicios/${this.categoriaId}`, { headers })
      .subscribe({
        next: (categoria: any) => {
          this.categoriaForm.patchValue({
            nombre: categoria.nombre,
            precio: categoria.precio
          });
        },
        error: (error) => {
          console.error('Error al cargar la categoría:', error);
          this.router.navigate(['/categoria']);
        }
      });
  }

  guardar(): void {
    if (this.categoriaForm.valid) {
      const username = 'admin';
      const password = 'admin123';
      const headers = {
        'Authorization': 'Basic ' + btoa(`${username}:${password}`),
        'ngrok-skip-browser-warning': 'true'
      };

      const categoriaActualizada = {
        nombre: this.categoriaForm.value.nombre,
        precio: parseFloat(this.categoriaForm.value.precio)
      };

      this.http.put(
        `https://pelican-alert-ram.ngrok-free.app/api/categoriaservicios/${this.categoriaId}`,
        categoriaActualizada,
        { headers }
      ).subscribe({
        next: () => {
          console.log('Categoría actualizada exitosamente');
          this.router.navigate(['/categoria']);
        },
        error: (error) => {
          console.error('Error al actualizar la categoría:', error);
        }
      });
    }
  }
}