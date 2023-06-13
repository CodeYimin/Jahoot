import { Box, Center, Grid, Text, VStack } from "@chakra-ui/react";
import { ReactElement, useEffect, useState } from "react";
import { QuestionData } from "../types/operatorWebsocketEvents";

interface QuestionProps {
  question: QuestionData;
}

function Question({ question }: QuestionProps): ReactElement {
  const [timeLeft, setTimeLeft] = useState(question.duration);

  useEffect(() => {
    const interval = setInterval(() => {
      if (timeLeft <= 0) {
        clearInterval(interval);
        return;
      }
      setTimeLeft((timeLeft) => timeLeft - 1000);
    }, 1000);
  }, []);

  console.log(question.duration);

  return (
    <Box>
      <Center h="100vh">
        <VStack spacing="5">
          <Text fontSize="6xl">{question.question}</Text>
          <Text fontSize="3xl">{timeLeft / 1000} seconds left</Text>
          <Grid templateColumns="1fr 1fr" gap="6">
            {question.answers.map((answer, i) => (
              <Text fontSize="3xl" key={answer} bgColor="whiteAlpha.200" p="5">
                {i + 1}. {answer}
              </Text>
            ))}
          </Grid>
        </VStack>
      </Center>
    </Box>
  );
}

export default Question;
