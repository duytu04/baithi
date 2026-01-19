import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <div class="app-container">
      <header class="app-header glass-card">
        <div class="header-content">
          <div class="logo-section">
            <div class="logo-icon">📚</div>
            <div>
              <h1 class="app-title">Book Management</h1>
              <p class="app-subtitle">Hệ thống quản lý sách chuyên nghiệp</p>
            </div>
          </div>
          <nav class="nav-links">
            <a routerLink="/publishers" routerLinkActive="active" class="nav-link">🏢 Publishers</a>
            <a routerLink="/authors" routerLinkActive="active" class="nav-link">✍️ Authors</a>
            <a routerLink="/books" routerLinkActive="active" class="nav-link">📚 Books</a>
          </nav>
        </div>
      </header>
      
      <main class="app-main">
        <router-outlet></router-outlet>
      </main>
      
      <footer class="app-footer">
        <p>© 2026 Book Management System. Built with Angular & Spring Boot.</p>
      </footer>
    </div>
  `,
  styles: [`
    .app-container {
      min-height: 100vh;
      display: flex;
      flex-direction: column;
    }

    .app-header {
      margin: var(--spacing-xl);
      margin-bottom: var(--spacing-md);
      animation: fadeIn 0.6s ease-out;
    }

    .header-content {
      display: flex;
      justify-content: space-between;
      align-items: center;
      gap: var(--spacing-lg);
      flex-wrap: wrap;
    }

    .logo-section {
      display: flex;
      align-items: center;
      gap: var(--spacing-md);
    }

    .logo-icon {
      font-size: 3rem;
      animation: float 3s ease-in-out infinite;
    }

    @keyframes float {
      0%, 100% { transform: translateY(0); }
      50% { transform: translateY(-10px); }
    }

    .app-title {
      margin: 0;
      font-size: 2rem;
      background: var(--primary-gradient);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
    }

    .app-subtitle {
      margin: 0;
      color: var(--text-secondary);
      font-size: 0.9rem;
    }

    .header-stats {
      display: flex;
      gap: var(--spacing-md);
    }

    .nav-links {
      display: flex;
      gap: var(--spacing-md);
      align-items: center;
    }

    .nav-link {
      padding: var(--spacing-sm) var(--spacing-md);
      border-radius: var(--radius-md);
      text-decoration: none;
      color: var(--text-secondary);
      font-weight: 600;
      font-size: 0.9rem;
      transition: all 0.3s ease;
      border: 1px solid transparent;
    }

    .nav-link:hover {
      color: var(--primary-light);
      background: rgba(102, 126, 234, 0.1);
      border-color: rgba(102, 126, 234, 0.3);
    }

    .nav-link.active {
      color: white;
      background: var(--primary-gradient);
      border-color: var(--primary);
      box-shadow: 0 2px 8px rgba(102, 126, 234, 0.3);
    }

    .stat-badge {
      display: flex;
      align-items: center;
      gap: var(--spacing-sm);
      padding: var(--spacing-sm) var(--spacing-md);
      background: rgba(102, 126, 234, 0.1);
      border: 1px solid rgba(102, 126, 234, 0.3);
      border-radius: var(--radius-full);
      font-size: 0.875rem;
      font-weight: 600;
      color: var(--primary-light);
    }

    .stat-icon {
      font-size: 1.2rem;
    }

    .app-main {
      flex: 1;
      padding: 0 var(--spacing-xl) var(--spacing-xl);
    }

    .app-footer {
      text-align: center;
      padding: var(--spacing-lg);
      color: var(--text-muted);
      font-size: 0.875rem;
      border-top: 1px solid var(--glass-border);
      background: var(--glass-bg);
      backdrop-filter: blur(10px);
    }

    @media (max-width: 768px) {
      .app-header {
        margin: var(--spacing-md);
      }

      .header-content {
        flex-direction: column;
        text-align: center;
      }

      .logo-section {
        flex-direction: column;
      }

      .app-main {
        padding: 0 var(--spacing-md) var(--spacing-md);
      }
    }
  `]
})
export class AppComponent {
  title = 'Book Management System';
}
