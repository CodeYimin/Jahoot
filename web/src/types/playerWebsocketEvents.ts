import { WebsocketEvent } from "./websocketEvents";

export interface QuestionStartingEvent extends WebsocketEvent {
  event: "questionStarting";
  timeRemaining: number;
}

export interface QuestionStartEvent extends WebsocketEvent {
  event: "questionStart";
  answerCount: number;
}

export interface QuestionEndEvent extends WebsocketEvent {
  event: "questionEnd";
  correct: boolean;
  questionScore: number;
  totalScore: number;
}

export interface GameEndEvent extends WebsocketEvent {
  event: "gameEnd";
  placement: number;
}
