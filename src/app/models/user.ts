import { project } from "./project";

export type User ={
    userName: string;
    email: string;
    password: string;
    phoneNumber:string;
    projects: project[];
  }