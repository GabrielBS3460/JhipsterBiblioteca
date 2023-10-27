import { IEstudante } from '@/shared/model/estudante.model';
import { ILivro } from '@/shared/model/livro.model';

export interface IEmprestimo {
  id?: number;
  dataEmprestimo?: Date | null;
  estudante?: IEstudante | null;
  livro?: ILivro | null;
}

export class Emprestimo implements IEmprestimo {
  constructor(
    public id?: number,
    public dataEmprestimo?: Date | null,
    public estudante?: IEstudante | null,
    public livro?: ILivro | null
  ) {}
}
