import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { personOutline, mailOutline, lockClosedOutline } from 'ionicons/icons';
import { IONIC_STANDALONE_COMPONENTS } from '../../shared/ionic-standalone';
import { AuthService } from '../../services/auth.service';
import { HttpClientModule } from '@angular/common/http';
import Swal from 'sweetalert2';

@Component({
    standalone: true,
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss'],
    imports: [
        CommonModule,
        ReactiveFormsModule,
        RouterLink,
        HttpClientModule,
        ...IONIC_STANDALONE_COMPONENTS
    ]
})
export class RegisterComponent {
    icons = {
        personOutline,
        mailOutline,
        lockClosedOutline,
    };

    form: FormGroup;

    constructor(
        private fb: FormBuilder,
        private authService: AuthService,
        private router: Router
    ) {
        this.form = this.fb.group({
            nombre: ['', Validators.required],
            apellido: ['', Validators.required],
            documento: ['', Validators.required],
            telefono: ['', Validators.required],
            email: ['', [Validators.required, Validators.email]],
            password: ['', Validators.required],
            confirmPassword: ['', Validators.required],
        });
    }


    submit() {
        if (this.form.invalid || this.form.value.password !== this.form.value.confirmPassword) {
            alert("Datos inválidos: Revisa que todos los campos estén completos y las contraseñas coincidan");
            return;
        }

        const data = {
            username: this.form.value.email,
            password: this.form.value.password,
            persona: {
                nombre: this.form.value.nombre,
                apellido: this.form.value.apellido,
                documento: this.form.value.documento,
                telefono: this.form.value.telefono,
            }
        };

        this.authService.registrarCliente(data).subscribe({
            next: (res) => {
                alert("Registro exitoso: Tu cuenta ha sido creada correctamente.");
                this.form.reset(); // Limpia el formulario
                this.router.navigate(['/login']);
            },
            error: (err) => {
                alert("Error: Hubo un problema al registrar el usuario.")
                console.error('Error en el registro:', err);
            }
        });
    }
}
