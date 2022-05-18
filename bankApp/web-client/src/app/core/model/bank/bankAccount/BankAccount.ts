import {CorrespondentBankType} from "./CorrespondentBankType";
import {CurrencyType} from "../card/CurrencyType";

export interface BankAccount {
  bankAccountId: number;
  beneficiaryAccount: string;
  bik: string;
  correspondentBankType: CorrespondentBankType;
  inn: string;
  beneficiaryId: number;
  expiredDate: Date;
  createdDate: Date;
  active: boolean;
  moneyAmount: number;
  currencyType: CurrencyType

}
