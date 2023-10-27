import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import EstudanteService from './estudante/estudante.service';
import LivroService from './livro/livro.service';
import EmprestimoService from './emprestimo/emprestimo.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('estudanteService') private estudanteService = () => new EstudanteService();
  @Provide('livroService') private livroService = () => new LivroService();
  @Provide('emprestimoService') private emprestimoService = () => new EmprestimoService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
