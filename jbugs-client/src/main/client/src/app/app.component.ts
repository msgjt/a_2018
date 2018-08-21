import {Component} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'JBugs';


  isUserLoggedIn(): boolean {
    return localStorage.getItem('currentUser') != null;
  }
}
