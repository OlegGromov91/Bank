import {Injectable} from '@angular/core';
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {BasicTransaction} from "../../../model/bank/transaction/BasicTransaction";
import {Observable} from "rxjs/";
import {SendingTransaction} from "../../../model/bank/transaction/SendingTransaction";
import {SendingTransactionAccount} from "../../../model/bank/transaction/SendingTransactionAccount";
import {Card} from "../../../model/bank/card/Card";
import {BanksCommission} from "../../../model/bank/transaction/BanksCommission";

@Injectable({
  providedIn: 'root'
})
export class TransferService {

  private basicUriApi: string = `http://localhost:8090/transfer/`

  constructor(private httpParams: HttpClientModule, private http: HttpClient) {
  }


  loadBankCommission(): Observable<Map<BanksCommission, number>> {
    const uri = this.basicUriApi + "commission";
    return this.http.get<Map<BanksCommission, number>>(uri)
  }

  transferMoneyToUserSelf(userId: number, cardId: number, basicTransaction: BasicTransaction): Observable<BasicTransaction> {
    const uri = this.basicUriApi + `${userId}/withinSystem/ToUserSelf/WithCard/${cardId}`;
    return this.http.put<BasicTransaction>(uri, basicTransaction);
  }

  transferMoneyWithCardWithinSystem(cardId: number, basicTransaction: BasicTransaction): Observable<BasicTransaction> {
    const uri = this.basicUriApi + `withinSystem/WithCard/${cardId}`;
    return this.http.put<BasicTransaction>(uri, basicTransaction);
  }

  transferMoneyWithCardOutsideSystem(cardId: number, sendingTransaction: SendingTransaction): Observable<SendingTransaction> {
    const uri = this.basicUriApi + `outsideSystem/WithCard/${cardId}`;
    return this.http.put<SendingTransaction>(uri, sendingTransaction);
  }

  transferMoneyFromBankAccountToBankAccount(sendingTransactionAccount: SendingTransactionAccount): Observable<SendingTransactionAccount> {
    const uri = this.basicUriApi + `withinSystem/WithBankAccount/${sendingTransactionAccount.beneficiaryBankAccountFrom.bankAccountId}`;
    return this.http.put<SendingTransactionAccount>(uri, sendingTransactionAccount);
  }

  transferMoneyFromCardToBankAccount(cardId: number, sendingTransactionAccount: SendingTransactionAccount): Observable<Card> {
    const uri = this.basicUriApi + `withinSystem/WithCard/${cardId}/BankAccount`;
    return this.http.put<Card>(uri, sendingTransactionAccount);
  }

}
