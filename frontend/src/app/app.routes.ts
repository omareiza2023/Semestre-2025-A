import { Routes } from '@angular/router';
import { RegisterComponent } from './pages/register/register.component';
<<<<<<< HEAD
=======
import { LoginComponent } from './pages/login/login.component';
import { AuthComponent } from './auth/auth.component';
>>>>>>> feature/HU-04

export const routes: Routes = [
  { path: '', redirectTo: 'register', pathMatch: 'full' },
  { path: 'register', component: RegisterComponent },
<<<<<<< HEAD
  // Puedes agregar el login luego
];
=======
  { path: 'login', component: LoginComponent },
  { path: 'auth', component: AuthComponent },

];

>>>>>>> feature/HU-04
