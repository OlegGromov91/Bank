import {User} from "../../security/User";
import {Card} from "../card/Card";

export interface UnlockRequestHistory {

  unlockRequestHistoryId: number;
  unlockRequestId: number;
  user: User;
  card: Card;

}
