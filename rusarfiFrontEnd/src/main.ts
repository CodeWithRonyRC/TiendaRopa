import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { App } from './app/app';
import { NavbarComponent } from './app/navbar/navbar';
import { HomeComponent } from './app/home/home';
import { FooterComponent } from './app/footer/footer';

bootstrapApplication(App, appConfig)
  .catch((err) => console.error(err));
