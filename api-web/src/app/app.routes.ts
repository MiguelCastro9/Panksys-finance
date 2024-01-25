import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { Error404Component } from './errors/error-404/error-404.component';
import { SimpleFinanceComponent } from './simple-finance/simple-finance.component';

export const routes: Routes = [

  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'simple-finance', component: SimpleFinanceComponent },
  { path: '**', component: Error404Component }
];