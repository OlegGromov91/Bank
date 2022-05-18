import {PaymentSystem} from "./PaymentSystem";
import {CurrencyType} from "./CurrencyType";
import {CardType} from "./CardType";

export interface Card {
  cardId: number;
  cardNumber: string;
  pinCode: string;
  cvv: string;
  expiredDate: Date;
  createdDate: Date;
  isActive: boolean;
  isBlock: boolean;
  paymentSystem: PaymentSystem;
  currencyType: CurrencyType;
  cardType: CardType;
  moneyAmount: number;
  userId: number;

}
