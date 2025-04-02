import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';

import { IonicModule } from '@ionic/angular'; // Importar IonicModule
import { AppRoutingModule } from './app.routes';

@NgModule({
  imports: [
    BrowserModule,
    IonicModule.forRoot(), // Asegurar que Ionic está correctamente importado
    AppRoutingModule
  ],
  providers: [],
})
export class AppModule {}