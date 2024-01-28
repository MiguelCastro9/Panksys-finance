import { Component } from '@angular/core';
import { MenuComponent } from '../menu/menu.component';

@Component({
  selector: 'app-simple-finance',
  standalone: true,
  imports: [MenuComponent],
  templateUrl: './simple-finance.component.html',
  styleUrl: './simple-finance.component.css'
})
export class SimpleFinanceComponent {

}
