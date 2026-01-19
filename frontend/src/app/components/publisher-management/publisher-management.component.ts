import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PublisherService } from '../../services/publisher.service';
import { Publisher } from '../../models/book.model';

@Component({
    selector: 'app-publisher-management',
    templateUrl: './publisher-management.component.html',
    styleUrls: ['./publisher-management.component.css']
})
export class PublisherManagementComponent implements OnInit {
    publishers: Publisher[] = [];
    publisherForm!: FormGroup;
    editForm!: FormGroup;
    loading = false;
    errorMessage = '';
    successMessage = '';
    editingId: number | null = null;

    constructor(
        private fb: FormBuilder,
        private publisherService: PublisherService
    ) {
        this.initForms();
    }

    ngOnInit(): void {
        this.loadPublishers();
    }

    private initForms(): void {
        this.publisherForm = this.fb.group({
            name: ['', [Validators.required, Validators.minLength(2)]],
            address: [''],
            website: ['', Validators.pattern(/^https?:\/\/.+/)],
            email: ['', Validators.email],
            phone: ['']
        });

        this.editForm = this.fb.group({
            name: ['', [Validators.required, Validators.minLength(2)]],
            address: [''],
            website: ['', Validators.pattern(/^https?:\/\/.+/)],
            email: ['', Validators.email],
            phone: ['']
        });
    }

    loadPublishers(): void {
        this.loading = true;
        this.publisherService.getPublishers().subscribe({
            next: (data) => {
                this.publishers = data;
                this.loading = false;
            },
            error: (error) => {
                this.errorMessage = error.message;
                this.loading = false;
            }
        });
    }

    onSubmit(): void {
        if (this.publisherForm.invalid) {
            Object.keys(this.publisherForm.controls).forEach(key => {
                this.publisherForm.get(key)?.markAsTouched();
            });
            return;
        }

        this.loading = true;
        this.publisherService.create(this.publisherForm.value).subscribe({
            next: (publisher) => {
                this.successMessage = `Đã thêm nhà xuất bản: ${publisher.name}`;
                this.publishers.unshift(publisher);
                this.publisherForm.reset();
                this.loading = false;
                setTimeout(() => this.successMessage = '', 3000);
            },
            error: (error) => {
                this.errorMessage = error.message;
                this.loading = false;
            }
        });
    }

    startEdit(publisher: Publisher): void {
        this.editingId = publisher.id;
        this.editForm.patchValue(publisher);
    }

    saveEdit(publisher: Publisher): void {
        if (this.editForm.invalid) return;

        this.loading = true;
        this.publisherService.update(publisher.id, this.editForm.value).subscribe({
            next: (updated) => {
                const index = this.publishers.findIndex(p => p.id === updated.id);
                if (index !== -1) {
                    this.publishers[index] = updated;
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

    deletePublisher(id: number): void {
        if (!confirm('Bạn có chắc chắn muốn xóa nhà xuất bản này?')) return;

        this.loading = true;
        this.publisherService.delete(id).subscribe({
            next: () => {
                this.publishers = this.publishers.filter(p => p.id !== id);
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

    isFieldInvalid(fieldName: string, form: FormGroup = this.publisherForm): boolean {
        const field = form.get(fieldName);
        return !!(field && field.invalid && field.touched);
    }
}
