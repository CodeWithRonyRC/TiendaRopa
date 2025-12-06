import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

/* import { NavbarComponent } from '../navbar/navbar';
import { FooterComponent } from '../footer/footer'; */

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './home.html',
  styleUrls: ['./home.css']
})
export class HomeComponent {}
