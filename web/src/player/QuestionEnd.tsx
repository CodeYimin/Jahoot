import { Box, Center, Text, VStack } from "@chakra-ui/react";
import { ReactElement } from "react";

interface QuestionEndProps {
  questionScore: number;
  totalScore: number;
  correct: boolean;
}

function QuestionEnd({
  questionScore,
  totalScore,
  correct,
}: QuestionEndProps): ReactElement {
  return (
    <Box>
      <Center h="100vh">
        <VStack>
          <Text fontSize="6xl">{correct ? "Correct!" : "Wrong! "}</Text>
          <Text fontSize="3xl">You earned {questionScore} points!</Text>
          <Text fontSize="3xl">Your total score is {totalScore} points!</Text>
        </VStack>
      </Center>
    </Box>
  );
}

export default QuestionEnd;
