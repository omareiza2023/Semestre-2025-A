import { Routes } from '@angular/router';
import { RegisterComponent } from './pages/register/register.component';
<<<<<<< HEAD
import { LoginComponent } from './pages/login/login.component';
import { ViewClientComponent } from './pages/view-client/view-client.component';
import { ViewAdminComponent } from './pages/view-admin/view-admin.component';
import { ListaServiciosComponent } from './pages/management-services/lista-servicios/lista-servicios.component';
import { CrearServicioComponent } from './pages/management-services/crear-servicios/crear-servicios.component';
import { ActualizarTarifaComponent } from './pages/management-services/actualizar-tarifas/actualizar-tarifas.component';
=======
>>>>>>> 8e31dd262735ffd8c31536bc6ca49848d71a4110

export const routes: Routes = [
  { path: '', redirectTo: 'register', pathMatch: 'full' },
  { path: 'register', component: RegisterComponent },
<<<<<<< HEAD
  { path: 'login', component: LoginComponent },
  { path: 'view-client', component: ViewClientComponent },
  { path: 'view-admin', component: ViewAdminComponent }, // Corregido el path
  { path: 'servicios', component: ListaServiciosComponent },
  { path: 'crear-servicio', component: CrearServicioComponent },
  { path: 'tarifas', component: ActualizarTarifaComponent },
];
=======
  // Puedes agregar el login luego
];
>>>>>>> 8e31dd262735ffd8c31536bc6ca49848d71a4110
