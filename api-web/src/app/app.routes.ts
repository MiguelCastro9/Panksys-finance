import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { Error404Component } from './errors/error-404/error-404.component';
import { SimpleFinanceComponent } from './simple-finance/simple-finance.component';
import { FeedbackComponent } from './feedback/feedback.component';
import { SignInComponent } from './sign-in/sign-in.component';
import { SignUpComponent } from './sign-up/sign-up.component';

export const routes: Routes = [

  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: 'sign-up', component: SignUpComponent },
  { path: 'sign-in', component: SignInComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'simple-finance', component: SimpleFinanceComponent },
  { path: 'feedback', component: FeedbackComponent },
  { path: '**', component: Error404Component }
];
