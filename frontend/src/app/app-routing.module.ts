import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {EmailValidate} from "./services/email-validate";

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'user'},
  {path: 'wallet', loadChildren: () => import('./exchange/exchange.module').then(m => m.ExchangeModule), canActivate: [EmailValidate]},
  {path: 'user', loadChildren: () => import('./user/user.module').then(m => m.UserModule)}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
