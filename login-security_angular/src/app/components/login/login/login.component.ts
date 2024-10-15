import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { NgxMaskDirective, NgxMaskPipe } from 'ngx-mask';
import { ILoginUser } from '../../../interfaces/ILoginUser';
import { FormsModule } from '@angular/forms';
import { UserAuthenticationService } from '../../../services/user-authentication.service';
import { HttpErrorResponse } from '@angular/common/http';
import { NgClass } from '@angular/common';
import { HeaderComponent } from "../../header/header.component";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [NgxMaskDirective, NgxMaskPipe, RouterLink, FormsModule, NgClass, HeaderComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  loginUser: ILoginUser = {
    email: '',
    password: ''
  }
  canSubmit: boolean = false;

  private userAuthentication: UserAuthenticationService = inject(UserAuthenticationService);
  private router: Router = inject(Router);

  validateForms(): void {
    if(
      this.loginUser.email.length !== 0 &&
      this.loginUser.password.length !== 0
    ) {
      this.canSubmit = true;
      return;
    }

    this.canSubmit = false;
  }

  login(): void {
    this.userAuthentication.authenticateUser(this.loginUser).subscribe({
      next: user => {
        sessionStorage.setItem('token', user.token);
        sessionStorage.setItem('name', user.user.name);
        sessionStorage.setItem('role', user.user.role.name);
        this.router.navigate([`home`]);
      },
      error: (error: HttpErrorResponse) => {
        let message: string = error.message;
        if(message.includes('403'))
          console.log('Erro de autenticacao/autorizacao');
      }
    })
  }
}
