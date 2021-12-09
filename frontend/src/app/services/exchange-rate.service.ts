import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ExchangeRate} from "../models/exchange-rate";

@Injectable({
  providedIn: 'root'
})
export class ExchangeRateService {
  private readonly url: string;

  constructor(private http: HttpClient) {
    this.url = 'http://localhost:8080';
  }

  public getCurrentExchangeRate(): Observable<ExchangeRate[]> {
    return this.http.get<ExchangeRate[]>(`${this.url}/api/rate/actual`);
  }

  public createExchangeRate(exchangeRate: ExchangeRate): Observable<ExchangeRate> {
    return this.http.post<ExchangeRate>(`${this.url}/api/rate`, exchangeRate);
  }
}
