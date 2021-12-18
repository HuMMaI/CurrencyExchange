import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {CountReport} from "../models/count-report";
import {ExchangeEventReport} from "../models/exchange-event-report";
import {Exchange} from "../models/exchange";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ReportService {
  private readonly url: string;

  constructor(private http: HttpClient) {
    this.url = environment.reportingServiceHost;
  }

  public getCountReport(): Observable<CountReport> {
    return this.http.get<CountReport>(`${this.url}/api/reports/count`);
  }

  public getExchangeEventReport(): Observable<ExchangeEventReport> {
    return this.http.get<ExchangeEventReport>(`${this.url}/api/reports/exchange-event`);
  }

  public getCurrencyReport(success: boolean): Observable<Exchange[]> {
    return this.http.get<Exchange[]>(`${this.url}/api/reports`, {params: {'success': success}});
  }
}
