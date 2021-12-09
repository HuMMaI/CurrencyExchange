import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {ExchangeModule} from "./wallet/exchange.module";
import {CommonModule} from "@angular/common";
import {HttpClientModule} from "@angular/common/http";
import {UserModule} from "./user/user.module";
import {NgChartsModule} from "ng2-charts";
import { ExchangeRateDialogComponent } from './wallet/exchange-rate-dialog/exchange-rate-dialog.component';
import {MatDialogModule} from "@angular/material/dialog";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {ReactiveFormsModule} from "@angular/forms";

const pagesModules = [ExchangeModule, UserModule];

@NgModule({
  declarations: [
    AppComponent,
    ExchangeRateDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    CommonModule,
    HttpClientModule,
    NgChartsModule,
    ...pagesModules,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AppModule { }
