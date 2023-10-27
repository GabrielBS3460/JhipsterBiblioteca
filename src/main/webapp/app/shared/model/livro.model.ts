export interface ILivro {
  id?: number;
  titulo?: string | null;
  autor?: string | null;
  dataLancamento?: Date | null;
}

export class Livro implements ILivro {
  constructor(public id?: number, public titulo?: string | null, public autor?: string | null, public dataLancamento?: Date | null) {}
}
