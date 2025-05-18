import { Routes } from '@angular/router';
import { RegisterComponent } from './pages/register/register.component';
<<<<<<< HEAD
<<<<<<< HEAD
import { LoginComponent } from './pages/login/login.component';
import { AuthComponent } from './auth/auth.component';
=======
>>>>>>> 059de6d (chore: commit inicial)
=======
=======
import { LoginComponent } from './pages/login/login.component';
import { AuthComponent } from './auth/auth.component';
>>>>>>> b213279 (feature/HU-04)
>>>>>>> c5bbe94 (feature/HU-04)

export const routes: Routes = [
  { path: '', redirectTo: 'register', pathMatch: 'full' },
  { path: 'register', component: RegisterComponent },
<<<<<<< HEAD
<<<<<<< HEAD
  { path: 'login', component: LoginComponent },
  { path: 'auth', component: AuthComponent },

];

=======
  // Puedes agregar el login luego
];
>>>>>>> 059de6d (chore: commit inicial)
=======
  // Puedes agregar el login luego
];
=======
  { path: 'login', component: LoginComponent },
  { path: 'auth', component: AuthComponent },

];

>>>>>>> b213279 (feature/HU-04)
>>>>>>> c5bbe94 (feature/HU-04)
