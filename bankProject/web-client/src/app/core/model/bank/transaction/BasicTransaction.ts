import {Card} from "../card/Card";

export interface BasicTransaction {
  card: Card;
  money: number;
  cardNumber: string;
}
