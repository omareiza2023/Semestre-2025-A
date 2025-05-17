    import { Component } from '@angular/core';
    import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
    import { CommonModule } from '@angular/common';
    import { RouterLink } from '@angular/router';
    import { personOutline  } from 'ionicons/icons';

    import { IONIC_STANDALONE_COMPONENTS } from '../../shared/ionic-standalone';

    @Component({
    standalone: true,
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss'],
    imports: [
        CommonModule,
        ReactiveFormsModule,
        RouterLink,
        ...IONIC_STANDALONE_COMPONENTS,
    ]
    })
    export class RegisterComponent {
        icons = {
        personOutline 
    };
    form: FormGroup;

    constructor(private fb: FormBuilder) {
        this.form = this.fb.group({
        nombre: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        password: ['', Validators.required],
        confirmPassword: ['', Validators.required],
        });
    }

    submit() {
        if (this.form.valid) {
        console.log(this.form.value);
        }
    }
    }
