import { IUser } from "./IUSer";

export interface IUserLogged {
    user: IUser,
    token: string
}