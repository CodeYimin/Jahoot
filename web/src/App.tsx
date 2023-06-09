import styled from "@emotion/styled";
import { useState } from "react";

export function App() {
  const [input, setInput] = useState<string>("");

  return (
    <Container>
      <form
        onSubmit={(event) => {
          event.preventDefault();
          console.log(input);
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
