import { Routes } from '@angular/router';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { InformacionEmpresaComponent } from './pages/informacion-empresa/informacion-empresa.component';
import { InfoUserComponent } from './pages/info-user/info-user.component';
import { ViewClientComponent } from './pages/view-client/view-client.component';
import { ViewAdminComponent } from './pages/view-admin/view-admin.component';


export const routes: Routes = [
  { path: '', redirectTo: 'register', pathMatch: 'full' },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'info-empresa', component: InformacionEmpresaComponent},
  { path: 'info-user', component: InfoUserComponent},
  { path: 'view-client', component: ViewClientComponent },
  { path: 'view-admin', component: ViewAdminComponent }
];
