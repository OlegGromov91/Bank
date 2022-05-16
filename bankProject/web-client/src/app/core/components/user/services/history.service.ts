import {Injectable} from '@angular/core';
import {Observable} from "rxjs/";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {History} from "../../../model/bank/history/History";
import {UnlockRequestHistory} from "../../../model/bank/history/UnlockRequestHistory";

@Injectable({
  providedIn: 'root'
})
export class HistoryService {

  private basicUriApi: string = `http://localhost:8090/history/`;

  constructor(private httpParams: HttpClientModule, private http: HttpClient) {
  }


  getLastTwentyHistoryOperationWithCard(cardId: number): Observable<History[]> {
    const uri = this.basicUriApi + `lastCardOperations/${cardId}`;
    return this.http.get<History[]>(uri);
  }

  getHistoryOperationWithBankAccount(bankAccountId: number): Observable<History[]> {
    const uri = this.basicUriApi + `lastBankAccountOperations/${bankAccountId}`;
    return this.http.get<History[]>(uri);
  }

  getUnlockRequestHistory(): Observable<UnlockRequestHistory[]> {
    const uri = this.basicUriApi + `unlockRequestHistory`;
    return this.http.get<UnlockRequestHistory[]>(uri);
  }

}
