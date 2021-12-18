import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Wallet} from "../models/wallet";
import {HttpClient} from "@angular/common/http";
import {CreateWallet} from "../models/create-wallet";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class WalletService {
  private readonly url: string;

  constructor(private http: HttpClient) {
    this.url = environment.currencyExchangeHost;
  }

  public getWallets(email?: string): Observable<Wallet[]> {
    return this.http.get<Wallet[]>(`${this.url}/api/wallet/${email}`);
  }

  public createWallet(wallet: CreateWallet): Observable<void> {
    return this.http.post<void>(`${this.url}/api/wallet`, wallet);
  }

  public bonus(email: string, increase: boolean): Observable<void> {
    return this.http.put<void>(`${this.url}/api/wallet/${email}`, {}, {params: {'increase': increase}});
  }
}
