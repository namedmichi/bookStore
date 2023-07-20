import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookDetailCardComponent } from './book-detail-card.component';

describe('BookDetailCardComponent', () => {
  let component: BookDetailCardComponent;
  let fixture: ComponentFixture<BookDetailCardComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BookDetailCardComponent],
    });
    fixture = TestBed.createComponent(BookDetailCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
