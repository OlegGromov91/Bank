import {Injectable} from '@angular/core';
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {Observable} from "rxjs/";
import {User} from "../../../model/security/User";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private basicUriApi: string = `http://localhost:8090/users/`

  constructor(private httpParams: HttpClientModule, private http: HttpClient) {
  }

  getUser(userId: number): Observable<User> {
    const uri = this.basicUriApi + `${userId}`;
    return this.http.get<User>(uri);
  }

}
