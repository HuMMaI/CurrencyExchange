import {NgModule} from "@angular/core";
import {UserComponent} from "./user.component";
import {UserRoutingModule} from "./user-routing.module";
import {CommonModule} from "@angular/common";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatCardModule} from "@angular/material/card";
import {ReactiveFormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";

@NgModule({
  declarations: [UserComponent],
  imports: [UserRoutingModule, CommonModule, MatFormFieldModule, MatInputModule, MatCardModule, ReactiveFormsModule, MatButtonModule]
})
export class UserModule {

}
