import { Routes } from '@angular/router';
import { RegisterComponent } from './pages/register/register.component';
<<<<<<< HEAD
import { LoginComponent } from './pages/login/login.component';
import { AuthComponent } from './auth/auth.component';
=======
>>>>>>> 059de6d (chore: commit inicial)

export const routes: Routes = [
  { path: '', redirectTo: 'register', pathMatch: 'full' },
  { path: 'register', component: RegisterComponent },
<<<<<<< HEAD
  { path: 'login', component: LoginComponent },
  { path: 'auth', component: AuthComponent },

];

=======
  // Puedes agregar el login luego
];
>>>>>>> 059de6d (chore: commit inicial)
