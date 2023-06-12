import { Box } from "@chakra-ui/react";
import { ReactElement, useState } from "react";
import {
  LeaderboardEntry,
  PlayerJoinEvent,
  QuestionData,
  QuestionEndEvent,
  QuestionStartingEvent,
} from "../types/operatorWebsocketEvents";
import { WebsocketEvent } from "../types/websocketEvents";
import CreateGame from "./CreateGame";
import Leaderboard from "./Leaderboard";
import Lobby from "./Lobby";
import Question from "./Question";
import QuestionStarting from "./QuestionStarting";

type State =
  | "createGame"
  | "lobby"
  | "questionStarting"
  | "question"
  | "leaderboard";

function Operator(): ReactElement {
  const [state, setState] = useState<State>("createGame");
  const [gameId, setGameId] = useState<string>("");

  const [players, setPlayers] = useState<string[]>([]);

  const [timeRemaining, setTimeRemaining] = useState<number>(0);
  const [question, setQuestion] = useState<QuestionData | undefined>(undefined);
  const [correctAnswerIndex, setCorrectAnswerIndex] = useState<number>(-1);
  const [leaderboard, setLeaderboard] = useState<LeaderboardEntry[]>([]);
  const [playerAnswers, setPlayerAnswers] = useState<number[]>([]);

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
        const playerData = data as PlayerJoinEvent;
        setPlayers((old) => [...old, playerData.name]);
      } else if (data.event === "questionStarting") {
        const questionData = data as QuestionStartingEvent;
        setTimeRemaining(questionData.timeRemaining);
        setQuestion(questionData.question);
        setState("questionStarting");
      } else if (data.event === "questionStart") {
        setState("question");
      } else if (data.event === "questionEnd") {
        const questionData = data as QuestionEndEvent;
        setLeaderboard(questionData.leaderboard);
        setCorrectAnswerIndex(questionData.correctAnswerIndex);
        setPlayerAnswers(questionData.playerAnswers);
      }
    };
    setWs(ws);
  }

  async function handleStartNextQuestion() {
    ws?.send("startNextQuestion");
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
      ) : state === "leaderboard" ? (
        <Leaderboard entries={[]} onStartNextQuestion={() => {}} />
      ) : null}
    </Box>
  );
}

export default Operator;
