import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EditarProdutoComponent } from './editar-produto.component';
import { ProdutoService } from '../../services/produto.service';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';

describe('EditarProdutoComponent', () => {
  let component: EditarProdutoComponent;
  let fixture: ComponentFixture<EditarProdutoComponent>;
  let produtoServiceMock: any;
  let routerMock: any;

  beforeEach(async () => {
    produtoServiceMock = jasmine.createSpyObj('ProdutoService', ['obterProdutoPorId', 'atualizarProduto']);
    routerMock = jasmine.createSpyObj('Router', ['navigate']);
    await TestBed.configureTestingModule({
      declarations: [ EditarProdutoComponent ],
      providers: [
        { provide: ProdutoService, useValue: produtoServiceMock },
        { provide: Router, useValue: routerMock },
        { provide: ActivatedRoute, useValue: { snapshot: { paramMap: { get: () => '123' } } } }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditarProdutoComponent);
    component = fixture.componentInstance;
    produtoServiceMock.obterProdutoPorId.and.returnValue(of({ codigo: '123', nome: 'Produto Teste', descricao: 'Descrição Teste', quantidade: 10 }));
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch product on init', () => {
    expect(component.produtoEmEdicao).toEqual({ codigo: '123', nome: 'Produto Teste', descricao: 'Descrição Teste', quantidade: 10 });
  });

  it('should call atualizarProduto on submit', () => {
    component.produtoEmEdicao = { codigo: '123', nome: 'Produto Teste', descricao: 'Descrição Teste', quantidade: 10 };
    component.atualizarProduto();
    expect(produtoServiceMock.atualizarProduto).toHaveBeenCalled();
    expect(routerMock.navigate).toHaveBeenCalledWith(['/menu-inicial']);
  });
});
