import { Box } from "@chakra-ui/react";
import { useState } from "react";
import { QuestionStartingEvent } from "../types/playerWebsocketEvents";
import { WebsocketEvent } from "../types/websocketEvents";
import { EnterName } from "./EnterName";
import { EnterPin } from "./EnterPin";
import Lobby from "./Lobby";
import QuestionStarting from "./QuestionStarting";

type State = "enterPin" | "enterName" | "lobby" | "questionStarting";

export function Player() {
  const [state, setState] = useState<State>("enterPin");
  const [name, setName] = useState<string>("");
  const [secondsRemaining, setSecondsRemaining] = useState<number>(0);

  function handleSetName(name: string) {
    setName(name);

    const ws = new WebSocket("ws://" + window.location.host + "/wsPlayer");
    // ws.onopen = () => {
    //   ws.send(`Hello I am ${name}`);
    // };

    ws.onmessage = (event) => {
      const data = JSON.parse(event.data as string) as WebsocketEvent;
      if (data.event === "questionStarting") {
        const questionData = data as QuestionStartingEvent;
        setSecondsRemaining(questionData.timeRemaining / 1000);
        setState("questionStarting");
      }
    };

    setState("lobby");
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
      ) : null}
    </Box>
  );
}
