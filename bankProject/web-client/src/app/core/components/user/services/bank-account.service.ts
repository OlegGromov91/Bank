import {Injectable} from '@angular/core';
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {Observable} from "rxjs";
import {BankAccount} from "../../../model/bank/bankAccount/BankAccount";

@Injectable({
  providedIn: 'root'
})
export class BankAccountService {
  private basicUriApi: string = `http://localhost:8090/bankAccount/`;

  constructor(private httpParams: HttpClientModule, private http: HttpClient) {
  }

  public createNewBankAccount(userId: number): Observable<BankAccount> {
    const uri = this.basicUriApi + `${userId}/bankAccounts`;
    // @ts-ignore
    return this.http.post<BankAccount>(uri);
  }

  public findAllBankAccountsByUserId(userId: number): Observable<BankAccount[]> {
    const uri = this.basicUriApi + `${userId}`;
    return this.http.get<BankAccount[]>(uri);
  }

}
