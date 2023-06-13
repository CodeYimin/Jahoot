import { WebsocketEvent } from "./websocketEvents";

export interface PlayerJoinEvent extends WebsocketEvent {
  event: "playerJoin";
  name: string;
}

export interface QuestionStartingEvent extends WebsocketEvent {
  event: "questionStarting";
  timeRemaining: number;
  question: QuestionData;
}

export interface QuestionStartEvent extends WebsocketEvent {
  event: "questionStart";
  question: QuestionData;
}

export interface QuestionData {
  question: string;
  duration: number;
  answers: string[];
}

export interface QuestionAnswerEvent extends WebsocketEvent {
  event: "questionAnswer";
}

export interface QuestionEndEvent extends WebsocketEvent {
  event: "questionEnd";
  question: QuestionData;
  answerCounts: number[];
  correctAnswerIndex: number;
  leaderboard: LeaderboardEntry[];
}

export interface GameEndEvent extends WebsocketEvent {
  event: "gameEnd";
  leaderboard: LeaderboardEntry[];
}

export interface LeaderboardEntry {
  player: string;
  score: number;
}
