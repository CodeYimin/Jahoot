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
        <VStack spacing="5">
          <Text fontSize="6xl">Leaderboard</Text>
          {entries.slice(0, 5).map((entry, i) => (
            <Text
              fontSize="xl"
              key={entry.player}
              bgColor="whiteAlpha.200"
              p="5"
            >
              {i + 1}. {entry.player}: {entry.score}
            </Text>
          ))}
          <Button size="lg" onClick={onStartNextQuestion}>
            Start Next Question
          </Button>
        </VStack>
      </Center>
    </Box>
  );
}

export default Leaderboard;
