import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { ICreateUser } from '../interfaces/ICreateUser';
import { catchError, Observable, throwError } from 'rxjs';
import { IUser } from '../interfaces/IUSer';
import { environment } from '../../environments/environment.development';
import { ILoginUser } from '../interfaces/ILoginUser';
import { IUserLogged } from '../interfaces/IUserLogged';

@Injectable({
  providedIn: 'root',
})
export class UserAuthenticationService {
  private http: HttpClient = inject(HttpClient);

  createUser(createUser: ICreateUser): Observable<IUser> {
    return this.http.post<IUser>(`${environment.apiUrl}/users`, createUser).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error(`Informações incompletas, erro: ${error.status}`);
        return throwError(() => new Error("Informações incompletas"));
      })
    );
  }

  authenticateUser(loginUser: ILoginUser): Observable<IUserLogged> {
    return this.http.post<IUserLogged>(`${environment.apiUrl}/users/login`, loginUser).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error(`Credencias incorretas, erro: ${error.status}`);
        return throwError(() => new Error(`Credencias incorretas`));
      })
    )
  }
}
