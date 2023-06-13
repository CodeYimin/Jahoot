import { Box, Button, Center, Grid, Text } from "@chakra-ui/react";
import { ReactElement, useState } from "react";

interface QuestionProps {
  answerCount: number;
  onAnswer: (answer: number) => void;
}

function Question({ answerCount, onAnswer }: QuestionProps): ReactElement {
  const [answered, setAnswered] = useState(false);

  return (
    <Box>
      <Center h="100vh">
        {!answered ? (
          <Grid templateColumns="1fr 1fr" gap="6" h="100vh" w="100vw">
            {Array(answerCount)
              .fill(0)
              .map((_, i) => (
                <Button
                  fontSize="3xl"
                  key={i}
                  w="100%"
                  h="100%"
                  onClick={() => {
                    if (answered) {
                      return;
                    }
                    onAnswer(i);
                    setAnswered(true);
                  }}
                >
                  {i + 1}
                </Button>
              ))}
          </Grid>
        ) : (
          <Text fontSize="3xl">You answered!</Text>
        )}
      </Center>
    </Box>
  );
}

export default Question;
