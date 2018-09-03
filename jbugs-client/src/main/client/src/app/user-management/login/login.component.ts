import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {LSKEY, TOKENKEY, User, UserService} from "../services/user.service";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {ToastrService} from "ngx-toastr";
import {Error, Information, Warning} from "../../communication/communication.component";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  userModel: User;
  loggedIn = false;
  baseURL = 'http://localhost:8080/jbugs/rest';
  RecaptchaOptions = {theme: 'clean'};
  recaptchaResponse: any;
  errorOccurred: boolean;
  errorMessage: Error;
  usernameError: boolean;
  usernameInformation: Information;
  passwordInformation: Information;
  failedLoginWarning: Warning;
  failedCounter: number;
  items = ['a', 'b', 'c', 'd', 'e', 'f'];
  @ViewChild('scrollMe') scrollContainer: ElementRef;
  @ViewChild('parallax') parallax: ElementRef;

  @ViewChild('container-login-username') containerUsername: ElementRef;

  constructor(private userService: UserService, private router: Router, private http: HttpClient,
              private toastrService: ToastrService) {
    this.userModel = {
      id: 0,
      firstName: '',
      lastName: '',
      isActive: false,
      phoneNumber: '',
      email: '',
      roles: [],
      username: '',
      password: ''
    };
    this.loggedIn = userService.isLoggedIn();
    this.errorOccurred = false;
    this.usernameInformation = {
      message: "Username was generated based on your first and last names. Please contact the administrator if forgotten",
      display: false
    };
    this.passwordInformation = {
      message: "Your password was given by the administrator, please contact him if forgotten.",
      display: false
    };
    this.failedLoginWarning = {
      message: "After 5 unsuccessful logins your account will be disabled and your administrator notified.",
      recommendation: null,
      display: false
    };
    this.errorMessage = null;
    this.failedCounter = 0;
  }

  ngOnInit() {
    this.usernameError = false;
    //this.scrollDownRightEvent(5, 2,1, 200, 100);

    this.parallax.nativeElement.scrollTop = 100;
    this.parallax.nativeElement.scrollLeft = 200;

  }

  delay(ms: number) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }

  startScrollDown(msBetween: number, heightIncrement: number, distance: number) {
    this.delay(msBetween).then(() => {
        this.parallax.nativeElement.scrollTop += heightIncrement;
        if (this.parallax.nativeElement.scrollTop < distance && this.errorOccurred == false) {
          this.startScrollDown(msBetween, heightIncrement, distance);
        }
      }
    );
  }

  scrollDownRightEvent(msBetween: number, xIncrement: number,yIncrement: number, distanceX: number, distanceY: number) {
    this.delay(msBetween).then(() => {
      let xIndex = this.parallax.nativeElement.scrollLeft;
      let yIndex = this.parallax.nativeElement.scrollTop;
      if (xIndex < distanceX) {
        this.parallax.nativeElement.scrollLeft += xIncrement;
      }
      if (yIndex < distanceY) {
        this.parallax.nativeElement.scrollTop += yIncrement;
      }
      if (yIndex == distanceY && xIndex == distanceX) {
        return;
      } else {
        this.scrollDownRightEvent(msBetween, xIncrement,yIncrement, distanceX, distanceY);
      }
    });
  }


  startScrollRight(msBetween: number, widthIncrement: number, distance: number) {
    this.delay(msBetween).then(() => {
        this.parallax.nativeElement.scrollLeft += widthIncrement;
        if (this.parallax.nativeElement.scrollTop < distance && this.errorOccurred == false) {
          this.startScrollRight(msBetween, widthIncrement, distance);
        }
      }
    );
  }


  toDegrees(angle: number) {
    return angle * (180 / Math.PI);
  }

  toRadians(angle) {
    return angle * (Math.PI / 180);
  }

  angleDegrees: number = 0;

  circlePoint(xCenter: number, yCenter: number, radius: number): number[] {
    let angleRadians = this.toRadians(this.angleDegrees);
    let xNew = Math.cos(angleRadians) * radius + xCenter;
    let yNew = Math.sin(angleRadians) * radius + yCenter;
    this.angleDegrees += 1;
    return [xNew, yNew];
  }

  circleAnimationStarted: boolean = false;

  startCircleScroll(msBetween: number, xCenter: number, yCenter: number, radius: number) {
    this.delay(msBetween).then(() => {
        this.circleAnimationStarted = true;
        let [xNew, yNew] = this.circlePoint(xCenter, yCenter, radius);
        this.parallax.nativeElement.scrollTop = yNew;
        this.parallax.nativeElement.scrollLeft = xNew;
        if (this.errorOccurred == true &&
          this.parallax.nativeElement.scrollLeft > 15 &&
          this.parallax.nativeElement.scrollLeft < 16 &&
          this.parallax.nativeElement.scrollTop > 46 &&
          this.parallax.nativeElement.scrollTop < 47) {
          this.circleAnimationStarted = false;
          return;
        }else{
          this.startCircleScroll(msBetween, xCenter, yCenter, radius);
        }
      }
    );
  }


  submitForm() {
    this.errorOccurred = false;
    this.loggedIn = true;

    if(this.circleAnimationStarted == false)
      this.startCircleScroll(30, 100, 100, 100);
     this.http.post(this.baseURL + '/captcha', this.recaptchaResponse).subscribe((response) => {
       if (response['success'] == true) {
         this.delay(2000).then(() => {
             this.userService.validateUserCredentials(this.userModel.username,
               this.userModel.password).subscribe(
               (response) => {
                 this.login(response.token);
                 localStorage.setItem("id", response.id);
                 this.loggedIn = true;
                 this.router.navigate(['./user_profile']);
               },
               (error) => {
                 this.errorOccurred = true;
                 this.errorMessage = error['error'];
                 if (this.errorMessage.id == 1404)
                   this.failedCounter++;

                 if (this.failedCounter > 1) {
                   this.usernameInformation.display = true;
                   this.passwordInformation.display = true;
                 }

                 if (this.failedCounter > 2)
                   this.failedLoginWarning.display = true;
                 this.loggedIn = false;
               }
             );

           }
         );
       }
     });
  }

  login(token: string) {
    localStorage.setItem(LSKEY, this.userModel.username);
    localStorage.setItem(TOKENKEY, token);
    this.loggedIn = true;
    this.getUsersPermissions(this.userModel.username);
  }

  resolved(captchaResponse: string) {
    this.recaptchaResponse = captchaResponse;
  }

  getUsersPermissions(username: string) {
    this.userService.getUsersPermissions(username).subscribe(
      (list) => {
        for (let i = 0; i < list.length; i++) {
          localStorage.setItem(list[i].type, "1");
        }
      }
    )
  }
}
