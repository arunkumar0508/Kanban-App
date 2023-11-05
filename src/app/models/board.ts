import { column } from "./column";

export type board={
    boardId:number| null;
    name:string;
    columns:column[];
}