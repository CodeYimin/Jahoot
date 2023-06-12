import { Box, Center, Text, VStack } from "@chakra-ui/react";
import { ReactElement } from "react";
import { QuestionData } from "../types/operatorWebsocketEvents";

interface QuestionProps {
  question: QuestionData;
}

function Question({ question }: QuestionProps): ReactElement {
  return (
    <Box>
      <Center h="100vh">
        <VStack>
          <Text fontSize="6xl">{question.question}</Text>
          <Text fontSize="3xl">Answers:</Text>
          {question.answers.map((answer) => (
            <Text fontSize="3xl" key={answer}>
              {answer}
            </Text>
          ))}
        </VStack>
      </Center>
    </Box>
  );
}

export default Question;
