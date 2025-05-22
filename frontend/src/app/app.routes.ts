import { Routes } from '@angular/router';
import { RegisterComponent } from './pages/register/register.component';
import { AgendarCitaComponent } from './pages/agendar-cita/agendar-cita.component';
import { ListaReservasComponent } from './pages/lista-reservas/lista-reservas.component';

export const routes: Routes = [
  { path: '', redirectTo: 'register', pathMatch: 'full' },
  { path: 'register', component: RegisterComponent },
  { path: 'agendar', component: AgendarCitaComponent },
  { path: 'agendar-cita', component: AgendarCitaComponent },
  { path: 'mis-reservas', component: ListaReservasComponent },
];