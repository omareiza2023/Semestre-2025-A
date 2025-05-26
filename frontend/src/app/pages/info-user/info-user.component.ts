// src/app/pages/info-user/info-user.component.ts

import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { Usuario } from './user';
import { UsuarioService } from '../../services/user.service';

@Component({
  selector: 'app-info-user',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './info-user.component.html',
  styleUrls: ['./info-user.component.scss']
})
export class InfoUserComponent implements OnInit {
  usuario: Usuario = new Usuario();
  private userId!: number;

  // Si tu API requiere Basic auth:
  username = 'admin';
  password = 'admin123';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private usuarioService: UsuarioService
  ) {}

  ngOnInit(): void {
    // 1) Obtener el ID desde la ruta: /info-user/:id
    this.userId = Number(this.route.snapshot.paramMap.get('id'));
    // 2) Cargar los datos
    this.cargarUsuario();
  }

  cargarUsuario(): void {
    this.usuarioService
      .obtenerUsuarioPorId(this.userId, this.username, this.password)
      .subscribe({
        next: (data) => {
          this.usuario = data;
        },
        error: (err) => {
          console.error('Error al cargar usuario', err);
          alert('No se pudo cargar la información del usuario.');
          // opcional: volver atrás o redirigir
        }
      });
  }

  guardarCambios() {
    alert("Cambios guardados exitosamente")
  }

  volver() {
    history.back();
  }
}