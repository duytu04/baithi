import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PublisherManagementComponent } from './components/publisher-management/publisher-management.component';
import { AuthorManagementComponent } from './components/author-management/author-management.component';
import { BookManagementComponent } from './components/book-management/book-management.component';

const routes: Routes = [
    { path: '', redirectTo: '/books', pathMatch: 'full' },
    { path: 'publishers', component: PublisherManagementComponent },
    { path: 'authors', component: AuthorManagementComponent },
    { path: 'books', component: BookManagementComponent },
    { path: '**', redirectTo: '/books' }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
