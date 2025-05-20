import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';

@Component({
  selector: 'app-contact-form',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, ButtonModule, CommonModule,
    InputTextModule, DialogModule,
    InputNumberModule,
    InputTextareaModule,],
  templateUrl: './contact-form.component.html',
  styleUrl: './contact-form.component.scss'
})
export class ContactFormComponent {
  editForm: FormGroup;
  messageEnvoye = false;
  constructor(private _fb: FormBuilder) {
    this.editForm = this._fb.group({
      email: [null, [Validators.required, Validators.email]],
      message: [null, [Validators.required, Validators.maxLength(300)]]
    });
  }

  onFormSubmit() {
    this.messageEnvoye = true;
  }
  public closeDialog() {
    this.messageEnvoye = false;
  }

}
