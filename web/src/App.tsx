import styled from "@emotion/styled";
import { useState } from "react";

export function App() {
  const [input, setInput] = useState<string>("");

  async function thing() {
    await fetch("/join", {
      method: "POST",
      body: "1111",
    });
    await fetch("/setName", {
      method: "POST",
      body: "A",
    });
    const ws = new WebSocket("ws://" + window.location.host + "/wsPlayer");
    ws.onopen = () => {
      ws.send("Hello oelurh");
    };

    ws.onmessage = (event) => {
      console.log(event.data);
    };
  }

  return (
    <Container>
      <form
        onSubmit={(event) => {
          event.preventDefault();
          // eslint-disable-next-line
          thing();
        }}
      >
        <input
          type="text"
          value={input}
          onChange={(event) => {
            setInput(event.target.value);
          }}
        />
        <button>Submit</button>
      </form>
    </Container>
  );
}

const Container = styled.div``;
