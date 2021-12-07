import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {User} from "../models/user";
import {HttpClient} from "@angular/common/http";
import {UserActivation} from "../models/user-activation";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private readonly url: string;

  constructor(private http: HttpClient) {
    this.url = 'http://localhost:8080';
  }

  public updateUser(user: User): Observable<User> {
    return this.http.put<User>(`${this.url}/api/user/`, user);
  }

  public sendActivationMail(email: String): Observable<boolean> {
    return this.http.post<boolean>(`${this.url}/api/user/activate/${email}`, {});
  }

  public validateEmail(user: UserActivation): Observable<void> {
    return this.http.post<void>(`${this.url}/api/user/validate/`, user);
  }

  public getByEmail(email: string): Observable<User> {
    return this.http.get<User>(`${this.url}/api/user/${email}`);
  }
}

