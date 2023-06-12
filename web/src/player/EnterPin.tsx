import { Box, Button, Grid, Input, VStack, useToast } from "@chakra-ui/react";
import { useState } from "react";
import { ColorModeSwitcher } from "../ColorModeSwitcher";

interface EnterPinProps {
  onJoin: () => void;
}

export function EnterPin({ onJoin }: EnterPinProps) {
  const [pin, setPin] = useState<string>("");
  const toast = useToast();

  async function handlePinSubmit() {
    const join = await fetch("/join", {
      method: "POST",
      body: pin,
    });

    setPin("");

    if (join.status !== 200) {
      toast({
        title: "Error",
        description: "Invalid pin",
        status: "error",
        duration: 2000,
        isClosable: true,
      });
      return;
    }

    toast({
      title: "Success",
      description: "Joined game",
      status: "success",
      duration: 2000,
      isClosable: true,
    });
    onJoin();
  }

  return (
    <Box textAlign="center" fontSize="xl">
      <Grid minH="100vh" p={3}>
        <ColorModeSwitcher justifySelf="flex-end" />
        <VStack spacing="5" w="30%" mx="auto">
          <Input
            size="lg"
            placeholder="Enter pin..."
            value={pin}
            onChange={(event) => {
              setPin(event.target.value);
            }}
          />
          <Button
            w="100%"
            onClick={() => {
              //eslint-disable-next-line
              handlePinSubmit();
            }}
          >
            Join Game
          </Button>
        </VStack>
      </Grid>
    </Box>
  );
}
