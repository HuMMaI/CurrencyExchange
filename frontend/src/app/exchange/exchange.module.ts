import { NgModule } from '@angular/core';
import { ExchangeComponent } from './exchange.component';
import { ExchangeRoutingModule } from './exchange-routing.module';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { ReactiveFormsModule } from '@angular/forms';
import { NgChartsModule } from 'ng2-charts';
import { PieChartComponent } from './pie-chart/pie-chart.component';

@NgModule({
  declarations: [ExchangeComponent, PieChartComponent],
  imports: [
    ExchangeRoutingModule,
    MatCardModule,
    MatButtonModule,
    CommonModule,
    MatTableModule,
    MatInputModule,
    MatIconModule,
    MatChipsModule,
    ReactiveFormsModule,
    NgChartsModule
  ]
})
export class ExchangeModule {

}
