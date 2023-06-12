import { Box, Button, Center } from "@chakra-ui/react";
import { ReactElement } from "react";

interface CreateGameProps {
  onCreateGame: () => void;
}

function CreateGame({ onCreateGame }: CreateGameProps): ReactElement {
  return (
    <Box>
      <Center h="100vh">
        <Button onClick={onCreateGame}>Create Game</Button>
      </Center>
    </Box>
  );
}

export default CreateGame;
