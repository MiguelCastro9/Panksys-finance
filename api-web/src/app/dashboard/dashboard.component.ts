import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import { MenuComponent } from '../menu/menu.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [MenuComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})

export class DashboardComponent implements OnInit {

  constructor() {
    Chart.register(...registerables);
   }

   @ViewChild("total_finances", {static: true}) element_total_finances!: ElementRef;
   @ViewChild("monthly_finances", {static: true}) element_monthly_finances!: ElementRef;

   ngOnInit(): void {

    new Chart(this.element_total_finances.nativeElement, {
      type: 'pie',
      data: {
        labels: ["Comédia","Romance","Ação","Terror", "Drama"],
        datasets: [{
          data: [10, 20, 30, 40, 50]
        }]
      },
      options: {
        responsive: true
      }
    });

    new Chart(this.element_monthly_finances.nativeElement, {
      type: 'pie',
      data: {
        labels: ["Comédia","Romance","Ação","Terror", "Drama"],
        datasets: [{
          data: [400, 30, 300, 400, 500]
        }]
      },
      options: {
        responsive: true
      }
    });
   }
}
