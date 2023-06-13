import { Box } from "@chakra-ui/react";
import { ReactElement, useState } from "react";
import {
  LeaderboardEntry,
  PlayerJoinEvent,
  QuestionData,
  QuestionEndEvent,
  QuestionStartEvent,
  QuestionStartingEvent,
} from "../types/operatorWebsocketEvents";
import { WebsocketEvent } from "../types/websocketEvents";
import CreateGame from "./CreateGame";
import Leaderboard from "./Leaderboard";
import Lobby from "./Lobby";
import Question from "./Question";
import QuestionEnd from "./QuestionEnd";
import QuestionStarting from "./QuestionStarting";

type State =
  | "createGame"
  | "lobby"
  | "questionStarting"
  | "question"
  | "questionEnd"
  | "leaderboard";

function Operator(): ReactElement {
  const [state, setState] = useState<State>("createGame");
  const [gameId, setGameId] = useState<string>("");

  const [players, setPlayers] = useState<string[]>([]);

  const [timeRemaining, setTimeRemaining] = useState<number>(0);
  const [question, setQuestion] = useState<QuestionData | undefined>(undefined);
  const [correctAnswerIndex, setCorrectAnswerIndex] = useState<number>(-1);
  const [leaderboard, setLeaderboard] = useState<LeaderboardEntry[]>([]);
  const [answerCounts, setAnswerCounts] = useState<number[]>([]);

  const [ws, setWs] = useState<WebSocket | undefined>(undefined);

  async function handleCreateGame() {
    const createGame = await fetch("/createGame", {
      method: "GET",
    });

    const data = (await createGame.json()) as { id: string };

    setGameId(data.id);
    setState("lobby");

    const ws = new WebSocket("ws://" + window.location.host + "/wsOperator");
    ws.onmessage = (event) => {
      console.log(event);
      const data = JSON.parse(event.data as string) as WebsocketEvent;
      if (data.event === "playerJoin") {
        const eventData = data as PlayerJoinEvent;
        setPlayers((old) => [...old, eventData.name]);
      } else if (data.event === "questionStarting") {
        const eventData = data as QuestionStartingEvent;
        setTimeRemaining(eventData.timeRemaining);
        setQuestion(eventData.question);
        setState("questionStarting");
      } else if (data.event === "questionStart") {
        const eventData = data as QuestionStartEvent;
        setState("question");
      } else if (data.event === "questionEnd") {
        const eventData = data as QuestionEndEvent;
        setLeaderboard(eventData.leaderboard);
        setCorrectAnswerIndex(eventData.correctAnswerIndex);
        setAnswerCounts(eventData.answerCounts);
        setState("questionEnd");
      }
    };
    setWs(ws);
  }

  async function handleStartNextQuestion() {
    ws?.send("startNextQuestion");
  }

  function handleLeaderboard() {
    setState("leaderboard");
  }

  return (
    <Box>
      {state === "createGame" ? (
        <CreateGame
          onCreateGame={() => {
            handleCreateGame();
          }}
        />
      ) : state === "lobby" ? (
        <Lobby
          gameId={gameId}
          players={players}
          onStartGame={handleStartNextQuestion}
        />
      ) : state === "questionStarting" ? (
        <QuestionStarting timeRemaining={timeRemaining} question={question!} />
      ) : state === "question" ? (
        <Question question={question!} />
      ) : state === "questionEnd" ? (
        <QuestionEnd
          question={question!}
          correctAnswerIndex={correctAnswerIndex}
          answerCounts={answerCounts}
          onLeaderboard={handleLeaderboard}
        />
      ) : state === "leaderboard" ? (
        <Leaderboard
          entries={leaderboard}
          onStartNextQuestion={handleStartNextQuestion}
        />
      ) : null}
    </Box>
  );
}

export default Operator;
