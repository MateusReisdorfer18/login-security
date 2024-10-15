import { NgClass, NgFor } from '@angular/common';
import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { ICreateUser } from '../../../interfaces/ICreateUser';
import { FormsModule } from '@angular/forms';
import { UserAuthenticationService } from '../../../services/user-authentication.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [RouterLink, NgFor, FormsModule, NgClass],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  tipos: string[] = ["Cliente", "Vendedor"];
  canSubmit: boolean = false;
  createUser: ICreateUser = {
    name: '',
    email: '',
    password: '',
    role: 'Cliente'
  };
  passwordConfirmation: string = '';

  private userAuthentication: UserAuthenticationService = inject(UserAuthenticationService);
  private router: Router = inject(Router);

  validateForms(): void {
    if(
      this.createUser.name.length !== 0 &&
      this.createUser.email.length !== 0 &&
      this.createUser.password.length !== 0 &&
      this.passwordConfirmation.length !== 0 &&
      this.createUser.role.length !== 0
    ) {
      this.canSubmit = true;
      return;
    }

    this.canSubmit = false
  }

  register(): void {
    if(this.passwordConfirmation !== this.createUser.password)
      return;

    console.log("As senhas sao iguais")
    this.userAuthentication.createUser(this.createUser).subscribe({
      next: user => {
        console.log(user);
        this.router.navigate(['login']);
      },
      error: error => {
        console.log(error);
      }
    })
  }
}
