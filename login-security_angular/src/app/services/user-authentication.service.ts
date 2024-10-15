import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { ICreateUser } from '../interfaces/ICreateUser';
import { catchError, Observable, throwError } from 'rxjs';
import { IUser } from '../interfaces/IUSer';
import { environment } from '../../environments/environment.development';
import { ILoginUser } from '../interfaces/ILoginUser';
import { IUserLogged } from '../interfaces/IUserLogged';

@Injectable({
  providedIn: 'root'
})
export class UserAuthenticationService {
  private http: HttpClient = inject(HttpClient);
  errorMessage: string = "";

  createUser(createUser: ICreateUser): Observable<IUser> {
    return this.http.post<IUser>(`${environment.apiUrl}/users`, createUser).pipe(
      catchError((error) => {
        console.error("Erro na API:", error);
        this.errorMessage = "Ocorreu um erro ao criar conta";
        return throwError(() => new Error("Erro ao efetuar cadastro"));
      })
    );
  }

  authenticateUser(loginUser: ILoginUser): Observable<IUserLogged> {
    return this.http.post<IUserLogged>(`${environment.apiUrl}/users/login`, loginUser).pipe(
      catchError((error) => {
        console.error("Erro na API:", error);
        this.errorMessage = "Ocorreu um erro ao logar na conta";
        return throwError(() => new Error("Erro ao efetuar login"));
      })
    )
  }
}
