import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {ExchangeRateService} from "../../services/exchange-rate.service";
import {ExchangeRate} from "../../models/exchange-rate";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-exchange-rate-dialog',
  templateUrl: './exchange-rate-dialog.component.html',
  styleUrls: ['./exchange-rate-dialog.component.scss']
})
export class ExchangeRateDialogComponent {

  constructor(private formBuilder: FormBuilder,
              private exchangeRateService: ExchangeRateService,
              public dialogRef: MatDialogRef<ExchangeRateDialogComponent>) { }

  public exchangeRateForm = this.formBuilder.group({
    currency: ['USD', [Validators.required]],
    sale: [null, [Validators.required, Validators.min(0)]],
    purchase: [null, [Validators.required, Validators.min(0)]]
  });

  public create(): void {
    this.exchangeRateService.createExchangeRate(this.exchangeRateForm.value).subscribe(
      (response: ExchangeRate) => {
        this.dialogRef.close([response] as ExchangeRate[]);
      }
    )
  }
}
