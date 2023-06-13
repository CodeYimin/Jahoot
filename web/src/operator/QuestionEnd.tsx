import { Box, Button, Center, Grid, Text, VStack } from "@chakra-ui/react";
import { ReactElement } from "react";
import { QuestionData } from "../types/operatorWebsocketEvents";

interface QuestionEndProps {
  question: QuestionData;
  answerCounts: number[];
  correctAnswerIndex: number;
  onLeaderboard: () => void;
}

function QuestionEnd({
  question,
  answerCounts,
  correctAnswerIndex,
  onLeaderboard,
}: QuestionEndProps): ReactElement {
  return (
    <Box>
      <Center h="100vh">
        <VStack spacing="5">
          <Text fontSize="6xl">{question.question}</Text>
          <Grid templateColumns="1fr 1fr" gap="6">
            {question.answers.map((answer, i) => (
              <Text
                fontSize="3xl"
                key={answer}
                color={
                  i === correctAnswerIndex ? "green.500" : "whiteAlpha.500"
                }
                bgColor="whiteAlpha.200"
                p="5"
              >
                {answer}: {answerCounts[i]} answers
              </Text>
            ))}
          </Grid>
          <Button size="lg" onClick={onLeaderboard}>
            Next
          </Button>
        </VStack>
      </Center>
    </Box>
  );
}

export default QuestionEnd;
