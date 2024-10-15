import { IRole } from "./IRole";

export interface IUser {
    id: string,
    name: string,
    role: IRole
}