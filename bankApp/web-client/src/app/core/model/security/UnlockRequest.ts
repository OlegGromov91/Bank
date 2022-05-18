import {User} from "./User";
import {Card} from "../bank/card/Card";

export interface UnlockRequest {
  cardId: number;
  userId: number;
  user: User;
  card: Card;
  secretWord: string;
  requestActive: boolean;
}
