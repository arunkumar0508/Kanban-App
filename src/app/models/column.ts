import { task } from "./task";

export type column={
    columnId:number | null;
    name:string;
    tasks:task[];
}