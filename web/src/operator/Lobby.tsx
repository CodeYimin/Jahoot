import { Box, Button, Center, Text, VStack } from "@chakra-ui/react";
import { ReactElement } from "react";

interface LobbyProps {
  gameId: string;
  players: string[];
  onStartGame: () => void;
}

function Lobby({ gameId, players, onStartGame }: LobbyProps): ReactElement {
  return (
    <Box>
      <Center h="100vh">
        <VStack>
          <Text fontSize="6xl">
            Join the game
            <br />
            PIN: {gameId}
          </Text>
          <Text fontSize="xl">Players: {players.join(", ")}</Text>
          <Button onClick={onStartGame}>Start Game</Button>
        </VStack>
      </Center>
    </Box>
  );
}

export default Lobby;
