import { Box, Center, Text, VStack } from "@chakra-ui/react";
import { ReactElement } from "react";
import { LeaderboardEntry } from "../types/operatorWebsocketEvents";

interface GameEndProps {
  leaderboard: LeaderboardEntry[];
}

function GameEnd({ leaderboard }: GameEndProps): ReactElement {
  return (
    <Box>
      <Center h="100vh">
        <VStack spacing="5">
          <Text fontSize="6xl">Game ended! Results:</Text>
          {leaderboard.slice(0, 5).map((entry, i) => (
            <Text
              fontSize="xl"
              key={entry.player}
              bgColor="whiteAlpha.200"
              p="5"
            >
              {i + 1}. {entry.player}: {entry.score}
            </Text>
          ))}
        </VStack>
      </Center>
    </Box>
  );
}

export default GameEnd;
