import { Box, Center, Text, VStack } from "@chakra-ui/react";
import { ReactElement } from "react";
import { QuestionData } from "../types/operatorWebsocketEvents";

interface QuestionStartingProps {
  timeRemaining: number;
  question: QuestionData;
}

function QuestionStarting({
  timeRemaining,
  question,
}: QuestionStartingProps): ReactElement {
  return (
    <Box>
      <Center h="100vh">
        <VStack>
          <Text fontSize="6xl">{question.question}</Text>
          <Text fontSize="3xl">
            Answers start in {timeRemaining / 1000} seconds
          </Text>
        </VStack>
      </Center>
    </Box>
  );
}

export default QuestionStarting;
