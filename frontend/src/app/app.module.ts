import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PublisherManagementComponent } from './components/publisher-management/publisher-management.component';
import { AuthorManagementComponent } from './components/author-management/author-management.component';
import { BookManagementComponent } from './components/book-management/book-management.component';

@NgModule({
    declarations: [
        AppComponent,
        PublisherManagementComponent,
        AuthorManagementComponent,
        BookManagementComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule { }
