import {Component, OnInit} from '@angular/core';
import {NotificationService} from "../services/notification.service";

import {interval} from "rxjs/internal/observable/interval";
import {Notification} from "../entities/Notification";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent implements OnInit {

  public currentNotifications: Notification[];
  public oldNotifications: Notification[];
  public displayedOldNotifications: Notification[]=[];
  public displayedNewNotifications: Notification[]=[];
  private NOTIFICATION_DELAY: number = 5000;

  constructor(private notificationService: NotificationService, private toastrService: ToastrService) {

  }

  ngOnInit() {
    this.getOldNotifications()
    if (this.notificationService.wasInstantiated() == false) {
      this.notificationService.instantiate();
      const source = interval(this.NOTIFICATION_DELAY);

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
                this.displayedOldNotifications.push(n)
              })
              subscriber.unsubscribe()
              console.log(this.displayedOldNotifications)
            }
          });
        }
      });
    }
  }
}
