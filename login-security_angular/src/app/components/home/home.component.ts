import { Component, inject, OnInit } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { ActivatedRoute } from '@angular/router';
import { IUserLogged } from '../../interfaces/IUserLogged';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [HeaderComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit{
  userLogged: IUserLogged = {
    user: {
      id: '',
      name: '',
      role: {
        id: '',
        name: ''
      }
    },
    token: ''
  }

  private route: ActivatedRoute = inject(ActivatedRoute);

  ngOnInit(): void {
    const token: string | null = sessionStorage.getItem('token');
    const name: string | null = sessionStorage.getItem('name');
    const role: string | null = sessionStorage.getItem('role');
    if(token == null || name == null || role == null)
      return;

    this.userLogged.token = token;
    this.userLogged.user.name = name;
    if(role.includes('CUSTOMER')) {
      this.userLogged.user.role.name = 'Cliente';
      return;
    }

    this.userLogged.user.role.name = 'Vendedor';
  }
}
