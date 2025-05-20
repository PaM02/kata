import { CommonModule } from "@angular/common";
import { Component, OnInit, inject, signal } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { Product } from "app/products/data-access/product.model";
import { ProductsService } from "app/products/data-access/products.service";
import { ProductFormComponent } from "app/products/ui/product-form/product-form.component";
import { MessageService } from "primeng/api";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import { InputNumberModule } from "primeng/inputnumber";
import { InputTextModule } from "primeng/inputtext";
import { RatingModule } from 'primeng/rating';
import { ToastModule } from 'primeng/toast';


const emptyProduct: Product = {
  id: 0,
  code: "",
  name: "",
  description: "",
  image: "",
  category: "",
  price: 0,
  quantity: 0,
  internalReference: "",
  shellId: 0,
  inventoryStatus: "INSTOCK",
  rating: 0,
  createdAt: 0,
  updatedAt: 0,
};

@Component({
  selector: "app-product-list",
  templateUrl: "./product-list.component.html",
  styleUrls: ["./product-list.component.scss"],
  standalone: true,
  imports: [DataViewModule, CardModule, InputNumberModule,
    InputTextModule, ToastModule, RatingModule, FormsModule, ButtonModule, DialogModule, ProductFormComponent, CommonModule],
})
export class ProductListComponent implements OnInit {
  private readonly productsService = inject(ProductsService);
  public readonly products = this.productsService.products;
  searchText!: string;
  public isDialogVisible = false;
  public isCreation = false;
  public readonly editedProduct = signal<Product>(emptyProduct);
  private _cart: Product[] = [];

  constructor(private messageService: MessageService) { }

  ngOnInit() {
    this.productsService.get().subscribe();
  }


  public onCreate() {
    this.isCreation = true;
    this.isDialogVisible = true;
    this.editedProduct.set(emptyProduct);
  }

  public onUpdate(product: Product) {
    this.isCreation = false;
    this.isDialogVisible = true;
    this.editedProduct.set(product);
  }

  public onDelete(product: Product) {
    this.productsService.delete(product.id).subscribe();
  }

  public onSave(product: Product) {
    if (this.isCreation) {
      this.productsService.create(product).subscribe();
    } else {
      this.productsService.update(product).subscribe();
    }
    this.closeDialog();
  }

  public addToCart(product: Product) {
    const saved = localStorage.getItem('cart');
    this._cart = saved ? JSON.parse(saved) : [];

    const existingItem = this._cart.find(item => item.id === product.id);

    if (existingItem) {
      existingItem.quantity += product.quantity;
    } else {
      this._cart.push(product);
    }

    localStorage.setItem('cart', JSON.stringify(this._cart));
    this.showMessage();
  }



  get filteredProducts() {
    const all = this.products();
    if (!this.searchText?.trim()) return all;
    const term = this.searchText.toLowerCase();
    return all.filter(p =>
      p.name.toLowerCase().includes(term) ||
      p.category.toLowerCase().includes(term)
    );
  }

  showMessage() {
    this.messageService.add({ severity: 'info', summary: 'Info', detail: 'Le produit a été ajouté au panier avec succès', life: 3000 });
  }


  public onCancel() {
    this.closeDialog();
  }

  private closeDialog() {
    this.isDialogVisible = false;
  }


}
