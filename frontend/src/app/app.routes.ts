import { Routes } from '@angular/router';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { InformacionEmpresaComponent } from './pages/informacion-empresa/informacion-empresa.component';
import { InfoUserComponent } from './pages/info-user/info-user.component';


export const routes: Routes = [
  { path: '', redirectTo: 'register', pathMatch: 'full' },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'info-empresa', component: InformacionEmpresaComponent},
  { path: 'info-user', component: InfoUserComponent}

];

