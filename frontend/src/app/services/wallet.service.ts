import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Wallet} from "../models/wallet";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class WalletService {
  private readonly url: string;

  constructor(private http: HttpClient) {
    this.url = 'http://localhost:8080';
  }

  public getWallets(email?: string): Observable<Wallet[]> {
    return this.http.get<Wallet[]>(`${this.url}/api/wallet/${email}`);
  }
}
