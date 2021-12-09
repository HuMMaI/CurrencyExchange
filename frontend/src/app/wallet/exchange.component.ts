import { Component, OnInit } from '@angular/core';
import { ExchangeRate } from '../models/exchange-rate';
import { WalletService } from '../services/wallet.service';
import { Wallet } from '../models/wallet';
import { ExchangeRateService } from '../services/exchange-rate.service';
import { Exchange } from '../models/exchange';
import { ExchangeService } from '../services/exchange.service';
import { FormBuilder, Validators } from '@angular/forms';
import { User } from '../models/user';
import { UserService } from '../services/user.service';
import { CountReport } from '../models/count-report';
import { ReportService } from '../services/report.service';
import { ExchangeEventReport } from '../models/exchange-event-report';
import { MatDialog } from '@angular/material/dialog';
import { ExchangeRateDialogComponent } from './exchange-rate-dialog/exchange-rate-dialog.component';

@Component({
  selector: 'app-wallet',
  templateUrl: './exchange.component.html',
  styleUrls: ['./exchange.component.scss']
})
export class ExchangeComponent implements OnInit {
  public exist = true;
  public uahWallet?: Wallet;
  public usdWallet?: Wallet;
  public exchangeRate: ExchangeRate[];
  public user?: User;
  public isEventSelected = false;
  public isExchangeRateExist = true;

  public displayedColumns: string[] = ['currency', 'sale', 'purchase'];
  public dataSource: ExchangeRate[];

  public successReportColumns: string[] = ['email', 'event', 'firstSum', 'firstCurrency', 'secondSum', 'secondCurrency'];
  public successReportDataSource: Exchange[];
  public failedReportColumns: string[] = ['email', 'event', 'firstSum', 'firstCurrency', 'secondCurrency'];
  public failedReportDataSource: Exchange[];

  public countReportLabels: string[] = [];
  public exchangeEventReportLabels: string[] = [];
  public countReportData: number[] = [];
  public exchangeEventReportData: number[] = [];

  constructor(private walletService: WalletService,
              private exchangeRateService: ExchangeRateService,
              private exchangeService: ExchangeService,
              private formBuilder: FormBuilder,
              private userService: UserService,
              private reportService: ReportService,
              private dialog: MatDialog) {
    this.dataSource = [];
    this.exchangeRate = [];
    this.successReportDataSource = [];
    this.failedReportDataSource = [];
  }

  public exchangeModel = this.formBuilder.group({
    event: ['', [Validators.required]],
    firstCurrency: ['USD', [Validators.required]],
    firstSum: [null, [Validators.required, Validators.min(0)]],
    secondCurrency: ['UAH', [Validators.required]],
    secondSum: [null, [Validators.required, Validators.min(0)]],
    email: ['', [Validators.required, Validators.email]]
  });

  ngOnInit(): void {
    if (!sessionStorage.getItem('user')) {
      const email = <string>sessionStorage.getItem('email');
      this.userService.getByEmail(email).subscribe(
        (user: User) => {
          this.exchangeModel.controls['email'].setValue(user.email);
          this.user = user;
          sessionStorage.removeItem('email');
          sessionStorage.setItem('user', JSON.stringify(user));
        }
      )
    } else {
      this.user = JSON.parse(<string>sessionStorage.getItem('user'));
      this.exchangeModel.controls['email'].setValue(this.user?.email);
    }

    this.fetchWallets();

    this.exchangeRateService.getCurrentExchangeRate().subscribe(
      (response: ExchangeRate[]) => {
        this.exchangeRate = response;
        this.dataSource = response;

        if (!response.length) {
          this.isExchangeRateExist = false;
        }
      }
    );

    this.reportsInit();
  }

