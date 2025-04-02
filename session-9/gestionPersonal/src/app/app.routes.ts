import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePage } from './home/home.page';
import { MedicoComponent } from './medico/medico.component';
import { EnfermeroComponent } from './enfermero/enfermero.component';
import { RecepcionistaComponent } from './recepcionista/recepcionista.component';
import { PacienteComponent } from './paciente/paciente.component';

export const routes: Routes = [
  { path: '', component: HomePage },
  { path: 'medico', component: MedicoComponent },
  { path: 'enfermero', component: EnfermeroComponent },
  { path: 'recepcionista', component: RecepcionistaComponent },
  { path: 'paciente', component: PacienteComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
