import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {UserService} from "../services/user.service";
import {UserActivation} from "../models/user-activation";
import {User} from "../models/user";
import {Subject, takeUntil} from "rxjs";
import {Router} from "@angular/router";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnDestroy {
  private ngUnsubscribe = new Subject();
  public isCodeSent = false;
  public isActivate = false;
  public isUserPresent = false;

  constructor(private formBuilder: FormBuilder, private userService: UserService, private router: Router) { }

  public emailForm = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    code: ['', [Validators.required]],
    name: ['', [Validators.required]],
    surname: ['', [Validators.required]]
  });

  public send(): void {
    const email = this.emailForm.controls['email'].value;

    this.userService.sendActivationMail(email)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
      (response: boolean) => {
        this.isCodeSent = true;
        this.isUserPresent = response;
      }
    );

    console.log(email);
  }

  public activate(): void {
    const userActivation = {
      email: this.emailForm.controls['email'].value,
      activationCode: this.emailForm.controls['code'].value
    } as UserActivation;

    this.userService.validateEmail(userActivation)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
      () => {
        sessionStorage.setItem('activate', 'true');
        if (this.isUserPresent) {
          sessionStorage.setItem('email', this.emailForm.controls['email'].value);
          this.router.navigate(['/wallet'])
          this.emailForm.reset();
        } else {
          this.isActivate = true;
          this.isCodeSent = false;
        }
      }
    );

    console.log('success');
  }

  public sendAgain(): void {
    this.userService.sendActivationMail(this.emailForm.controls['email'].value)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        () => {
          this.isCodeSent = true;
        }
      );

    console.log('send again');
  }

  public cancel(): void {
    this.isCodeSent = false;
    console.log('cancel');
  }

  public start(): void {
    const user = {
      email: this.emailForm.controls['email'].value,
      name: this.emailForm.controls['name'].value,
      surname: this.emailForm.controls['surname'].value
    } as User;

    this.userService.updateUser(user)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
      (user: User) => {
        const userJson = JSON.stringify(user);
        sessionStorage.setItem('user', userJson);

        this.router.navigate(['/wallet'])
        this.emailForm.reset();
      }
    );
  }

  public ngOnDestroy(): void {
    this.ngUnsubscribe.next(true);
    this.ngUnsubscribe.complete();
  }
}
