import { Box } from "@chakra-ui/react";
import { useState } from "react";
import {
  GameEndEvent,
  QuestionEndEvent,
  QuestionStartEvent,
  QuestionStartingEvent,
} from "../types/playerWebsocketEvents";
import { WebsocketEvent } from "../types/websocketEvents";
import { EnterName } from "./EnterName";
import { EnterPin } from "./EnterPin";
import GameEnd from "./GameEnd";
import Lobby from "./Lobby";
import Question from "./Question";
import QuestionEnd from "./QuestionEnd";
import QuestionStarting from "./QuestionStarting";

type State =
  | "enterPin"
  | "enterName"
  | "lobby"
  | "questionStarting"
  | "question"
  | "questionEnd"
  | "gameEnd";

export function Player() {
  const [state, setState] = useState<State>("enterPin");
  const [name, setName] = useState<string>("");
  const [secondsRemaining, setSecondsRemaining] = useState<number>(0);
  const [answerCount, setAnswerCount] = useState<number>(0);
  const [questionScore, setQuestionScore] = useState<number>(0);
  const [totalScore, setTotalScore] = useState<number>(0);
  const [correct, setCorrect] = useState<boolean>(false);
  const [ws, setWs] = useState<WebSocket | undefined>(undefined);
  const [placement, setPlacement] = useState<number>(0);

  function handleSetName(name: string) {
    setName(name);

    const ws = new WebSocket("ws://" + window.location.host + "/wsPlayer");
    // ws.onopen = () => {
    //   ws.send(`Hello I am ${name}`);
    // };

    ws.onmessage = (event) => {
      const data = JSON.parse(event.data as string) as WebsocketEvent;
      if (data.event === "questionStarting") {
        const eventData = data as QuestionStartingEvent;
        setSecondsRemaining(eventData.timeRemaining / 1000);
        setState("questionStarting");
      } else if (data.event === "questionStart") {
        const eventData = data as QuestionStartEvent;
        setAnswerCount(eventData.answerCount);
        setState("question");
      } else if (data.event === "questionEnd") {
        const eventData = data as QuestionEndEvent;
        setCorrect(eventData.correct);
        setQuestionScore(eventData.questionScore);
        setTotalScore(eventData.totalScore);
        setState("questionEnd");
      } else if (data.event === "gameEnd") {
        const eventData = data as GameEndEvent;
        setPlacement(eventData.placement);
        setState("gameEnd");
      }
    };

    setState("lobby");
    setWs(ws);
  }

  function handleAnswer(answer: number) {
    ws?.send(answer.toString());
  }

  return (
    <Box>
      {state === "enterPin" ? (
        <EnterPin onJoin={() => setState("enterName")} />
      ) : state === "enterName" ? (
        <EnterName onSetName={handleSetName} />
      ) : state == "lobby" ? (
        <Lobby name={name} />
      ) : state == "questionStarting" ? (
        <QuestionStarting secondsRemaining={secondsRemaining} />
      ) : state == "question" ? (
        <Question answerCount={answerCount} onAnswer={handleAnswer} />
      ) : state == "questionEnd" ? (
        <QuestionEnd
          correct={correct}
          questionScore={questionScore}
          totalScore={totalScore}
        />
      ) : state == "gameEnd" ? (
        <GameEnd placement={placement} />
      ) : null}
    </Box>
  );
}
