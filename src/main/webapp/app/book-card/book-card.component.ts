import { Component, Input } from '@angular/core';
import { IBook } from '../entities/book/book.model';
import { NgIf } from '@angular/common';
@Component({
  standalone: true,
  selector: 'jhi-book-card',
  templateUrl: './book-card.component.html',
  styleUrls: ['./book-card.component.scss'],
  imports: [NgIf],
})
export class BookCardComponent {
  @Input()
  book: IBook;

  constructor() {}

  ngOnInit(): void {
    //Called after the constructor, initializing input properties, and the first call to ngOnChanges.
    //Add 'implements OnInit' to the class.
  }
}
