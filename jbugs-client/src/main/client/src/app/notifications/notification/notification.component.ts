import {Component, OnInit} from '@angular/core';
import {NotificationService} from "../services/notification.service";

import {interval} from "rxjs/internal/observable/interval";
import {Notification} from "../entities/Notification";
import {ToastrService} from "ngx-toastr";
import {Router} from "@angular/router";

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent implements OnInit {

  public currentNotifications: Notification[];
  public oldNotifications: Notification[];
  public displayedAllNotifications: Notification[] = [];
  public displayedNewNotifications: Notification[] = [];
  private NOTIFICATION_DELAY: number = 5000;

  constructor(private notificationService: NotificationService, private toastrService: ToastrService,
              private router: Router) {

  }

  ngOnInit() {
    this.getOldNotifications();
    if (this.notificationService.wasInstantiated() == false) {
      this.notificationService.instantiate();
      const source = interval(this.NOTIFICATION_DELAY);
      this.displayedNewNotifications=[];

      source.subscribe(() => {
        let id = localStorage.getItem("id");
        if( id != null ) {
          this.notificationService.getNewNotifications().subscribe(notifications => {
            this.currentNotifications = notifications;
            if(this.currentNotifications.length!=0){
              this.currentNotifications.forEach(n=>{
                this.displayedNewNotifications.push(n)
              })
            }
            this.currentNotifications.forEach(n => {
              this.toastrService.info(n.type, n.message)
                .onShown.subscribe(() => {
                  let sound = new Audio("../../../assets/notificationsound.mp3");
                  sound.play();
              });
            });
          },(error)=>{
            if(error.status == 403){
              this.router.navigate(['/error']);
            }
            if(error.status == 401){
              this.router.navigate(['/norights']);
            }
          });
        }
      });
    }
  }


  getOldNotifications() {
    if (this.notificationService.wasInstantiatedForOld() == false) {
      this.notificationService.instantiateForOld();
      const source = interval(this.NOTIFICATION_DELAY);
       let subscriber=source.subscribe(() => {
        let id = localStorage.getItem("id");
        if( id != null ) {
          this.notificationService.getOldNotifications().subscribe(notifications => {
            this.oldNotifications = notifications;
            if(this.oldNotifications.length!=0){
              this.oldNotifications.forEach(n=>{
                this.displayedAllNotifications.push(n)
              });
              subscriber.unsubscribe();
              console.log(this.displayedAllNotifications)
            }
          },
            (error)=>{
              if(error.status == 403){
                localStorage.clear();
                this.router.navigate(['/login']);
              }
              if(error.status == 401){
                this.router.navigate(['/norights']);
              }
            });
        }
      });
    }
  }

  seeNotification(notification: Notification) {
    let index = this.displayedNewNotifications.findIndex(notif => notif.id == notification.id);
    this.displayedNewNotifications.splice(index,1);
    this.displayedAllNotifications.push(notification);
  }
}
