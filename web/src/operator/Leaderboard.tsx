import { Box, Button, Center, Text, VStack } from "@chakra-ui/react";
import { ReactElement } from "react";
import { LeaderboardEntry } from "../types/operatorWebsocketEvents";

interface LeaderboardProps {
  entries: LeaderboardEntry[];
  onStartNextQuestion: () => void;
}

function Leaderboard({
  entries,
  onStartNextQuestion,
}: LeaderboardProps): ReactElement {
  return (
    <Box>
      <Center h="100vh">
        <VStack>
          <Text fontSize="6xl">Leaderboard</Text>
          {entries.slice(0, 5).map((entry) => (
            <Text fontSize="xl" key={entry.player}>
              {entry.player}: {entry.score}
            </Text>
          ))}
          <Button onClick={onStartNextQuestion}>Start Next Question</Button>
        </VStack>
      </Center>
    </Box>
  );
}

export default Leaderboard;
