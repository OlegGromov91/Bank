import {Injectable} from '@angular/core';
import {Observable} from "rxjs/";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {UnlockRequest} from "../../../model/security/UnlockRequest";

@Injectable({
  providedIn: 'root'
})
export class UnlockRequestService {
  private basicUriApi: string = `http://localhost:8090/unlockRequest/`;

  constructor(private httpParams: HttpClientModule, private http: HttpClient) {
  }


  showUnlockRequests(): Observable<UnlockRequest[]> {
    return this.http.get<UnlockRequest[]>(this.basicUriApi);
  }

  unlockUserCard(cardId: number, unlockRequest: UnlockRequest): Observable<any> {
    const uri = this.basicUriApi + `unlockCard/${cardId}`;
    return this.http.put<any>(uri, unlockRequest);
  }

  sendRequestForCardUnlock(cardId: number, unlockRequest: UnlockRequest): Observable<any> {
    const uri = this.basicUriApi + `createRequest/${cardId}`;
    return this.http.post<any>(uri, unlockRequest);
  }
}
