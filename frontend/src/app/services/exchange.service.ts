import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Exchange} from "../models/exchange";

@Injectable({
  providedIn: 'root'
})
export class ExchangeService {
  private readonly url: string;

  constructor(private http: HttpClient) {
    this.url = 'http://localhost:8080';
  }

  public exchange(exchange: Exchange): Observable<void> {
    return this.http.post<void>(`${this.url}/api/exchange`, exchange);
  }
}
