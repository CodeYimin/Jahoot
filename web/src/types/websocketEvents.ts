export type WebsocketEventType =
  | "playerJoin"
  | "questionStarting"
  | "questionStart"
  | "questionEnd"
  | "questionAnswer"
  | "gameEnd";

export interface WebsocketEvent {
  event: WebsocketEventType;
}
