import { Component } from '@angular/core';
import {IONIC_STANDALONE_COMPONENTS} from './shared/ionic-standalone';
import { home } from 'ionicons/icons';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    ...IONIC_STANDALONE_COMPONENTS,
  ],
  templateUrl: './app.component.html',
})
export class AppComponent {
  icons = {
    home,
  };
  title = 'app-compras';
}
