export interface IEstudante {
  id?: number;
  primeiroNome?: string;
  sobrenome?: string;
  email?: string;
  idade?: number;
}

export class Estudante implements IEstudante {
  constructor(public id?: number, public primeiroNome?: string, public sobrenome?: string, public email?: string, public idade?: number) {}
}
