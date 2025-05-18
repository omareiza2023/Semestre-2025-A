import { Routes } from '@angular/router';
import { RegisterComponent } from './pages/register/register.component';
import { AgendarCitaComponent } from './pages/agendar-cita/agendar-cita.component';

export const routes: Routes = [
  { path: '', redirectTo: 'register', pathMatch: 'full' },
  { path: 'register', component: RegisterComponent },
  { path: 'agendar', component: AgendarCitaComponent }
];