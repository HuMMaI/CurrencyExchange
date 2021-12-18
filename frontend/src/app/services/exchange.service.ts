import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Exchange} from "../models/exchange";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ExchangeService {
  private readonly url: string;

  constructor(private http: HttpClient) {
    this.url = environment.currencyExchangeHost;
  }

  public exchange(exchange: Exchange): Observable<void> {
    return this.http.post<void>(`${this.url}/api/exchange`, exchange);
  }
}