  public reportsInit(): void {
    this.reportService.getCountReport().subscribe(
      (response: CountReport) => {
        this.countReportLabels = ['Successful', 'Failed'];
        this.countReportData = [response.successful, response.failed];
      }
    );

    this.reportService.getExchangeEventReport().subscribe(
      (response: ExchangeEventReport) => {
        this.exchangeEventReportLabels = ['Sold', 'Purchase'];
        this.exchangeEventReportData = [response.sold, response.purchase];
      }
    );

    this.reportService.getCurrencyReport(true).subscribe(
      (response: Exchange[]) => {
        this.successReportDataSource = response;
      }
    );

    this.reportService.getCurrencyReport(false).subscribe(
      (response: Exchange[]) => {
        this.failedReportDataSource = response;
      }
    );
  }

  public fetchWallets(): void {
    this.walletService.getWallets(this.user?.email).subscribe(
      (response: Wallet[]) => {
        if (response.length) {
          if (response[0].currency === 'UAH') {
            this.uahWallet = response[0];
            this.usdWallet = response[1];
          } else {
            this.usdWallet = response[0];
            this.uahWallet = response[1];
          }
        }
      }
    );
  }

  public onChange(): void {
    let sum: number;
    if (this.exchangeEvent === 'SALE') {
      if (this.firstCurrency === 'USD') {
        sum = this.firstSum * this.exchangeRate[0].purchase;
      } else {
        sum = this.firstSum / this.exchangeRate[0].purchase;
      }
    } else {
      if (this.firstCurrency === 'USD') {
        sum = this.firstSum * this.exchangeRate[0].sale;
      } else {
        sum = this.firstSum / this.exchangeRate[0].sale;
      }
    }

    sum = Math.round(sum * 100.0) / 100.0;
    this.exchangeModel.controls['secondSum'].setValue(sum);
  }

  public exchange(event: Event): void {
    const exchangeDTO = this.createExchangeObject();

    this.exchangeService.exchange(exchangeDTO).subscribe(
      () => {
        this.exchangeModel.controls['firstSum'].reset();
        this.exchangeModel.controls['secondSum'].reset();
        this.fetchWallets();
      },
      error => {
        alert(error.error.message);
        this.exchangeModel.controls['firstSum'].reset();
        this.exchangeModel.controls['secondSum'].reset();
      }
    );
  }

  public eventSelection(event: any): void {
    if (!this.isEventSelected) {
      this.isEventSelected = true;
    }

    this.exchangeModel.controls['event'].setValue(event.attributes['value'].value);

    if (this.firstSum) {
      this.onChange();
    }
  }

  public swapCurrency(): void {
    const firstCurrency = this.firstCurrency;
    const secondCurrency = this.secondCurrency;

    this.exchangeModel.controls['firstCurrency'].setValue(secondCurrency);
    this.exchangeModel.controls['secondCurrency'].setValue(firstCurrency);

    if (this.firstSum) {
      this.onChange();
    }
  }

  public get exchangeEvent(): string {
    return this.exchangeModel.controls['event'].value;
  }

  public get firstCurrency(): string {
    return this.exchangeModel.controls['firstCurrency'].value;
  }

  public get firstSum(): number {
    return this.exchangeModel.controls['firstSum'].value;
  }

  public get secondCurrency(): string {
    return this.exchangeModel.controls['secondCurrency'].value;
  }

  public get secondSum(): number {
    return this.exchangeModel.controls['secondSum'].value;
  }

  private createExchangeObject(): Exchange {
    return {
      'email': this.exchangeModel.controls['email'].value,
      'event': this.exchangeModel.controls['event'].value,
      'firstCurrency': this.exchangeModel.controls['firstCurrency'].value,
      'secondCurrency': this.exchangeModel.controls['secondCurrency'].value,
      'sum': this.exchangeModel.controls['firstSum'].value
    };
  }

  public openExchangeRateDialog(): void {
    const dialogRef = this.dialog.open(ExchangeRateDialogComponent, {
      width: '550px'
    });

    dialogRef.afterClosed().subscribe(
      (result: ExchangeRate[]) => {
        this.exchangeRate = result;
        this.dataSource = result;
        this.isExchangeRateExist = true;
      }
    )
  }
}
