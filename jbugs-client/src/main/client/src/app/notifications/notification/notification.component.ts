import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
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
  public static notifSize: number = 0;
  private NOTIFICATION_DELAY: number = 5000;
  @ViewChild('btnReadAll') btnReadAll: ElementRef;

  constructor(private notificationService: NotificationService, private toastrService: ToastrService, private router: Router) {

  }

  ngOnInit() {
    this.getOldNotifications();
  }

  clear(){
    this.oldNotifications = [];
    this.currentNotifications = [];

    this.displayedAllNotifications = [];
    this.displayedNewNotifications = [];
    this.notificationService.deinstantiate();
    this.notificationService.deinstantiateForOld();

    let clickRealAll: HTMLElement = this.btnReadAll.nativeElement as HTMLElement;
    clickRealAll.click();
  }

  getNewNotifications() {
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
                this.displayedNewNotifications.push(n);

                NotificationComponent.notifSize = this.displayedNewNotifications.length;
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

  toHide(notification) {
    return this.displayedNewNotifications.findIndex(n => n.id == notification.id) == -1;
  }

  getOldNotifications() {
    console.log("INSTANTIATED FOR OLD: " + this.notificationService.wasInstantiatedForOld());
    console.log("ALL NOTIFICATIONS LENGTH: " + this.displayedAllNotifications.length);
    if (this.notificationService.wasInstantiatedForOld() == false &&
        this.displayedAllNotifications.length == 0) {
      this.notificationService.instantiateForOld();
      const source = interval(500);
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
            }
              subscriber.unsubscribe();
          },
            (error)=>{
              if(error.status == 403){
                localStorage.clear();
                this.router.navigate(['/login']);
              }
              if(error.status == 401){
                this.router.navigate(['/norights']);
              }
            },
            () => this.getNewNotifications());
        }
      });
    }
  }

  seeNotification(notification: Notification) {
    let index = this.displayedNewNotifications.findIndex(notif => notif.id == notification.id);
    this.displayedNewNotifications.splice(index,1);
    this.displayedAllNotifications.push(notification);
    NotificationComponent.notifSize--;
  }

  seeAllNotifications() {
    this.displayedNewNotifications.forEach(notification => this.displayedAllNotifications.push(notification));
    this.displayedNewNotifications.splice(0, this.displayedNewNotifications.length);
    NotificationComponent.notifSize = 0;
  }

  static size(): number {
    return NotificationComponent.notifSize;
  }
}
