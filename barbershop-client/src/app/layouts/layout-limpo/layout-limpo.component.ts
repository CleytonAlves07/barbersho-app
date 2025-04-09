import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-layout-limpo',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  template: `<router-outlet></router-outlet>`
})
export class LayoutLimpoComponent { }
