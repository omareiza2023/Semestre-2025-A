
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

import { AgendarCitaComponent } from './pages/gestion-reservas/agendar-cita/agendar-cita.component';
import { ListaReservasComponent } from './pages/lista-reservas/lista-reservas.component';
import { CategoriasComponent } from './pages/gestion-categorias/categorias/categorias.component';
import { CrearCategoriaComponent } from './pages/gestion-categorias/crear-categoria/crear-categoria.component';
import { BarberosComponent } from './pages/gestion-barberos/barberos/barberos.component';
import { CrearBarberoComponent } from './pages/gestion-barberos/crear-barbero/crear-barbero.component';
import { EditarCategoriaComponent } from './pages/gestion-categorias/editar-categoria/editar-categoria.component';

import { AgendarCitaComponent } from './pages/agendar-cita/agendar-cita.component';
import { ListaReservasComponent } from './pages/lista-reservas/lista-reservas.component';




export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'view-client', component: ViewClientComponent },
  { path: 'view-admin', component: ViewAdminComponent },
  { path: 'servicios', component: ListaServiciosComponent },
  { path: 'crear-servicio', component: CrearServicioComponent },
  { path: 'tarifas', component: ActualizarServicioComponent },
  { path: 'info-empresa', component: InformacionEmpresaComponent},
  { path: 'info-user', component: InfoUserComponent},
  { path: 'view-client', component: ViewClientComponent },
  { path: 'agendar-cita', component: AgendarCitaComponent },
  { path: 'mis-reservas', component: ListaReservasComponent },

  { path: 'crear-categoria', component: CrearCategoriaComponent },
  { path: 'categoria', component: CategoriasComponent },
  { path: 'editar-categoria/:id', component: EditarCategoriaComponent },
  { path: 'barberos', component: BarberosComponent },
  { path: 'crear-barbero', component: CrearBarberoComponent },
];


