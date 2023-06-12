import { ReactElement } from "react";
import { QuestionData } from "../types/operatorWebsocketEvents";

interface QuestionProps {
  question: QuestionData;
}

function Question({}: QuestionProps): ReactElement {
  return <div></div>;
}

export default Question;
