import { Box, Center, Text } from "@chakra-ui/react";
import { ReactElement } from "react";

interface GameEndProps {
  placement: number;
}

function GameEnd({ placement }: GameEndProps): ReactElement {
  return (
    <Box>
      <Center h="100vh">
        <Text fontSize="6xl">Game ended! You placed {placement}!</Text>
      </Center>
    </Box>
  );
}

export default GameEnd;
