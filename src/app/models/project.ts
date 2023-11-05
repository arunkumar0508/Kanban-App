import { board } from "./board";

export type project={
    id?: string;
    name: string;
    description: string;
    board?:board | null;
}