import { Box, Center, Text } from "@chakra-ui/react";
import { ReactElement } from "react";

interface QuestionStartingProps {
  secondsRemaining: number;
}

function QuestionStarting({
  secondsRemaining,
}: QuestionStartingProps): ReactElement {
  return (
    <Box>
      <Center h="100vh">
        <Text fontSize="6xl">Starting in {secondsRemaining} seconds</Text>
      </Center>
    </Box>
  );
}

export default QuestionStarting;
