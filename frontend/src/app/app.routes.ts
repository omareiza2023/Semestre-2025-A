
import { Routes } from '@angular/router';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { ViewClientComponent } from './pages/view-client/view-client.component';
import { ListaServiciosComponent } from './pages/management-services/lista-servicios/lista-servicios.component';
import { CrearServicioComponent } from './pages/management-services/crear-servicios/crear-servicios.component';
import { InformacionEmpresaComponent } from './pages/informacion-empresa/informacion-empresa.component';
import { InfoUserComponent } from './pages/info-user/info-user.component';
import { ViewAdminComponent } from './pages/view-admin/view-admin.component';
import { ActualizarServicioComponent } from './pages/management-services/actualizar-tarifas/actualizar-tarifas.component';
import { InfoUserComponent } from './pages/info-user/info-user.component';


export const routes: Routes = [
  { path: '', redirectTo: 'register', pathMatch: 'full' },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'view-client', component: ViewClientComponent },
  { path: 'view-admin', component: ViewAdminComponent },
  { path: 'servicios', component: ListaServiciosComponent },
  { path: 'crear-servicio', component: CrearServicioComponent },
  { path: 'tarifas', component: ActualizarTarifaComponent },
  { path: 'info-empresa', component: InformacionEmpresaComponent},
  { path: 'info-user', component: InfoUserComponent},
  { path: 'view-client', component: ViewClientComponent },
  { path: 'view-admin', component: ViewAdminComponent }
];


