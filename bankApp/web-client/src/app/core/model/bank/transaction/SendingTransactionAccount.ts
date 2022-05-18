import {BasicTransaction} from "./BasicTransaction";
import {BankAccount} from "../bankAccount/BankAccount";

export interface SendingTransactionAccount extends BasicTransaction {

  beneficiaryBankAccountFrom: BankAccount;
  beneficiaryBankAccountTo: BankAccount;
  beneficiaryAccount: string;

}
