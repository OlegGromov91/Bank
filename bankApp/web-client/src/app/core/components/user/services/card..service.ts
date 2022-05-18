import {Injectable} from '@angular/core';
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {Observable} from "rxjs/";
import {Card} from "../../../model/bank/card/Card";
import {BasicTransaction} from "../../../model/bank/transaction/BasicTransaction";

@Injectable({
  providedIn: 'root'
})
export class CardService {
  private basicUriApi: string = `http://localhost:8090/cards/`;

  constructor(private httpParams: HttpClientModule, private http: HttpClient) {
  }


  getCardById(cardId: number): Observable<Card> {
    const uri = this.basicUriApi + `${cardId}`;
    return this.http.get<Card>(uri);
  }

  showCards(userId: number): Observable<Card[]> {
    console.log(userId);
    const uri = this.basicUriApi + `user/${userId}`;
    return this.http.get<Card[]>(uri);
  }

  validatePinCode(cardId: number, pinCode: string): Observable<any> {
    const uri = this.basicUriApi + `${cardId}/pinValidate`;
    return this.http.post<any>(uri, pinCode);
  }

  createNewCard(userId: number, card: Card): Observable<Card> {
    const uri = this.basicUriApi + `user/${userId}/newCard`;
    return this.http.post<Card>(uri, card);
  }

  increaseBalance(cardId: number, basicTransaction: BasicTransaction): Observable<BasicTransaction> {
    const uri = this.basicUriApi + `${cardId}/increaseBalance`;
    return this.http.put<BasicTransaction>(uri, basicTransaction);
  }

}
