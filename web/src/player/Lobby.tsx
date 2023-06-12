import { Box, Center, Text, VStack } from "@chakra-ui/react";
import { ReactElement } from "react";

interface LobbyProps {
  name: string;
}

function Lobby({ name }: LobbyProps): ReactElement {
  return (
    <Box>
      <Center h="100vh">
        <VStack>
          <Text fontSize="6xl">
            Welcome to the game
            <br />
            {name}!
          </Text>
          <Text fontSize="xl">View your name on the screen!</Text>
        </VStack>
      </Center>
    </Box>
  );
}

export default Lobby;
