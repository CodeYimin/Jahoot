import { Box, Button, Grid, Input, VStack, useToast } from "@chakra-ui/react";
import { useState } from "react";
import { ColorModeSwitcher } from "../ColorModeSwitcher";

interface EnterNameProps {
  onSetName: (name: string) => void;
}

export function EnterName({ onSetName }: EnterNameProps) {
  const [name, setName] = useState<string>("");
  const toast = useToast();

  async function handleNameSubmit() {
    const setName = await fetch("/setName", {
      method: "POST",
      body: name,
    });

    onSetName(name);
  }

  return (
    <Box textAlign="center" fontSize="xl">
      <Grid minH="100vh" p={3}>
        <ColorModeSwitcher justifySelf="flex-end" />
        <VStack spacing="5" w="30%" mx="auto">
          <Input
            size="lg"
            placeholder="Enter name..."
            value={name}
            onChange={(event) => {
              setName(event.target.value);
            }}
          />
          <Button
            w="100%"
            onClick={() => {
              //eslint-disable-next-line
              handleNameSubmit();
            }}
          >
            Set Name
          </Button>
        </VStack>
      </Grid>
    </Box>
  );
}
