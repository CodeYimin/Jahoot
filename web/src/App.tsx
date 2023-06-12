import { Box, Button, Center, HStack } from "@chakra-ui/react";
import { useState } from "react";
import Operator from "./operator/Operator";
import { Player } from "./player/Player";

type State = "Operator" | "Player";

export function App() {
  const [state, setState] = useState<State | undefined>();

  return (
    <Box textAlign="center">
      {state === "Operator" ? (
        <Operator />
      ) : state === "Player" ? (
        <Player />
      ) : (
        <Center h="100vh">
          <HStack>
            <Button onClick={() => setState("Player")}>Player</Button>
            <Button onClick={() => setState("Operator")}>Operator</Button>
          </HStack>
        </Center>
      )}
    </Box>
  );
}
