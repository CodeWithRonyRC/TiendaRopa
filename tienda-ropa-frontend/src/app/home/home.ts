import { Component } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar';
import { FooterComponent } from '../footer/footer';

@Component({
  selector: 'app-home',
  standalone: true,
  templateUrl: './home.html',
  styleUrls: ['./home.css'],
  imports: [NavbarComponent, FooterComponent]   // 👈 aquí los agregas
})
export class HomeComponent {}
