import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthorService } from '../../services/author.service';
import { Author } from '../../models/book.model';

@Component({
    selector: 'app-author-management',
    templateUrl: './author-management.component.html',
    styleUrls: ['./author-management.component.css']
})
export class AuthorManagementComponent implements OnInit {
    authors: Author[] = [];
    authorForm!: FormGroup;
    editForm!: FormGroup;
    loading = false;
    errorMessage = '';
    successMessage = '';
    editingId: number | null = null;

    constructor(
        private fb: FormBuilder,
        private authorService: AuthorService
    ) {
        this.initForms();
    }

    ngOnInit(): void {
        this.loadAuthors();
    }

    private initForms(): void {
        this.authorForm = this.fb.group({
            fullName: ['', [Validators.required, Validators.minLength(2)]],
            nationality: ['', Validators.required],
            biography: [''],
            website: ['', Validators.pattern(/^https?:\/\/.+/)]
        });

        this.editForm = this.fb.group({
            fullName: ['', [Validators.required, Validators.minLength(2)]],
            nationality: ['', Validators.required],
            biography: [''],
            website: ['', Validators.pattern(/^https?:\/\/.+/)]
        });
    }

    loadAuthors(): void {
        this.loading = true;
        this.authorService.getAll().subscribe({
            next: (data) => {
                this.authors = data;
                this.loading = false;
            },
            error: (error) => {
                this.errorMessage = error.message;
                this.loading = false;
            }
        });
    }

    onSubmit(): void {
        if (this.authorForm.invalid) {
            Object.keys(this.authorForm.controls).forEach(key => {
                this.authorForm.get(key)?.markAsTouched();
            });
            return;
        }

        this.loading = true;
        this.authorService.create(this.authorForm.value).subscribe({
            next: (author) => {
                this.successMessage = `Đã thêm tác giả: ${author.fullName}`;
                this.authors.unshift(author);
                this.authorForm.reset();
                this.loading = false;
                setTimeout(() => this.successMessage = '', 3000);
            },
            error: (error) => {
                this.errorMessage = error.message;
                this.loading = false;
            }
        });
    }

    startEdit(author: Author): void {
        this.editingId = author.id;
        this.editForm.patchValue(author);
    }

    saveEdit(author: Author): void {
        if (this.editForm.invalid) return;

        this.loading = true;
        this.authorService.update(author.id, this.editForm.value).subscribe({
            next: (updated) => {
                const index = this.authors.findIndex(a => a.id === updated.id);
                if (index !== -1) {
                    this.authors[index] = updated;
                }
                this.successMessage = 'Cập nhật thành công!';
                this.cancelEdit();
                this.loading = false;
                setTimeout(() => this.successMessage = '', 3000);
            },
            error: (error) => {
                this.errorMessage = error.message;
                this.loading = false;
            }
        });
    }

    cancelEdit(): void {
        this.editingId = null;
        this.editForm.reset();
    }

    deleteAuthor(id: number): void {
        if (!confirm('Bạn có chắc chắn muốn xóa tác giả này?')) return;

        this.loading = true;
        this.authorService.delete(id).subscribe({
            next: () => {
                this.authors = this.authors.filter(a => a.id !== id);
                this.successMessage = 'Đã xóa thành công!';
                this.loading = false;
                setTimeout(() => this.successMessage = '', 3000);
            },
            error: (error) => {
                this.errorMessage = error.message;
                this.loading = false;
            }
        });
    }

    isFieldInvalid(fieldName: string, form: FormGroup = this.authorForm): boolean {
        const field = form.get(fieldName);
        return !!(field && field.invalid && field.touched);
    }
}
