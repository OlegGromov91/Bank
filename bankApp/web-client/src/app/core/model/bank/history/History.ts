import {User} from "../../security/User";
import {Card} from "../card/Card";
import {BlockReason} from "./BlockReason";
import {OperationType} from "./OperationType";
import {BankAccount} from "../bankAccount/BankAccount";

export interface History {


  historyId: number;
  operationDate: Date;
  message: string;
  isSuccess: boolean;
  moneyAmount: string;
  user: User;
  card: Card;
  cardRecipientNumber: string;
  blockReason: BlockReason;
  operationType: OperationType;
  bankAccount: BankAccount;
  bankAccountRecipientNumber: string;

}
