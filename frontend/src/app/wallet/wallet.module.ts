import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {WalletComponent} from "./wallet.component";
import {WalletRoutingModule} from "./wallet-routing.module";
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import {CommonModule} from "@angular/common";
import {MatTableModule} from "@angular/material/table";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {MatChipsModule} from "@angular/material/chips";
import {ReactiveFormsModule} from "@angular/forms";
import {NgChartsModule} from "ng2-charts";
import {PieChartComponent} from "./pie-chart/pie-chart.component";

@NgModule({
  declarations: [WalletComponent, PieChartComponent],
  imports: [
    WalletRoutingModule,
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
export class WalletModule {

}
